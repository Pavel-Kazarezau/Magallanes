package io.kazarezau.magallanes.trip.service;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.core.CountriesService;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripAttendeesAddedEvent;
import io.kazarezau.magallanes.trip.TripCancelledEvent;
import io.kazarezau.magallanes.trip.TripCreatedEvent;
import io.kazarezau.magallanes.trip.TripRescheduledEvent;
import io.kazarezau.magallanes.trip.TripStatus;
import io.kazarezau.magallanes.trip.TripUnavailableException;
import io.kazarezau.magallanes.trip.UnsupportedPointException;
import io.kazarezau.magallanes.trip.validation.TripAttendeeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock
    private CountriesService countriesService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private TripAttendeeValidator tripAttendeeValidator;
    @InjectMocks
    private TripServiceImpl tripService;

    @Test
    void shouldCreateTrip() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip.Essence essence = new Trip.Essence();
        essence.setName("Trip 1");
        essence.setCreatorId(creatorId);
        essence.setAttendees(List.of(additionalAttendeeId));
        essence.setCountry("Poland");
        essence.setCity("Warsaw");
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        when(countriesService.containsCountryAndCity(any(), any())).thenReturn(true);

        tripService.createTrip(essence);
        verify(eventPublisher).publishEvent(any(TripCreatedEvent.class));
    }

    @Test
    void shouldFailIfPointIsUnsupported() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip.Essence essence = new Trip.Essence();
        essence.setName("Trip 1");
        essence.setCreatorId(creatorId);
        essence.setAttendees(List.of(additionalAttendeeId));
        essence.setCountry("Poland");
        essence.setCity("Warsaw");
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        when(countriesService.containsCountryAndCity(any(), any())).thenReturn(false);

        final UnsupportedPointException exception = assertThrows(UnsupportedPointException.class,
                () -> tripService.createTrip(essence));
        assertEquals("This country and city does not exist", exception.getMessage());
        verify(eventPublisher, never()).publishEvent(any(TripCreatedEvent.class));
    }

    @Test
    void rescheduleTrip() {
        final Trip.Essence essence = new Trip.Essence();
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(true);

        tripService.rescheduleTrip(tripSpy, essence);
        verify(eventPublisher).publishEvent(any(TripRescheduledEvent.class));
        verify(tripSpy).reschedule(any());
    }

    @Test
    void shouldFailRescheduleTripIfUserIsNotCreator() {
        final Trip.Essence essence = new Trip.Essence();
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(false);

        assertThrows(TripUnavailableException.class, () -> tripService.rescheduleTrip(tripSpy, essence));
        verify(eventPublisher, never()).publishEvent(any(TripRescheduledEvent.class));
        verify(tripSpy, never()).reschedule(any());
    }

    @Test
    void cancelTrip() {
        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(true);

        tripService.cancelTrip(tripSpy);
        verify(eventPublisher).publishEvent(any(TripCancelledEvent.class));
        verify(tripSpy).cancel();
    }

    @Test
    void shouldFailCancelTripIfUserIsNotCreator() {
        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(false);

        assertThrows(TripUnavailableException.class, () -> tripService.cancelTrip(tripSpy));
        verify(eventPublisher, never()).publishEvent(any(TripCancelledEvent.class));
        verify(tripSpy, never()).cancel();
    }

    @Test
    void updateAttendeesWithAddingAttendee() {
        final Trip.Essence essence = new Trip.Essence();
        essence.setAttendees(List.of(UUID.randomUUID()));

        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(true);

        tripService.updateAttendees(tripSpy, essence);
        verify(eventPublisher).publishEvent(any(TripAttendeesAddedEvent.class));
        verify(tripSpy).updateAttendees(any());
    }

    @Test
    void shouldFailUpdateAttendees() {
        final Trip.Essence essence = new Trip.Essence();
        essence.setAttendees(List.of(UUID.randomUUID()));

        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip tripSpy = Mockito.spy(trip);
        when(tripAttendeeValidator.isCurrentUserACreator(any())).thenReturn(false);

        assertThrows(TripUnavailableException.class, () -> tripService.cancelTrip(tripSpy));
        verify(eventPublisher, never()).publishEvent(any(TripAttendeesAddedEvent.class));
        verify(tripSpy, never()).updateAttendees(any());
    }

    @Test
    void shouldFindTripById() {
        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        when(tripAttendeeValidator.isCurrentUserInAttendees(any())).thenReturn(true);

        assertDoesNotThrow(() -> tripService.findById(trip));
    }

    @Test
    void shouldFailIfFindTripByIdOperatingFromCurrentUserNotInAttendees() {
        final UUID creatorId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1, 1));
        trip.setEndDate(LocalDate.of(2001, 1, 1));
        trip.setStatus(TripStatus.COMPLETED);

        when(tripAttendeeValidator.isCurrentUserInAttendees(any())).thenReturn(false);

        assertThrows(TripUnavailableException.class, () -> tripService.findById(trip));
    }
}
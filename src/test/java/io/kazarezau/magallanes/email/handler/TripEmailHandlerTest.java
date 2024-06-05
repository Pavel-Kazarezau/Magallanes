package io.kazarezau.magallanes.email.handler;

import io.kazarezau.magallanes.email.EmailService;
import io.kazarezau.magallanes.email.UserEmailExtractor;
import io.kazarezau.magallanes.email.ics.IcsService;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripAttendeesAddedEvent;
import io.kazarezau.magallanes.trip.TripAttendeesRemovedEvent;
import io.kazarezau.magallanes.trip.TripCancelledEvent;
import io.kazarezau.magallanes.trip.TripCreatedEvent;
import io.kazarezau.magallanes.trip.TripRescheduledEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripEmailHandlerTest {

    @Mock
    private IcsService icsService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserEmailExtractor userEmailExtractor;
    @InjectMocks
    private TripEmailHandler handler;


    @Test
    void onTripCreation() {
        final Trip trip = new Trip();
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        final TripCreatedEvent event = TripCreatedEvent.builder().trip(trip).build();

        when(icsService.generateIcs(any(), any(), any())).thenReturn(null);
        when(userEmailExtractor.getEmails(any())).thenReturn(null);
        handler.onTripCreation(event);
        verify(emailService).send(any(), any());
    }

    @Test
    void onTripReschedule() {
        final Trip trip = new Trip();
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        final TripRescheduledEvent event = TripRescheduledEvent.builder().trip(trip).build();

        when(icsService.generateIcs(any(), any(), any())).thenReturn(null);
        when(userEmailExtractor.getEmails(any())).thenReturn(null);
        handler.onTripReschedule(event);
        verify(emailService).send(any(), any());
    }

    @Test
    void onTripCancel() {
        final Trip trip = new Trip();
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        final TripCancelledEvent event = TripCancelledEvent.builder().trip(trip).build();

        when(userEmailExtractor.getEmails(any())).thenReturn(null);
        handler.onTripCancel(event);
        verify(emailService).send(any(), any());
    }

    @Test
    void onAttendeeAdded() {
        final Trip trip = new Trip();
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        final TripAttendeesAddedEvent event = TripAttendeesAddedEvent.builder().trip(trip).build();

        when(icsService.generateIcs(any(), any(), any())).thenReturn(null);
        when(userEmailExtractor.getEmails(any())).thenReturn(null);
        handler.onAttendeeAdded(event);
        verify(emailService).send(any(), any());
    }

    @Test
    void onAttendeeRemoved() {
        final Trip trip = new Trip();
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        final TripAttendeesRemovedEvent event = TripAttendeesRemovedEvent.builder().trip(trip).build();

        when(userEmailExtractor.getEmails(any())).thenReturn(null);
        handler.onAttendeeRemoved(event);
        verify(emailService).send(any(), any());
    }
}
package io.kazarezau.magallanes.trip;

import io.kazarezau.magallanes.trip.input.command.CancelTripCommand;
import io.kazarezau.magallanes.trip.input.command.CreateTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindWithFilteringTripCommand;
import io.kazarezau.magallanes.trip.input.command.UpdateTripCommand;
import io.kazarezau.magallanes.trip.input.mapper.TripEssenceMapper;
import io.kazarezau.magallanes.trip.output.repository.TripFilteringCriteria;
import io.kazarezau.magallanes.trip.output.repository.TripRepository;
import io.kazarezau.magallanes.trip.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripApplicationTest {

    @Mock
    private TripEssenceMapper tripEssenceMapper;
    @Mock
    private TripService tripService;
    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TripApplication tripApplication;

    @Test
    void findTrips() {
        tripApplication.findTrips(new FindWithFilteringTripCommand(
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                "Poland",
                List.of(UUID.randomUUID())
        ), Pageable.unpaged());
        verify(tripRepository).findAllWithCriteria(any(TripFilteringCriteria.class), eq(Pageable.unpaged()));
        verify(tripService).findTrips(any());
    }

    @Test
    void findTrip() {
        final UUID id = UUID.randomUUID();

        tripApplication.findTrip(new FindTripCommand(id));

        verify(tripRepository).findById(id);
        verify(tripService).findById(any());
    }

    @Test
    void createTrip() {
        final CreateTripCommand command = new CreateTripCommand();

        final Trip.Essence essence = new Trip.Essence();
        when(tripEssenceMapper.createTripCommandToTripEssence(command)).thenReturn(essence);

        tripApplication.createTrip(command);
        verify(tripService).createTrip(essence);
        verify(tripRepository).save(any());
    }

    @Test
    void reschedule() {
        final UpdateTripCommand command = new UpdateTripCommand();

        final Trip.Essence essence = new Trip.Essence();
        when(tripEssenceMapper.updateTripCommandToTripEssence(command)).thenReturn(essence);
        when(tripService.rescheduleTrip(any(), eq(essence))).thenReturn(new Trip());

        tripApplication.reschedule(command);

        verify(tripRepository).findById(any());
        verify(tripService).rescheduleTrip(any(), eq(essence));
        verify(tripRepository).update(any(Trip.class));
    }

    @Test
    void cancel() {
        final CancelTripCommand command = new CancelTripCommand();
        command.setId(UUID.randomUUID());
        when(tripService.cancelTrip(any())).thenReturn(new Trip());

        tripApplication.cancel(command);
        verify(tripRepository).findById(command.getId());
        verify(tripService).cancelTrip(any());
        verify(tripRepository).update(any(Trip.class));
    }

    @Test
    void updateAttendees() {
        final UpdateTripCommand command = new UpdateTripCommand();
        final Trip.Essence essence = new Trip.Essence();
        when(tripEssenceMapper.updateTripCommandToTripEssence(command)).thenReturn(essence);
        when(tripService.updateAttendees(any(), eq(essence))).thenReturn(new Trip());

        tripApplication.updateAttendees(command);

        verify(tripRepository).findById(any());
        verify(tripService).updateAttendees(any(), eq(essence));
        verify(tripRepository).update(any(Trip.class));
    }
}
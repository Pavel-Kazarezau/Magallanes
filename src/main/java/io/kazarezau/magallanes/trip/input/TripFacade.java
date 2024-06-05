package io.kazarezau.magallanes.trip.input;

import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.input.command.CancelTripCommand;
import io.kazarezau.magallanes.trip.input.command.CreateTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindWithFilteringTripCommand;
import io.kazarezau.magallanes.trip.input.command.UpdateTripCommand;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Pageable;

import java.util.List;

@PrimaryPort
public interface TripFacade {

    List<Trip> findTrips(FindWithFilteringTripCommand command, Pageable pageable);

    Trip findTrip(FindTripCommand command);

    Trip createTrip(CreateTripCommand command);

    Trip reschedule(UpdateTripCommand command);

    Trip cancel(CancelTripCommand command);

    Trip updateAttendees(final UpdateTripCommand command);
}

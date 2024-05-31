package io.kazarezau.magallanes.trip.input.rest.controller;

import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripApplication;
import io.kazarezau.magallanes.trip.input.command.CancelTripCommand;
import io.kazarezau.magallanes.trip.input.command.CreateTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindWithFilteringTripCommand;
import io.kazarezau.magallanes.trip.input.command.UpdateTripCommand;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@PrimaryAdapter
@RestController
@RequestMapping("trip")
@RequiredArgsConstructor
public class TripController {

    private static final String ID_OF_TRIP_MUST_BE_PROVIDED = "ID of Trip must be provided";
    private final TripApplication tripApplication;

    @GetMapping
    public List<Trip> findAll(
            final @RequestParam(required = false) LocalDate startDateFrom,
            final @RequestParam(required = false) LocalDate startDateTo,
            final @RequestParam(required = false) LocalDate endDateFrom,
            final @RequestParam(required = false) LocalDate endDateTo,
            final @RequestParam(required = false) String country,
            final @RequestParam(name = "attendeesId", required = false) List<UUID> attendeesIds,
            final Pageable pageable) {

        final FindWithFilteringTripCommand command = new FindWithFilteringTripCommand(
                startDateFrom, startDateTo, endDateFrom, endDateTo, country, attendeesIds);
        return tripApplication.findTrips(command, pageable);
    }

    @GetMapping("/{id}")
    public Trip findTrip(final @PathVariable UUID id) {
        final FindTripCommand command = new FindTripCommand(id);
        return tripApplication.findTrip(command);
    }

    @PostMapping
    public Trip cancellTrip(final @RequestBody CreateTripCommand command) {
        return tripApplication.createTrip(command);
    }

    @PutMapping("/{id}/reschedule")
    public Trip resheduleTrip(final @RequestBody @Validated UpdateTripCommand command,
                              final @PathVariable UUID id) {
        command.setId(Objects.requireNonNull(id, ID_OF_TRIP_MUST_BE_PROVIDED));
        return tripApplication.reschedule(command);
    }

    @PutMapping("/{id}/update_attendees")
    public Trip updateAttendees(final @RequestBody @Validated UpdateTripCommand command,
                                final @PathVariable UUID id) {
        command.setId(Objects.requireNonNull(id, ID_OF_TRIP_MUST_BE_PROVIDED));
        return tripApplication.updateAttendees(command);
    }

    @PutMapping("/{id}/cancel")
    public Trip cancellTrip(final @RequestBody @Validated CancelTripCommand command,
                            final @PathVariable UUID id) {
        command.setId(Objects.requireNonNull(id, ID_OF_TRIP_MUST_BE_PROVIDED));
        return tripApplication.cancel(command);
    }
}

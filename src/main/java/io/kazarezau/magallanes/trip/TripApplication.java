package io.kazarezau.magallanes.trip;

import io.kazarezau.magallanes.trip.input.TripFacade;
import io.kazarezau.magallanes.trip.input.command.CancelTripCommand;
import io.kazarezau.magallanes.trip.input.command.CreateTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindTripCommand;
import io.kazarezau.magallanes.trip.input.command.FindWithFilteringTripCommand;
import io.kazarezau.magallanes.trip.input.command.UpdateTripCommand;
import io.kazarezau.magallanes.trip.input.mapper.TripEssenceMapper;
import io.kazarezau.magallanes.trip.output.repository.TripFilteringCriteria;
import io.kazarezau.magallanes.trip.output.repository.TripRepository;
import io.kazarezau.magallanes.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Application
@Service
@RequiredArgsConstructor
public class TripApplication implements TripFacade {

    private final TripEssenceMapper tripEssenceMapper;
    private final TripService tripService;
    private final TripRepository tripRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Trip> findTrips(final FindWithFilteringTripCommand command, final Pageable pageable) {
        final TripFilteringCriteria tripFilteringCriteria = new TripFilteringCriteria(
                command.getStartDateFrom(),
                command.getStartDateTo(),
                command.getEndDateFrom(),
                command.getEndDateTo(),
                command.getCountry(),
                command.getAttendeesIds()
        );
        final List<Trip> searchedTrips = tripRepository.findAllWithCriteria(tripFilteringCriteria, pageable);
        return tripService.findTrips(searchedTrips);
    }

    @Override
    @Transactional(readOnly = true)
    public Trip findTrip(final FindTripCommand command) {
        final Trip trip = tripRepository.findById(Objects.requireNonNull(command.getId()));
        return tripService.findById(trip);
    }

    @Override
    @Transactional
    public Trip createTrip(final CreateTripCommand command) {
        final Trip.Essence essence = tripEssenceMapper.createTripCommandToTripEssence(command);

        final Trip trip = tripService.createTrip(essence);
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip reschedule(final UpdateTripCommand command) {
        final Trip.Essence essence = tripEssenceMapper.updateTripCommandToTripEssence(command);

        final Trip trip = tripRepository.findById(command.getId());
        final Trip rescheduledTrip = tripService.rescheduleTrip(trip, essence);

        return tripRepository.update(rescheduledTrip);
    }

    @Override
    @Transactional
    public Trip cancel(final CancelTripCommand command) {
        final Trip trip = tripRepository.findById(command.getId());
        final Trip cancelledTrip = tripService.cancelTrip(trip);

        return tripRepository.update(cancelledTrip);
    }

    @Override
    @Transactional
    public Trip updateAttendees(final UpdateTripCommand command) {
        final Trip.Essence essence = tripEssenceMapper.updateTripCommandToTripEssence(command);

        final Trip trip = tripRepository.findById(command.getId());
        final Trip updatedTrip = tripService.updateAttendees(trip, essence);

        return tripRepository.update(updatedTrip);
    }
}

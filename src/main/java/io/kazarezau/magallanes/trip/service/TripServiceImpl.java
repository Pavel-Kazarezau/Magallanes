package io.kazarezau.magallanes.trip.service;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.core.CountriesService;
import io.kazarezau.magallanes.trip.UnsupportedPointException;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripUnavailableException;
import io.kazarezau.magallanes.trip.TripAttendeesAddedEvent;
import io.kazarezau.magallanes.trip.TripAttendeesRemovedEvent;
import io.kazarezau.magallanes.trip.TripCancelledEvent;
import io.kazarezau.magallanes.trip.TripCreatedEvent;
import io.kazarezau.magallanes.trip.TripRescheduledEvent;
import io.kazarezau.magallanes.trip.validation.TripAttendeeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final CountriesService countriesService;
    private final ApplicationEventPublisher eventPublisher;
    private final TripAttendeeValidator tripAttendeeValidator;

    @Override
    public Trip createTrip(final Trip.Essence essence) {
        final String country = Objects.requireNonNull(essence.getCountry());
        final String city = Objects.requireNonNull(essence.getCity());

        if (!countriesService.containsCountryAndCity(country, city)) {
            throw new UnsupportedPointException("This country and city does not exist");
        }

        final Trip createdTrip = new Trip(essence);

        eventPublisher.publishEvent(TripCreatedEvent.builder().trip(createdTrip).build());
        return createdTrip;
    }

    @Override
    public Trip rescheduleTrip(final Trip trip, final Trip.Essence essence) {
        if (!tripAttendeeValidator.isCurrentUserACreator(trip)) {
            throw new TripUnavailableException();
        }
        trip.reschedule(essence);
        eventPublisher.publishEvent(TripRescheduledEvent.builder().trip(trip).build());
        return trip;
    }

    @Override
    public Trip cancelTrip(final Trip trip) {
        if (!tripAttendeeValidator.isCurrentUserACreator(trip)) {
            throw new TripUnavailableException();
        }
        trip.cancel();
        eventPublisher.publishEvent(TripCancelledEvent.builder().trip(trip).build());
        return trip;
    }

    @Override
    public Trip updateAttendees(final Trip trip, final Trip.Essence essence) {
        if (!tripAttendeeValidator.isCurrentUserACreator(trip)) {
            throw new TripUnavailableException();
        }
        final Set<User.UserId> newlyAddedAttendees = this.getNewlyAddedAttendees(trip, essence);
        final Set<User.UserId> removedAttendees = this.getRemovedAttendees(trip, essence);

        trip.updateAttendees(essence);

        if (!newlyAddedAttendees.isEmpty()) {
            eventPublisher.publishEvent(
                    TripAttendeesAddedEvent.builder()
                            .attendees(newlyAddedAttendees)
                            .trip(trip)
                            .build());
        }
        if (!removedAttendees.isEmpty()) {
            eventPublisher.publishEvent(
                    TripAttendeesRemovedEvent.builder()
                            .attendees(removedAttendees)
                            .trip(trip)
                            .build());
        }
        return trip;
    }

    @Override
    public Trip findById(final Trip trip) {
        if (!tripAttendeeValidator.isCurrentUserInAttendees(trip)) {
            throw new TripUnavailableException();
        }
        return trip;
    }

    @Override
    public List<Trip> findTrips(final List<Trip> searchedTrips) {
        return searchedTrips.stream()
                .filter(tripAttendeeValidator::isCurrentUserInAttendees)
                .toList();
    }

    private Set<User.UserId> getNewlyAddedAttendees(final Trip trip, final Trip.Essence essence) {
        final Set<User.UserId> newlyAddedAttendees = essence.getAttendees().stream()
                .map(User.UserId::new)
                .collect(Collectors.toSet());
        newlyAddedAttendees.removeAll(trip.getAttendees());
        return newlyAddedAttendees;
    }

    private Set<User.UserId> getRemovedAttendees(final Trip trip, final Trip.Essence essence) {
        final HashSet<User.UserId> oldAttendees = new HashSet<>(trip.getAttendees());
        oldAttendees.removeAll(essence.getAttendees().stream()
                .map(User.UserId::new)
                .collect(Collectors.toSet()));
        return oldAttendees;
    }
}

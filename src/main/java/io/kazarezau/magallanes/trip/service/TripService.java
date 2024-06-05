package io.kazarezau.magallanes.trip.service;

import io.kazarezau.magallanes.trip.Trip;

import java.util.List;

public interface TripService {

    Trip createTrip(Trip.Essence essence);

    Trip rescheduleTrip(Trip trip, Trip.Essence essence);

    Trip cancelTrip(Trip trip);

    Trip updateAttendees(Trip trip, Trip.Essence essence);

    Trip findById(Trip trip);

    List<Trip> findTrips(List<Trip> searchedTrips);
}

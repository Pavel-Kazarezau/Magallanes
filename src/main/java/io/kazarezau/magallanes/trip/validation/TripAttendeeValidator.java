package io.kazarezau.magallanes.trip.validation;

import io.kazarezau.magallanes.trip.Trip;

public interface TripAttendeeValidator {

    boolean isCurrentUserACreator(Trip trip);

    boolean isCurrentUserInAttendees(Trip trip);
}

package io.kazarezau.magallanes.trip.validation;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.trip.Trip;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TripAttendeeValidatorImpl implements TripAttendeeValidator {

    @Override
    public boolean isCurrentUserACreator(final Trip trip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User principal = (User) auth.getPrincipal();
        return principal.getId().equals(trip.getCreatorId());
    }

    @Override
    public boolean isCurrentUserInAttendees(final Trip trip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User principal = (User) auth.getPrincipal();
        return trip.getAttendees().contains(principal.getId());
    }
}

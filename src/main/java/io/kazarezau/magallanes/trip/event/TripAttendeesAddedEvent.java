package io.kazarezau.magallanes.trip.event;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.trip.Trip;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TripAttendeesAddedEvent {

    private final Trip trip;

    private final Set<User.UserId> attendees;

}

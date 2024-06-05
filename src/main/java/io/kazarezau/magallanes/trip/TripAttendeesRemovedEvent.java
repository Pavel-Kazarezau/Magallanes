package io.kazarezau.magallanes.trip;

import io.kazarezau.magallanes.account.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TripAttendeesRemovedEvent {

    private final Trip trip;

    private final Set<User.UserId> attendees;
}

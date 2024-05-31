package io.kazarezau.magallanes.trip.event;

import io.kazarezau.magallanes.trip.Trip;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripRescheduledEvent {

    private final Trip trip;
}

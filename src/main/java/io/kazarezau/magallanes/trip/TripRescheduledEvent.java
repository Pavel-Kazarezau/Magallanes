package io.kazarezau.magallanes.trip;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripRescheduledEvent {

    private final Trip trip;
}

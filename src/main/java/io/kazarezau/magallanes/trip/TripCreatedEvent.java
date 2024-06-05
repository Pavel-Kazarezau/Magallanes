package io.kazarezau.magallanes.trip;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripCreatedEvent {

    private final Trip trip;
}

package io.kazarezau.magallanes.trip.input.command;

import lombok.Data;

import java.util.UUID;

@Data
public class CancelTripCommand {
    private UUID id;
}

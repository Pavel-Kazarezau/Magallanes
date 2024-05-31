package io.kazarezau.magallanes.trip.input.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FindTripCommand {
    private UUID id;
}

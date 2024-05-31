package io.kazarezau.magallanes.trip.input.command;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateTripCommand {
    private String name;
    private UUID creatorId;
    private List<UUID> attendees;
    private String country;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
}

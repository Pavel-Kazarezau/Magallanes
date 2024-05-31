package io.kazarezau.magallanes.trip.input.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateTripCommand {
    @JsonIgnore
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<UUID> attendees;
}

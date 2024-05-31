package io.kazarezau.magallanes.trip.input.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FindWithFilteringTripCommand {

    private LocalDate startDateFrom;

    private LocalDate startDateTo;

    private LocalDate endDateFrom;

    private LocalDate endDateTo;

    private String country;

    private List<UUID> attendeesIds;
}

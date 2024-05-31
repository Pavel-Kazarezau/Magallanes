package io.kazarezau.magallanes.trip.output.repository;

import io.kazarezau.magallanes.trip.Trip;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TripRepository {

    Trip findById(UUID id);

    Trip save(Trip trip);

    Trip update(Trip trip);

    void update(List<Trip> trips);

    List<Trip> findAllPlannedWithCurrentDateAsStartDate();

    List<Trip> findAllOverdueActive();

    List<Trip> findAllWithCriteria(TripFilteringCriteria criteria, Pageable pageable);
}

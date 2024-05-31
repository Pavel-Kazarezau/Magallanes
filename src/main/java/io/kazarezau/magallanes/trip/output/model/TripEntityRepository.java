package io.kazarezau.magallanes.trip.output.model;

import io.kazarezau.magallanes.trip.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TripEntityRepository extends JpaRepository<TripEntity, UUID> {

    List<TripEntity> findTripEntitiesByStartDateAndStatusIs(final LocalDate currentTime, final TripStatus status);

    List<TripEntity> findTripEntitiesByEndDateBeforeAndStatusIs(final LocalDate currentTime, final TripStatus status);
}

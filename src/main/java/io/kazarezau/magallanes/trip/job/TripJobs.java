package io.kazarezau.magallanes.trip.job;

import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.output.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TripJobs {

    private final TripRepository tripRepository;

    @Scheduled(cron = "${scheduled.trip.activation}")
    @Transactional
    public void activateTripsStartedToday() {
        final List<Trip> trips = tripRepository.findAllPlannedWithCurrentDateAsStartDate();
        trips.forEach(Trip::activate);

        tripRepository.update(trips);
    }

    @Scheduled(cron = "${scheduled.trip.completing}")
    @Transactional
    public void completeTripsEndedToday() {
        final List<Trip> trips = tripRepository.findAllOverdueActive();
        trips.forEach(Trip::complete);

        tripRepository.update(trips);
    }
}

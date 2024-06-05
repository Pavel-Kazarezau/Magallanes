package io.kazarezau.magallanes.trip;

import io.kazarezau.magallanes.account.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {

    @Test
    void create() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();

        final Trip.Essence essence = new Trip.Essence();
        essence.setName("Trip 1");
        essence.setCreatorId(creatorId);
        essence.setAttendees(List.of(additionalAttendeeId));
        essence.setCountry("Poland");
        essence.setCity("Warsaw");
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.ACTIVE);
        Trip trip = new Trip(essence);

        assertEquals(expected, trip);
    }

    @Test
    void rescheduleToActive() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1,1));
        trip.setEndDate(LocalDate.of(2001, 1,1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip.Essence essence = new Trip.Essence();
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MAX);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.ACTIVE);

        trip.reschedule(essence);
        assertEquals(expected, trip);
    }

    @Test
    void rescheduleToPlanned() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1,1));
        trip.setEndDate(LocalDate.of(2001, 1,1));
        trip.setStatus(TripStatus.COMPLETED);

        final Trip.Essence essence = new Trip.Essence();
        essence.setStartDate(LocalDate.MAX);
        essence.setEndDate(LocalDate.MAX);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MAX);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.PLANNED);

        trip.reschedule(essence);
        assertEquals(expected, trip);
    }

    @Test
    void rescheduleToCompleted() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.of(2000, 1,1));
        trip.setEndDate(LocalDate.of(2001, 1,1));
        trip.setStatus(TripStatus.ACTIVE);

        final Trip.Essence essence = new Trip.Essence();
        essence.setStartDate(LocalDate.MIN);
        essence.setEndDate(LocalDate.MIN);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MIN);
        expected.setStatus(TripStatus.COMPLETED);

        trip.reschedule(essence);
        assertEquals(expected, trip);
    }

    @Test
    void updateAttendees() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        trip.setStatus(TripStatus.ACTIVE);

        final UUID thirdAttendeeId = UUID.randomUUID();
        final Trip.Essence essence = new Trip.Essence();
        essence.setAttendees(List.of(creatorId, additionalAttendeeId, thirdAttendeeId));

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(
                new User.UserId(creatorId),
                new User.UserId(additionalAttendeeId),
                new User.UserId(thirdAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.ACTIVE);

        trip.updateAttendees(essence);
        assertEquals(expected, trip);
    }

    @Test
    void cancel() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        trip.setStatus(TripStatus.ACTIVE);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(
                new User.UserId(creatorId),
                new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.CANCELLED);

        trip.cancel();
        assertEquals(expected, trip);
    }

    @Test
    void activate() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        trip.setStatus(TripStatus.PLANNED);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(
                new User.UserId(creatorId),
                new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.ACTIVE);

        trip.activate();
        assertEquals(expected, trip);
    }

    @Test
    void complete() {
        final UUID creatorId = UUID.randomUUID();
        final UUID additionalAttendeeId = UUID.randomUUID();
        final Trip trip = new Trip();
        trip.setName("Trip 1");
        trip.setCreatorId(new User.UserId(creatorId));
        trip.setAttendees(Set.of(new User.UserId(creatorId), new User.UserId(additionalAttendeeId)));
        trip.setPoint(new Trip.Point("Poland", "Warsaw"));
        trip.setStartDate(LocalDate.MIN);
        trip.setEndDate(LocalDate.MAX);
        trip.setStatus(TripStatus.PLANNED);

        final Trip expected = new Trip();
        expected.setName("Trip 1");
        expected.setCreatorId(new User.UserId(creatorId));
        expected.setAttendees(Set.of(
                new User.UserId(creatorId),
                new User.UserId(additionalAttendeeId)));
        expected.setPoint(new Trip.Point("Poland", "Warsaw"));
        expected.setStartDate(LocalDate.MIN);
        expected.setEndDate(LocalDate.MAX);
        expected.setStatus(TripStatus.COMPLETED);

        trip.complete();
        assertEquals(expected, trip);
    }
}
package io.kazarezau.magallanes.trip;

import io.kazarezau.magallanes.account.User;
import io.kazarezau.magallanes.core.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AggregateRoot
@Getter
@Setter
@NoArgsConstructor
public final class Trip extends BaseEntity<Trip.TripId> {

    private String name;

    private User.UserId creatorId;

    private Set<User.UserId> attendees = new HashSet<>();

    private Point point;

    private LocalDate startDate;

    private LocalDate endDate;

    private TripStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public record TripId(UUID id) implements Identifier {
    }

    @ValueObject
    public record Point(String country, String city) {
    }

    public Trip(final Trip.Essence essence) {
        super(new TripId(UUID.randomUUID()));
        this.name = Objects.requireNonNull(essence.getName());
        this.creatorId = new User.UserId(Objects.requireNonNull(essence.creatorId));
        this.setAttendees(essence);
        this.point = new Point(Objects.requireNonNull(essence.country), Objects.requireNonNull(essence.city));
        this.startDate = Objects.requireNonNull(essence.startDate);
        this.endDate = Objects.requireNonNull(essence.endDate);
        this.status = resolveStatus();
    }

    private void setAttendees(final Essence essence) {
        if (!CollectionUtils.isEmpty(essence.attendees)) {
            this.attendees = essence.attendees.stream()
                    .map(User.UserId::new)
                    .collect(Collectors.toSet());
        } else {
            this.attendees = new HashSet<>();
        }
        this.attendees.add(new User.UserId(essence.creatorId));
    }

    public void reschedule(final Essence essence) {
        this.startDate = Objects.requireNonNull(essence.getStartDate());
        this.endDate = Objects.requireNonNull(essence.getEndDate());
        this.status = resolveStatus();
    }

    public void updateAttendees(final Essence essence) {
        this.setAttendees(essence);
    }

    public void cancel() {
        this.status = TripStatus.CANCELLED;
    }

    public void activate() {
        this.status = TripStatus.ACTIVE;
    }

    public void complete() {
        this.status = TripStatus.COMPLETED;
    }

    private TripStatus resolveStatus() {
        if (this.getStartDate().isAfter(LocalDate.now())) {
            return TripStatus.PLANNED;
        } else {
            if (this.getEndDate().isBefore(LocalDate.now())) {
                return TripStatus.COMPLETED;
            } else {
                return TripStatus.ACTIVE;
            }
        }
    }

    @Data
    public static class Essence {
        private String name;
        private UUID creatorId;
        private List<UUID> attendees;
        private String country;
        private String city;
        private LocalDate startDate;
        private LocalDate endDate;
        private TripStatus tripStatus;
    }
}

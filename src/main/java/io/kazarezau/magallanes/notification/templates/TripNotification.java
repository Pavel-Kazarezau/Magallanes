package io.kazarezau.magallanes.notification.templates;

import io.kazarezau.magallanes.trip.Trip;
import lombok.Getter;

@Getter
public class TripNotification extends NotificationTemplate {

    private final Trip trip;

    public TripNotification(final String title, final Trip trip) {
        super(title);
        this.trip = trip;
    }
}

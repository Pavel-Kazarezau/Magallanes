package io.kazarezau.magallanes.notification.listener;

import io.kazarezau.magallanes.notification.templates.NotificationTitles;
import io.kazarezau.magallanes.notification.templates.TripNotification;
import io.kazarezau.magallanes.trip.event.TripCreatedEvent;
import io.kazarezau.magallanes.trip.event.TripRescheduledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushNotificationListener {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    @ApplicationModuleListener
    void onTripCreation(TripCreatedEvent event) {
        final TripNotification notification = new TripNotification(NotificationTitles.TRIP_CREATED, event.getTrip());
        send(notification);
    }

    @ApplicationModuleListener
    void onTripReschedule(TripRescheduledEvent event) {
        final TripNotification notification = new TripNotification(NotificationTitles.TRIP_RESCHEDULED, event.getTrip());
        send(notification);
    }

    private void send(final TripNotification notification) {
        final String exchange = rabbitProperties.getTemplate().getExchange();
        rabbitTemplate.convertAndSend(exchange, "", notification);
    }
}

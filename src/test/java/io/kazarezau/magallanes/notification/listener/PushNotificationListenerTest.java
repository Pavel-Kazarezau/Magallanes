package io.kazarezau.magallanes.notification.listener;

import io.kazarezau.magallanes.notification.templates.TripNotification;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripCreatedEvent;
import io.kazarezau.magallanes.trip.TripRescheduledEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PushNotificationListenerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private RabbitProperties rabbitProperties;
    @InjectMocks
    private PushNotificationListener listener;

    @Test
    void onTripCreation() {
        final TripCreatedEvent event = TripCreatedEvent.builder().trip(new Trip()).build();
        final RabbitProperties.Template template = new RabbitProperties.Template();
        template.setExchange("test.exchange");

        when(rabbitProperties.getTemplate()).thenReturn(template);

        listener.onTripCreation(event);

        verify(rabbitTemplate).convertAndSend(eq("test.exchange"), eq(""), any(TripNotification.class));
    }

    @Test
    void onTripReschedule() {
        final TripRescheduledEvent event = TripRescheduledEvent.builder().trip(new Trip()).build();
        final RabbitProperties.Template template = new RabbitProperties.Template();
        template.setExchange("test.exchange");

        when(rabbitProperties.getTemplate()).thenReturn(template);

        listener.onTripReschedule(event);

        verify(rabbitTemplate).convertAndSend(eq("test.exchange"), eq(""), any(TripNotification.class));
    }
}
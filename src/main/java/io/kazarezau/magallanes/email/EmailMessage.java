package io.kazarezau.magallanes.email;

import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@Builder
@Getter
public class EmailMessage {

    @Getter
    @AllArgsConstructor
    public enum Template {
        TRIP_CREATED("trip-created.ftl"),
        TRIP_RESCHEDULED("trip-rescheduled.ftl"),
        TRIP_CANCELLED("trip-cancelled.ftl"),
        TRIP_ATTENDEE_REMOVED("trip-attendee-removed.ftl");

        private final String name;
    }

    @NonNull
    private String subject;
    @NonNull
    private Map<String, Object> args;
    private ByteArrayDataSource file;
    @NonNull
    private Template template;
}

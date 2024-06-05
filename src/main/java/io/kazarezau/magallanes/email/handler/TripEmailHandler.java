package io.kazarezau.magallanes.email.handler;

import io.kazarezau.magallanes.email.EmailMessage;
import io.kazarezau.magallanes.email.EmailService;
import io.kazarezau.magallanes.email.UserEmailExtractor;
import io.kazarezau.magallanes.email.ics.IcsService;
import io.kazarezau.magallanes.trip.Trip;
import io.kazarezau.magallanes.trip.TripAttendeesAddedEvent;
import io.kazarezau.magallanes.trip.TripAttendeesRemovedEvent;
import io.kazarezau.magallanes.trip.TripCancelledEvent;
import io.kazarezau.magallanes.trip.TripCreatedEvent;
import io.kazarezau.magallanes.trip.TripRescheduledEvent;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TripEmailHandler {

    private final IcsService icsService;
    private final EmailService emailService;
    private final UserEmailExtractor userEmailExtractor;

    @ApplicationModuleListener
    void onTripCreation(TripCreatedEvent event) {
        final Trip trip = event.getTrip();
        final String city = trip.getPoint().city();
        final String country = trip.getPoint().country();
        final LocalDate startDate = trip.getStartDate();
        final LocalDate endDate = trip.getEndDate();

        final String subject = String.format("Invitation to visit %s, %s ", city, country);

        final ByteArrayDataSource icsFile = icsService.generateIcs(startDate, endDate, subject);
        final EmailMessage message = EmailMessage.builder()
                .subject(subject)
                .args(Map.of(
                        "city", city,
                        "country", country,
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .file(icsFile)
                .template(EmailMessage.Template.TRIP_CREATED)
                .build();
        final List<String> recipients = userEmailExtractor.getEmails(trip.getAttendees());
        emailService.send(recipients, message);
    }

    @ApplicationModuleListener
    void onTripReschedule(TripRescheduledEvent event) {
        final Trip trip = event.getTrip();
        final String city = trip.getPoint().city();
        final String country = trip.getPoint().country();
        final LocalDate startDate = trip.getStartDate();
        final LocalDate endDate = trip.getEndDate();

        final String subject = String.format("Reschedule the Trip to %s, %s ", city, country);

        final ByteArrayDataSource icsFile = icsService.generateIcs(startDate, endDate, subject);
        final EmailMessage message = EmailMessage.builder()
                .subject(subject)
                .args(Map.of(
                        "city", city,
                        "country", country,
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .file(icsFile)
                .template(EmailMessage.Template.TRIP_RESCHEDULED)
                .build();

        final List<String> recipients = userEmailExtractor.getEmails(trip.getAttendees());
        emailService.send(recipients, message);
    }

    @ApplicationModuleListener
    void onTripCancel(TripCancelledEvent event) {
        final Trip trip = event.getTrip();
        final String city = trip.getPoint().city();
        final String country = trip.getPoint().country();
        final LocalDate startDate = trip.getStartDate();
        final LocalDate endDate = trip.getEndDate();

        final String subject = String.format("Cancellation of Trip to %s, %s ", city, country);
        final EmailMessage message = EmailMessage.builder()
                .subject(subject)
                .args(Map.of(
                        "city", city,
                        "country", country,
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .template(EmailMessage.Template.TRIP_CANCELLED)
                .build();

        final List<String> recipients = userEmailExtractor.getEmails(trip.getAttendees());
        emailService.send(recipients, message);
    }

    @ApplicationModuleListener
    void onAttendeeAdded(TripAttendeesAddedEvent event) {
        final Trip trip = event.getTrip();
        final String city = trip.getPoint().city();
        final String country = trip.getPoint().country();
        final LocalDate startDate = trip.getStartDate();
        final LocalDate endDate = trip.getEndDate();

        final String subject = String.format("Invitation to visit %s, %s ", city, country);

        final ByteArrayDataSource icsFile = icsService.generateIcs(startDate, endDate, subject);
        final EmailMessage message = EmailMessage.builder()
                .subject(subject)
                .args(Map.of(
                        "city", city,
                        "country", country,
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .file(icsFile)
                .template(EmailMessage.Template.TRIP_CREATED)
                .build();

        final List<String> recipients = userEmailExtractor.getEmails(event.getAttendees());
        emailService.send(recipients, message);
    }

    @ApplicationModuleListener
    void onAttendeeRemoved(TripAttendeesRemovedEvent event) {
        final Trip trip = event.getTrip();
        final String city = trip.getPoint().city();
        final String country = trip.getPoint().country();
        final LocalDate startDate = trip.getStartDate();
        final LocalDate endDate = trip.getEndDate();

        final String subject = String.format("Cancellation of Trip to %s, %s ", city, country);

        final EmailMessage message = EmailMessage.builder()
                .subject(subject)
                .args(Map.of(
                        "city", city,
                        "country", country,
                        "startDate", startDate,
                        "endDate", endDate
                ))
                .template(EmailMessage.Template.TRIP_ATTENDEE_REMOVED)
                .build();
        final List<String> recipients = userEmailExtractor.getEmails(event.getAttendees());
        emailService.send(recipients, message);
    }
}

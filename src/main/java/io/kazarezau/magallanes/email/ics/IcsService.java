package io.kazarezau.magallanes.email.ics;

import jakarta.mail.util.ByteArrayDataSource;
import lombok.SneakyThrows;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class IcsService {

    @SneakyThrows
    public ByteArrayDataSource generateIcs(final LocalDate startDate,
                                           final LocalDate endDate,
                                           final String eventName) {

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        final Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        final VEvent event = new VEvent(
                new net.fortuna.ical4j.model.Date(start),
                new net.fortuna.ical4j.model.Date(end), eventName);
        event.getProperties().add(new Uid(UUID.randomUUID().toString()));
        event.validate();

        final PropertyList<Property> calendarProperties = new PropertyList<>();
        calendarProperties.add(new ProdId());
        calendarProperties.add(Version.VERSION_2_0);
        calendarProperties.add(new Uid(UUID.randomUUID().toString()));

        final ComponentList<CalendarComponent> calendarComponents = new ComponentList<>();
        calendarComponents.add(event);

        final Calendar calendar = new Calendar(calendarProperties, calendarComponents);

        CalendarOutputter outputted = new CalendarOutputter();
        outputted.output(calendar, stream);

        return new ByteArrayDataSource(stream.toByteArray(), "application/ical");
    }
}

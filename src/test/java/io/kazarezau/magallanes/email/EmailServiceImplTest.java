package io.kazarezau.magallanes.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import io.kazarezau.magallanes.email.config.MailConfig;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MailConfig mailConfig;
    @Mock
    private Configuration configuration;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void shouldSendEmail() throws IOException {

        final EmailMessage message = new EmailMessage(
                "Test subject",
                Map.of(),
                null,
                EmailMessage.Template.TRIP_CREATED);

        when(configuration.getTemplate("trip-created.ftl"))
                .thenReturn(new Template("", new StringReader(""), new Configuration(new Version("2.3.0"))));
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getDefaultInstance(new Properties())));
        when(mailConfig.getUsername()).thenReturn("magallanes@gmail.com");

        emailService.send(List.of("john@mail.com", "michael@mail.com"), message);

        verify(mailSender).send(any(MimeMessage.class));
    }
}
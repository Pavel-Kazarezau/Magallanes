package io.kazarezau.magallanes.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.kazarezau.magallanes.email.config.MailConfig;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;
    private final Configuration configuration;

    @SneakyThrows
    @Override
    public void send(final List<String> to, final EmailMessage emailMessage) {
        final InternetAddress[] addresses = to.stream()
                .map(el -> {
                    try {
                        return new InternetAddress(el);
                    } catch (AddressException e) {
                        throw new EmailException("Error converting email address", e);
                    }
                })
                .toArray(InternetAddress[]::new);

        final Template template = configuration.getTemplate(emailMessage.getTemplate().getName());
        final StringWriter stringWriter = new StringWriter();
        template.process(emailMessage.getArgs(), stringWriter);
        final String body = stringWriter.toString();

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
        mimeMessage.setFrom(mailConfig.getUsername());
        mimeMessage.setSubject(emailMessage.getSubject());
        mimeMessage.setText(body);

        Optional.ofNullable(emailMessage.getFile())
                .ifPresent(file -> {
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                        helper.setText(body);
                        helper.addAttachment("invite.ics", file);
                    } catch (MessagingException e) {
                        throw new EmailException("Failed loading ics invitation file", e);
                    }
                });
        send(mimeMessage);
    }

    private void send(final MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }
}

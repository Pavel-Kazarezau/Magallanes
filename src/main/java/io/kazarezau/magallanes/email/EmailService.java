package io.kazarezau.magallanes.email;

import java.util.List;

public interface EmailService {

    void send(List<String> to, EmailMessage emailMessage);
}

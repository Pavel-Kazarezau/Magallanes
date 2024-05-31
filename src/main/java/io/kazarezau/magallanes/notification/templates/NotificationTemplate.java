package io.kazarezau.magallanes.notification.templates;

import lombok.Getter;

@Getter
public abstract class NotificationTemplate {

    protected final String title;

    protected NotificationTemplate(final String title) {
        this.title = title;
    }
}

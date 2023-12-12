package ua.cdu.edu.vu.event.notification.api.domain;

public record Notification(String clientId,
                           long eventId,
                           String key) {
}

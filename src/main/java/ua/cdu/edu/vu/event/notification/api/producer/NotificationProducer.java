package ua.cdu.edu.vu.event.notification.api.producer;

import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;
import ua.cdu.edu.vu.event.notification.api.domain.Notification;
import ua.cdu.edu.vu.event.notification.api.exception.SendNotificationException;

public interface NotificationProducer {

    void sendNotification(Notification notification, ApiClient apiClient) throws SendNotificationException;
}

package ua.cdu.edu.vu.event.notification.api.mapper;

import org.mapstruct.Mapper;
import ua.cdu.edu.vu.event.notification.api.domain.Notification;
import ua.cdu.edu.vu.event.notification.api.dto.NotificationPayload;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NotificationMapper {

    NotificationPayload convertToDto(Notification notification);
}

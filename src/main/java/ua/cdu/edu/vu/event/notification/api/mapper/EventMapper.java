package ua.cdu.edu.vu.event.notification.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.cdu.edu.vu.event.notification.api.dto.EventRequest;
import ua.cdu.edu.vu.event.notification.api.dto.Event;
import ua.cdu.edu.vu.event.notification.api.domain.entity.EventEntity;
import ua.cdu.edu.vu.event.notification.api.domain.Notification;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = ReminderMapper.class)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "notified", ignore = true)
    EventEntity convertToDomain(EventRequest dto);

    Event convertToDto(EventEntity entity);

    @Mapping(target = "eventId", source = "id")
    @Mapping(target = "clientId", source = "createdBy")
    Notification convertToNotification(EventEntity entity);
}

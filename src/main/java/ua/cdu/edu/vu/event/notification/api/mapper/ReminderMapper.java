package ua.cdu.edu.vu.event.notification.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.cdu.edu.vu.event.notification.api.dto.Reminder;
import ua.cdu.edu.vu.event.notification.api.domain.entity.ReminderEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ReminderMapper {

    @Mapping(target = "id", ignore = true)
    ReminderEntity convertToDomain(Reminder reminder);
}

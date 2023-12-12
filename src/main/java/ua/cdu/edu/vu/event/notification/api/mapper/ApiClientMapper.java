package ua.cdu.edu.vu.event.notification.api.mapper;

import org.mapstruct.Mapper;
import ua.cdu.edu.vu.event.notification.api.domain.entity.ApiClientEntity;
import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ApiClientMapper {

    ApiClient convertToDto(ApiClientEntity entity);
}

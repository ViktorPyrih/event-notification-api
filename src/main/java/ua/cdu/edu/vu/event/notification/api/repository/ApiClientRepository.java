package ua.cdu.edu.vu.event.notification.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.cdu.edu.vu.event.notification.api.domain.entity.ApiClientEntity;
import ua.cdu.edu.vu.event.notification.api.model.Role;

import java.util.List;

@Repository
public interface ApiClientRepository extends JpaRepository<ApiClientEntity, String> {

    List<ApiClientEntity> findAllByRole(Role role);
}

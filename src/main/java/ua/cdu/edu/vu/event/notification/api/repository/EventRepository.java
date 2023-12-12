package ua.cdu.edu.vu.event.notification.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.cdu.edu.vu.event.notification.api.domain.entity.EventEntity;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(value = """
        SELECT DISTINCT
            e.*
        FROM
            event e
        JOIN
            reminder r
        ON
            e.id = r.event_id
        WHERE
            e.date_time = cast(:dateTime as timestamptz) + cast(concat(r.units_before_start, ' ', r.units) as interval)
        ORDER BY
            e.date_time
    """, nativeQuery = true)
    List<EventEntity> findAllUpcomingEvents(ZonedDateTime dateTime);

    @Query(value = """
        SELECT DISTINCT
            e.*
        FROM
            event e
        JOIN
            reminder r
        ON
            e.id = r.event_id
        WHERE
            e.notified IS FALSE AND e.date_time < cast(:dateTime as timestamptz) + cast(concat(r.units_before_start, ' ', r.units) as interval)
        ORDER BY
            e.date_time
    """, nativeQuery = true)
    List<EventEntity> findAllLostEvents(ZonedDateTime dateTime);

    List<EventEntity> findAllByKeyAndCreatedBy(String key, String createdBy);

    @Transactional
    @Modifying
    @Query("UPDATE EventEntity SET notified = true WHERE id = :id")
    void markNotifiedById(long id);
}

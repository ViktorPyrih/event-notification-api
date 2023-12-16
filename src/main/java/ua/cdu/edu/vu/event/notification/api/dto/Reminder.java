package ua.cdu.edu.vu.event.notification.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ua.cdu.edu.vu.event.notification.api.model.TimeUnit;

import java.time.Duration;

public record Reminder(@NotNull @Positive int unitsBeforeStart, @NotNull TimeUnit units) {

    public Duration duration() {
        return Duration.of(unitsBeforeStart, units.toTemporalUnit());
    }
}

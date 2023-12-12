package ua.cdu.edu.vu.event.notification.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ua.cdu.edu.vu.event.notification.api.model.TimeUnit;

public record Reminder(@NotNull @Positive Integer unitsBeforeStart, @NotNull TimeUnit units) {
}

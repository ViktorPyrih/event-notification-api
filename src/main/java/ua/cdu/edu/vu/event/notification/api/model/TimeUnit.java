package ua.cdu.edu.vu.event.notification.api.model;

import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RequiredArgsConstructor
public enum TimeUnit {

    MINUTES(ChronoUnit.MINUTES), HOURS(ChronoUnit.HOURS), DAYS(ChronoUnit.DAYS);

    private final ChronoUnit temporalUnit;

    public TemporalUnit toTemporalUnit() {
        return temporalUnit;
    }
}

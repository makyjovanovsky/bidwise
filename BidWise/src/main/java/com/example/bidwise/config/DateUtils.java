package com.example.bidwise.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static long calculateSecondsDifference(LocalDateTime finish) {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), finish);
    }

    public static long calculateMinutesDifference(LocalDateTime finish) {
        return ChronoUnit.MINUTES.between(LocalDateTime.now(), finish);
    }
}


package com.sma.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
public enum DayBias {
    UNKNOWN("UNKNOWN"),
    TREND_UP("Trend Up Day"),
    TREND_DOWN("Trend Down Day"),
    RANGE_DAY("Range Day");

    final String dayBias;

    DayBias(String s) {
        this.dayBias = s;
    }
}

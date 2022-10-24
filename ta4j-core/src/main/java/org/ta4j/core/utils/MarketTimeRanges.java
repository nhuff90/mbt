package org.ta4j.core.utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Key times during the day
 */
public enum MarketTimeRanges {
    // ETH Range
    ETH(MarketTime.ETH_START_TIME.getLocalTime(), MarketTime.ETH_END_TIME.getLocalTime()),

    // RTH
    RTH(MarketTime.RTH_START_TIME.getLocalTime(), MarketTime.RTH_END_TIME.getLocalTime()),

    // AM Range
    AM_RANGE(MarketTime.AM_START_TIME.getLocalTime(), MarketTime.AM_END_TIME.getLocalTime()),
    AM_BOUNCE_RANGE(MarketTime.AM_BOUNCE_START_TIME.getLocalTime(), MarketTime.AM_BOUNCE_END_TIME.getLocalTime()),

    // Micro
    MICRO_RANGE(MarketTime.MICRO_START_TIME.getLocalTime(), MarketTime.MICRO_END_TIME.getLocalTime()),

    // PM Range
    PM_RANGE(MarketTime.PM_START_TIME.getLocalTime(), MarketTime.PM_END_TIME.getLocalTime()),
    PM_BOUNCE_RANGE(MarketTime.PM_BOUNCE_START_TIME.getLocalTime(), MarketTime.PM_BOUNCE_END_TIME.getLocalTime()),

    // Up To PM Range
    UP_TO_PM_RANGE(MarketTime.AM_START_TIME.getLocalTime(), MarketTime.PM_START_TIME.getLocalTime()),

    // Power Hour -- up to 3:00 to 3:58 EST
    POWER_HOUR_RANGE(MarketTime.POWER_HOUR_START_TIME.getLocalTime(), MarketTime.POWER_HOUR_END_TIME.getLocalTime()),

    // Initial Balance Range
    INITIAL_BALANCE_RANGE(MarketTime.INITIAL_BALANCE_START_TIME.getLocalTime(), MarketTime.INITIAL_BALANCE_END_TIME.getLocalTime()),

    // Opening Drive Range -- todo - need to determine OD time. 930-935? 930-945?
    OPENING_DRIVE_RANGE(MarketTime.OPENING_DRIVE_START_TIME.getLocalTime(), MarketTime.OPENING_DRIVE_END_TIME.getLocalTime());

    final LocalTime startTime ;
    final LocalTime endTime ;

    MarketTimeRanges(LocalTime startTime, LocalTime endTime) {
        Objects.requireNonNull( startTime ) ;
        Objects.requireNonNull( endTime ) ;
        this.startTime = startTime ;
        this.endTime = endTime ;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}

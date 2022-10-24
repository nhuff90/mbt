package org.ta4j.core.utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Key times during the day
 */
public enum MarketTime {
    // ETH Times
    ETH_START_TIME(LocalTime.parse( "16:00:00" )),
    OVERNIGHT_START_TIME(LocalTime.parse( "00:00:00" )),
    ETH_END_TIME(LocalTime.parse( "09:30:00" )),

    // RTH Times
    RTH_START_TIME(LocalTime.parse( "09:30:00" )),
    RTH_END_TIME(LocalTime.parse( "16:00:00" )),
    RTH_END_TIME_1558(LocalTime.parse( "15:58:00" )),

    // AM Range Times
    AM_START_TIME(LocalTime.parse( "09:30:00" )),
    AM_BOUNCE_START_TIME(LocalTime.parse( "10:03:00" )),
    AM_BOUNCE_END_TIME(LocalTime.parse( "10:10:00" )),
    AM_BOUNCE_STOP_LOSS_TIME(LocalTime.parse( "10:20:00" ) ),
    AM_END_TIME(LocalTime.parse( "10:05:00" )),

    // Micro Range Times
    MICRO_START_TIME(LocalTime.parse( "11:30:00" )),
    MICRO_END_TIME(LocalTime.parse( "12:05:00" )),

    // PM Range Times
    PM_START_TIME(LocalTime.parse( "13:30:00" )),
    PM_BOUNCE_START_TIME(LocalTime.parse( "13:33:00" )),
    PM_BOUNCE_END_TIME(LocalTime.parse( "14:10:00" )),
    PM_BOUNCE_STOP_LOSS_TIME(LocalTime.parse( "14:20:00" ) ),
    PM_END_TIME(LocalTime.parse( "14:05:00" )),

    // Power Hour -- up to 3:00 to 3:58 EST
    POWER_HOUR_START_TIME(LocalTime.parse( "15:00:00" )),
    POWER_HOUR_END_TIME(LocalTime.parse( "15:58:00" )),

    // Initial Balance Range
    INITIAL_BALANCE_START_TIME(LocalTime.parse( "09:30:00" ) ),
    INITIAL_BALANCE_END_TIME(LocalTime.parse( "10:30:00" )),

    // Opening Drive Range -- todo - need to determine OD time. 930-935? 930-945?
    OPENING_DRIVE_START_TIME(LocalTime.parse( "09:30:00" ) ),
    OPENING_DRIVE_END_TIME(LocalTime.parse( "09:40:00" ));

    private final LocalTime localTime ;

    MarketTime(LocalTime localTime) {
        Objects.requireNonNull( localTime ) ;
        this.localTime = localTime ;
    }

    public static boolean isStartOfAm(ZonedDateTime endTime) {
        return is(endTime.toLocalTime(), AM_START_TIME.localTime);
    }

    public static boolean isStartOfPmRange(ZonedDateTime endTime) {
        return is(endTime.toLocalTime(), PM_START_TIME.localTime);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public static Boolean isInAmRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.AM_START_TIME.getLocalTime(), MarketTime.AM_END_TIME.getLocalTime());
    }

    public static Boolean isInMicroRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.MICRO_START_TIME.getLocalTime(), MarketTime.MICRO_END_TIME.getLocalTime());
    }

    public static Boolean isInPmRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.PM_START_TIME.getLocalTime(), MarketTime.PM_END_TIME.getLocalTime());
    }

    public static Boolean isAfterPmRange(LocalTime time)  {
        return time.isAfter( MarketTime.PM_END_TIME.getLocalTime());
    }

    public static Boolean isAfterAmRange(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.AM_END_TIME.getLocalTime());
    }

    public static Boolean isInUpToPmRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.AM_START_TIME.getLocalTime(), MarketTime.PM_START_TIME.getLocalTime());
    }

    public static Boolean isAfterAmStop(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.AM_BOUNCE_STOP_LOSS_TIME.getLocalTime());
    }

    public static Boolean isAfterPmStop(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.PM_BOUNCE_STOP_LOSS_TIME.getLocalTime());
    }

    public static Boolean isAmBounceRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.AM_BOUNCE_START_TIME.getLocalTime(), MarketTime.AM_BOUNCE_END_TIME.getLocalTime());
    }

    public static Boolean isPmBounceRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.PM_BOUNCE_START_TIME.getLocalTime(), MarketTime.PM_BOUNCE_END_TIME.getLocalTime());
    }

    public static Boolean isBetweenTimes(LocalTime time, LocalTime startTime, LocalTime endTime)  {
        return !time.isBefore( startTime ) &&
                !time.isAfter( endTime );
    }

    public static Boolean is(LocalTime time, LocalTime time2)  {
        return time.equals( time2 );
    }

    public static boolean isRegularTradingHours(ZonedDateTime endTime) {
        return isBetweenTimes(endTime.toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime(), MarketTime.RTH_END_TIME.getLocalTime());
    }
}

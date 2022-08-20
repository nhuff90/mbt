package org.ta4j.core.utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Key times during the day
 */
public enum MarketTime {
    // ETH Times
    ETH_START_AFTER(LocalTime.parse( "16:00:00" )),
    OVERNIGHT_START_AFTER(LocalTime.parse( "00:00:00" )),
    ETH_END_BEFORE(LocalTime.parse( "09:30:00" )),

    //RTH Times
    RTH_START_AFTER(LocalTime.parse( "09:29:00" )),
    RTH_END_BEFORE(LocalTime.parse( "16:00:00" )),

    // AM Range Times
    AM_START_AFTER(LocalTime.parse( "09:29:00" )),
    AM_START(LocalTime.parse( "09:30:00" )),
    AM_END_BEFORE(LocalTime.parse( "10:06:00" )),
    AM_BOUNCE_START_AFTER(LocalTime.parse( "10:02:00" )),
    AM_BOUNCE_END_BEFORE(LocalTime.parse( "10:11:00" )),
    AM_BOUNCE_STOP_LOSS(LocalTime.parse( "10:20:00" ) ),
    AM_END(LocalTime.parse( "10:05:00" )),

    // Micro Range Times
    MICRO_START_AFTER(LocalTime.parse( "11:29:00" )),
    MICRO_END_BEFORE(LocalTime.parse( "12:06:00" )),

    // PM Range Times
    PM_START_AFTER(LocalTime.parse( "13:29:00" )),
    PM_START(LocalTime.parse( "13:30:00" )),
    PM_END_BEFORE(LocalTime.parse( "14:06:00" )),
    PM_BOUNCE_START_AFTER(LocalTime.parse( "14:02:00" )),
    PM_BOUNCE_END_BEFORE(LocalTime.parse( "14:11:00" )),
    PM_BOUNCE_STOP_LOSS(LocalTime.parse( "14:20:00" ) ),
    PM_END(LocalTime.parse( "14:05:00" ));

    private final LocalTime localTime ;

    MarketTime(LocalTime localTime) {
        Objects.requireNonNull( localTime ) ;
        this.localTime = localTime ;
    }

    public static boolean isStartOfAm(ZonedDateTime endTime) {
        return is(endTime.toLocalTime(), AM_START.localTime);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public static Boolean isInAmRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.AM_START_AFTER.getLocalTime(), MarketTime.AM_END_BEFORE.getLocalTime());
    }

    public static Boolean isInMicroRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.MICRO_START_AFTER.getLocalTime(), MarketTime.MICRO_END_BEFORE.getLocalTime());
    }

    public static Boolean isInPmRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.PM_START_AFTER.getLocalTime(), MarketTime.PM_END_BEFORE.getLocalTime());
    }

    public static Boolean isAfterPmRange(LocalTime time)  {
        return time.isAfter( MarketTime.PM_END.getLocalTime());
    }

    public static Boolean isAfterAmRange(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.AM_END.getLocalTime());
    }

    public static Boolean isAfterAmStop(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.AM_BOUNCE_STOP_LOSS.getLocalTime());
    }

    public static Boolean isAfterPmStop(ZonedDateTime time)  {
        return time.toLocalTime().isAfter( MarketTime.PM_BOUNCE_STOP_LOSS.getLocalTime());
    }

    public static Boolean isAmBounceRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.AM_BOUNCE_START_AFTER.getLocalTime(), MarketTime.AM_BOUNCE_END_BEFORE.getLocalTime());
    }

    public static Boolean isPmBounceRange(ZonedDateTime time)  {
        return isBetweenTimes(time.toLocalTime(), MarketTime.PM_BOUNCE_START_AFTER.getLocalTime(), MarketTime.PM_BOUNCE_END_BEFORE.getLocalTime());
    }

    public static Boolean isBetweenTimes(LocalTime time, LocalTime startTime, LocalTime endTime)  {
        return time.isAfter( startTime ) &&
                time.isBefore( endTime );
    }

    public static Boolean is(LocalTime time, LocalTime time2)  {
        return time.equals( time2 );
    }
}

package org.ta4j.core.utils;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class TimeUtils {

    public static Boolean isBetweenTimes(LocalTime time, LocalTime startTime, LocalTime endTime)  {
        return !time.isBefore( startTime ) &&
                !time.isAfter( endTime );
    }

    public static Boolean is(LocalTime time, LocalTime time2)  {
        return time.equals( time2 );
    }

    public static Boolean isBefore(ZonedDateTime time, MarketTime marketTime)  {
        return time.toLocalTime().isBefore( marketTime.getLocalTime());
    }

    public static Boolean isAfter(ZonedDateTime time, MarketTime marketTime)  {
        return time.toLocalTime().isAfter( marketTime.getLocalTime());
    }

    public static boolean isStartOfRange(ZonedDateTime time, MarketTimeRanges range) {
        return TimeUtils.is(time.toLocalTime(), range.startTime);
    }

    public static Boolean isInRange(ZonedDateTime time, MarketTimeRanges range)  {
        return TimeUtils.isBetweenTimes(time.toLocalTime(), range.startTime, range.endTime);
    }

    public static Boolean isAfterRange(ZonedDateTime time, MarketTimeRanges range)  {
        return time.toLocalTime().isAfter(range.endTime);
    }
}

package org.ta4j.core.utils;

import org.ta4j.core.Bar;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Key times during the day
 */
public enum MarketTime {
    // ETH Times
    ETH_END_TIME_0929(LocalTime.parse( "09:29:00" )),
    ETH_START_TIME_1600(LocalTime.parse( "16:00:00" )),
    ETH_2359(LocalTime.parse( "23:59:00" )),
    ETH_0000(LocalTime.parse( "00:00:00" )),

    // RTH Times
    RTH_START_TIME_0930(LocalTime.parse( "09:30:00" )),
    RTH_1000(LocalTime.parse( "10:00:00" )),
    RTH_1030(LocalTime.parse( "10:30:00" )),
    RTH_1100(LocalTime.parse( "11:00:00" )),
    RTH_1130(LocalTime.parse( "11:30:00" )),
    RTH_1200(LocalTime.parse( "12:00:00" )),
    RTH_1230(LocalTime.parse( "12:30:00" )),
    RTH_1300(LocalTime.parse( "13:00:00" )),
    RTH_1330(LocalTime.parse( "13:30:00" )),
    RTH_1400(LocalTime.parse( "14:00:00" )),
    RTH_1430(LocalTime.parse( "14:30:00" )),
    RTH_1500(LocalTime.parse( "15:00:00" )),
    RTH_1530(LocalTime.parse( "15:00:00" )),
    RTH_END_TIME_1559(LocalTime.parse( "15:59:00" )),


    /*
    Other times below
     */
    RTH_0959(LocalTime.parse( "09:59:00" )),
    RTH_1029(LocalTime.parse( "10:29:00" )),
    RTH_1059(LocalTime.parse( "10:59:00" )),
    RTH_1129(LocalTime.parse( "11:29:00" )),
    RTH_1159(LocalTime.parse( "11:59:00" )),
    RTH_1229(LocalTime.parse( "12:29:00" )),
    RTH_1259(LocalTime.parse( "12:59:00" )),
    RTH_1329(LocalTime.parse( "13:29:00" )),
    RTH_1359(LocalTime.parse( "13:59:00" )),
    RTH_1429(LocalTime.parse( "14:29:00" )),
    RTH_1459(LocalTime.parse( "14:59:00" )),
    RTH_1529(LocalTime.parse( "15:29:00" )),

    RTH_0931(LocalTime.parse( "09:31:00" )),
    RTH_0935(LocalTime.parse( "09:35:00" )),
    RTH_0945(LocalTime.parse( "09:45:00" )),
    RTH_1005(LocalTime.parse( "10:05:00" )),
    RTH_1205(LocalTime.parse( "12:05:00" )),
    RTH_1405(LocalTime.parse( "14:05:00" )),
    RTH_1558(LocalTime.parse( "15:58:00" )),

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

    // First 30 mins Range
    OPEN_TO_1000_START_TIME(LocalTime.parse( "09:30:00" ) ),
    OPEN_TO_1000_END_TIME(LocalTime.parse( "10:30:00" )),

    // Opening Drive Range -- todo - need to determine OD time. 930-935? 930-945?
    OPENING_DRIVE_START_TIME(LocalTime.parse( "09:30:00" ) ),
    OPENING_DRIVE_END_TIME(LocalTime.parse( "09:45:00" )),

    // OMAR Range
    OMAR_START_TIME(LocalTime.parse( "09:30:00" ) ),
    OMAR_END_TIME(LocalTime.parse( "09:30:00" )),

    // Opening 5 mins Range
    OPENING_5MINS_START_TIME(LocalTime.parse( "09:30:00" ) ),
    OPENING_5MINS_END_TIME(LocalTime.parse( "09:34:00" ));

    private final LocalTime localTime ;

    MarketTime(LocalTime localTime) {
        Objects.requireNonNull( localTime ) ;
        this.localTime = localTime ;
    }

    /*
    Start/End of Sessions
    */
    public static boolean isStartOfRthSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_START_TIME_0930.getLocalTime());
    }

    public static boolean isEndOfRthSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime());
    }

    public static boolean isStartOfAmSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_START_TIME_0930.getLocalTime());
    }

    public static boolean isEndOfAmSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_1005.getLocalTime());
    }

    public static boolean isStartOfMicroSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_1130.getLocalTime());
    }

    public static boolean isEndOfMicroSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_1205.getLocalTime());
    }

    public static boolean isStartOfPmSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_1330.getLocalTime());
    }

    public static boolean isEndOfPmSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.RTH_1405.getLocalTime());
    }

    public static boolean isStartOfOvernightSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.ETH_START_TIME_1600.getLocalTime());
    }

    public static boolean isEndOfOvernightSession(Bar bar) {
        return TimeUtils.is(bar.getEndTime().toLocalTime(), MarketTime.ETH_END_TIME_0929.getLocalTime());
    }

    /*
    In Sessions
     */
    public static boolean isInRthSession(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_START_TIME_0930.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime());
    }

    public static boolean isInAmSession(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_START_TIME_0930.getLocalTime(), MarketTime.RTH_1005.getLocalTime());
    }

    public static boolean isInMicroSession(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_1130.getLocalTime(), MarketTime.RTH_1205.getLocalTime());
    }

    public static boolean isInPmSession(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_1330.getLocalTime(), MarketTime.RTH_1405.getLocalTime());
    }

    public static boolean isInOvernightSession(Bar bar) {
        return (TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.ETH_START_TIME_1600.getLocalTime(), MarketTime.ETH_2359.getLocalTime()) ||
                TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.ETH_0000.getLocalTime(), MarketTime.ETH_END_TIME_0929.getLocalTime()));
    }

    /*
    Post Sessions
     */
    public static boolean isPostAmSessionToRthEnd(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_1005.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime());
    }

    public static boolean isPostMicroSessionToRthEnd(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_1405.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime());
    }

    public static boolean isPostPmSessionToRthEnd(Bar bar) {
        return TimeUtils.isBetweenTimes(bar.getEndTime().toLocalTime(), MarketTime.RTH_1405.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime());
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}

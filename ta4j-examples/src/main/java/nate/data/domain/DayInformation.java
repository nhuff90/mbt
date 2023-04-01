package com.sma.domain;

import com.sma.utils.DayBias;
import com.sma.v01.domain.Candle;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Map;

public class DayInformation {

    /*
    1. Candles mapped by timeframe (aka 1 min)
    2. AM/Mid/PM Hi, Lo and 25%, 50%, 75% Ranges
    3. Daily Hi, Lo, Mid, 25%, 50%, 75% ranges.
    3. Previous day DayInformation
    4. Day Bias (trend up/down range up/down)
     */

    Map<LocalDateTime, Candle> timeCandleMap;
    Range amRange;
    Range midDayRange;
    Range pmRange;
    DayInformation priorDayInfo;
    DayBias dayBias;

}

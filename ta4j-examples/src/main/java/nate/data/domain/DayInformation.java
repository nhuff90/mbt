package nate.data.domain;

import nate.data.utils.DayBias;

import java.time.LocalDateTime;
import java.util.Map;

public class DayInformation {

    Map<LocalDateTime, Candle> timeCandleMap;
    Range amRange;
    Range midDayRange;
    Range pmRange;
    DayInformation priorDayInfo;
    DayBias dayBias;

}

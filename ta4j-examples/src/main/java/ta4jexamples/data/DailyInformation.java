package ta4jexamples.data;

import org.ta4j.core.Bar;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DailyInformation {
    LocalDate date;
    List<Bar> bars;
    Map<ZonedDateTime, Bar> timeBarMap;
    DailyInformation priorDayInfo;
    Range rthDayRange = new Range();
    Range amRange = new Range();
    Range microRange = new Range();
    Range pmRange = new Range();
    DayBias dayBias;

    Map<DailyRange, Range> rangeMap = new HashMap<>();

    // Other ranges below
    Range rthToPMStartRange = new Range();

    public enum DailyRange {
        RTH,
        AM,
        PM,
        MICRO,
        RTH_TO_PM
    }


    private void configureRangeMap() {
        rangeMap.put(DailyRange.RTH, rthDayRange);
        rangeMap.put(DailyRange.AM, amRange);
        rangeMap.put(DailyRange.PM, pmRange);
        rangeMap.put(DailyRange.MICRO, microRange);
        rangeMap.put(DailyRange.RTH_TO_PM, rthToPMStartRange);
    }

    public DailyInformation(LocalDate date, List<Bar> bars) {
        this(date, bars, null);
    }

    public DailyInformation(LocalDate date, List<Bar> bars, DailyInformation priorDayInfo) {
        this.date = date;
        this.bars = bars;
        this.timeBarMap = bars.stream().collect(Collectors.toMap(Bar::getBeginTime, Function.identity()));
        this.priorDayInfo = priorDayInfo;

        generateData();
    }

    private void generateData() {
        bars.forEach( bar -> {
            rthDayRange.setRangeValues(bar, MarketTime.RTH_START_TIME, MarketTime.RTH_END_TIME);
            amRange.setRangeValues(bar, MarketTime.AM_START_TIME, MarketTime.AM_END_TIME);
            microRange.setRangeValues(bar, MarketTime.MICRO_START_TIME, MarketTime.MICRO_END_TIME);
            pmRange.setRangeValues(bar, MarketTime.PM_START_TIME, MarketTime.PM_END_TIME);
            rthToPMStartRange.setRangeValues(bar, MarketTime.RTH_START_TIME, MarketTime.PM_START_TIME);
        });
        configureRangeMap();
    }

    public LocalDate getDate() {
        return date;
    }

    public Num getOpenPrice(DailyRange range) {
        return (rangeMap.get(range) != null) ? rangeMap.get(range).getOpenPrice() : null;
    }
    public Num getClosePrice(DailyRange range) {
        return (rangeMap.get(range) != null) ? rangeMap.get(range).getClosePrice() : null;
    }
    public Num getHighPrice(DailyRange range) {
        return (rangeMap.get(range) != null) ? rangeMap.get(range).getHighPrice() : null;
    }
    public Num getLowPrice(DailyRange range) {
        return (rangeMap.get(range) != null) ? rangeMap.get(range).getLowPrice() : null;
    }

}

package ta4jexamples.data;

import org.ta4j.core.Bar;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DailyInformation {
    LocalDate date;
    List<Bar> bars;
    Map<ZonedDateTime, Bar> timeBarMap;
    DailyInformation priorDayInfo;
    Range rthDayRange = new Range();
    Range rthEndAt1558DayRange = new Range();
    Range amRange = new Range();
    Range microRange = new Range();
    Range pmRange = new Range();
    Range powerHourRange = new Range();
    Range initialBalanceRange = new Range();
    Range openingDriveRange = new Range();
    DayTrend dayTrend;

    Map<DailyRange, Range> rangeMap = new HashMap<>();

    // Other ranges below
    Range rthToPMStartRange = new Range();

    public enum DailyRange {
        RTH,
        RTH_END_AT_1558,
        AM,
        PM,
        MICRO,
        RTH_TO_PM,
        OPENING_DRIVE,
        INITIAL_BALANCE,
        POWER_HOUR,
        PRIOR_DAY_RTH,
    }

    private void configureRangeMap() {
        rangeMap.put(DailyRange.RTH, rthDayRange);
        rangeMap.put(DailyRange.RTH_END_AT_1558, rthEndAt1558DayRange);
        rangeMap.put(DailyRange.AM, amRange);
        rangeMap.put(DailyRange.PM, pmRange);
        rangeMap.put(DailyRange.MICRO, microRange);
        rangeMap.put(DailyRange.RTH_TO_PM, rthToPMStartRange);
        rangeMap.put(DailyRange.OPENING_DRIVE, openingDriveRange);
        rangeMap.put(DailyRange.INITIAL_BALANCE, initialBalanceRange);
        rangeMap.put(DailyRange.POWER_HOUR, powerHourRange);
//        rangeMap.put(DailyRange.PRIOR_DAY_RTH, priorDayInfo.getRange(DailyRange.RTH));
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
            rthDayRange.setRangeValues(bar, MarketTime.RTH_START_TIME_0930, MarketTime.RTH_END_TIME_1559);
            rthEndAt1558DayRange.setRangeValues(bar, MarketTime.RTH_START_TIME_0930, MarketTime.RTH_1558);
            amRange.setRangeValues(bar, MarketTime.AM_START_TIME, MarketTime.AM_END_TIME);
            microRange.setRangeValues(bar, MarketTime.MICRO_START_TIME, MarketTime.MICRO_END_TIME);
            pmRange.setRangeValues(bar, MarketTime.PM_START_TIME, MarketTime.PM_END_TIME);
            rthToPMStartRange.setRangeValues(bar, MarketTime.RTH_START_TIME_0930, MarketTime.PM_START_TIME);
            powerHourRange.setRangeValues(bar, MarketTime.POWER_HOUR_START_TIME, MarketTime.POWER_HOUR_END_TIME);
            initialBalanceRange.setRangeValues(bar, MarketTime.INITIAL_BALANCE_START_TIME, MarketTime.INITIAL_BALANCE_END_TIME);
            openingDriveRange.setRangeValues(bar, MarketTime.OPENING_DRIVE_START_TIME, MarketTime.OPENING_DRIVE_END_TIME);
        });
        configureRangeMap();
        this.dayTrend = determineDayTrend();
    }

    private DayTrend determineDayTrend() {
        if (rthDayRange == null || rthDayRange.getOpenPrice() == null || rthDayRange.getHighPrice() == null ||
                rthDayRange.getLowPrice() == null || rthDayRange. getClosePrice() == null) {
            return DayTrend.NA;
        }
        if (this.rthDayRange.getClosePrice().isGreaterThan(this.rthDayRange.getOpenPrice()) &&
                this.rthDayRange.getClosePrice().isGreaterThan(this.amRange.getHighPrice()) &&
                this.rthDayRange.getLowPrice().isLessThan(this.pmRange.getLowPrice()) &&
                this.rthDayRange.getLowPrice().isEqual(this.amRange.getLowPrice())) {
            if (!this.rthDayRange.getLowPrice().isEqual(this.amRange.getLowPrice())) {
                return DayTrend.UNPREDICTABLE;
            }
            return DayTrend.TREND_UP;
        } else if (this.rthDayRange.getClosePrice().isLessThan(this.rthDayRange.getOpenPrice()) &&
                this.rthDayRange.getClosePrice().isLessThan(this.amRange.getLowPrice()) &&
                this.rthDayRange.getHighPrice().isGreaterThan(this.pmRange.getHighPrice()) &&
                this.rthDayRange.getHighPrice().isEqual(this.amRange.getHighPrice())) {
            if (!this.rthDayRange.getHighPrice().isEqual(this.amRange.getHighPrice())) {
                return DayTrend.UNPREDICTABLE;
            }
            return DayTrend.TREND_DOWN;
        } else {
            if (isUnpredictableFromRange()) {
                return DayTrend.UNPREDICTABLE;
            }
            AtomicInteger barCountAboveAmMid = new AtomicInteger();
            AtomicInteger barCountBelowAmMid = new AtomicInteger();
            bars.forEach( bar -> {
                // Only is RTH
                if (!bar.getEndTime().toLocalTime().isBefore(MarketTime.RTH_START_TIME_0930.getLocalTime()) &&
                        !bar.getEndTime().toLocalTime().isAfter(MarketTime.RTH_END_TIME_1559.getLocalTime())) {
                    if (bar.getOpenPrice().isGreaterThan(this.amRange.getMiddlePrice()) &&
                    bar.getClosePrice().isGreaterThan(this.amRange.getMiddlePrice())) {
                        barCountAboveAmMid.getAndIncrement();
                    } else if (bar.getOpenPrice().isLessThan(this.amRange.getMiddlePrice()) &&
                            bar.getClosePrice().isLessThan(this.amRange.getMiddlePrice())) {
                        barCountBelowAmMid.getAndIncrement();
                    }
                }
            });
            if (barCountAboveAmMid.get() > barCountBelowAmMid.get()) {

                return DayTrend.RANGE_UP;
            } else if (barCountBelowAmMid.get() > barCountAboveAmMid.get()) {
                return DayTrend.RANGE_DOWN;
            } else {
                return DayTrend.UNPREDICTABLE;
            }
        }
    }

    private boolean isUnpredictableFromRange() {
        Num aboveAmHighRange = this.rthDayRange.getHighPrice().minus(this.amRange.getHighPrice());
        Num aboveAmLowRange = this.amRange.getLowPrice().minus(this.rthDayRange.getLowPrice());

        return (aboveAmHighRange.isGreaterThan(amRange.getRangeSize()) || aboveAmLowRange.isGreaterThan(amRange.getRangeSize()));
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

    public DayTrend getDayTrend() {
        return dayTrend;
    }

    public void setDayTrend(DayTrend dayTrend) {
        this.dayTrend = dayTrend;
    }

    public Num getHalfBackPrice(DailyRange range) {
        if (rangeMap.get(range) == null || rangeMap.get(range).getHighPrice() == null) {
            return null;
        }
        Num halfBackRange = rangeMap.get(range).getHighPrice().minus(rangeMap.get(range).getLowPrice()).dividedBy(DecimalNum.valueOf(2));
        return rangeMap.get(range).getHighPrice().minus(halfBackRange);
    }

    public Range getRange(DailyRange range) {
        return rangeMap.get(range);
    }
}

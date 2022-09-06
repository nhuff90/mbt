package ta4jexamples.data;

import org.ta4j.core.Bar;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    // Other ranges below
    Range rthToPMStartRange = new Range();

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
    }

    public LocalDate getDate() {
        return date;
    }

    public Num getPMHighPrice() {
        return pmRange.getHigh();
    }

    public Num getPMLowPrice() {
        return pmRange.getLow();
    }

    public Num getDailyOpenPrice() {
        return rthDayRange.getOpenPrice();
    }

    public Num getHighPriceUpToPMPrice() {
        return rthToPMStartRange.getHigh();
    }

    public Num getLowPriceUpToPMPrice() {
        return rthToPMStartRange.getLow();
    }

    public Num getPMRangeOpenPrice() {
        return pmRange.getOpenPrice();
    }

    public Num getPMRangeClosePrice() {
        return pmRange.getClosePrice();
    }

    public Num getDailyClosePrice() {
        return rthDayRange.getClosePrice();
    }

    public Num getRTHToPMHighPrice() {
        return rthToPMStartRange.getHigh();
    }

    public Num getRTHToPMLowPrice() {
        return rthToPMStartRange.getLow();
    }

    public Num getRTHToPMCenterPrice() {
        return rthToPMStartRange.getPercentileFromRange(.5);
    }

    public Num getRTHToPMAtPercentilePrice(double percent) {
        return rthToPMStartRange.getPercentileFromRange(percent);
    }

    public double getPMStartPriceToRthLow() {
        if (pmRange.getOpenPrice()==null || rthToPMStartRange.getLow() == null){
            return Double.NaN;
        }
        else {
            return pmRange.getOpenPrice().minus(rthToPMStartRange.getLow()).doubleValue();
        }
    }

    public double getPMEndPriceToRthLow() {
        if (pmRange.getClosePrice()==null || rthToPMStartRange.getLow() == null){
            return Double.NaN;
        }
        else {
            return pmRange.getClosePrice().minus(rthToPMStartRange.getLow()).doubleValue();
        }
    }
}

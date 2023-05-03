package nate.stats;

import nate.stats.domain.TrueFalseDailyMgiAndPeriodOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrendVsRangeStats extends Stats {

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        runStats(dailyMgiMap);
    }

    private void runStats(Map<LocalDate, DailyMgi> dailyMgiMap) {
        printTrendVsRange(dailyMgiMap);
    }

    private void printTrendVsRange(Map<LocalDate, DailyMgi> dailyMgiMap) {
        Map<LocalDate, String> dateToTrendVsRangeMap = new LinkedHashMap<>();
        dailyMgiMap.forEach((date, dailyMgi) -> {

            if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
                if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(),
                        MarketTime.RTH_START_TIME_0930.getLocalTime(),
                        MarketTime.RTH_1030.getLocalTime()) &&
                        TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(),
                                MarketTime.RTH_1500.getLocalTime(),
                                MarketTime.RTH_END_TIME_1559.getLocalTime())) {
                    System.out.println(date + " Trend_Down");
                } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(),
                        MarketTime.RTH_START_TIME_0930.getLocalTime(),
                        MarketTime.RTH_1030.getLocalTime()) &&
                        TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(),
                                MarketTime.RTH_1500.getLocalTime(),
                                MarketTime.RTH_END_TIME_1559.getLocalTime())) {
                    System.out.println(date + " Trend_Up");

                } else {
                    System.out.println(date + " Range");
                }
            }
        });
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 13), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        TrendVsRangeStats nHodBy30mPeriodStatsTest = new TrendVsRangeStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

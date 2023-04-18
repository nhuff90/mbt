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

    public static PeriodNhodResultsMap periodNHODResultsMap = new PeriodNhodResultsMap();

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

    public static class PeriodNhodResultsMap {
        public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNhodAfterResultsMap = new LinkedHashMap<>();
        public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNlodAfterResultsMap = new LinkedHashMap<>();

        public PeriodNhodResultsMap() {
            periodNhodAndNhodAfterResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNhodAfterResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());

            periodNhodAndNlodAfterResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNhodAndNlodAfterResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());
        }

        public void addToNhodAfterIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNhodAndNhodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodNhodAndNhodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNhodAfterIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNhodAndNhodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodNhodAndNhodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNlodAfterIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNhodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodNhodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNlodAfterIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNhodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodNhodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodNhodAndNhodAfterResultsMap() {
            return periodNhodAndNhodAfterResultsMap;
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodNhodAndNlodAfterResultsMap() {
            return periodNhodAndNlodAfterResultsMap;
        }

        public void setPeriodNhodAndNhodAfterResultsMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNhodAfterResultsMap) {
            PeriodNhodResultsMap.periodNhodAndNhodAfterResultsMap = periodNhodAndNhodAfterResultsMap;
        }

        public void setPeriodNhodAndNlodAfterResultsMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNlodAfterResultsMap) {
            PeriodNhodResultsMap.periodNhodAndNlodAfterResultsMap = periodNhodAndNlodAfterResultsMap;
        }
    }
}

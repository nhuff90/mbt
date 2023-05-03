package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.rules.nate.DailyTrend;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrendVsRangeBasedOnOpening15mStats extends Stats {

    public static TrendVsRangeDailyMgiOhlcResults resultsMap = new TrendVsRangeDailyMgiOhlcResults();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        runStats(dailyMgiMap);
        printResults();
    }

    private void printResults() {
        /*
        Print % 
         */
        System.out.println("Hits, Total Days");
        System.out.println(resultsMap.getTrendUpMap().size() + ", " + resultsMap.getTotalEntries());
        System.out.println(resultsMap.getTrendDownMap().size() + ", " + resultsMap.getTotalEntries());
        System.out.println(resultsMap.getRangeMap().size() + ", " + resultsMap.getTotalEntries());
        
        /*
        Print % by day of the week
         */
        Map<DayOfWeek, List<DailyMgi>> trendUpDayMap = convertMapToDaysMap(resultsMap.getTrendUpMap());
        Map<DayOfWeek, List<DailyMgi>> trendDownDayMap = convertMapToDaysMap(resultsMap.getTrendDownMap());
        Map<DayOfWeek, List<DailyMgi>> rangeDayMap = convertMapToDaysMap(resultsMap.getRangeMap());

        int totalMons = trendUpDayMap.get(DayOfWeek.MONDAY).size() + trendDownDayMap.get(DayOfWeek.MONDAY).size() + rangeDayMap.get(DayOfWeek.MONDAY).size();
        int totalTues = trendUpDayMap.get(DayOfWeek.TUESDAY).size() + trendDownDayMap.get(DayOfWeek.TUESDAY).size() + rangeDayMap.get(DayOfWeek.TUESDAY).size();
        int totalWeds = trendUpDayMap.get(DayOfWeek.WEDNESDAY).size() + trendDownDayMap.get(DayOfWeek.WEDNESDAY).size() + rangeDayMap.get(DayOfWeek.WEDNESDAY).size();
        int totalThurs = trendUpDayMap.get(DayOfWeek.THURSDAY).size() + trendDownDayMap.get(DayOfWeek.THURSDAY).size() + rangeDayMap.get(DayOfWeek.THURSDAY).size();
        int totalFris = trendUpDayMap.get(DayOfWeek.FRIDAY).size() + trendDownDayMap.get(DayOfWeek.FRIDAY).size() + rangeDayMap.get(DayOfWeek.FRIDAY).size();

        System.out.println("MONDAYS, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY");
        StringBuilder trendUpSb = new StringBuilder();
        trendUpSb.append((double) trendUpDayMap.get(DayOfWeek.MONDAY).size() / (double) totalMons);
        trendUpSb.append(",");
        trendUpSb.append((double) trendUpDayMap.get(DayOfWeek.TUESDAY).size() / (double) totalTues);
        trendUpSb.append(",");
        trendUpSb.append((double) trendUpDayMap.get(DayOfWeek.WEDNESDAY).size() / (double) totalWeds);
        trendUpSb.append(",");
        trendUpSb.append((double) trendUpDayMap.get(DayOfWeek.THURSDAY).size() / (double) totalThurs);
        trendUpSb.append(",");
        trendUpSb.append((double) trendUpDayMap.get(DayOfWeek.FRIDAY).size() / (double) totalFris);
        System.out.println(trendUpSb);

        StringBuilder trendDownSb = new StringBuilder();
        trendDownSb.append((double) trendDownDayMap.get(DayOfWeek.MONDAY).size() / (double) totalMons);
        trendDownSb.append(",");
        trendDownSb.append((double) trendDownDayMap.get(DayOfWeek.TUESDAY).size() / (double) totalTues);
        trendDownSb.append(",");
        trendDownSb.append((double) trendDownDayMap.get(DayOfWeek.WEDNESDAY).size() / (double) totalWeds);
        trendDownSb.append(",");
        trendDownSb.append((double) trendDownDayMap.get(DayOfWeek.THURSDAY).size() / (double) totalThurs);
        trendDownSb.append(",");
        trendDownSb.append((double) trendDownDayMap.get(DayOfWeek.FRIDAY).size() / (double) totalFris);
        System.out.println(trendDownSb);

        StringBuilder rangeSb = new StringBuilder();
        rangeSb.append((double) rangeDayMap.get(DayOfWeek.MONDAY).size() / (double) totalMons);
        rangeSb.append(",");
        rangeSb.append((double) rangeDayMap.get(DayOfWeek.TUESDAY).size() / (double) totalTues);
        rangeSb.append(",");
        rangeSb.append((double) rangeDayMap.get(DayOfWeek.WEDNESDAY).size() / (double) totalWeds);
        rangeSb.append(",");
        rangeSb.append((double) rangeDayMap.get(DayOfWeek.THURSDAY).size() / (double) totalThurs);
        rangeSb.append(",");
        rangeSb.append((double) rangeDayMap.get(DayOfWeek.FRIDAY).size() / (double) totalFris);
        System.out.println(rangeSb);

        printO15mEqIbRange();
        printO15mEqAmRange();
    }

    private void printO15mEqIbRange() {

        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : resultsMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();
        highEq.forEach(dailyMgi -> {
            if (resultsMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqTrendUp.add(dailyMgi);
            } else if (resultsMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqTrendDown.add(dailyMgi);
            } else if (resultsMap.getRangeMap().containsKey(dailyMgi)) {
                highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O15m=IBH");
        StringBuilder highEqSb = new StringBuilder();
        highEqSb.append(highEqTrendUp.size());
        highEqSb.append(",");
        highEqSb.append(highEqTrendDown.size());
        highEqSb.append(",");
        highEqSb.append(highEqRange.size());
        highEqSb.append(",");
        highEqSb.append(highEq.size());

        System.out.println(highEqSb);

        List<DailyMgi> lowEqTrendUp = new ArrayList<>();
        List<DailyMgi> lowEqTrendDown = new ArrayList<>();
        List<DailyMgi> lowEqRange = new ArrayList<>();
        lowEq.forEach(dailyMgi -> {
            if (resultsMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqTrendUp.add(dailyMgi);
            } else if (resultsMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqTrendDown.add(dailyMgi);
            } else if (resultsMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqRange.add(dailyMgi);
            }
        });
        System.out.println("O15m=IBL");
        StringBuilder lowEqSb = new StringBuilder();
        lowEqSb.append(lowEqTrendUp.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEqTrendDown.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEqRange.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEq.size());

        System.out.println(lowEqSb);
    }

    private void printO15mEqAmRange() {

        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : resultsMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();
        highEq.forEach(dailyMgi -> {
            if (resultsMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqTrendUp.add(dailyMgi);
            } else if (resultsMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqTrendDown.add(dailyMgi);
            } else if (resultsMap.getRangeMap().containsKey(dailyMgi)) {
                highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O15m=AMH");
        StringBuilder highEqSb = new StringBuilder();
        highEqSb.append(highEqTrendUp.size());
        highEqSb.append(",");
        highEqSb.append(highEqTrendDown.size());
        highEqSb.append(",");
        highEqSb.append(highEqRange.size());
        highEqSb.append(",");
        highEqSb.append(highEq.size());

        System.out.println(highEqSb);

        List<DailyMgi> lowEqTrendUp = new ArrayList<>();
        List<DailyMgi> lowEqTrendDown = new ArrayList<>();
        List<DailyMgi> lowEqRange = new ArrayList<>();
        lowEq.forEach(dailyMgi -> {
            if (resultsMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqTrendUp.add(dailyMgi);
            } else if (resultsMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqTrendDown.add(dailyMgi);
            } else if (resultsMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqRange.add(dailyMgi);
            }
        });
        System.out.println("O15m=AML");
        StringBuilder lowEqSb = new StringBuilder();
        lowEqSb.append(lowEqTrendUp.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEqTrendDown.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEqRange.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEq.size());

        System.out.println(lowEqSb);
    }


    private Map<DayOfWeek, List<DailyMgi>> convertMapToDaysMap(Map<DailyMgi, OHLCIndicator> trendUpMap) {
        Map<DayOfWeek, List<DailyMgi>> daysToTrendMap = new LinkedHashMap<>();
        daysToTrendMap.put(DayOfWeek.MONDAY, new ArrayList<>());
        daysToTrendMap.put(DayOfWeek.TUESDAY, new ArrayList<>());
        daysToTrendMap.put(DayOfWeek.WEDNESDAY, new ArrayList<>());
        daysToTrendMap.put(DayOfWeek.THURSDAY, new ArrayList<>());
        daysToTrendMap.put(DayOfWeek.FRIDAY, new ArrayList<>());

        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendUpMap.entrySet()) {
            List<DailyMgi> list = daysToTrendMap.get(entry.getKey().getRthOhlc().getOpen().getDate().getDayOfWeek());
            list.add(entry.getKey());
            daysToTrendMap.put(entry.getKey().getRthOhlc().getOpen().getDate().getDayOfWeek(), list);
        }

        return daysToTrendMap;
    }

    private void runStats(Map<LocalDate, DailyMgi> dailyMgiMap) {
        Map<LocalDate, String> dateToTrendVsRangeMap = new LinkedHashMap<>();
        dailyMgiMap.forEach((date, dailyMgi) -> {

            if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
                /*
                Trend up
                1. AMH < MicroH < PMH
                2. Open < PML
                 */
                /*
                Trend down
                1. AML > MicroL > PMH
                2. Open > PMH
                 */
                if (dailyMgi.getDailyTrend() == DailyTrend.TREND_DOWN) {
//                    System.out.println(date + " Trend_Down");
                    resultsMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.TREND_UP) {
//                    System.out.println(date + " Trend_Up");
                    resultsMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.RANGE) {
                    {
//                    System.out.println(date + " Range");
                        resultsMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());
                    }
                }
            }
        });
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 13), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        TrendVsRangeBasedOnOpening15mStats nHodBy30mPeriodStatsTest = new TrendVsRangeBasedOnOpening15mStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

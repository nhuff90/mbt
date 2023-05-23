package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrendVsRangeBasedOnOpening5mStats extends TrendVsRangeStats {

    //todo - Create unit test!!

    public static TrendVsRangeDailyMgiOhlcResults trendMap = new TrendVsRangeDailyMgiOhlcResults();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        trendMap = populateTrendMap(dailyMgiMap);
        printResults();
    }

    private void printResults() {
        /*
        Print % 
         */
        System.out.println("Hits, Total Days");
        System.out.println(trendMap.getTrendUpMap().size() + ", " + trendMap.getTotalEntries());
        System.out.println(trendMap.getTrendDownMap().size() + ", " + trendMap.getTotalEntries());
        System.out.println(trendMap.getRangeMap().size() + ", " + trendMap.getTotalEntries());
        
        /*
        Print % by day of the week
         */
        Map<DayOfWeek, List<DailyMgi>> trendUpDayMap = convertMapToDaysMap(trendMap.getTrendUpMap());
        Map<DayOfWeek, List<DailyMgi>> trendDownDayMap = convertMapToDaysMap(trendMap.getTrendDownMap());
        Map<DayOfWeek, List<DailyMgi>> rangeDayMap = convertMapToDaysMap(trendMap.getRangeMap());

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

        printO5mEqIbRange();
        printO5mEqAmRange();
        printO5mEqO15mRange();
    }

    private void printO5mEqIbRange() {

        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();
        highEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O5m=IBH");
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
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqRange.add(dailyMgi);
            }
        });
        System.out.println("O5m=IBL");
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

    private void printO5mEqAmRange() {

        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();
        highEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O5m=AMH");
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
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqRange.add(dailyMgi);
            }
        });
        System.out.println("O5m=AML");
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

    private void printO5mEqO15mRange() {

        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();
        highEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O5mH=O15mH");
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
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqRange.add(dailyMgi);
            }
        });
        System.out.println("O5mL=O15mL");
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

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 13), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        TrendVsRangeBasedOnOpening5mStats nHodBy30mPeriodStatsTest = new TrendVsRangeBasedOnOpening5mStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

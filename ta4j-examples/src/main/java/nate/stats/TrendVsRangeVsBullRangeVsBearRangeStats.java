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

public class TrendVsRangeVsBullRangeVsBearRangeStats extends TrendVsRangeStats {

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
        System.out.println(trendMap.getBullishRangeMap().size() + ", " + trendMap.getTotalEntries());
        System.out.println(trendMap.getBearishRangeMap().size() + ", " + trendMap.getTotalEntries());
        System.out.println(trendMap.getRangeMap().size() + ", " + trendMap.getTotalEntries());

        /*
        Print % by day of the week
         */
        Map<DayOfWeek, List<DailyMgi>> trendUpDayMap = convertMapToDaysMap(trendMap.getTrendUpMap());
        Map<DayOfWeek, List<DailyMgi>> trendDownDayMap = convertMapToDaysMap(trendMap.getTrendDownMap());
        Map<DayOfWeek, List<DailyMgi>> bullishRangeDayMap = convertMapToDaysMap(trendMap.getBullishRangeMap());
        Map<DayOfWeek, List<DailyMgi>> bearishRangeDayMap = convertMapToDaysMap(trendMap.getBearishRangeMap());
        Map<DayOfWeek, List<DailyMgi>> rangeDayMap = convertMapToDaysMap(trendMap.getRangeMap());

        int totalMons = trendUpDayMap.get(DayOfWeek.MONDAY).size() + trendDownDayMap.get(DayOfWeek.MONDAY).size() + rangeDayMap.get(DayOfWeek.MONDAY).size() + bullishRangeDayMap.get(DayOfWeek.MONDAY).size() + bearishRangeDayMap.get(DayOfWeek.MONDAY).size();
        int totalTues = trendUpDayMap.get(DayOfWeek.TUESDAY).size() + trendDownDayMap.get(DayOfWeek.TUESDAY).size() + rangeDayMap.get(DayOfWeek.TUESDAY).size() + bullishRangeDayMap.get(DayOfWeek.TUESDAY).size() + bearishRangeDayMap.get(DayOfWeek.TUESDAY).size();
        int totalWeds = trendUpDayMap.get(DayOfWeek.WEDNESDAY).size() + trendDownDayMap.get(DayOfWeek.WEDNESDAY).size() + rangeDayMap.get(DayOfWeek.WEDNESDAY).size() + bullishRangeDayMap.get(DayOfWeek.WEDNESDAY).size() + bearishRangeDayMap.get(DayOfWeek.WEDNESDAY).size();
        int totalThurs = trendUpDayMap.get(DayOfWeek.THURSDAY).size() + trendDownDayMap.get(DayOfWeek.THURSDAY).size() + rangeDayMap.get(DayOfWeek.THURSDAY).size() + bullishRangeDayMap.get(DayOfWeek.THURSDAY).size() + bearishRangeDayMap.get(DayOfWeek.THURSDAY).size();
        int totalFris = trendUpDayMap.get(DayOfWeek.FRIDAY).size() + trendDownDayMap.get(DayOfWeek.FRIDAY).size() + rangeDayMap.get(DayOfWeek.FRIDAY).size() + bullishRangeDayMap.get(DayOfWeek.FRIDAY).size() + bearishRangeDayMap.get(DayOfWeek.FRIDAY).size();

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

        StringBuilder bullRangeSb = new StringBuilder();
        bullRangeSb.append((double) bullishRangeDayMap.get(DayOfWeek.MONDAY).size() / (double) totalMons);
        bullRangeSb.append(",");
        bullRangeSb.append((double) bullishRangeDayMap.get(DayOfWeek.TUESDAY).size() / (double) totalTues);
        bullRangeSb.append(",");
        bullRangeSb.append((double) bullishRangeDayMap.get(DayOfWeek.WEDNESDAY).size() / (double) totalWeds);
        bullRangeSb.append(",");
        bullRangeSb.append((double) bullishRangeDayMap.get(DayOfWeek.THURSDAY).size() / (double) totalThurs);
        bullRangeSb.append(",");
        bullRangeSb.append((double) bullishRangeDayMap.get(DayOfWeek.FRIDAY).size() / (double) totalFris);
        System.out.println(bullRangeSb);

        StringBuilder bearRangeSb = new StringBuilder();
        bearRangeSb.append((double) bearishRangeDayMap.get(DayOfWeek.MONDAY).size() / (double) totalMons);
        bearRangeSb.append(",");
        bearRangeSb.append((double) bearishRangeDayMap.get(DayOfWeek.TUESDAY).size() / (double) totalTues);
        bearRangeSb.append(",");
        bearRangeSb.append((double) bearishRangeDayMap.get(DayOfWeek.WEDNESDAY).size() / (double) totalWeds);
        bearRangeSb.append(",");
        bearRangeSb.append((double) bearishRangeDayMap.get(DayOfWeek.THURSDAY).size() / (double) totalThurs);
        bearRangeSb.append(",");
        bearRangeSb.append((double) bearishRangeDayMap.get(DayOfWeek.FRIDAY).size() / (double) totalFris);
        System.out.println(bearRangeSb);

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

        TrendVsRangeVsBullRangeVsBearRangeStats trendVsRangeVsBullRangeVsBearRangeStats = new TrendVsRangeVsBullRangeVsBearRangeStats();
        trendVsRangeVsBullRangeVsBearRangeStats.evaluate();
    }
}

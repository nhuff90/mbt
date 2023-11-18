package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.rules.nate.DailyTrend;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.*;

public class TrendVsRangeBasedOnOmarStats extends TrendVsRangeStats {

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

        printOmarEqIbRange();
        printOmarEqAmRange();
        printOmarEqOpening15mRange();
        printOmarEqOpening5mRange();
    }

    private void printOmarEqAmRange() {
    /*
    If OMARH=AMH or OMARL=AML, % chance for trend day
     */
        List<DailyMgi> omarHighEqAMH = new ArrayList<>();
        List<DailyMgi> omarLowEqAML = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                omarHighEqAMH.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                omarLowEqAML.add(dailyMgi);
            }
        }

        /*
        OMARH=AMH
         */
        List<DailyMgi> omarHighEqAMHTrendUp = new ArrayList<>();
        List<DailyMgi> omarHighEqAMHTrendDown = new ArrayList<>();
        List<DailyMgi> omarHighEqAMHRange = new ArrayList<>();
        omarHighEqAMH.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarHighEqAMHTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarHighEqAMHTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarHighEqAMHRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("OMARH=AMH");
        StringBuilder omarHighEqAMHSb = new StringBuilder();
        omarHighEqAMHSb.append(omarHighEqAMHTrendUp.size());
        omarHighEqAMHSb.append(",");
        omarHighEqAMHSb.append(omarHighEqAMHTrendDown.size());
        omarHighEqAMHSb.append(",");
        omarHighEqAMHSb.append(omarHighEqAMHRange.size());
        omarHighEqAMHSb.append(",");
        omarHighEqAMHSb.append(omarHighEqAMH.size());

        System.out.println(omarHighEqAMHSb);

        /*
        OMARL=AML
         */
        List<DailyMgi> omarLowEqAMLTrendUp = new ArrayList<>();
        List<DailyMgi> omarLowEqAMLTrendDown = new ArrayList<>();
        List<DailyMgi> omarLowEqAMLRange = new ArrayList<>();
        omarLowEqAML.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarLowEqAMLTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarLowEqAMLTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarLowEqAMLRange.add(dailyMgi);
            }
        });
        System.out.println("OMARL=AML");
        StringBuilder omarLowEqAMLSb = new StringBuilder();
        omarLowEqAMLSb.append(omarLowEqAMLTrendUp.size());
        omarLowEqAMLSb.append(",");
        omarLowEqAMLSb.append(omarLowEqAMLTrendDown.size());
        omarLowEqAMLSb.append(",");
        omarLowEqAMLSb.append(omarLowEqAMLRange.size());
        omarLowEqAMLSb.append(",");
        omarLowEqAMLSb.append(omarLowEqAML.size());

        System.out.println(omarLowEqAMLSb);
    }

    private void printOmarEqIbRange() {
    /*
    If OMARH=IBH or OMARL=IBL, % chance for trend day
     */
        List<DailyMgi> omarHighEq = new ArrayList<>();
        List<DailyMgi> omarLowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                omarHighEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                omarLowEq.add(dailyMgi);
            }
        }

        /*
        OMARH=IBH
         */
        List<DailyMgi> omarHighEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarHighEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarHighEqRange = new ArrayList<>();
        omarHighEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarHighEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarHighEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarHighEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("OMARH=IBH");
        StringBuilder omarHighEqSb = new StringBuilder();
        omarHighEqSb.append(omarHighEqTrendUp.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqTrendDown.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqRange.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEq.size());

        System.out.println(omarHighEqSb);

        /*
        OMARL=IBL
         */
        List<DailyMgi> omarLowEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarLowEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarLowEqRange = new ArrayList<>();
        omarLowEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarLowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarLowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarLowEqRange.add(dailyMgi);
            }
        });
        System.out.println("OMARL=IBL");
        StringBuilder omarLowEqSb = new StringBuilder();
        omarLowEqSb.append(omarLowEqTrendUp.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqTrendDown.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqRange.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEq.size());

        System.out.println(omarLowEqSb);
    }

    private void printOmarEqOpening15mRange() {
        List<DailyMgi> omarHighEq = new ArrayList<>();
        List<DailyMgi> omarLowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice())) {
                omarHighEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice())) {
                omarLowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> omarHighEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarHighEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarHighEqRange = new ArrayList<>();
        omarHighEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarHighEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarHighEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarHighEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("OMARH=O15mH");
        StringBuilder omarHighEqSb = new StringBuilder();
        omarHighEqSb.append(omarHighEqTrendUp.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqTrendDown.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqRange.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEq.size());

        System.out.println(omarHighEqSb);

        List<DailyMgi> omarLowEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarLowEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarLowEqRange = new ArrayList<>();
        omarLowEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarLowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarLowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarLowEqRange.add(dailyMgi);
            }
        });
        System.out.println("OMARL=O15mL");
        StringBuilder omarLowEqSb = new StringBuilder();
        omarLowEqSb.append(omarLowEqTrendUp.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqTrendDown.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqRange.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEq.size());

        System.out.println(omarLowEqSb);
    }

    private void printOmarEqOpening5mRange() {
        List<DailyMgi> omarHighEq = new ArrayList<>();
        List<DailyMgi> omarLowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice())) {
                omarHighEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice())) {
                omarLowEq.add(dailyMgi);
            }
        }

        List<DailyMgi> omarHighEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarHighEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarHighEqRange = new ArrayList<>();
        omarHighEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarHighEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarHighEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarHighEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("OMARH=O5mH");
        StringBuilder omarHighEqSb = new StringBuilder();
        omarHighEqSb.append(omarHighEqTrendUp.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqTrendDown.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEqRange.size());
        omarHighEqSb.append(",");
        omarHighEqSb.append(omarHighEq.size());

        System.out.println(omarHighEqSb);

        List<DailyMgi> omarLowEqTrendUp = new ArrayList<>();
        List<DailyMgi> omarLowEqTrendDown = new ArrayList<>();
        List<DailyMgi> omarLowEqRange = new ArrayList<>();
        omarLowEq.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                omarLowEqTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                omarLowEqTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                omarLowEqRange.add(dailyMgi);
            }
        });
        System.out.println("OMARL=O5mL");
        StringBuilder omarLowEqSb = new StringBuilder();
        omarLowEqSb.append(omarLowEqTrendUp.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqTrendDown.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEqRange.size());
        omarLowEqSb.append(",");
        omarLowEqSb.append(omarLowEq.size());

        System.out.println(omarLowEqSb);
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
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 13), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        TrendVsRangeBasedOnOmarStats nHodBy30mPeriodStatsTest = new TrendVsRangeBasedOnOmarStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

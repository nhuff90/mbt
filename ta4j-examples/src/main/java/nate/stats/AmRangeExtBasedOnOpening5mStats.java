package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AmRangeExtBasedOnOpening5mStats extends TrendVsRangeStats {
    //todo - Create unit test!!

    public static TrendVsRangeDailyMgiOhlcResults trendMap = new TrendVsRangeDailyMgiOhlcResults();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        trendMap = populateTrendMap(dailyMgiMap);
        printResults();
    }

    protected TrendVsRangeDailyMgiOhlcResults populateTrendMap(Map<LocalDate, DailyMgi> dailyMgiMap) {
        TrendVsRangeDailyMgiOhlcResults trendMap = new TrendVsRangeDailyMgiOhlcResults();
        dailyMgiMap.forEach((date, dailyMgi) -> {

            if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
                /*
                Trend up
                1. AM 150% extension after AM Range Close
                 */
                /*
                Trend down
                1. AM -150% extension after AM Range Close
                 */
                if (dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getPostAmRangeOhlc().getHigh() != null &&
                        dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, true)) &&
                        dailyMgi.getAmRangeOhlc().getLow() != null && dailyMgi.getPostAmRangeOhlc().getLow() != null &&
                        dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThanOrEqual(dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, false))) {
                    // High and Low extensions met. Pick the first
                    //todo - figure this out
                    Boolean breakIsUp = isFirstPriceBreakAfterTimeUp(dailyMgi, dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, true), dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, false), MarketTime.RTH_1005);

                    if (breakIsUp != null && breakIsUp) {
                        System.out.println(date + " Trend_Up");
                        trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());
                    } else if (breakIsUp != null && !breakIsUp) {
                        System.out.println(date + " Trend_Down");
                        trendMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());
                    }

                } else if (dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getPostAmRangeOhlc().getHigh() != null &&
                        dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, true))) {
                    System.out.println(date + " Trend_Up");
                    trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getAmRangeOhlc().getLow() != null && dailyMgi.getPostAmRangeOhlc().getLow() != null &&
                        dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThanOrEqual(dailyMgi.getAmRangeOhlc().getExtensionOfRange(0.5, false))) {
                    System.out.println(date + " Trend_Down");
                    trendMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());

                }

                if (!trendMap.getTrendDownMap().containsKey(dailyMgi) && !trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                    System.out.println(date + " Range");
                    trendMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());

                }
            }
        });
        return trendMap;
    }

    private Boolean isFirstPriceBreakAfterTimeUp(DailyMgi dailyMgi, Num priceToBreakUp, Num priceToBreakDown, MarketTime startTime) {
        for (OHLCIndicator ohlcIndicator : dailyMgi.getOneMinOhlcList()) {
            if (TimeUtils.isAfter(ohlcIndicator.getOpen().getTime(), startTime)) {
                if (ohlcIndicator.getHigh().getPrice().isGreaterThanOrEqual(priceToBreakUp)) {
                    return true;
                } else if (ohlcIndicator.getLow().getPrice().isLessThanOrEqual(priceToBreakDown)) {
                    return false;
                }
            }
        }
        return null;
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

        printEqIbRange();
        printEqAmRange();
        printEqO15mRange();
    }

    private void printEqAmRange() {
        List<DailyMgi> highEqAMH = new ArrayList<>();
        List<DailyMgi> lowEqAML = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : trendMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEqAMH.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEqAML.add(dailyMgi);
            }
        }

        /*
        High = High
         */

        List<DailyMgi> highEqAMHTrendUp = new ArrayList<>();
        List<DailyMgi> highEqAMHTrendDown = new ArrayList<>();
        List<DailyMgi> highEqAMHRange = new ArrayList<>();
        highEqAMH.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                highEqAMHTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                highEqAMHTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                highEqAMHRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.println("O5mH=AMH");
        StringBuilder highEqAMHSb = new StringBuilder();
        highEqAMHSb.append(highEqAMHTrendUp.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highEqAMHTrendDown.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highEqAMHRange.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highEqAMH.size());

        System.out.println(highEqAMHSb);


        /*
        Low = Low
         */
        List<DailyMgi> lowEqAMLTrendUp = new ArrayList<>();
        List<DailyMgi> lowEqAMLTrendDown = new ArrayList<>();
        List<DailyMgi> lowEqAMLRange = new ArrayList<>();
        lowEqAML.forEach(dailyMgi -> {
            if (trendMap.getTrendUpMap().containsKey(dailyMgi)) {
                lowEqAMLTrendUp.add(dailyMgi);
            } else if (trendMap.getTrendDownMap().containsKey(dailyMgi)) {
                lowEqAMLTrendDown.add(dailyMgi);
            } else if (trendMap.getRangeMap().containsKey(dailyMgi)) {
                lowEqAMLRange.add(dailyMgi);
            }
        });
        System.out.println("O5mL=AML");
        StringBuilder lowEqAMLSb = new StringBuilder();
        lowEqAMLSb.append(lowEqAMLTrendUp.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(lowEqAMLTrendDown.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(lowEqAMLRange.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(lowEqAML.size());

        System.out.println(lowEqAMLSb);
    }

    private void printEqIbRange() {
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

        /*
        High = High
         */
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
        System.out.println("O5mH=IBH");
        StringBuilder highEqSb = new StringBuilder();
        highEqSb.append(highEqTrendUp.size());
        highEqSb.append(",");
        highEqSb.append(highEqTrendDown.size());
        highEqSb.append(",");
        highEqSb.append(highEqRange.size());
        highEqSb.append(",");
        highEqSb.append(highEq.size());

        System.out.println(highEqSb);


        /*
        Low = Low
         */
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
        System.out.println("O5mL=IBL");
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

    private void printEqO15mRange() {
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

        /*
        High = High
         */
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


        /*
        Low = Low
         */
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
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 24), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        AmRangeExtBasedOnOpening5mStats nHodBy30mPeriodStatsTest = new AmRangeExtBasedOnOpening5mStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.DoubleFormatter;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RangeExtensionsBasedOnOpeningRangesStats extends TrendVsRangeStats {
    //todo - Create unit test!!

    public static TrendVsRangeDailyMgiOhlcResults ibExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults amExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults o15mExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults o5mExtensionMap = new TrendVsRangeDailyMgiOhlcResults();

//    public static HighLowEqualsResults o5mIbHighLowEqualsResults = new HighLowEqualsResults();
//    public static HighLowEqualsResults o5mAmHighLowEqualsResults = new HighLowEqualsResults();
//    public static HighLowEqualsResults o5mO15mHighLowEqualsResults = new HighLowEqualsResults();




    private static final String IB = "IB";
    private static final String AM = "AM";
    private static final String O15M = "O15M";
    private static final String O5M = "O5M";
    private static final String OMAR = "OMAR";

    public static Map<String, HighLowEqualsResults> omarO5mHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> omarO15mHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> omarAmHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> omarIbHighLowEqualsResultMap = new LinkedHashMap<>();

    public static Map<String, HighLowEqualsResults> o5mO15mHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> o5mAmHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> o5mIbHighLowEqualsResultMap = new LinkedHashMap<>();

    public static Map<String, HighLowEqualsResults> o15mAmHighLowEqualsResultMap = new LinkedHashMap<>();
    public static Map<String, HighLowEqualsResults> o15mIbHighLowEqualsResultMap = new LinkedHashMap<>();

    public static Map<String, HighLowEqualsResults> amIbHighLowEqualsResultMap = new LinkedHashMap<>();


    private static Map<String, String> highEqHighMapToPrint = new LinkedHashMap<String, String>();
    private static Map<String, String> lowEqLowMapToPrint = new LinkedHashMap<String, String>();

    double percentExtensionToTest = 0.5;

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        populateTrendMaps(dailyMgiMap);
        populateAndPrintResultsMaps();
    }

    protected void populateTrendMaps(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            populateTrendMap(ibExtensionMap, date, dailyMgi, dailyMgi.getIbOhlc());
            populateTrendMap(amExtensionMap, date, dailyMgi, dailyMgi.getAmRangeOhlc());
            populateTrendMap(o15mExtensionMap, date, dailyMgi, dailyMgi.getOpening15MinRangeOhlc());
            populateTrendMap(o5mExtensionMap, date, dailyMgi, dailyMgi.getOpening5MinRangeOhlc());
        });
    }

    private void populateTrendMap(TrendVsRangeDailyMgiOhlcResults trendMap, LocalDate date, DailyMgi dailyMgi, OHLCIndicator rangeToTest) {
        if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
            if (rangeToTest.getHigh() != null &&
                    rangeToTest.getLow() != null) {

                Boolean breakIsUp = isFirstPriceBreakAfterTimeUp(dailyMgi, rangeToTest.getExtensionOfRange(percentExtensionToTest, true), rangeToTest.getExtensionOfRange(percentExtensionToTest, false), MarketTime.RTH_1005);

                if (breakIsUp == null) {
//                    System.out.println(date + " Range");
                    trendMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());
                } else if (breakIsUp) {
//                    System.out.println(date + " Trend_Up");
                    trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());
                } else {
//                    System.out.println(date + " Trend_Down");
                    trendMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());
                }
            }
        }
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

    private void populateAndPrintResultsMaps() {

        /*
        Initialize Trend Maps
         */
        initResultsMaps();

        /*
        Print
         */
        System.out.println("Extension = " + DoubleFormatter.formatPercent(percentExtensionToTest) + ", IB Ext Up Hit 1st, IB Ext Down Hit 1st, Neither Hit, Total, " +
                "AM Ext Up Hit 1st, AM Ext Down Hit 1st, Neither Hit, Total, " +
                "O15m Ext Up Hit 1st, O15m Ext Down Hit 1st, Neither Hit, Total, " +
                "O5m Ext Up Hit 1st, O5m Ext Down Hit 1st, Neither Hit, Total");

        /*
        OMAR
         */
        printOmarEqO5mRange(IB, ibExtensionMap);
        printOmarEqO5mRange(AM, amExtensionMap);
        printOmarEqO5mRange(O15M, o15mExtensionMap);
        printOmarEqO5mRange(O5M, o5mExtensionMap);

        printOmarEqO15mRange(IB, ibExtensionMap);
        printOmarEqO15mRange(AM, amExtensionMap);
        printOmarEqO15mRange(O15M, o15mExtensionMap);

        printOmarEqAmRange(IB, ibExtensionMap);
        printOmarEqAmRange(AM, amExtensionMap);

        printOmarEqIbRange(IB, ibExtensionMap);

        /*
        O5m
         */
        printO5mEqO15mRange(IB, ibExtensionMap);
        printO5mEqO15mRange(AM, amExtensionMap);
        printO5mEqO15mRange(O15M, o15mExtensionMap);

        printO5mEqAMRange(IB, ibExtensionMap);
        printO5mEqAMRange(AM, amExtensionMap);

        printO5mEqIBRange(IB, ibExtensionMap);

        /*
        O15m
         */
        printO15mEqAMRange(IB, ibExtensionMap);
        printO15mEqAMRange(AM, amExtensionMap);

        printO15mEqIBRange(IB, ibExtensionMap);

        /*
        AM
         */
        printAmEqIBRange(IB, ibExtensionMap);

        printMaps();
    }

    private void initResultsMaps() {
        initMap(omarO5mHighLowEqualsResultMap);
        initMap(omarO15mHighLowEqualsResultMap);
        initMap(omarAmHighLowEqualsResultMap);
        initMap(omarIbHighLowEqualsResultMap);

        initMap(o5mO15mHighLowEqualsResultMap);
        initMap(o5mAmHighLowEqualsResultMap);
        initMap(o5mIbHighLowEqualsResultMap);

        initMap(o15mAmHighLowEqualsResultMap);
        initMap(o15mIbHighLowEqualsResultMap);

        initMap(amIbHighLowEqualsResultMap);
    }

    private void initMap(Map<String, HighLowEqualsResults> map) {
        map.put(IB, new HighLowEqualsResults());
        map.put(AM, new HighLowEqualsResults());
        map.put(O15M, new HighLowEqualsResults());
        map.put(O5M, new HighLowEqualsResults());
        map.put(OMAR, new HighLowEqualsResults());
    }

    private void printMaps() {
        highEqHighMapToPrint.forEach((str, str2) -> {
            System.out.println(str + ", " + str2);
        });

        lowEqLowMapToPrint.forEach((str, str2) -> {
            System.out.println(str + ", " + str2);
        });
    }

    private void printOmarEqO5mRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "OMARH=O5mH", lowEq, "OMARL=O5mL", omarO5mHighLowEqualsResultMap.get(extensionString));
    }

    private void printOmarEqO15mRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "OMARH=O15mH", lowEq, "OMARL=O15mL", omarO15mHighLowEqualsResultMap.get(extensionString));
    }

    private void printOmarEqAmRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "OMARH=AMH", lowEq, "OMARL=AML", omarAmHighLowEqualsResultMap.get(extensionString));
    }

    private void printOmarEqIbRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "OMARH=IBH", lowEq, "OMARL=IBL", omarIbHighLowEqualsResultMap.get(extensionString));
    }

    private void printO5mEqO15mRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "O5mH=O15mH", lowEq, "O5mL=O15mL", o5mO15mHighLowEqualsResultMap.get(extensionString));
    }

    private void printO5mEqAMRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "O5mH=AMH", lowEq, "O5mL=AML", o5mAmHighLowEqualsResultMap.get(extensionString));
    }

    private void printO5mEqIBRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "O5mH=IBH", lowEq, "O5mL=IBL", o5mIbHighLowEqualsResultMap.get(extensionString));
    }

    private void printO15mEqAMRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "O15mH=AMH", lowEq, "O15mL=AML", o15mAmHighLowEqualsResultMap.get(extensionString));
    }

    private void printO15mEqIBRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "O15mH=IBH", lowEq, "O15mL=IBL", o15mIbHighLowEqualsResultMap.get(extensionString));
    }

    private void printAmEqIBRange(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEq = new ArrayList<>();
        List<DailyMgi> lowEq = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getAmRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getIbOhlc().getHigh().getPrice())) {
                highEq.add(dailyMgi);
            }
            if (dailyMgi.getAmRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getIbOhlc().getLow().getPrice())) {
                lowEq.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, highEq, "AMH=IBH", lowEq, "AML=IBL", amIbHighLowEqualsResultMap.get(extensionString));
    }

    private void addResultsToMapToPrint(TrendVsRangeDailyMgiOhlcResults extensionMap, List<DailyMgi> highEq,
                                        String str, List<DailyMgi> lowEq, String str2,
                                        HighLowEqualsResults highLowEqualsResults) {
        /*
        High = High
         */

        highEq.forEach(dailyMgi -> {
            if (extensionMap.getTrendUpMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqTrendUp.add(dailyMgi);
            } else if (extensionMap.getTrendDownMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqTrendDown.add(dailyMgi);
            } else if (extensionMap.getRangeMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqRange.add(dailyMgi);
            }
        });

        StringBuilder highEqSb = new StringBuilder();
        highEqSb.append(highLowEqualsResults.highEqTrendUp.size());
        highEqSb.append(",");
        highEqSb.append(highLowEqualsResults.highEqTrendDown.size());
        highEqSb.append(",");
        highEqSb.append(highLowEqualsResults.highEqRange.size());
        highEqSb.append(",");
        highEqSb.append(highEq.size());

        if (highEqHighMapToPrint.containsKey(str)) {
            String resultsString = highEqHighMapToPrint.get(str);
            highEqHighMapToPrint.put(str, resultsString + "," + highEqSb.toString());
        } else {
            highEqHighMapToPrint.put(str, highEqSb.toString());
        }

        /*
        Low = Low
         */
        lowEq.forEach(dailyMgi -> {
            if (extensionMap.getTrendUpMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqTrendUp.add(dailyMgi);
            } else if (extensionMap.getTrendDownMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqTrendDown.add(dailyMgi);
            } else if (extensionMap.getRangeMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqRange.add(dailyMgi);
            }
        });
        StringBuilder lowEqSb = new StringBuilder();
        lowEqSb.append(highLowEqualsResults.lowEqTrendUp.size());
        lowEqSb.append(",");
        lowEqSb.append(highLowEqualsResults.lowEqTrendDown.size());
        lowEqSb.append(",");
        lowEqSb.append(highLowEqualsResults.lowEqRange.size());
        lowEqSb.append(",");
        lowEqSb.append(lowEq.size());

        if (lowEqLowMapToPrint.containsKey(str2)) {
            String resultsString = lowEqLowMapToPrint.get(str2);
            lowEqLowMapToPrint.put(str2, resultsString + "," + lowEqSb.toString());
        } else {
            lowEqLowMapToPrint.put(str2, lowEqSb.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        RangeExtensionsBasedOnOpeningRangesStats nHodBy30mPeriodStatsTest = new RangeExtensionsBasedOnOpeningRangesStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }

    public static class HighLowEqualsResults {
        List<DailyMgi> highEqTrendUp = new ArrayList<>();
        List<DailyMgi> highEqTrendDown = new ArrayList<>();
        List<DailyMgi> highEqRange = new ArrayList<>();

        List<DailyMgi> lowEqTrendUp = new ArrayList<>();
        List<DailyMgi> lowEqTrendDown = new ArrayList<>();
        List<DailyMgi> lowEqRange = new ArrayList<>();

        public List<DailyMgi> getHighEqTrendUp() {
            return highEqTrendUp;
        }

        public void setHighEqTrendUp(List<DailyMgi> highEqTrendUp) {
            this.highEqTrendUp = highEqTrendUp;
        }

        public List<DailyMgi> getHighEqTrendDown() {
            return highEqTrendDown;
        }

        public void setHighEqTrendDown(List<DailyMgi> highEqTrendDown) {
            this.highEqTrendDown = highEqTrendDown;
        }

        public List<DailyMgi> getHighEqRange() {
            return highEqRange;
        }

        public void setHighEqRange(List<DailyMgi> highEqRange) {
            this.highEqRange = highEqRange;
        }

        public List<DailyMgi> getLowEqTrendUp() {
            return lowEqTrendUp;
        }

        public void setLowEqTrendUp(List<DailyMgi> lowEqTrendUp) {
            this.lowEqTrendUp = lowEqTrendUp;
        }

        public List<DailyMgi> getLowEqTrendDown() {
            return lowEqTrendDown;
        }

        public void setLowEqTrendDown(List<DailyMgi> lowEqTrendDown) {
            this.lowEqTrendDown = lowEqTrendDown;
        }

        public List<DailyMgi> getLowEqRange() {
            return lowEqRange;
        }

        public void setLowEqRange(List<DailyMgi> lowEqRange) {
            this.lowEqRange = lowEqRange;
        }
    }
}

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
import java.util.*;

public class MicroInsideAm_AmExtensionsStats extends TrendVsRangeStats {
    //todo - Create unit test!!

    public static Map<Double, TrendVsRangeDailyMgiOhlcResults> microInsideAmExtensionMap = new LinkedHashMap<>();

    private static final String MICRO_INSIDE_AM = "Micro Inside AM";

    public static Map<String, Results> microInsideAmResultMap = new LinkedHashMap<>();


    private static Map<String, String> microInsideAmMapToPrint = new LinkedHashMap<String, String>();

    private static final List<Double> percentExtensionsToTestList = Arrays.asList(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);

    @Override
    public void evaluate() {
        evaluate(percentExtensionsToTestList);
    }

    public void evaluate(List<Double> percentExtensionsToTestList) {
        initMapToPrint(percentExtensionsToTestList);
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        for(double percentExtensionsToTest: percentExtensionsToTestList) {
            populateTrendMaps(dailyMgiMap, percentExtensionsToTest);
            populateAndPrintResultsMaps(percentExtensionsToTest);
        }

        printMaps();
    }

    private void initMapToPrint(List<Double> percentExtensionsToTestList) {
        for(double percentExtensionsToTest: percentExtensionsToTestList) {
            microInsideAmMapToPrint.put(getExtensionString(percentExtensionsToTest), "");
            microInsideAmExtensionMap.put(percentExtensionsToTest, new TrendVsRangeDailyMgiOhlcResults());
        }
    }

    public static String getExtensionString(double percentExtensionsToTest) {
        return MICRO_INSIDE_AM + " | AM Ext: " + DoubleFormatter.formatPercent(percentExtensionsToTest);
    }

    protected void populateTrendMaps(Map<LocalDate, DailyMgi> dailyMgiMap, double percentExtensionsToTest) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            populateTrendMap(microInsideAmExtensionMap, date, dailyMgi, dailyMgi.getAmRangeOhlc(), percentExtensionsToTest);
        });
    }

    private void populateTrendMap(Map<Double, TrendVsRangeDailyMgiOhlcResults> trendMap, LocalDate date, DailyMgi dailyMgi, OHLCIndicator rangeToTest, double percentExtensionToTest) {
        if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
            if (rangeToTest.getHigh() != null &&
                    rangeToTest.getLow() != null) {

                Boolean breakIsUp = isFirstPriceBreakAfterTimeUp(dailyMgi, rangeToTest.getExtensionOfRange(percentExtensionToTest, true), rangeToTest.getExtensionOfRange(percentExtensionToTest, false), MarketTime.RTH_1205);

                if (breakIsUp == null) {
//                    System.out.println(date + " Range");
                    trendMap.get(percentExtensionToTest).addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());
                } else if (breakIsUp) {
//                    System.out.println(date + " Trend_Up");
                    trendMap.get(percentExtensionToTest).addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());
                } else {
//                    System.out.println(date + " Trend_Down");
                    trendMap.get(percentExtensionToTest).addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());
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

    private void populateAndPrintResultsMaps(double percentExtensionsToTest) {

        /*
        Initialize Trend Maps
         */
        initResultsMaps(percentExtensionsToTest);
        
        /*
        AM
         */
        populateResultsMapToPrint(MICRO_INSIDE_AM, microInsideAmExtensionMap.get(percentExtensionsToTest), percentExtensionsToTest);
    }

    private void initResultsMaps(double percentExtensionToTest) {
            initMaps(microInsideAmResultMap, percentExtensionToTest);
    }

    private void initMaps(Map<String, Results> map, double percentExtensionToTest) {
        map.put(getExtensionString(percentExtensionToTest), new Results());
    }

    private void printMaps() {
        microInsideAmMapToPrint.forEach((str, str2) -> {
            System.out.println(str + ", " + str2);
        });
    }

    private void populateResultsMapToPrint(String extensionString, TrendVsRangeDailyMgiOhlcResults extensionMap, double percentExtensionToTest) {
        List<DailyMgi> microInsideAm = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getAmRangeOhlc() != null && dailyMgi.getMicroRangeOhlc() != null
                    && dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getAmRangeOhlc().getLow() != null
                    && dailyMgi.getMicroRangeOhlc().getHigh() != null && dailyMgi.getMicroRangeOhlc().getLow() != null
                    && dailyMgi.getAmRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getMicroRangeOhlc().getHigh().getPrice())
                    && dailyMgi.getAmRangeOhlc().getLow().getPrice().isLessThanOrEqual(dailyMgi.getMicroRangeOhlc().getLow().getPrice())) {
                microInsideAm.add(dailyMgi);
            }
        }

        addResultsToMapToPrint(extensionMap, microInsideAm, getExtensionString(percentExtensionToTest), microInsideAmResultMap.get(getExtensionString(percentExtensionToTest)));
    }

    private void addResultsToMapToPrint(TrendVsRangeDailyMgiOhlcResults extensionMap, List<DailyMgi> microInsideAmList,
                                        String str, Results microInsideAmResults) {
        /*
        Micro Inside AM
         */

        microInsideAmList.forEach(dailyMgi -> {
            if (extensionMap.getTrendUpMap().containsKey(dailyMgi)) {
                microInsideAmResults.trendUp.add(dailyMgi);
                System.out.println("Trend Up: " + dailyMgi.getRthOhlc().getOpen().getDate());
            } else if (extensionMap.getTrendDownMap().containsKey(dailyMgi)) {
                microInsideAmResults.trendDown.add(dailyMgi);
                System.out.println("Trend Down: " + dailyMgi.getRthOhlc().getOpen().getDate());
            } else if (extensionMap.getRangeMap().containsKey(dailyMgi)) {
                microInsideAmResults.range.add(dailyMgi);
                System.out.println("Range: " + dailyMgi.getRthOhlc().getOpen().getDate());
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(microInsideAmResults.trendUp.size());
        sb.append(",");
        sb.append(microInsideAmResults.trendDown.size());
        sb.append(",");
        sb.append(microInsideAmResults.range.size());
        sb.append(",");
        sb.append(microInsideAmList.size());

        if (microInsideAmMapToPrint.containsKey(str) && !microInsideAmMapToPrint.get(str).equals("")) {
            String resultsString = microInsideAmMapToPrint.get(str);
            microInsideAmMapToPrint.put(str, resultsString + "," + sb.toString());
        } else {
            microInsideAmMapToPrint.put(str, sb.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        MicroInsideAm_AmExtensionsStats rangeExtensionsBasedOnOpeningRangesStats = new MicroInsideAm_AmExtensionsStats();
        rangeExtensionsBasedOnOpeningRangesStats.evaluate(percentExtensionsToTestList);
    }

    public static void main(List<Double> percentExtensionsToTestList) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        MicroInsideAm_AmExtensionsStats rangeExtensionsBasedOnOpeningRangesStats = new MicroInsideAm_AmExtensionsStats();
        rangeExtensionsBasedOnOpeningRangesStats.evaluate(percentExtensionsToTestList);
    }

    public static class Results {
        List<DailyMgi> trendUp = new ArrayList<>();
        List<DailyMgi> trendDown = new ArrayList<>();
        List<DailyMgi> range = new ArrayList<>();

        public List<DailyMgi> getTrendUp() {
            return trendUp;
        }

        public void setTrendUp(List<DailyMgi> trendUp) {
            this.trendUp = trendUp;
        }

        public List<DailyMgi> getTrendDown() {
            return trendDown;
        }

        public void setTrendDown(List<DailyMgi> trendDown) {
            this.trendDown = trendDown;
        }

        public List<DailyMgi> getRange() {
            return range;
        }

        public void setRange(List<DailyMgi> range) {
            this.range = range;
        }
    }
}

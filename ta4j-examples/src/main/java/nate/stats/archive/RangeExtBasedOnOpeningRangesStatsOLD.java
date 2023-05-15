package nate.stats.archive;

import nate.stats.TrendVsRangeStats;
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
import java.util.List;
import java.util.Map;

public class RangeExtBasedOnOpeningRangesStatsOLD extends TrendVsRangeStats {
    //todo - Create unit test!!

    public static TrendVsRangeDailyMgiOhlcResults ibExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults amExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults o15mExtensionMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrendVsRangeDailyMgiOhlcResults o5mExtensionMap = new TrendVsRangeDailyMgiOhlcResults();

    public static HighLowEqualsResults amIbHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults o15mIbHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults o5mIbHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults omarIbHighLowEqualsResults = new HighLowEqualsResults();

    public static HighLowEqualsResults o15mAmHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults o5mAmHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults omarAmHighLowEqualsResults = new HighLowEqualsResults();

    public static HighLowEqualsResults o5mO15mHighLowEqualsResults = new HighLowEqualsResults();
    public static HighLowEqualsResults omarO15mHighLowEqualsResults = new HighLowEqualsResults();

    public static HighLowEqualsResults omarO5mHighLowEqualsResults = new HighLowEqualsResults();

    double percentExtensionToTest = 1;

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        populateTrendMaps(dailyMgiMap);
        printResults();
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
                    System.out.println(date + " Range");
                    trendMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());
                } else if (breakIsUp) {
                    System.out.println(date + " Trend_Up");
                    trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());
                } else {
                    System.out.println(date + " Trend_Down");
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

    private void printResults() {
        System.out.println("IB - Hits, Total Days");
        System.out.println(ibExtensionMap.getTrendUpMap().size() + ", " + ibExtensionMap.getTotalEntries());
        System.out.println(ibExtensionMap.getTrendDownMap().size() + ", " + ibExtensionMap.getTotalEntries());
        System.out.println(ibExtensionMap.getRangeMap().size() + ", " + ibExtensionMap.getTotalEntries());
        System.out.println("AM - Hits, Total Days");
        System.out.println(amExtensionMap.getTrendUpMap().size() + ", " + amExtensionMap.getTotalEntries());
        System.out.println(amExtensionMap.getTrendDownMap().size() + ", " + amExtensionMap.getTotalEntries());
        System.out.println(amExtensionMap.getRangeMap().size() + ", " + amExtensionMap.getTotalEntries());
        System.out.println("O15m - Hits, Total Days");
        System.out.println(o15mExtensionMap.getTrendUpMap().size() + ", " + o15mExtensionMap.getTotalEntries());
        System.out.println(o15mExtensionMap.getTrendDownMap().size() + ", " + o15mExtensionMap.getTotalEntries());
        System.out.println(o15mExtensionMap.getRangeMap().size() + ", " + o15mExtensionMap.getTotalEntries());
        System.out.println("O5m - Hits, Total Days");
        System.out.println(o5mExtensionMap.getTrendUpMap().size() + ", " + o5mExtensionMap.getTotalEntries());
        System.out.println(o5mExtensionMap.getTrendDownMap().size() + ", " + o5mExtensionMap.getTotalEntries());
        System.out.println(o5mExtensionMap.getRangeMap().size() + ", " + o5mExtensionMap.getTotalEntries());
        /*
        Print
         */
        System.out.println("~~~~~ OMAR Tests - Testing Extension: " + percentExtensionToTest + "% ~~~~~");
        printOmarEqIbRange(ibExtensionMap);
        printOmarEqAmRange(amExtensionMap);
        printOmarEqO15mRange(o15mExtensionMap);
        printOmarEqO5mRange(o5mExtensionMap);

        System.out.println("");
        System.out.println("~~~~~ O5m Tests - Testing Extension: " + percentExtensionToTest + "% ~~~~~");
        printO5mEqIBRange(ibExtensionMap);
        printO5mEqAmRange(amExtensionMap);
        printO5mEqO15mRange(o15mExtensionMap);

        System.out.println("");
        System.out.println("~~~~~ O15m Tests - Testing Extension: " + percentExtensionToTest + "% ~~~~~");
        printO15mEqIbRange(ibExtensionMap);
        printO15mEqAmRange(amExtensionMap);

        System.out.println("");
        System.out.println("~~~~~ AM Tests - Testing Extension: " + percentExtensionToTest + "% ~~~~~");
        printAmRangeEqIbRange(ibExtensionMap);
    }

    private void printO15mEqAmRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEqAMH = new ArrayList<>();
        List<DailyMgi> lowEqAML = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening15MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEqAMH.add(dailyMgi);
            }
            if (dailyMgi.getOpening15MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEqAML.add(dailyMgi);
            }
        }

        printResults(extensionMap, highEqAMH, "O15mH=AMH", lowEqAML, "O15mL=AML", o15mAmHighLowEqualsResults);
    }
    private void printO5mEqAmRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEqAMH = new ArrayList<>();
        List<DailyMgi> lowEqAML = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOpening5MinRangeOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEqAMH.add(dailyMgi);
            }
            if (dailyMgi.getOpening5MinRangeOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEqAML.add(dailyMgi);
            }
        }

        printResults(extensionMap, highEqAMH, "O5mH=AMH", lowEqAML, "O5mL=AML", o5mAmHighLowEqualsResults);
    }
    private void printOmarEqAmRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
        List<DailyMgi> highEqAMH = new ArrayList<>();
        List<DailyMgi> lowEqAML = new ArrayList<>();
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : extensionMap.getAllMap().entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            if (dailyMgi.getOmarOhlc().getHigh().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {
                highEqAMH.add(dailyMgi);
            }
            if (dailyMgi.getOmarOhlc().getLow().getPrice().isEqual(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                lowEqAML.add(dailyMgi);
            }
        }

        printResults(extensionMap, highEqAMH, "OMARH=AMH", lowEqAML, "OMAR=AML", omarAmHighLowEqualsResults);
    }
    private void printO15mEqIbRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "O15mH=IBH", lowEq, "O15mL=IBL", o15mIbHighLowEqualsResults);
    }
    private void printO5mEqIBRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "O5mH=IBH", lowEq, "O5mL=IBL", o5mIbHighLowEqualsResults);
    }
    private void printAmRangeEqIbRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "AMH=IBH", lowEq, "AML=IBL", amIbHighLowEqualsResults);
    }
    private void printOmarEqIbRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "OMARH=IBH", lowEq, "OMARL=IBL", omarIbHighLowEqualsResults);
    }
    private void printOmarEqO15mRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "OmarH=O15mH", lowEq, "OmarL=O15mL", omarO15mHighLowEqualsResults);
    }
    private void printO5mEqO15mRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "O5mH=O15mH", lowEq, "O5mL=O15mL", o5mO15mHighLowEqualsResults);
    }
    private void printOmarEqO5mRange(TrendVsRangeDailyMgiOhlcResults extensionMap) {
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

        printResults(extensionMap, highEq, "OMARH=O5mH", lowEq, "OMARL=O5mL", omarO5mHighLowEqualsResults);
    }

    private void printResults(TrendVsRangeDailyMgiOhlcResults extensionMap, List<DailyMgi> highEqAMH, String str, List<DailyMgi> lowEqAML, String str2, HighLowEqualsResults highLowEqualsResults) {
        /*
        High = High
         */

        highEqAMH.forEach(dailyMgi -> {
            if (extensionMap.getTrendUpMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqTrendUp.add(dailyMgi);
            } else if (extensionMap.getTrendDownMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqTrendDown.add(dailyMgi);
            } else if (extensionMap.getRangeMap().containsKey(dailyMgi)) {
                highLowEqualsResults.highEqRange.add(dailyMgi);
            }
        });
        System.out.println("Trend Up, Trend Down, Range, Total, Trend Up %, Trend Down %, Range %");
        System.out.print(str + ",");
        StringBuilder highEqAMHSb = new StringBuilder();
        highEqAMHSb.append(highLowEqualsResults.highEqTrendUp.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highLowEqualsResults.highEqTrendDown.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highLowEqualsResults.highEqRange.size());
        highEqAMHSb.append(",");
        highEqAMHSb.append(highEqAMH.size());

        System.out.println(highEqAMHSb);


        /*
        Low = Low
         */
        lowEqAML.forEach(dailyMgi -> {
            if (extensionMap.getTrendUpMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqTrendUp.add(dailyMgi);
            } else if (extensionMap.getTrendDownMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqTrendDown.add(dailyMgi);
            } else if (extensionMap.getRangeMap().containsKey(dailyMgi)) {
                highLowEqualsResults.lowEqRange.add(dailyMgi);
            }
        });
        System.out.print(str2 + ",");
        StringBuilder lowEqAMLSb = new StringBuilder();
        lowEqAMLSb.append(highLowEqualsResults.lowEqTrendUp.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(highLowEqualsResults.lowEqTrendDown.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(highLowEqualsResults.lowEqRange.size());
        lowEqAMLSb.append(",");
        lowEqAMLSb.append(lowEqAML.size());

        System.out.println(lowEqAMLSb);
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        RangeExtBasedOnOpeningRangesStatsOLD nHodBy30mPeriodStatsTest = new RangeExtBasedOnOpeningRangesStatsOLD();
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

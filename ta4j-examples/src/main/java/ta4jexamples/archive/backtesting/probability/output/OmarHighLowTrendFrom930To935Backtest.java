package ta4jexamples.archive.backtesting.probability.output;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.ResultsAnalysisAsTable;
import org.ta4j.core.analysis.ResultsAnalysisWithRowName;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.MarketTimeRanges;
import ta4jexamples.archive.backtesting.probability.edge.EdgeOmarHodLodTrendBacktest;
import ta4jexamples.archive.backtesting.probability.edge.omar.EdgeOmarHodLodWithVwapAndWvwapTrendBacktest;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class OmarHighLowTrendFrom930To935Backtest {
    static int startYear = 2021;
    static int endYear = 2022;

    static Map<MarketTimeRanges, String> marketTimeRangesStringMap = new HashMap<MarketTimeRanges, String>() {{
        put(MarketTimeRanges.OPENING_5MINS_RANGE, "930-935");
//        put(MarketTimeRanges.OPENING_DRIVE_RANGE, "930-945");
    }};
    static Map<MarketTime, String> marketTimeStringMap = new HashMap<MarketTime, String>() {{
        put(MarketTime.AM_END_TIME, "AM Range");
        put(MarketTime.RTH_END_TIME_1558, "RTH Range");
    }};

    static List<Double> rangeMultiplierTakeProfitList = Arrays.asList(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
    static List<ResultsAnalysisWithRowName> resultsAnalysisList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
                ZonedDateTime.of(LocalDate.of(startYear, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
                ZonedDateTime.of(LocalDate.of(endYear, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        for(Double rangeMultiplierTakeProfit : rangeMultiplierTakeProfitList) {
            resultsAnalysisList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.AM_END_TIME, EdgeOmarHodLodTrendBacktest.TrendToTest.UP));
            resultsAnalysisList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.AM_END_TIME, EdgeOmarHodLodTrendBacktest.TrendToTest.DOWN));
            resultsAnalysisList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.RTH_END_TIME_1558, EdgeOmarHodLodTrendBacktest.TrendToTest.UP));
            resultsAnalysisList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.RTH_END_TIME_1558, EdgeOmarHodLodTrendBacktest.TrendToTest.DOWN));
        }

        createAndPrintResults("Omar = 930-935 [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisList);
    }

    private static ResultsAnalysisWithRowName runBacktests(BarSeries series, Double rangeMultiplierTakeProfit,
                                                           MarketTimeRanges range, MarketTime amEndTime,
                                                           EdgeOmarHodLodTrendBacktest.TrendToTest trendToTest) {

        /**
         * Update class here
         */
        TradingRecord omarHighTradingRecord = EdgeOmarHodLodWithVwapAndWvwapTrendBacktest.runOmarTradingRecord(series,
                rangeMultiplierTakeProfit, range, amEndTime,
                trendToTest);
        String omarHighOrLowStr = (trendToTest == EdgeOmarHodLodTrendBacktest.TrendToTest.UP ? "OMAR Low = " + marketTimeRangesStringMap.get(range) + " Low"
                : "OMAR High = " + marketTimeRangesStringMap.get(range) + " High");
        return new ResultsAnalysisWithRowName(series, omarHighTradingRecord,
                omarHighOrLowStr + " | % chance " + rangeMultiplierTakeProfit + "x " +
                        marketTimeRangesStringMap.get(range) + " Range TP Before " + marketTimeStringMap.get(amEndTime) + " close");
    }

    private static void createAndPrintResults(String tableName, List<String> columnHeaders, List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNameList) {
        ResultsAnalysisAsTable resultsAnalysisAsTableRthRange = new ResultsAnalysisAsTable(tableName,
                columnHeaders, resultsAnalysisWithRowNameList);

        resultsAnalysisAsTableRthRange.print();
    }
}

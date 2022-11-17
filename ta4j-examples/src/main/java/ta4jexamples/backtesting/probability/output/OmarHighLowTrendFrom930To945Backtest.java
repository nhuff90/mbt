package ta4jexamples.backtesting.probability.output;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.ResultsAnalysisAsTable;
import org.ta4j.core.analysis.ResultsAnalysisWithRowName;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.MarketTimeRanges;
import ta4jexamples.backtesting.probability.edge.EdgeOmarHodLodTrendBacktest;
import ta4jexamples.backtesting.probability.edge.omar.EdgeOmarHodLodWithTrendBacktest;
import ta4jexamples.backtesting.probability.edge.omar.EdgeOmarHodLodWithVwapAndWvwapTrendBacktest;
import ta4jexamples.backtesting.probability.edge.omar.EdgeOmarHodLodWithVwapTrendBacktest;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class OmarHighLowTrendFrom930To945Backtest {
    static int startYear = 2018;
    static int endYear = 2022;

    static MarketTimeRanges rangeToTest = MarketTimeRanges.OPENING_DRIVE_RANGE;

    static Map<MarketTimeRanges, String> marketTimeRangesStringMap = new HashMap<MarketTimeRanges, String>() {{
        put(MarketTimeRanges.OPENING_DRIVE_RANGE, "930-945");
    }};
    static Map<MarketTime, String> marketTimeStringMap = new HashMap<MarketTime, String>() {{
        put(MarketTime.AM_END_TIME, "AM Range");
        put(MarketTime.RTH_END_TIME_1558, "RTH Range");
    }};

    static List<Double> rangeMultiplierTakeProfitList = Arrays.asList(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
    static List<ResultsAnalysisWithRowName> resultsAnalysisTrendUpAmRangeList = new ArrayList<>();
    static List<ResultsAnalysisWithRowName> resultsAnalysisTrendDownAmRangeList = new ArrayList<>();
    static List<ResultsAnalysisWithRowName> resultsAnalysisTrendUpRthRangeList = new ArrayList<>();
    static List<ResultsAnalysisWithRowName> resultsAnalysisTrendDownRthRangeList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
                ZonedDateTime.of(LocalDate.of(startYear, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
                ZonedDateTime.of(LocalDate.of(endYear, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        for(Double rangeMultiplierTakeProfit : rangeMultiplierTakeProfitList) {
            resultsAnalysisTrendUpAmRangeList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    rangeToTest, MarketTime.AM_END_TIME, EdgeOmarHodLodTrendBacktest.TrendToTest.UP));
            resultsAnalysisTrendDownAmRangeList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    rangeToTest, MarketTime.AM_END_TIME, EdgeOmarHodLodTrendBacktest.TrendToTest.DOWN));
            resultsAnalysisTrendUpRthRangeList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    rangeToTest, MarketTime.RTH_END_TIME_1558, EdgeOmarHodLodTrendBacktest.TrendToTest.UP));
            resultsAnalysisTrendDownRthRangeList.add(runBacktests(series, rangeMultiplierTakeProfit,
                    rangeToTest, MarketTime.RTH_END_TIME_1558, EdgeOmarHodLodTrendBacktest.TrendToTest.DOWN));
        }

        createAndPrintResults("Omar Low = " +
                        marketTimeRangesStringMap.get(rangeToTest) + " Low. By close of AM Range [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisTrendUpAmRangeList);

        createAndPrintResults("Omar High " + marketTimeRangesStringMap.get(rangeToTest) + " = " +
                        marketTimeRangesStringMap.get(rangeToTest) + " High. By close of AM Range [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisTrendDownAmRangeList);

        createAndPrintResults("Omar Low " + marketTimeRangesStringMap.get(rangeToTest) + " = " +
                        marketTimeRangesStringMap.get(rangeToTest) + " Low. By close of RTH Range [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisTrendUpRthRangeList);

        createAndPrintResults("Omar High " + marketTimeRangesStringMap.get(rangeToTest) + " = " +
                        marketTimeRangesStringMap.get(rangeToTest) + " High. By close of RTH Range [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisTrendDownRthRangeList);
    }

    private static ResultsAnalysisWithRowName runBacktests(BarSeries series, Double rangeMultiplierTakeProfit,
                                                           MarketTimeRanges range, MarketTime amEndTime,
                                                           EdgeOmarHodLodTrendBacktest.TrendToTest trendToTest) {

        /**
         * Update class here
         */
        TradingRecord omarHighTradingRecord = EdgeOmarHodLodWithVwapTrendBacktest.runOmarTradingRecord(series,
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

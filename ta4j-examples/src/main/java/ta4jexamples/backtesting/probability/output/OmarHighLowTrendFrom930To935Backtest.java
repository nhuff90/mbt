package ta4jexamples.backtesting.probability.output;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.ResultsAnalysisAsTable;
import org.ta4j.core.analysis.ResultsAnalysisWithRowName;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.backtesting.probability.OmarHighIs930To935HighTrendDownBacktest;
import ta4jexamples.backtesting.probability.OmarLowIs930To935LowTrendUpBacktest;
import ta4jexamples.backtesting.probability.edge.EdgeOmarHighIs930To935HighTrendDownBacktest;
import ta4jexamples.backtesting.probability.edge.EdgeOmarLowIs930To935LowTrendUpBacktest;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OmarHighLowTrendFrom930To935Backtest {
    static int startYear = 2018;
    static int endYear = 2022;
    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
                ZonedDateTime.of(LocalDate.of(startYear, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
                ZonedDateTime.of(LocalDate.of(endYear, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();


        List<Double> rangeMultiplierTakeProfitList = Arrays.asList(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
        List<ResultsAnalysisWithRowName> resultsAnalysisOmarHighWithRowNamesAmRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> resultsAnalysisOmarLowWithRowNamesAmRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> resultsAnalysisOmarHighWithRowNamesRthRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> resultsAnalysisOmarLowWithRowNamesRthRange = new ArrayList<>();

        List<ResultsAnalysisWithRowName> edgeResultsAnalysisOmarHighWithRowNamesAmRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> edgeResultsAnalysisOmarLowWithRowNamesAmRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> edgeResultsAnalysisOmarHighWithRowNamesRthRange = new ArrayList<>();
        List<ResultsAnalysisWithRowName> edgeResultsAnalysisOmarLowWithRowNamesRthRange = new ArrayList<>();

        for(Double rangeMultiplierTakeProfit: rangeMultiplierTakeProfitList) {
            // By AM Range Close
            TradingRecord omarHighIs930To935TrendDownAmRangeTradingRecord = OmarHighIs930To935HighTrendDownBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.AM_END_TIME);
            resultsAnalysisOmarHighWithRowNamesAmRange.add(new ResultsAnalysisWithRowName(series, omarHighIs930To935TrendDownAmRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By AM Range End"));

            TradingRecord omarLowIs930To935TrendUpAmRangeTradingRecord = OmarLowIs930To935LowTrendUpBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.AM_END_TIME);
            resultsAnalysisOmarLowWithRowNamesAmRange.add(new ResultsAnalysisWithRowName(series, omarLowIs930To935TrendUpAmRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By AM Range End"));

            // By RTH Range Close
            TradingRecord omarHighIs930To935TrendDownRthRangeTradingRecord = OmarHighIs930To935HighTrendDownBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.RTH_END_TIME_1558);
            resultsAnalysisOmarHighWithRowNamesRthRange.add(new ResultsAnalysisWithRowName(series, omarHighIs930To935TrendDownRthRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By RTH Range End"));

            TradingRecord omarLowIs930To935TrendUpRthRangeTradingRecord = OmarLowIs930To935LowTrendUpBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.RTH_END_TIME_1558);
            resultsAnalysisOmarLowWithRowNamesRthRange.add(new ResultsAnalysisWithRowName(series, omarLowIs930To935TrendUpRthRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By RTH Range End"));

            /**
             * Finding edge
             */
            // By AM Range Close
            TradingRecord findingEdgeOmarHighIs930To935TrendDownAmRangeTradingRecord = EdgeOmarHighIs930To935HighTrendDownBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.AM_END_TIME);
            edgeResultsAnalysisOmarHighWithRowNamesAmRange.add(new ResultsAnalysisWithRowName(series, findingEdgeOmarHighIs930To935TrendDownAmRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By AM Range End"));

            TradingRecord findingEdgeOmarLowIs930To935TrendUpAmRangeTradingRecord = EdgeOmarLowIs930To935LowTrendUpBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.AM_END_TIME);
            edgeResultsAnalysisOmarLowWithRowNamesAmRange.add(new ResultsAnalysisWithRowName(series, findingEdgeOmarLowIs930To935TrendUpAmRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By AM Range End"));

            // By RTH Range Close
            TradingRecord findingEdgeOmarLowIs930To935TrendUpRthRangeTradingRecord = EdgeOmarHighIs930To935HighTrendDownBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.RTH_END_TIME_1558);
            edgeResultsAnalysisOmarHighWithRowNamesRthRange.add(new ResultsAnalysisWithRowName(series, findingEdgeOmarLowIs930To935TrendUpRthRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By RTH Range End"));

            TradingRecord findingEdgeOmarLowIs930To935TrendUpRTHRangeTradingRecord = EdgeOmarLowIs930To935LowTrendUpBacktest.runOmarTradingRecord(series,
                    rangeMultiplierTakeProfit, MarketTime.RTH_END_TIME_1558);
            edgeResultsAnalysisOmarLowWithRowNamesRthRange.add(new ResultsAnalysisWithRowName(series, findingEdgeOmarLowIs930To935TrendUpRTHRangeTradingRecord,
                    "% chance " + rangeMultiplierTakeProfit + "x 930-935 Range By RTH Range End"));
        }

        createAndPrintResults("Omar High = 930-935 High [" + startYear + "-" + endYear + "]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisOmarHighWithRowNamesAmRange);
        createAndPrintResults("Omar Low = 930-935 Low [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisOmarLowWithRowNamesAmRange);

        createAndPrintResults("Omar High = 930-935 High [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisOmarHighWithRowNamesRthRange);
        createAndPrintResults("Omar Low = 930-935 Low [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), resultsAnalysisOmarLowWithRowNamesRthRange);

        /**
         * Finding Edge
         */
//        createAndPrintResults("Omar High = 930-935 High and below DVWAP [" + startYear + "-" + endYear +"]",
//                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarHighWithRowNamesAmRange);
//        createAndPrintResults("Omar Low = 930-935 Low and above DVWAP [" + startYear + "-" + endYear +"]",
//                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarLowWithRowNamesAmRange);
//
//        createAndPrintResults("Omar High = 930-935 High and below DVWAP [" + startYear + "-" + endYear +"]",
//                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarHighWithRowNamesRthRange);
//        createAndPrintResults("Omar Low = 930-935 Low and above DVWAP [" + startYear + "-" + endYear +"]",
//                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarLowWithRowNamesRthRange);


        createAndPrintResults("Omar High = 930-935 High and below DVWAP/WVWAP [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarHighWithRowNamesAmRange);
        createAndPrintResults("Omar Low = 930-935 Low and above DVWAP/WVWAP [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarLowWithRowNamesAmRange);

        createAndPrintResults("Omar High = 930-935 High and below DVWAP/WVWAP [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarHighWithRowNamesRthRange);
        createAndPrintResults("Omar Low = 930-935 Low and above DVWAP/WVWAP [" + startYear + "-" + endYear +"]",
                Arrays.asList("Win%", "EV", "Max Drawdown"), edgeResultsAnalysisOmarLowWithRowNamesRthRange);
    }

    private static void createAndPrintResults(String tableName, List<String> columnHeaders, List<ResultsAnalysisWithRowName> resultsAnalysisWithRowNameList) {
        ResultsAnalysisAsTable resultsAnalysisAsTableRthRange = new ResultsAnalysisAsTable(tableName,
                columnHeaders, resultsAnalysisWithRowNameList);

        resultsAnalysisAsTableRthRange.print();
    }
}

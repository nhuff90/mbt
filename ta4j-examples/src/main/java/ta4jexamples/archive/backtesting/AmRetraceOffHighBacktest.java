/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2021 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ta4jexamples.archive.backtesting;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;
import org.ta4j.core.indicators.nate.archive.AMRangeIndicator;
import org.ta4j.core.indicators.helpers.OHLCPriceIndicator;
import org.ta4j.core.rules.nate.AMHighRetraceRule;
import org.ta4j.core.rules.nate.AMRangeStopGainRule;
import org.ta4j.core.rules.nate.AMRangeStopLossRule;
import org.ta4j.core.rules.nate.AMRangeTimeStopRule;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;


public class AmRetraceOffHighBacktest {

    static List<Double> enterTradeOffsets = Arrays.asList(0.9, 0.8);
    static List<Double> takeProfitOffsets = Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5);
    static List<Double> stopLossOffsets = Arrays.asList(0.1, 0.2, 0.3);

//    static List<Double> enterTradeOffsets = Arrays.asList(0.9);
//    static List<Double> takeProfitOffsets = Arrays.asList(0.4);
//    static List<Double> stopLossOffsets = Arrays.asList(0.2);

    static Double ENTER_TRADE_OFFSET = 0.9;
    static Double TAKE_PROFIT_OFFSET = 0.4;
    static Double STOP_LOSS_OFFSET = 0.2;

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 29 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesBetweenYears(
//                ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
//                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 29 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        for(Double d1 : enterTradeOffsets) {
            ENTER_TRADE_OFFSET = d1;
            for(Double d2 : takeProfitOffsets) {
                TAKE_PROFIT_OFFSET = d2;
                for(Double d3 : stopLossOffsets) {
                    STOP_LOSS_OFFSET = d3;
                    System.out.println("Testing|" + ENTER_TRADE_OFFSET + "|" + TAKE_PROFIT_OFFSET + "|" + STOP_LOSS_OFFSET);
                    runBacktestAMHighBounce(series, ENTER_TRADE_OFFSET, TAKE_PROFIT_OFFSET, STOP_LOSS_OFFSET);
                }
            }
        }
    }

    private static void runBacktestAMHighBounce(BarSeries series, Double enterTradeOffset, Double takeProfitOffset, Double stopLossOffset) {
        // AM Bounce Backtest
        AMRangeIndicator amRangeIndicatorHighBounce = new AMRangeIndicator(series);
        OHLCPriceIndicator ohlcPriceIndicatorHighBounce = new OHLCPriceIndicator(series);

        TradingRecord amHighBounceTradingRecord = runAMHighTradingRecord(series, amRangeIndicatorHighBounce, ohlcPriceIndicatorHighBounce, enterTradeOffset, takeProfitOffset, stopLossOffset);


        // AM High Bounce
//        System.out.println("----START AM High Bounce----");
        reportAnalysis(series, amHighBounceTradingRecord);
//        System.out.println("----END AM High Bounce----");
    }


    private static TradingRecord runAMHighTradingRecord(BarSeries series, AMRangeIndicator amRangeIndicator, OHLCPriceIndicator ohlcPriceIndicator,
                                                        Double enterTradeOffset, Double takeProfitOffset, Double stopLossOffeset) {
        // Buy Rule
        Rule buyingRule = new AMHighRetraceRule(series, amRangeIndicator, enterTradeOffset);

        // Sell Rule
        Rule sellingRule = new AMRangeStopGainRule(ohlcPriceIndicator, amRangeIndicator, takeProfitOffset)
                .or(new AMRangeStopLossRule(ohlcPriceIndicator, amRangeIndicator, stopLossOffeset))
                .or(new AMRangeTimeStopRule(series));

        // Run backtest
        BarSeriesAverageBarPriceManager seriesManager = new BarSeriesAverageBarPriceManager(series);
        return seriesManager.run(new BaseStrategy(buyingRule, sellingRule), Trade.TradeType.SELL);
    }

    private static void reportAnalysis(BarSeries series, TradingRecord tradingRecord) {
        // Analysis
        if (tradingRecord.getPositionCount() == 0) {
            System.out.println("No trades taken");
        } else {
            ResultsAnalysis resultsAnalysis = new ResultsAnalysis(series, tradingRecord);
//            resultsAnalysis.printResults();
            resultsAnalysis.printResults();
        }
    }

}

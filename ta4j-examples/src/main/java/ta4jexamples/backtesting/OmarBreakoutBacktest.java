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
package ta4jexamples.backtesting;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.OrRule;
import org.ta4j.core.rules.mine.*;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class OmarBreakoutBacktest {

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 3 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesFromSmaApp();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesFromSmaApp( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 11 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesBetweenYears(
//                ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
//                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 29 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        // OMAR breakout up
        System.out.println("OMAR breakouts up");
        reportAnalysis(series, runOmarTradingRecord(series, Trade.TradeType.BUY, 3, -2));

        // OMAR breakout down
        System.out.println("OMAR breakouts down");
        reportAnalysis(series, runOmarTradingRecord(series, Trade.TradeType.SELL, -3, 2));

        
    }

    private static TradingRecord runOmarTradingRecord(BarSeries series, Trade.TradeType tradeType, double takeProfit, double stopLoss) {
        HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
        LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);

        // Buy Rule
        Rule buyingRule = (tradeType.equals(Trade.TradeType.BUY) ? new OmarBreakoutUpRule(series) : new OmarBreakoutDownRule(series));

        // Sell Rule
        Rule sellingRule = new TakeProfitIncludingPriorCandleRule(highPriceIndicator, lowPriceIndicator, takeProfit).or(new StopLossIncludingPriorCandleRule(highPriceIndicator, lowPriceIndicator, stopLoss));

        //TakeProfit and StopLoss
        TakeProfitStopLossAbstract takeProfitStopLossAbstract = new TakeProfitStopLossAbstract() {

            @Override
            public Num getExitPrice(Strategy strategy, TradingRecord tradingRecord, BarSeries barSeries, int i) {
                // Find satisfied rule
                Rule rule = strategy.getExitRule();
                TakeProfitRule takeProfitRule = null;
                StopLossRule stopLossRule;
                if (((OrRule) rule).getRule1() instanceof TakeProfitRule) {
                    takeProfitRule = (TakeProfitRule) ((OrRule) rule).getRule1();
                } else if (((OrRule) rule).getRule1()instanceof StopLossRule) {
                    stopLossRule = (StopLossRule) ((OrRule) rule).getRule1();
                }

                if (((OrRule) rule).getRule2() instanceof TakeProfitRule) {
                    takeProfitRule = (TakeProfitRule) ((OrRule) rule).getRule2();
                } else if (((OrRule) rule).getRule2() instanceof StopLossRule) {
                    stopLossRule = (StopLossRule) ((OrRule) rule).getRule2();
                }
                assert takeProfitRule != null;
                double priceOffset =  (takeProfitRule.isSatisfied() ? takeProfit : stopLoss);
                return tradingRecord.getCurrentPosition().getEntry().getPricePerAsset().plus(DecimalNum.valueOf(priceOffset));
            }
        };

        // Determine entry strategy based on trade type
        BarSeriesConfigureEntryManager.EntryStrategy entryStrategy = (tradeType.equals(Trade.TradeType.BUY) ? BarSeriesConfigureEntryManager.EntryStrategy.PREVIOUS_BAR_HIGH : BarSeriesConfigureEntryManager.EntryStrategy.PREVIOUS_BAR_LOW);

        // Run backtest
        BarSeriesConfigureEntryManager seriesManager = new BarSeriesConfigureEntryManager(series, entryStrategy, takeProfitStopLossAbstract);
        return seriesManager.run(new BaseStrategy(buyingRule, sellingRule), tradeType);
    }

    private static void reportAnalysis(BarSeries series, TradingRecord tradingRecord) {
        // Analysis
        if (tradingRecord.getPositionCount() == 0) {
            System.out.println("No trades taken");
        } else {
            ResultsAnalysis resultsAnalysis = new ResultsAnalysis(series, tradingRecord);
            resultsAnalysis.printResults();
//            resultsAnalysis.printAllTrades();
        }
    }

}

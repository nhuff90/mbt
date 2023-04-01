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
import org.ta4j.core.indicators.DateTimeIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.nate.archive.UpToPMRangeIndicator;
import org.ta4j.core.rules.TimeRangeRule;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;


public class PmOpenRangeBacktest {

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesFromSmaApp();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate(ZonedDateTime.of ( LocalDate.of ( 2022, 6,  9), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of ( LocalDate.of ( 2022, 1,  1), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesFromSmaAppBetweenYears(
//                ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
//                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        // OMAR breakout up
        System.out.println("20-40% Shorts");
        reportAnalysis(series, runUpToPmOpenRangeTradingRecord(series, .20, .40, Trade.TradeType.SELL));

        // OMAR breakout down
        System.out.println("60-80% Longs");
        reportAnalysis(series, runUpToPmOpenRangeTradingRecord(series, .60, .80, Trade.TradeType.BUY));
//        reportAnalysis(series, runOmarTradingRecord(series, Trade.TradeType.SELL, -3, 2));

        
    }

    private static TradingRecord runUpToPmOpenRangeTradingRecord(BarSeries series, double lowRangePercentage, double highRangePercentage, Trade.TradeType tradeType) {
        UpToPMRangeIndicator upToPMRangeIndicator = new UpToPMRangeIndicator(series);

        // Buy Rule
        Rule buyingRule = new UpToPMOpenRangeRule(series, upToPMRangeIndicator, lowRangePercentage, highRangePercentage);

        // Sell Rule
        List<TimeRangeRule.TimeRange> timeRanges = Collections.singletonList(new TimeRangeRule.TimeRange(MarketTime.RTH_END_TIME_1558.getLocalTime(), MarketTime.RTH_END_TIME_1558.getLocalTime()));
        DateTimeIndicator timeIndicator = new DateTimeIndicator(series);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        Rule sellingRule = new TimeRangeRule(timeRanges, timeIndicator);//.or(new StopGainRule(closePrice, 0.5).or(new StopLossRule(closePrice, 0.5))); // sell at close - todo - update?


        // Determine entry strategy based on trade type
//        BarSeriesConfigureEntryManager.EntryStrategy entryStrategy = (tradeType.equals(Trade.TradeType.BUY) ? BarSeriesConfigureEntryManager.EntryStrategy.PREVIOUS_BAR_HIGH : BarSeriesConfigureEntryManager.EntryStrategy.PREVIOUS_BAR_LOW);

        // Run backtest
        BarSeriesConfigureEntryManager seriesManager = new BarSeriesConfigureEntryManager(series);
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

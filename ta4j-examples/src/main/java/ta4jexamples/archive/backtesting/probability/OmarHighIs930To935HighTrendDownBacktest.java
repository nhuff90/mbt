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
package ta4jexamples.archive.backtesting.probability;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;
import org.ta4j.core.indicators.DateTimeIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.nate.archive.OmarRangeIndicator;
import org.ta4j.core.indicators.nate.archive.Opening5MinsRangeIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.rules.TimeRangeRule;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class OmarHighIs930To935HighTrendDownBacktest {

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
                ZonedDateTime.of ( LocalDate.of ( 2022, 12, 31 ), LocalTime.of ( 16, 00 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();


        List<Double> rangeMultiplierTakeProfitList = Arrays.asList(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);

        for(Double rangeMultiplierTakeProfit: rangeMultiplierTakeProfitList) {
            System.out.println("OMAR HOD = 5 min Range HOD -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + "x -- by AM close");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit, MarketTime.AM_END_TIME));


            System.out.println("OMAR HOD = 5 min Range HOD -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + " -- by EOD");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit, MarketTime.RTH_1558));
            System.out.println("");
        }
        
    }

    public static TradingRecord runOmarTradingRecord(BarSeries series, double rangeMultiplierTakeProfit, MarketTime byCloseOfRangeTime) {
        OmarRangeIndicator omarRangeIndicator = new OmarRangeIndicator(series);
        Opening5MinsRangeIndicator opening5MinsRangeIndicator = new Opening5MinsRangeIndicator(series);

        // Buy Rule
        // If OMAR high = opening 5 min range high
        Rule buyingRule = new Opening5MinRangeTrendDownRule(series, omarRangeIndicator, opening5MinsRangeIndicator);

        // Sell Rule
        // If 100% of opening 5 min range OR opening 5 min range high break.
        HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
        LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);

        // Sell Rule
        DateTimeIndicator timeIndicator = new DateTimeIndicator(series);

        // If 1x/1.5x/etc. of opening 5 min range OR by AM close.
        List<TimeRangeRule.TimeRange> timeRanges = Collections.singletonList(new TimeRangeRule.TimeRange(byCloseOfRangeTime.getLocalTime(), byCloseOfRangeTime.getLocalTime()));
        Rule sellingRule = new TakeProfitRangePercentageRule(highPriceIndicator, lowPriceIndicator, opening5MinsRangeIndicator, DoubleNum.valueOf(rangeMultiplierTakeProfit)).or(new TimeRangeRule(timeRanges, timeIndicator));

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
//            resultsAnalysis.printAllTrades();
            resultsAnalysis.printResults();
        }
    }

}

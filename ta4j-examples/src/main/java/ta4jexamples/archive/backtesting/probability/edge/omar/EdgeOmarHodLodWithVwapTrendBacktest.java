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
package ta4jexamples.archive.backtesting.probability.edge.omar;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.DateTimeIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.indicators.nate.archive.OmarRangeIndicator;
import org.ta4j.core.indicators.nate.archive.OpenTo1000RangeIndicator;
import org.ta4j.core.indicators.nate.archive.Opening5MinsRangeIndicator;
import org.ta4j.core.indicators.nate.archive.OpeningDriveRangeIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.TimeRangeRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.MarketTimeRanges;
import ta4jexamples.archive.backtesting.probability.edge.EdgeOmarHodLodTrendBacktest;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Backtest
 * Buy Rule:
 *  -OMAR High = 930-935 High
 *  -Below VWAP and WVWAP
 *
 *  Sell Rule:
 *  -By provided time range
 *
 *  The purpose of this Edge class is to try new things. Once done implementing a backtest,
 *  archive to the omar package.
 */
public class EdgeOmarHodLodWithVwapTrendBacktest implements EdgeOmarHodLodTrendBacktest {
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
            System.out.println("OMAR HOD = 5 min Range HOD OPENING_5MINS_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + "x -- by AM close");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.AM_END_TIME, TrendToTest.DOWN));


            System.out.println("OMAR HOD = 5 min Range HOD OPENING_5MINS_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + " -- by EOD");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.RTH_1558, TrendToTest.DOWN));
            System.out.println("");

            System.out.println("OMAR HOD = 5 min Range HOD OPENING_DRIVE_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + "x -- by AM close");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_DRIVE_RANGE, MarketTime.AM_END_TIME, TrendToTest.DOWN));


            System.out.println("OMAR HOD = 5 min Range HOD OPENING_DRIVE_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + " -- by EOD");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_DRIVE_RANGE, MarketTime.RTH_1558, TrendToTest.DOWN));
            System.out.println("");

            System.out.println("OMAR LOD = 5 min Range LOD OPENING_5MINS_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + "x -- by AM close");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.AM_END_TIME, TrendToTest.UP));


            System.out.println("OMAR LOD = 5 min Range LOD OPENING_5MINS_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + " -- by EOD");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_5MINS_RANGE, MarketTime.RTH_1558, TrendToTest.UP));
            System.out.println("");

            System.out.println("OMAR LOD = 5 min Range LOD OPENING_DRIVE_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + "x -- by AM close");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_DRIVE_RANGE, MarketTime.AM_END_TIME, TrendToTest.UP));


            System.out.println("OMAR LOD = 5 min Range LOD OPENING_DRIVE_RANGE -- rangeMultiplierTakeProfit: " + rangeMultiplierTakeProfit + " -- by EOD");
            reportAnalysis(series, runOmarTradingRecord(series, rangeMultiplierTakeProfit,
                    MarketTimeRanges.OPENING_DRIVE_RANGE, MarketTime.RTH_1558, TrendToTest.UP));
            System.out.println("");
        }
        
    }

    public static TradingRecord runOmarTradingRecord(BarSeries series, double rangeMultiplierTakeProfit,
                                                     MarketTimeRanges testingTimeRange, MarketTime byCloseOfRangeTime,
                                                     TrendToTest trendToTest) {
        OmarRangeIndicator omarRangeIndicator = new OmarRangeIndicator(series);

        // Buy Rule
        VWAPIndicator dailyVwapIndicator = new VWAPIndicator(series, 500);
        VWAPIndicator weeklyVwapIndicator = new VWAPIndicator(series, 5000);

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
        // If OMAR high = opening 5 min range high and below DVWAP and WVWAP
        AbstractRule rangeBuyRule = null;
        AbstractRule vwapBuyRule = null;
        AbstractRule wvwapBuyRule = null;
        CachedIndicator<Range> rangeCachedIndicator = null;
        Trade.TradeType tradeType = null;
        if (testingTimeRange.equals(MarketTimeRanges.OPENING_5MINS_RANGE)) {
            rangeCachedIndicator = new Opening5MinsRangeIndicator(series);
            if (trendToTest.equals(TrendToTest.DOWN)) {
                tradeType = Trade.TradeType.SELL;
                rangeBuyRule = new Opening5MinRangeTrendDownRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new OverIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new OverIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            } else if (trendToTest.equals(TrendToTest.UP)) {
                tradeType = Trade.TradeType.BUY;
                rangeBuyRule = new Opening5MinRangeTrendUpRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new UnderIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new UnderIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            }
        } else if (testingTimeRange.equals(MarketTimeRanges.OPENING_DRIVE_RANGE)) {
            rangeCachedIndicator = new OpeningDriveRangeIndicator(series);
            if (trendToTest.equals(TrendToTest.DOWN)) {
                tradeType = Trade.TradeType.SELL;
                rangeBuyRule = new OpeningDriveTrendDownRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new OverIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new OverIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            } else if (trendToTest.equals(TrendToTest.UP)) {
                tradeType = Trade.TradeType.BUY;
                rangeBuyRule = new OpeningDriveTrendUpRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new UnderIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new UnderIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            }
        } else if (testingTimeRange.equals(MarketTimeRanges.OPEN_TO_1000_RANGE)) {
            rangeCachedIndicator = new OpenTo1000RangeIndicator(series);
            if (trendToTest.equals(TrendToTest.DOWN)) {
                tradeType = Trade.TradeType.SELL;
                rangeBuyRule = new OpeningDriveTrendDownRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new OverIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new OverIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            } else if (trendToTest.equals(TrendToTest.UP)) {
                tradeType = Trade.TradeType.BUY;
                rangeBuyRule = new OpeningDriveTrendUpRule(series, omarRangeIndicator, rangeCachedIndicator);
                vwapBuyRule = new UnderIndicatorRule(dailyVwapIndicator, closePriceIndicator);
                wvwapBuyRule = new UnderIndicatorRule(weeklyVwapIndicator, closePriceIndicator);
            }
        }

        assert rangeBuyRule != null;
        Rule buyingRule = rangeBuyRule
                .and(vwapBuyRule);
//                .and(wvwapBuyRule);

        // Sell Rule
        // If 100% of opening 5 min range.
        HighPriceIndicator highPriceIndicator = new HighPriceIndicator(series);
        LowPriceIndicator lowPriceIndicator = new LowPriceIndicator(series);

        // Sell Rule
        DateTimeIndicator timeIndicator = new DateTimeIndicator(series);

        // If 1x/1.5x/etc. of opening 5 min range OR by AM close.
        List<TimeRangeRule.TimeRange> timeRanges = Collections.singletonList(new TimeRangeRule.TimeRange(byCloseOfRangeTime.getLocalTime(),
                byCloseOfRangeTime.getLocalTime()));
        Rule sellingRule = new TakeProfitRangePercentageRule(highPriceIndicator, lowPriceIndicator, rangeCachedIndicator,
                DoubleNum.valueOf(rangeMultiplierTakeProfit)).or(new TimeRangeRule(timeRanges, timeIndicator));

        // Run backtest
        BarSeriesAverageBarPriceManager seriesManager = new BarSeriesAverageBarPriceManager(series);
        TradingRecord tradingRecord =  seriesManager.run(new BaseStrategy(buyingRule, sellingRule), tradeType);

        // For debugging
//        reportAnalysis(series, tradingRecord);
        return tradingRecord;
    }

    private static void reportAnalysis(BarSeries series, TradingRecord tradingRecord) {
        // Analysis
        if (tradingRecord.getPositionCount() == 0) {
            System.out.println("No trades taken");
        } else {
            ResultsAnalysis resultsAnalysis = new ResultsAnalysis(series, tradingRecord);
            resultsAnalysis.printAllTrades();
            resultsAnalysis.printResults();
        }
    }

}

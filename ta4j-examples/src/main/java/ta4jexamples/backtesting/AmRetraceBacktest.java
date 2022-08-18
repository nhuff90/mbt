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
import org.ta4j.core.analysis.criteria.*;
import org.ta4j.core.analysis.criteria.pnl.*;
import org.ta4j.core.indicators.AMRangeIndicator;
import org.ta4j.core.indicators.helpers.OHLCPriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.*;
import org.ta4j.core.utils.DoubleFormatter;
import ta4jexamples.loaders.CsvBarsLoader;


public class AmRetraceBacktest {


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadSpx1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 4, 5 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadSpx1MinSeries();

        // AM Low Bounce
        AMRangeIndicator amRangeIndicatorLowBounce = new AMRangeIndicator(series);
        OHLCPriceIndicator ohlcPriceIndicatorLowBounce = new OHLCPriceIndicator(series);
        // AM Bounce Backtest
        AMRangeIndicator amRangeIndicatorHighBounce = new AMRangeIndicator(series);
        OHLCPriceIndicator ohlcPriceIndicatorHighBounce = new OHLCPriceIndicator(series);

        TradingRecord amLowBounceTradingRecord = runAMLowTradingRecord(series, amRangeIndicatorLowBounce, ohlcPriceIndicatorLowBounce);
        TradingRecord amHighBounceTradingRecord = runAMHighTradingRecord(series, amRangeIndicatorHighBounce, ohlcPriceIndicatorHighBounce);

//        TradingRecord copyOfAmLowBounceTradingRecord = amLowBounceTradingRecord;
//        filterTradingRecords(amLowBounceTradingRecord, amHighBounceTradingRecord, series);
//        filterTradingRecords(amHighBounceTradingRecord, copyOfAmLowBounceTradingRecord, series);

        // AM Low Bounce
        System.out.println("----START AM Low Bounce----");
        reportAnalysis(series, amLowBounceTradingRecord);
        System.out.println("----END AM Low Bounce----");


        // AM High Bounce
        System.out.println("----START AM High Bounce----");
        reportAnalysis(series, amHighBounceTradingRecord);
        System.out.println("----END AM High Bounce----");

        }

    /**
     * Not worth the hassel...
     * @param series
     * @param amRangeIndicator
     * @param ohlcPriceIndicator
     * @return
     */
//    private static void filterTradingRecords(TradingRecord tradingRecordToReturn, TradingRecord tradingRecord2, BarSeries series) {
//        Map<LocalDate, Bar> mapToCompare = new HashMap<>();
//        tradingRecord2.getPositions().forEach(pos -> {
//            LocalDate localDate = series.getBar(pos.getEntry().getIndex()).getEndTime().toLocalDate();
//            mapToCompare.put(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()), series.getBar(pos.getEntry().getIndex()));
//        });
//        List<Position> positionsToRemove = new ArrayList<>();
//        for(Position pos : tradingRecordToReturn.getPositions()) {
//            Bar bar = series.getBar(pos.getEntry().getIndex());
//            LocalDate localDate = bar.getEndTime().toLocalDate();
//            Bar barFromMap = mapToCompare.get(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()));
//
//            if (barFromMap != null && barFromMap.getEndTime().isBefore(bar.getEndTime())) {
//                positionsToRemove.add(pos);
//            }
//        }
//
//        positionsToRemove.forEach(p -> tradingRecordToReturn.getPositions().remove(p));
//        System.out.println("test");
//    }

    private static TradingRecord runAMHighTradingRecord(BarSeries series, AMRangeIndicator amRangeIndicator, OHLCPriceIndicator ohlcPriceIndicator) {
        // Buy Rule
        Rule buyingRule = new AMHighRetraceRule(series, amRangeIndicator, 0.8);

        // Sell Rule
        Rule sellingRule = new AMRangeStopGainRule(ohlcPriceIndicator, amRangeIndicator, 0.6)
                .or(new AMRangeStopLossRule(ohlcPriceIndicator, amRangeIndicator, 0.2))
                .or(new AMRangeTimeStopRule(series));

        // Run backtest
        BarSeriesAverageBarPriceManager seriesManager = new BarSeriesAverageBarPriceManager(series);
        return seriesManager.run(new BaseStrategy(buyingRule, sellingRule), Trade.TradeType.SELL);
    }

    private static TradingRecord runAMLowTradingRecord(BarSeries series, AMRangeIndicator amRangeIndicator, OHLCPriceIndicator ohlcPriceIndicator) {
        // Buy Rule
        Rule buyingRule = new AMLowRetraceRule(series, amRangeIndicator, 0.2);

        // Sell Rule
        Rule sellingRule = new AMRangeStopGainRule(ohlcPriceIndicator, amRangeIndicator, 0.4)
                .or(new AMRangeStopLossRule(ohlcPriceIndicator, amRangeIndicator, 0.2))
                .or(new AMRangeTimeStopRule(series));

        // Run backtest
        BarSeriesAverageBarPriceManager seriesManager = new BarSeriesAverageBarPriceManager(series);
        return seriesManager.run(new BaseStrategy(buyingRule, sellingRule));
    }

    private static void reportAnalysis(BarSeries series, TradingRecord tradingRecord) {
        // Analysis
        if (tradingRecord.getPositionCount() == 0) {
            System.out.println("No trades taken");
        } else {
            AnalysisCriterion averageProfitCriterion = new AverageProfitCriterion();
            AnalysisCriterion averageLossCriterion = new AverageLossCriterion();
            AnalysisCriterion numberOfWinningPositionsCriterion = new NumberOfWinningPositionsCriterion();
            AnalysisCriterion numberOfLosingPositionsCriterion = new NumberOfLosingPositionsCriterion();
            Num numberOfWinningPositions = numberOfWinningPositionsCriterion.calculate(series, tradingRecord);
            Num numberOfLosingPositions = numberOfLosingPositionsCriterion.calculate(series, tradingRecord);

            // Getting the winning positions ratio
            AnalysisCriterion winningPositionsRatio = new WinningPositionsRatioCriterion();
            System.out.println("Number of positions for our strategy: " + tradingRecord.getPositionCount());
            System.out.println("Winning Trades: " + numberOfWinningPositions + " | Losing Trade: " + numberOfLosingPositions);
            System.out.println("Winning positions ratio: " + DoubleFormatter.formatPercent(winningPositionsRatio.calculate(series, tradingRecord).doubleValue()));

            // Profit Loss
            AnalysisCriterion netProfitCriterion = new ProfitLossCriterion();
            System.out.println("Profit/Loss: " + DoubleFormatter.formatDollar(netProfitCriterion.calculate(series, tradingRecord).doubleValue()));

            // Drawdown
            MaximumDrawdownCriterion maximumDrawdownCriterion = new MaximumDrawdownCriterion();
            System.out.println("Max Drawdown: " + DoubleFormatter.formatPercent(maximumDrawdownCriterion.calculate(series, tradingRecord).doubleValue()));

            // Trade Details
//            for (Position position : tradingRecord.getPositions()) {
//                Bar entryBar = series.getBar(position.getEntry().getIndex());
//                Bar exitBar = series.getBar(position.getExit().getIndex());
//                System.out.println("Date: " + entryBar.getDateName() + " | Profitable?: " + (position.getProfit().doubleValue() > 0) +
//                        " | Entry: " + entryBar.calculateAverageBarPrice() + " | Exit: " + exitBar.calculateAverageBarPrice() +
//                        " | Profit: " + DoubleFormatter.formatDollar(position.getProfit().doubleValue()) +
//                        " | Closed at: " + exitBar.getEndTime());
//
//            }

            // EV
            Num winningEv = averageProfitCriterion.calculate(series, tradingRecord).multipliedBy(numberOfWinningPositions);
            Num losingEv = averageLossCriterion.calculate(series, tradingRecord).multipliedBy(numberOfLosingPositions);
            double ev = winningEv.plus(losingEv).doubleValue() / (tradingRecord.getPositionCount());
            System.out.println("Expected Value: " + DoubleFormatter.formatDollar(ev));
        }
    }

}

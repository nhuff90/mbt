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

import java.util.Arrays;
import java.util.List;


public class AmRetraceBacktest {

    static List<Double> enterTradeOffsets = Arrays.asList(0.9, 0.8, 0.7);
    static List<Double> takeProfitOffsets = Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5);
    static List<Double> stopLossOffsets = Arrays.asList(0.05, 0.1, 0.2, 0.3);

//    static List<Double> enterTradeOffsets = Arrays.asList(0.8);
//    static List<Double> takeProfitOffsets = Arrays.asList(0.35);
//    static List<Double> stopLossOffsets = Arrays.asList(0.05);

    static Double ENTER_TRADE_OFFSET = 0.95;
    static Double TAKE_PROFIT_OFFSET = 0.1;
    static Double STOP_LOSS_OFFSET = 0.05;

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 30 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 30 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        for(Double d1 : enterTradeOffsets) {
            ENTER_TRADE_OFFSET = d1;
            for(Double d2 : takeProfitOffsets) {
                TAKE_PROFIT_OFFSET = d2;
                for(Double d3 : stopLossOffsets) {
                    STOP_LOSS_OFFSET = d3;
                    System.out.println("Testing|" + ENTER_TRADE_OFFSET + "|" + TAKE_PROFIT_OFFSET + "|" + STOP_LOSS_OFFSET);
                    runBacktestAMLowBounce(series, ENTER_TRADE_OFFSET, TAKE_PROFIT_OFFSET, STOP_LOSS_OFFSET);
//                    runBacktestAMHighBounce(series, ENTER_TRADE_OFFSET, TAKE_PROFIT_OFFSET, STOP_LOSS_OFFSET);
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

    private static void runBacktestAMLowBounce(BarSeries series, Double enterTradeOffset, Double takeProfitOffset, Double stopLossOffset) {
        // AM Low Bounce
        AMRangeIndicator amRangeIndicatorLowBounce = new AMRangeIndicator(series);
        OHLCPriceIndicator ohlcPriceIndicatorLowBounce = new OHLCPriceIndicator(series);

        TradingRecord amLowBounceTradingRecord = runAMLowTradingRecord(series, amRangeIndicatorLowBounce, ohlcPriceIndicatorLowBounce, enterTradeOffset, takeProfitOffset, stopLossOffset);

        // AM Low Bounce
//        System.out.println("----START AM Low Bounce----");
        reportAnalysis(series, amLowBounceTradingRecord);
//        System.out.println("----END AM Low Bounce----");
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

    private static TradingRecord runAMLowTradingRecord(BarSeries series, AMRangeIndicator amRangeIndicator, OHLCPriceIndicator ohlcPriceIndicator,
                                                       Double enterTradeOffset, Double takeProfitOffset, Double stopLossOffeset) {
        // Buy Rule
        Rule buyingRule = new AMLowRetraceRule(series, amRangeIndicator, 1-enterTradeOffset);

        // Sell Rule
        Rule sellingRule = new AMRangeStopGainRule(ohlcPriceIndicator, amRangeIndicator, 1-takeProfitOffset)
                .or(new AMRangeStopLossRule(ohlcPriceIndicator, amRangeIndicator, stopLossOffeset))
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
//            System.out.println("Number of positions for our strategy: " + tradingRecord.getPositionCount());
//            System.out.println("Winning Trades: " + numberOfWinningPositions + " | Losing Trade: " + numberOfLosingPositions);
//            System.out.println("Winning positions ratio: " + DoubleFormatter.formatPercent(winningPositionsRatio.calculate(series, tradingRecord).doubleValue()));

            // Profit Loss
            AnalysisCriterion netProfitCriterion = new ProfitLossCriterion();
//            System.out.println("Profit/Loss: " + DoubleFormatter.formatDollar(netProfitCriterion.calculate(series, tradingRecord).doubleValue()));

            // Drawdown
            MaximumDrawdownCriterion maximumDrawdownCriterion = new MaximumDrawdownCriterion();
//            System.out.println("Max Drawdown: " + DoubleFormatter.formatPercent(maximumDrawdownCriterion.calculate(series, tradingRecord).doubleValue()));

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
            double winningPercent = winningPositionsRatio.calculate(series, tradingRecord).doubleValue();
            double ev = winningEv.plus(losingEv).doubleValue() / (tradingRecord.getPositionCount());
//            if (ev > 6.2 && maximumDrawdownCriterion.calculate(series, tradingRecord).doubleValue() < .0075 && winningPercent > 0.6) {
            if (ev > 0 && winningPercent > 0.5) {
                System.out.println("ENTER_TRADE_OFFSET:" + ENTER_TRADE_OFFSET + "|TAKE_PROFIT_OFFSET:" + TAKE_PROFIT_OFFSET + "|STOP_LOSS_OFFSET:" + STOP_LOSS_OFFSET);
                System.out.println("#ofPos " + tradingRecord.getPositionCount() + " ev: " + DoubleFormatter.formatDollar(ev)
                        + " Profit/Loss: " + DoubleFormatter.formatDollar(netProfitCriterion.calculate(series, tradingRecord).doubleValue()) + " WinningTrades: "
                        + numberOfWinningPositions + " LosingTrades: " + numberOfLosingPositions + " MaxDrawdown: "
                        + DoubleFormatter.formatPercent(maximumDrawdownCriterion.calculate(series, tradingRecord).doubleValue()) + " Winning % " + DoubleFormatter.formatPercent(winningPercent));
            }
        }
    }

}

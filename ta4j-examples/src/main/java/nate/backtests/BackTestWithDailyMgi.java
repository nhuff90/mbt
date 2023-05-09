package nate.backtests;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;

public abstract class BackTestWithDailyMgi {

    public static TradingRecord tradingRecord;
    public static BarSeries series;

    protected static TradingRecord run(BarSeries series, Rule buyingRules, Rule sellingRules, Trade.TradeType tradeType) {
        // Run backtest
        BarSeriesWithDailyMgiManager seriesManager = new BarSeriesWithDailyMgiManager(series);
        tradingRecord = seriesManager.run(new BaseStrategy(buyingRules, sellingRules), tradeType);
        reportAnalysis(series, tradingRecord);
        return tradingRecord;
    }

    protected static void reportAnalysis(BarSeries series, TradingRecord tradingRecord) {
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

package org.ta4j.core.analysis;

import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.MaximumDrawdownCriterion;
import org.ta4j.core.analysis.criteria.NumberOfLosingPositionsCriterion;
import org.ta4j.core.analysis.criteria.NumberOfWinningPositionsCriterion;
import org.ta4j.core.analysis.criteria.WinningPositionsRatioCriterion;
import org.ta4j.core.analysis.criteria.pnl.AverageLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.AverageProfitCriterion;
import org.ta4j.core.analysis.criteria.pnl.ProfitLossCriterion;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.DoubleFormatter;

import java.util.Arrays;
import java.util.List;

public class ResultsAnalysis {

    BarSeries series;
    TradingRecord tradingRecord;

    // Win %
    AnalysisCriterion winningPositionsRatio;

    // EV
    AnalysisCriterion averageProfitCriterion;
    AnalysisCriterion averageLossCriterion;
    AnalysisCriterion numberOfWinningPositionsCriterion;
    AnalysisCriterion numberOfLosingPositionsCriterion;

    // P/L
    AnalysisCriterion netProfitCriterion;

    // Max Drawdown
    MaximumDrawdownCriterion maximumDrawdownCriterion;

    //6 Month Rolling Sharpe Ratio

    //Monthly R PnL

    List<String> columnHeaders = Arrays.asList("EV", "MaxDrawdown", "Win%"); // todo - Possibly use this for printing out with headers and data below to use in Excel.

    public ResultsAnalysis(BarSeries series, TradingRecord tradingRecord) {
        this.series = series;
        this.tradingRecord = tradingRecord;
        this.averageProfitCriterion = new AverageProfitCriterion();
        this.averageLossCriterion = new AverageLossCriterion();
        this.numberOfWinningPositionsCriterion = new NumberOfWinningPositionsCriterion();
        this.numberOfLosingPositionsCriterion = new NumberOfLosingPositionsCriterion();
        this.netProfitCriterion = new ProfitLossCriterion();
        this.maximumDrawdownCriterion = new MaximumDrawdownCriterion();
        this.winningPositionsRatio = new WinningPositionsRatioCriterion();
    }

    public double getEv() {
        Num numberOfWinningPositions = numberOfWinningPositionsCriterion.calculate(series, tradingRecord);
        Num numberOfLosingPositions = numberOfLosingPositionsCriterion.calculate(series, tradingRecord);
        Num winningEv = averageProfitCriterion.calculate(series, tradingRecord).multipliedBy(numberOfWinningPositions);
        Num losingEv = averageLossCriterion.calculate(series, tradingRecord).multipliedBy(numberOfLosingPositions);
        double ev = winningEv.plus(losingEv).doubleValue() / (tradingRecord.getPositionCount());
        return ev;
    }

    public String getEvAsString() {
        return DoubleFormatter.formatDollar(getEv());
    }

    public double getMaxDrawdown() {
        return maximumDrawdownCriterion.calculate(series, tradingRecord).doubleValue();
    }

    public String getMaxDrawdownAsString() {
        return DoubleFormatter.formatPercent(getMaxDrawdown());
    }

    public double getWinPercentage() {
        return winningPositionsRatio.calculate(series, tradingRecord).doubleValue();
    }

    public String getWinPercentageAsString() {
        return DoubleFormatter.formatPercent(getWinPercentage());
    }

    public double getWinningTradeCount() {
        return numberOfWinningPositionsCriterion.calculate(series, tradingRecord).doubleValue();
    }

    public double getLosingTradeCount() {
        return numberOfLosingPositionsCriterion.calculate(series, tradingRecord).doubleValue();
    }

    @Override
    public String toString() {
        return "ResultsAnalysis{" +
                "EV=" + getEvAsString() +
                ", Max Drawdown=" + getMaxDrawdownAsString() +
                ", Win%=" + getWinPercentageAsString() +
                ", #OfWins=" + getWinningTradeCount() +
                ", #OfLosses=" + getLosingTradeCount() +
                '}';
    }

    public String toStringValuesOnly() {
        return  getEvAsString() +
                ", " + getMaxDrawdownAsString() +
                ", " + getWinPercentageAsString() +
                ", " + getWinningTradeCount() +
                ", " + getLosingTradeCount() ;
    }

    public void printResults() {
        printResults(null, null, null);
    }

    public void printResults(Double ev, Double maxDrawdown, Double winPercent) {
        // If null, just set to the calculated value so it will not be filtered out.
        ev = (ev != null ? ev : getEv());
        maxDrawdown = (maxDrawdown != null ? maxDrawdown : getMaxDrawdown());
        winPercent = (winPercent != null ? winPercent : getWinPercentage());
        if (ev <= getEv() && maxDrawdown <= getMaxDrawdown() && winPercent <= getWinPercentage()) {
            System.out.println(toString());
        }
    }

    public void printAllTrades() {
        tradingRecord.getPositions().forEach(pos -> {

            Bar entryBar = series.getBar(pos.getEntry().getIndex());
            Bar exitBar = series.getBar(pos.getExit().getIndex());
            System.out.println("Profitable?: " + (pos.getProfit().doubleValue() >= 0 ? "true" : "false")+ "\n " +
                    "\tEntry Time: " + entryBar.getEndTime() + " | Entry:" + pos.getEntry().toString() + "\n" +
                    "\tExit Time: " + exitBar.getEndTime() +  " | Exit: " + pos.getExit().toString());
        });
    }
}

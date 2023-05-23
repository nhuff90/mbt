package org.ta4j.core.analysis;

import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.*;
import org.ta4j.core.analysis.criteria.pnl.AverageLossCriterion;
import org.ta4j.core.analysis.criteria.pnl.AverageProfitCriterion;
import org.ta4j.core.analysis.criteria.pnl.ProfitLossCriterion;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.DoubleFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    AnalysisCriterion returnOverMaxDrawdownCriterion;

    // P/L
    AnalysisCriterion profitCriterion;

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
        this.profitCriterion = new ProfitLossCriterion();
        this.maximumDrawdownCriterion = new MaximumDrawdownCriterion();
        this.winningPositionsRatio = new WinningPositionsRatioCriterion();
        this.returnOverMaxDrawdownCriterion = new ReturnOverMaxDrawdownCriterion();
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

    private double getReturnOverMaxDrawdownCriterion() {
        return returnOverMaxDrawdownCriterion.calculate(series, tradingRecord).doubleValue();
    }

    public String getMaxDrawdownAsString() {
        return DoubleFormatter.formatPercent(getMaxDrawdown());
    }

    private String getReturnOverMaxDrawdownCriterionAsString() {
        return "" + getReturnOverMaxDrawdownCriterion();
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
                "EV= " + getEvAsString() +
                ", Win%= " + getWinPercentageAsString() +
                ", Max Drawdown= " + getMaxDrawdownAsString() +
                ", returnOverMaxDrawdownCriterion= " + getLosingTradeCount() +
                ", #OfWins= " + getWinningTradeCount() +
                ", #OfLosses= " + getLosingTradeCount() +
                '}';
    }

    public String toStringValuesOnly() {
        return  getEvAsString() +
                ", " + getWinPercentageAsString() +
                ", " + getMaxDrawdownAsString() +
                ", " + getReturnOverMaxDrawdownCriterionAsString() +
                ", " + getWinningTradeCount() +
                ", " + getLosingTradeCount() ;
    }

    public void printResults() {
        printResults(null, null, null, null, null);
    }

    public void printResultsWithLabels() {
        printResultsWithLabels(null, null, null, null, null);
    }

    public void printResults(String buyingRulesStr, String sellingRulesStr) {
        printResults(buyingRulesStr, sellingRulesStr, null, null, null);
    }

    public void printResultsWithLabels(String buyingRulesStr, String sellingRulesStr) {
        printResultsWithLabels(buyingRulesStr, sellingRulesStr, null, null, null);
    }

    public void printResultsWithLabels(String buyingRulesStr, String sellingRulesStr, Double ev, Double maxDrawdown, Double winPercent) {
        // If null, just set to the calculated value so it will not be filtered out.
        ev = (ev != null ? ev : getEv());
        maxDrawdown = (maxDrawdown != null ? maxDrawdown : getMaxDrawdown());
        winPercent = (winPercent != null ? winPercent : getWinPercentage());
        String rules = (buyingRulesStr != null && sellingRulesStr != null) ? buyingRulesStr + "," + sellingRulesStr + ", " : "";
        if (ev <= getEv() && maxDrawdown <= getMaxDrawdown() && winPercent <= getWinPercentage()) {
            System.out.println(rules + toString());
        }
    }

    public void printResults(String buyingRulesStr, String sellingRulesStr, Double ev, Double maxDrawdown, Double winPercent) {
        // If null, just set to the calculated value so it will not be filtered out.
        ev = (ev != null ? ev : getEv());
        maxDrawdown = (maxDrawdown != null ? maxDrawdown : getMaxDrawdown());
        winPercent = (winPercent != null ? winPercent : getWinPercentage());
        String rules = (buyingRulesStr != null && sellingRulesStr != null) ? buyingRulesStr + "," + sellingRulesStr + ", " : "";
        if (ev <= getEv() && maxDrawdown <= getMaxDrawdown() && winPercent <= getWinPercentage()) {
            System.out.println(rules + toStringValuesOnly());
        }
    }

    public void printWinRate() {
        System.out.println("Win%: " + getWinPercentageAsString() );
    }

    public void printAllTrades() {
        AtomicInteger i = new AtomicInteger();
        tradingRecord.getPositions().forEach(pos -> {
            i.getAndIncrement();
            System.out.println("Trade # " + i);
            Bar entryBar = series.getBar(pos.getEntry().getIndex());
            Bar exitBar = series.getBar(pos.getExit().getIndex());
            System.out.println("Profitable?: " + (pos.getProfit().doubleValue() >= 0 ? "true" : "false")+ " | P/L: $" + pos.getProfit().doubleValue() + "\n " +
                    "\tEntry Time: " + entryBar.getEndTime() + " | Entry:" + pos.getEntry().toString() + "\n" +
                    "\tExit Time: " + exitBar.getEndTime() +  " | Exit: " + pos.getExit().toString());
        });
    }

    public void exportResultsToCSV() {
        System.out.println("Win%: " + getWinPercentageAsString() );
    }
}

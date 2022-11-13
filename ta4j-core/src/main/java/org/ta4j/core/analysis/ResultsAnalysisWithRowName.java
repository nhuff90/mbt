package org.ta4j.core.analysis;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;

public class ResultsAnalysisWithRowName extends ResultsAnalysis {

    String rowName;

    public ResultsAnalysisWithRowName(BarSeries series, TradingRecord tradingRecord, String rowName) {
        super(series, tradingRecord);
        this.rowName = rowName;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public void printCommaSeparated() {
        System.out.println(rowName + ", " + getWinPercentageAsString() + ", " + getEvAsString() + ", " + getMaxDrawdownAsString());
    }
}

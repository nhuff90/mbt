package org.ta4j.core.rules;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.AMRangeIndicator;

public class AMHighRetraceRule implements Rule {
    BarSeries series;
    AMRangeIndicator amRangeIndicator;

    public AMHighRetraceRule(BarSeries series, AMRangeIndicator amRangeIndicator) {
        this.series = series;
        this.amRangeIndicator = amRangeIndicator;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        return false;
    }
}

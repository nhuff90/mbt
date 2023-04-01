package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class UpToPMRangeIndicator extends CachedIndicator<Range> {
    private final UpToPMRangeTimeIndicator upToPMRangeTimeIndicator;
    private Range upToPmRange;

    public UpToPMRangeIndicator(BarSeries series) {
        super(series);
        upToPMRangeTimeIndicator = new UpToPMRangeTimeIndicator(series);
        upToPmRange = new Range(series.getBarByEndTime(MarketTime.AM_START_TIME.getLocalTime()), MarketTime.AM_START_TIME, MarketTime.PM_START_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (upToPMRangeTimeIndicator.getValue(index)) {
            upToPmRange.setRangeValues(getBarSeries().getBar(index), MarketTime.AM_START_TIME, MarketTime.PM_START_TIME);
        }
        return upToPmRange;
    }
}

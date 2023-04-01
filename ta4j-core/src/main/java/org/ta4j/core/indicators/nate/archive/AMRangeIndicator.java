package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class AMRangeIndicator extends CachedIndicator<Range> {
    private final AMRangeTimeIndicator amRangeTimeIndicator;
    private Range amRange;

    public AMRangeIndicator(BarSeries series) {
        super(series);
        amRangeTimeIndicator = new AMRangeTimeIndicator(series);
        amRange = new Range(series.getBarByEndTime(MarketTime.AM_START_TIME.getLocalTime()), MarketTime.AM_START_TIME, MarketTime.AM_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (amRangeTimeIndicator.getValue(index)) {
            amRange.setRangeValues(getBarSeries().getBar(index), MarketTime.AM_START_TIME, MarketTime.AM_END_TIME);
        }
        return amRange;
    }
}

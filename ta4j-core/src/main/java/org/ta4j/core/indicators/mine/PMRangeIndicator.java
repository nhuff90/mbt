package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class PMRangeIndicator extends CachedIndicator<Range> {
    private final PMRangeTimeIndicator pmRangeTimeIndicator;
    private Range pmRange;

    public PMRangeIndicator(BarSeries series) {
        super(series);
        pmRangeTimeIndicator = new PMRangeTimeIndicator(series);
        pmRange = new Range(series.getBarByEndTime(MarketTime.PM_START_TIME.getLocalTime()), MarketTime.PM_START_TIME, MarketTime.PM_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (pmRangeTimeIndicator.getValue(index)) {
            pmRange.setRangeValues(getBarSeries().getBar(index), MarketTime.PM_START_TIME, MarketTime.PM_END_TIME);
        }
        return pmRange;
    }
}

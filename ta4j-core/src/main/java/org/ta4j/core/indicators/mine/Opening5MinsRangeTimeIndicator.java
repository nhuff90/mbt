package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

public class Opening5MinsRangeTimeIndicator extends CachedIndicator<Boolean> {
    protected Opening5MinsRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return TimeUtils.isInRange(getBarSeries().getBar(index).getEndTime(), MarketTimeRanges.OPENING_5MINS_RANGE);
    }
}

package org.ta4j.core.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.utils.MarketTime;

public class PMRangeTimeIndicator extends CachedIndicator<Boolean>{
    protected PMRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return MarketTime.isInPmRange(getBarSeries().getBar(index).getEndTime());
    }
}

package org.ta4j.core.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.utils.MarketTime;

public class AMRangeTimeIndicator extends CachedIndicator<Boolean>{
    protected AMRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return MarketTime.isInAmRange(getBarSeries().getBar(index).getEndTime());
    }
}

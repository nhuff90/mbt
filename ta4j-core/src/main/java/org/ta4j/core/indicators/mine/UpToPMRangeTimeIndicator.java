package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.utils.MarketTime;

public class UpToPMRangeTimeIndicator extends CachedIndicator<Boolean> {
    protected UpToPMRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return MarketTime.isInUpToPmRange(getBarSeries().getBar(index).getEndTime());
    }
}

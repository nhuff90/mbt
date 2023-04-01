package org.ta4j.core.indicators.nate.archive;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

public class UpToPMRangeTimeIndicator extends CachedIndicator<Boolean> {
    protected UpToPMRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return TimeUtils.isInRange(getBarSeries().getBar(index).getEndTime(), MarketTimeRanges.UP_TO_PM_RANGE);
    }
}

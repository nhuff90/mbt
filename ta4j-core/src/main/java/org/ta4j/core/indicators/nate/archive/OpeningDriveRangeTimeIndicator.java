package org.ta4j.core.indicators.nate.archive;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

public class OpeningDriveRangeTimeIndicator extends CachedIndicator<Boolean> {
    protected OpeningDriveRangeTimeIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        return TimeUtils.isInRange(getBarSeries().getBar(index).getEndTime(), MarketTimeRanges.OPENING_DRIVE_RANGE);
    }
}

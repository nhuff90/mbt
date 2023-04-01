package org.ta4j.core.indicators.nate.archive;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class OpeningDriveRangeIndicator extends CachedIndicator<Range> {
    private final OpeningDriveRangeTimeIndicator openingDriveRangeTimeIndicator;
    private Range openingDriveRange;

    public OpeningDriveRangeIndicator(BarSeries series) {
        super(series);
        openingDriveRangeTimeIndicator = new OpeningDriveRangeTimeIndicator(series);
        openingDriveRange = new Range(series.getBarByEndTime(MarketTime.OPENING_DRIVE_START_TIME.getLocalTime()), MarketTime.OPENING_DRIVE_START_TIME, MarketTime.OPENING_DRIVE_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (openingDriveRangeTimeIndicator.getValue(index)) {
            openingDriveRange.setRangeValues(getBarSeries().getBar(index), MarketTime.OPENING_DRIVE_START_TIME, MarketTime.OPENING_DRIVE_END_TIME);
        }
        return openingDriveRange;
    }
}

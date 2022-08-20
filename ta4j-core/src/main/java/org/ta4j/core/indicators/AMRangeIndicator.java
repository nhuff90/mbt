package org.ta4j.core.indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;

public class AMRangeIndicator extends CachedIndicator<Range> {
    private final AMRangeTimeIndicator amRangeTimeIndicator;
    private Range amRange;

    public AMRangeIndicator(BarSeries series) {
        super(series);
        amRangeTimeIndicator = new AMRangeTimeIndicator(series);
        amRange = new Range(series.getBarByEndTime(MarketTime.AM_START.getLocalTime()));
    }

    @Override
    protected Range calculate(int index) {
        if (amRangeTimeIndicator.getValue(index)) {
            amRange.setRangeValues(getBarSeries().getBar(index));
        }
        return amRange;
    }
}

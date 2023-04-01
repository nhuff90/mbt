package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class OpenTo1000RangeIndicator extends CachedIndicator<Range> {
    private final OpenTo1000RangeTimeIndicator openTo1000RangeTimeIndicator;
    private Range openTo1000Range;

    public OpenTo1000RangeIndicator(BarSeries series) {
        super(series);
        openTo1000RangeTimeIndicator = new OpenTo1000RangeTimeIndicator(series);
        openTo1000Range = new Range(series.getBarByEndTime(MarketTime.OPEN_TO_1000_START_TIME.getLocalTime()),
                MarketTime.OPEN_TO_1000_START_TIME, MarketTime.OPEN_TO_1000_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (openTo1000RangeTimeIndicator.getValue(index)) {
            openTo1000Range.setRangeValues(getBarSeries().getBar(index), MarketTime.OPEN_TO_1000_START_TIME, MarketTime.OPEN_TO_1000_END_TIME);
        }
        return openTo1000Range;
    }
}

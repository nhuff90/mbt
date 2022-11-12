package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class Opening5MinsRangeIndicator extends CachedIndicator<Range> {
    private final Opening5MinsRangeTimeIndicator opening5MinsRangeTimeIndicator;
    private Range opening5MinRange;

    public Opening5MinsRangeIndicator(BarSeries series) {
        super(series);
        opening5MinsRangeTimeIndicator = new Opening5MinsRangeTimeIndicator(series);
        opening5MinRange = new Range(series.getBarByEndTime(MarketTime.OPENING_5MINS_START_TIME.getLocalTime()), MarketTime.OPENING_5MINS_START_TIME, MarketTime.OPENING_5MINS_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (opening5MinsRangeTimeIndicator.getValue(index)) {
            opening5MinRange.setRangeValues(getBarSeries().getBar(index), MarketTime.OPENING_5MINS_START_TIME, MarketTime.OPENING_5MINS_END_TIME);
        }
        return opening5MinRange;
    }
}

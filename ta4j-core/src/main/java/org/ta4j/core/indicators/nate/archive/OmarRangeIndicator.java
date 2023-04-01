package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.utils.MarketTime;

public class OmarRangeIndicator extends CachedIndicator<Range> {
    private final OmarRangeTimeIndicator omarRangeTimeIndicator;
    private Range omarRange;

    public OmarRangeIndicator(BarSeries series) {
        super(series);
        omarRangeTimeIndicator = new OmarRangeTimeIndicator(series);
        omarRange = new Range(series.getBarByEndTime(MarketTime.OMAR_START_TIME.getLocalTime()), MarketTime.OMAR_START_TIME, MarketTime.OMAR_END_TIME);
    }

    @Override
    protected Range calculate(int index) {
        if (omarRangeTimeIndicator.getValue(index)) {
            omarRange.setRangeValues(getBarSeries().getBar(index), MarketTime.OMAR_START_TIME, MarketTime.OMAR_END_TIME);
        }
        return omarRange;
    }
}

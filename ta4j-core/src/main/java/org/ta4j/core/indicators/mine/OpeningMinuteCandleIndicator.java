package org.ta4j.core.indicators.mine;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

public class OpeningMinuteCandleIndicator extends CachedIndicator<Bar> {
    private Bar openingBar;

    public OpeningMinuteCandleIndicator(BarSeries series) {
        super(series);
    }


    @Override
    protected Bar calculate(int index) {
        if (TimeUtils.isStartOfRange(getBarSeries().getBar(index).getEndTime(), MarketTimeRanges.AM_RANGE)) {
            openingBar = getBarSeries().getBar(index);
        }
        return openingBar;
    }
}

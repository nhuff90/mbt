package org.ta4j.core.indicators.mine;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;

public class OmarBreakoutIndicator extends CachedIndicator<Bar> {
    Opening1MinBarTimeIndicator opening1MinBarTimeIndicator;
    Bar openingBar;

    protected OmarBreakoutIndicator(BarSeries series) {
        super(series);
        opening1MinBarTimeIndicator = new Opening1MinBarTimeIndicator(series);
    }


    @Override
    protected Bar calculate(int index) {
        if (opening1MinBarTimeIndicator.getValue(index)) {
            openingBar = getBarSeries().getBar(index);
        }
        return openingBar;
    }
}

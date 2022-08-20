package org.ta4j.core.indicators.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;

public class OpeningDriveUpIndicator extends CachedIndicator<Boolean> {
    protected OpeningDriveUpIndicator(BarSeries series) {
        super(series);
    }


    @Override
    protected Boolean calculate(int index) {
        return null;
    }
}

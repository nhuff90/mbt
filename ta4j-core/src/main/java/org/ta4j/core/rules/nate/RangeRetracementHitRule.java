/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2021 Ta4j Organization & respective
 * authors (see AUTHORS)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.rules.nate;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.AbstractRule;

/**
 * Satisfied when px hits the range retracement of the specified range
 */
public class RangeRetracementHitRule extends AbstractRule {
    private final BarSeries series;
    private final Range range;
    private final double retracementPercent;
    private final Boolean retracementFromHighOfRange;

    public RangeRetracementHitRule(BarSeries series, Range range, double retracementPercent, Boolean retracementFromHighOfRange) {
        this.series = series;
        this.range = range;
        this.retracementPercent = retracementPercent;
        this.retracementFromHighOfRange = retracementFromHighOfRange;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        OHLCIndicator ohlcRange = DailyMgiBuyRule.getRangeOhlc(range);


        if (ohlcRange == null || ohlcRange.getClose() == null) {
            return false;
        }

        if (ohlcRange.getClose() != null && isRetracementHit(series.getBar(index), ohlcRange, retracementPercent, retracementFromHighOfRange)) {
            satisfied = true;
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

    private boolean isRetracementHit(Bar bar, OHLCIndicator ohlcRange, double retracementPercent, Boolean retracementFromHighOfRange) {
        if (retracementFromHighOfRange && bar.getLowPrice().isLessThanOrEqual(ohlcRange.getRetracementOfRange(retracementPercent, true))) {
            return true;
        } else if (!retracementFromHighOfRange && bar.getHighPrice().isGreaterThanOrEqual(ohlcRange.getRetracementOfRange(retracementPercent, false))) {
            return true;
        }

        return false;
    }
}

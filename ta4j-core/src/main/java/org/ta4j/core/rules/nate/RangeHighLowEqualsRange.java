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
 * Satisfied when px hits the range extension of the specified range
 */
public class RangeHighLowEqualsRange extends AbstractRule {
    private final BarSeries series;
    private final Range firstRange;
    private final Range secondRange;
    private final Boolean compareHighs;

    public RangeHighLowEqualsRange(BarSeries series, Range firstRange, Range secondRange, Boolean compareHighs) {
        this.series = series;
        this.firstRange = firstRange;
        this.secondRange = secondRange;
        this.compareHighs = compareHighs;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        OHLCIndicator ohlcFirstRange = DailyMgiBuyRule.getRangeOhlc(firstRange);
        OHLCIndicator ohlcSecondRange = DailyMgiBuyRule.getRangeOhlc(secondRange);

        if (ohlcFirstRange == null || ohlcSecondRange == null) {
            return false;
        }

        if (compareHighs) {
            if (ohlcFirstRange.getHigh() != null && ohlcSecondRange.getHigh() != null && ohlcFirstRange.getHigh().getPrice().isEqual(ohlcSecondRange.getHigh().getPrice())) {
                satisfied = true;
            }
        } else {
            if (ohlcFirstRange.getLow() != null && ohlcSecondRange.getLow() != null && ohlcFirstRange.getLow().getPrice().isEqual(ohlcSecondRange.getLow().getPrice())) {
                satisfied = true;
            }
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }
}

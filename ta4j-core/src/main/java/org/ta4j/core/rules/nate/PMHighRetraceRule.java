/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2021 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.rules.nate;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.nate.archive.PMRangeIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

/**
 * PM High Retrace Rule.
 */
public class PMHighRetraceRule extends AbstractRule {

    private final BarSeries series;
    private final PMRangeIndicator pmRangeIndicator;
    private final double percentToTakeTrade;
    private boolean tradeTaken;

    public PMHighRetraceRule(BarSeries series, PMRangeIndicator pmRangeIndicator, double percentToTakeTrade) {
        this.series = series;
        this.pmRangeIndicator = pmRangeIndicator;
        this.percentToTakeTrade = percentToTakeTrade;
        this.tradeTaken = false;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (TimeUtils.isInRange(series.getBar(index).getEndTime(), MarketTimeRanges.AM_RANGE )) {
            tradeTaken = false;
        }
        Range range = pmRangeIndicator.getValue(index);
        boolean satisfied = false;
        if (TimeUtils.isInRange(series.getBar(index).getEndTime(), MarketTimeRanges.PM_BOUNCE_RANGE) && !tradeTaken) {
            satisfied = series.getBar(index).getHighPrice().isGreaterThanOrEqual(range.getPercentileFromRange(percentToTakeTrade));
            tradeTaken = true;
        }
        traceIsSatisfied(index, satisfied);
//        if (satisfied) {
//            System.out.println("test - AMHighRetraceRule");
//        }
        return satisfied;
    }
}

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
package org.ta4j.core.rules.mine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.mine.AMRangeIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.utils.MarketTime;

/**
 * AM Retrace Rule.
 *
 * Satisfied when the value of the first {@link Indicator indicator}
 * crosses-down the value of the second one.
 */
public class AMLowRetraceRule extends AbstractRule {

    private final BarSeries series;
    private final AMRangeIndicator amRangeIndicator;
    private final double percentToTakeTrade;
    private boolean tradeTaken;

    public AMLowRetraceRule(BarSeries series, AMRangeIndicator amRangeIndicator, double percentToTakeTrade) {
        this.series = series;
        this.amRangeIndicator = amRangeIndicator;
        this.percentToTakeTrade = percentToTakeTrade;
        this.tradeTaken = false;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (MarketTime.isStartOfAm(series.getBar(index).getEndTime())) {
            tradeTaken = false;
        }
        Range range = amRangeIndicator.getValue(index);
        boolean satisfied = false;
        if (MarketTime.isAmBounceRange(series.getBar(index).getEndTime()) && !tradeTaken) {
            satisfied = series.getBar(index).getLowPrice().isLessThanOrEqual(range.getPercentileFromRange(percentToTakeTrade));
            tradeTaken = satisfied;
        }
        traceIsSatisfied(index, satisfied);
//        if (satisfied) {
//            System.out.println("test - AMLowRetraceRule");
//        }
        return satisfied;
    }
}

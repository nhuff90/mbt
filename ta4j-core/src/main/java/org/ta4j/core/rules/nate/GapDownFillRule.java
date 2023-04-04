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

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

/**
 * Satisfied when there is a gap up from yesterday's closed that is filled to prior day's high
 */
public class GapDownFillRule extends AbstractRule {
    private final BarSeries series;
    protected double gapFillPercentage;

    public GapDownFillRule(BarSeries series, double gapFillPercentage) {
        this.series = series;
        this.gapFillPercentage = gapFillPercentage;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;

        if (DailyMgiBuyRule.priorDayRthOhlc.getLow() != null && isGapDownFilled(DailyMgiBuyRule.rthOhlc.getOpen().getPrice(), DailyMgiBuyRule.priorDayRthOhlc.getLow().getPrice(), series.getBar(index).getHighPrice())) {
            satisfied = true;
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

    /**
     * Returns true if the gap down was filled.
     * @param rthOpenPrice
     * @param targetPrice
     * @param currentPrice
     * @return
     */
    private boolean isGapDownFilled(Num rthOpenPrice, Num targetPrice, Num currentPrice) {
        Num delta = targetPrice.minus(rthOpenPrice);
        if (delta.isLessThan(DecimalNum.valueOf(0))) {
            delta = delta.multipliedBy(DecimalNum.valueOf(-1));
        }
        Num partialGapTargetDelta = delta.multipliedBy(DecimalNum.valueOf(gapFillPercentage));
        Num partialGapTargetPrice = rthOpenPrice.plus(partialGapTargetDelta);
        return partialGapTargetPrice.isLessThanOrEqual(currentPrice);
    }
}

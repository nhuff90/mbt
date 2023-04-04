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

import java.time.LocalDate;

/**
 * Satisfied when there is a gap up from yesterday's closed to today's open
 */
public class GapUpRule extends AbstractRule {
    private final BarSeries series;

    public GapUpRule(BarSeries series) {
        this.series = series;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;

        if (DailyMgiBuyRule.priorDayRthOhlc.getClose() == null || DailyMgiBuyRule.rthOhlc.getOpen() == null || DailyMgiBuyRule.dailyTradeTaken) {
            return false;
        }

        if (DailyMgiBuyRule.priorDayRthOhlc.getClose() != null && isValidGap(DailyMgiBuyRule.priorDayRthOhlc.getClose().getPrice(), DailyMgiBuyRule.rthOhlc.getOpen().getPrice(), 5)) {
            satisfied = true;
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

    /**
     * Returns true if there is a gap up that is greater than or equal to minGapSize
     * @param closePrice
     * @param openPrice
     * @param minGapSize
     * @return
     */
    private boolean isValidGap(Num closePrice, Num openPrice, int minGapSize) {
        return openPrice.minus(closePrice).isGreaterThanOrEqual(DecimalNum.valueOf(minGapSize));
    }
}

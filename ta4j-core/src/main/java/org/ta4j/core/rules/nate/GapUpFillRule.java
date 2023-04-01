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
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;

/**
 * Satisfied when there is a gap up from yesterday's closed to today's open
 */
public class GapUpRule extends DailyOHLCRule {

    boolean satisfied = false;

    public GapUpRule(BarSeries series) {
        super(series);
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        updateDailyOhlc(index, tradingRecord);

        if (priorDayRthOhlc.getClose() != null && TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime()) &&
                isValidGap(priorDayRthOhlc.getClose().getPrice(), rthOhlc.getOpen().getPrice(), 5)) {
            satisfied = true;
        }

        // Reset satisfied flag if new day
        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.ETH_0000.getLocalTime())) {
            satisfied = false;
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

    /**
     * Returns true if there is a gap up that is greater than or equal to minGapSize
     * @param minGapSize
     * @return
     */
    private boolean isValidGap(Num closePrice, Num openPrice, int minGapSize) {
        return openPrice.minus(closePrice).isGreaterThanOrEqual(DecimalNum.valueOf(minGapSize));
    }
}

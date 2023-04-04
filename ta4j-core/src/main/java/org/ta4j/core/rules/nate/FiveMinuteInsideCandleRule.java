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
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.AbstractRule;

/**
 * Satisfied when there is a 5 minute candle which is engulfed by the previous 5 min candle.
 */
public class FiveMinuteInsideCandleRule extends AbstractRule {
    private final BarSeries series;

    public FiveMinuteInsideCandleRule(BarSeries series) {
        this.series = series;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;

        OHLCIndicator fiveMinEngulfingCandle = getEngulfingCandle();
        if (fiveMinEngulfingCandle != null) {
            if (fiveMinEngulfingCandle.getHigh().getPrice().isLessThan(series.getBar(index).getHighPrice()) || fiveMinEngulfingCandle.getLow().getPrice().isGreaterThan(series.getBar(index).getLowPrice())) {
                satisfied = true;
            }
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

    private OHLCIndicator getEngulfingCandle() {
        OHLCIndicator previousFiveMinCandle = DailyMgiBuyRule.get5MinCandle(1);
        OHLCIndicator previousPreviousFiveMinCandle = DailyMgiBuyRule.get5MinCandle(2);

        if (previousPreviousFiveMinCandle.getHigh().getPrice().isGreaterThan(previousFiveMinCandle.getHigh().getPrice()) && previousPreviousFiveMinCandle.getLow().getPrice().isLessThan(previousFiveMinCandle.getLow().getPrice())) {
            return previousPreviousFiveMinCandle;
        } else {
            return null;
        }
    }
}

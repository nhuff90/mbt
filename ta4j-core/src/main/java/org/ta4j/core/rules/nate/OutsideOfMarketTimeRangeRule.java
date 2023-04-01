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
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;

import java.time.LocalDate;

/**
 * Satisfied when the bar is inside of the given range
 */
public class OutsideOfMarketTimeRangeRule extends AbstractRule {
    protected BarSeries series;
    private MarketTime startTime;
    private MarketTime endTime;

    public OutsideOfMarketTimeRangeRule(BarSeries series, MarketTime startTime, MarketTime endTime) {
        this.series = series;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;

        if (TimeUtils.isBefore(series.getBar(index).getEndTime(), startTime) ||  TimeUtils.isAfter(series.getBar(index).getEndTime(), endTime)) {
            satisfied = true;
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }

}

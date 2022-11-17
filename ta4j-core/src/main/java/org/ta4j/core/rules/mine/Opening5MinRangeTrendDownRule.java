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
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.indicators.mine.Opening5MinsRangeIndicator;
import org.ta4j.core.indicators.mine.OmarRangeIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.MarketTimeRanges;
import org.ta4j.core.utils.TimeUtils;

/**
 * Opening5MinRangeTrendDownRule.
 */
public class Opening5MinRangeTrendDownRule extends AbstractRule {

    private final BarSeries series;
    private final OmarRangeIndicator omarRangeIndicator;
    private final CachedIndicator<Range> opening5MinsRangeIndicator;
    private boolean tradeTaken;

    public Opening5MinRangeTrendDownRule(BarSeries series, OmarRangeIndicator omarRangeIndicator, CachedIndicator<Range> opening5MinsRangeIndicator) {
        this.series = series;
        this.omarRangeIndicator = omarRangeIndicator;
        this.opening5MinsRangeIndicator = opening5MinsRangeIndicator;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        if (TimeUtils.isStartOfRange(series.getBar(index).getEndTime(), MarketTimeRanges.AM_RANGE)) {
            tradeTaken = false;
        }
        Range range = opening5MinsRangeIndicator.getValue(index);
        Num open5minsRangeHigh =  opening5MinsRangeIndicator.getValue(index).getHighPrice();
        Num omarHigh = omarRangeIndicator.getValue(index).getHighPrice(); // this must be outside of if statement below checking isAfter
        boolean satisfied = false;
        if (TimeUtils.isAfter(series.getBar(index).getEndTime(), MarketTime.OPENING_5MINS_END_TIME) && !tradeTaken) {
            satisfied = open5minsRangeHigh.isEqual(omarHigh);
            tradeTaken = true;
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }
}

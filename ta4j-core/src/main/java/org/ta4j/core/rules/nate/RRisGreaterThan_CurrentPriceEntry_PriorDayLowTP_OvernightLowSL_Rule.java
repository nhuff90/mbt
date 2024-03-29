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
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

/**
 * Satisfied when the OpeningPrintEntry to PriorDayHighStopLoss rr is greater than the provided rr.
 */
public class RRisGreaterThan_CurrentPriceEntry_PriorDayLowTP_OvernightLowSL_Rule extends AbstractRule {
    protected BarSeries series;
    protected double rr;

    public RRisGreaterThan_CurrentPriceEntry_PriorDayLowTP_OvernightLowSL_Rule(BarSeries series, double rr) {
        this.series = series;
        this.rr = rr;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;

        if (series.getBar(index)!= null &&
                DailyMgiBuyRule.priorDayRthOhlc.getLow() != null &&
                DailyMgiBuyRule.overnightRthOhlc.getLow() != null) {
            Num currentBarHighPrice = series.getBar(index).getHighPrice();
            Num priorDayLowPrice = DailyMgiBuyRule.priorDayRthOhlc.getLow().getPrice();
            Num overnightLowPrice = DailyMgiBuyRule.overnightRthOhlc.getLow().getPrice();

            Num possibleProfit = priorDayLowPrice.minus(currentBarHighPrice);
            Num possibleLoss = currentBarHighPrice.minus(overnightLowPrice);

            if ((possibleProfit.doubleValue()) > (possibleLoss.doubleValue() * rr)) {
                satisfied = true;
            }
        }

        traceIsSatisfied(index, satisfied);

        return satisfied;
    }
}

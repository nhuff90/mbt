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

import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

/**
 * A take profit rule.
 *
 * Satisfied when the high/low price reaches the gain threshold.
 */
public class TakeProfitInPointsRule extends TakeProfitStopLossRuleInterface {


    /**
     * The high price indicator
     */
    final HighPriceIndicator highPrice;

    /**
     * The low price indicator
     */
    final LowPriceIndicator lowPrice;

    /**
     * The gain in points
     */
    final Num takeProfitInPoints;


    /**
     * Constructor.
     *
     * @param highPrice             the high price indicator
     * @param lowPrice              the low price indicator
     * @param takeProfitInPoints    the take profits in points
     */
    public TakeProfitInPointsRule(HighPriceIndicator highPrice, LowPriceIndicator lowPrice, Number takeProfitInPoints) {
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.takeProfitInPoints = DecimalNum.valueOf(takeProfitInPoints);
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        setSatisfied(false);
        // No trading history or no position opened, no loss
        if (tradingRecord != null) {
            Position currentPosition = tradingRecord.getCurrentPosition();
            if (currentPosition.isOpened()) {

                Num entryPrice = currentPosition.getEntry().getNetPrice();

                if (currentPosition.getEntry().isBuy()) {
                    setSatisfied(isBuyGainSatisfied(entryPrice, highPrice.getValue(index)));
                } else {
                    setSatisfied(isSellGainSatisfied(entryPrice, lowPrice.getValue(index)));
                }
            }
        }
        traceIsSatisfied(index, isSatisfied());
        return isSatisfied();
    }

    boolean isSellGainSatisfied(Num entryPrice, Num currentPrice) {
        return currentPrice.isLessThanOrEqual(entryPrice.plus(takeProfitInPoints));
    }

    boolean isBuyGainSatisfied(Num entryPrice, Num currentPrice) {
        return currentPrice.isGreaterThanOrEqual(entryPrice.plus(takeProfitInPoints));
    }
}

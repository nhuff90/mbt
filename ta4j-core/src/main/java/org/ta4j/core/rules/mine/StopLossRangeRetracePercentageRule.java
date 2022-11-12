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

import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.indicators.mine.Opening5MinsRangeIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

/**
 * A stop loss rule given a range indicator and percentage.
 *
 * Satisfied when price retraces the provided percent of the given range.
 * e.g., range = 200-300, provided % = 50%, stop loss for buy will be 250 and sell will be 250
 */
public class StopLossRangeRetracePercentageRule extends TakeProfitStopLossRuleInterface {

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
    final Opening5MinsRangeIndicator opening5MinsRangeIndicator;

    /**
     * The percentage of the range to Stop Out
     */
    private Num percentageOfRange;


    /**
     * Constructor.
     *  @param highPrice             the high price indicator
     * @param lowPrice              the low price indicator
     * @param opening5MinsRangeIndicator
     */
    public StopLossRangeRetracePercentageRule(HighPriceIndicator highPrice, LowPriceIndicator lowPrice, Opening5MinsRangeIndicator opening5MinsRangeIndicator,
                                              Num percentageOfRange) {
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.percentageOfRange = percentageOfRange;
        this.opening5MinsRangeIndicator = opening5MinsRangeIndicator;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
//        boolean satisfied = false;
        setSatisfied(false);
        // No trading history or no position opened, no loss
        if (tradingRecord != null) {
            Position currentPosition = tradingRecord.getCurrentPosition();
            if (currentPosition.isOpened()) {
                Range range = opening5MinsRangeIndicator.getValue(index);
                if (currentPosition.getEntry().isBuy()) {
                    setSatisfied(isBuyStopSatisfied(this.lowPrice.getValue(index), calculateBuyStop(range, percentageOfRange)));
                } else {
                    setSatisfied(isSellStopSatisfied(this.highPrice.getValue(index), calculateSellStop(range, percentageOfRange)));
                }
            }
        }
        traceIsSatisfied(index, isSatisfied());
        return isSatisfied();
    }

    private Num calculateBuyStop(Range range, Num percentageOfRange) {
        Num rangeSize = range.getRangeSize();
        Num stopLossOffset = rangeSize.multipliedBy(DecimalNum.valueOf(percentageOfRange.doubleValue()));
        return range.getHighPrice().minus(stopLossOffset);
    }

    private Num calculateSellStop(Range range, Num percentageOfRange) {
        Num rangeSize = range.getRangeSize();
        Num stopLossOffset = rangeSize.multipliedBy(DecimalNum.valueOf(percentageOfRange.doubleValue()));
        return range.getLowPrice().plus(stopLossOffset);
    }

    boolean isBuyStopSatisfied(Num price, Num buyStopPrice) {
        return price.isLessThanOrEqual(buyStopPrice);
    }

    boolean isSellStopSatisfied(Num price, Num sellStopPrice) {
        return price.isGreaterThanOrEqual(sellStopPrice);
    }
}

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
package org.ta4j.core.rules;

import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.AMRangeIndicator;
import org.ta4j.core.indicators.helpers.OHLCPriceIndicator;
import org.ta4j.core.num.Num;

/**
 * A stop-gain rule.
 *
 * Satisfied when the close price reaches the gain threshold.
 */
public class AMRangeStopGainRule extends AbstractRule {

    OHLCPriceIndicator ohlcPriceIndicator;
    AMRangeIndicator amRangeIndicator;
    double percentOfRangeToTakeProfits;

    public AMRangeStopGainRule(OHLCPriceIndicator ohlcPriceIndicator, AMRangeIndicator amRangeIndicator, double percentOfRangeToTakeProfits) {
        super();
        this.ohlcPriceIndicator = ohlcPriceIndicator;
        this.amRangeIndicator = amRangeIndicator;
        this.percentOfRangeToTakeProfits = percentOfRangeToTakeProfits;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        // No trading history or no position opened, no loss
        if (tradingRecord != null) {
            Position currentPosition = tradingRecord.getCurrentPosition();
            if (currentPosition.isOpened()) {

                Num currentPrice = ohlcPriceIndicator.getValue(index).getClosePriceIndicator(index);

                if (currentPosition.getEntry().isBuy()) {
                    satisfied = isBuyGainSatisfied(currentPrice, amRangeIndicator.getValue(index).getPercentileFromRange(percentOfRangeToTakeProfits));
                } else {
                    satisfied = isSellGainSatisfied(currentPrice, amRangeIndicator.getValue(index).getPercentileFromRange(percentOfRangeToTakeProfits));
                }
            }
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }

    private boolean isSellGainSatisfied(Num currentPrice, Num stopPrice) {
        return currentPrice.isLessThanOrEqual(stopPrice);
    }

    private boolean isBuyGainSatisfied(Num currentPrice, Num stopPrice) {
        return currentPrice.isGreaterThanOrEqual(stopPrice);
    }
}

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
package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.num.Num;

/**
 * Open/High/Low/Close price indicator.
 */
public class OHLCPriceIndicator extends AbstractIndicator<OHLCPriceIndicator> {

    ClosePriceIndicator closePriceIndicator;
    OpenPriceIndicator openPriceIndicator;
    HighPriceIndicator highPriceIndicator;
    LowPriceIndicator lowPriceIndicator;

    public OHLCPriceIndicator(BarSeries series) {
        super(series);
        this.closePriceIndicator = new ClosePriceIndicator(series);
        this.openPriceIndicator = new OpenPriceIndicator(series);
        this.highPriceIndicator = new HighPriceIndicator(series);
        this.lowPriceIndicator = new LowPriceIndicator(series);
    }

    @Override
    public OHLCPriceIndicator getValue(int index) {
        return this;
    }

    public Num getClosePriceIndicator(int index) {
        return closePriceIndicator.getValue(index);
    }

    public Num getOpenPriceIndicator(int index) {
        return openPriceIndicator.getValue(index);
    }

    public Num getHighPriceIndicator(int index) {
        return highPriceIndicator.getValue(index);
    }

    public Num getLowPriceIndicator(int index) {
        return lowPriceIndicator.getValue(index);
    }
}

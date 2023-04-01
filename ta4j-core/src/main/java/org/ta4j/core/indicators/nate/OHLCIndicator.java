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
package org.ta4j.core.indicators.nate;

import org.ta4j.core.indicators.nate.helper.DateTimePrice;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;

/**
 * Open/High/Low/Close price and time.
 */
public class OHLCIndicator {

    DateTimePrice open;
    DateTimePrice high;
    DateTimePrice low;
    DateTimePrice close;
    Num midpoint;


    public OHLCIndicator() {
    }

    public OHLCIndicator(DateTimePrice open, DateTimePrice high, DateTimePrice low, DateTimePrice close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.midpoint = calculateMidpoint();
    }

    private Num calculateMidpoint() {
        if (high == null || low == null) {
            return null;
        }
        return (high.getPrice().plus(low.getPrice())).dividedBy(DecimalNum.valueOf(2));
    }

    public DateTimePrice getClose() {
        return close;
    }

    public void setClose(DateTimePrice close) {
        this.close = close;
    }

    public DateTimePrice getOpen() {
        return open;
    }

    public void setOpen(DateTimePrice open) {
        this.open = open;
    }

    public DateTimePrice getHigh() {
        return high;
    }

    public void setHigh(DateTimePrice high) {
        this.high = high;
        this.midpoint = calculateMidpoint();
    }

    public DateTimePrice getLow() {
        return low;
    }

    public void setLow(DateTimePrice low) {
        this.low = low;
        this.midpoint = calculateMidpoint();
    }
}

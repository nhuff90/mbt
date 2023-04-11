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
 * Stores a triple of pre, in, and post OHLC.
 */
public class PreInPostOHLCIndicator {
    private OHLCIndicator preOhlc;
    private OHLCIndicator ohlc;
    private OHLCIndicator postOhlc;

    public PreInPostOHLCIndicator(OHLCIndicator preOhlc, OHLCIndicator ohlc, OHLCIndicator postOhlc) {
        this.preOhlc = preOhlc;
        this.ohlc = ohlc;
        this.postOhlc = postOhlc;
    }

    public OHLCIndicator getPreOhlc() {
        return preOhlc;
    }

    public void setPreOhlc(OHLCIndicator preOhlc) {
        this.preOhlc = preOhlc;
    }

    public OHLCIndicator getOhlc() {
        return ohlc;
    }

    public void setOhlc(OHLCIndicator ohlc) {
        this.ohlc = ohlc;
    }

    public OHLCIndicator getPostOhlc() {
        return postOhlc;
    }

    public void setPostOhlc(OHLCIndicator postOhlc) {
        this.postOhlc = postOhlc;
    }
}

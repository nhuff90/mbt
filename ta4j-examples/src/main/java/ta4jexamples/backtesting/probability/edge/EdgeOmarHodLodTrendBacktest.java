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
package ta4jexamples.backtesting.probability.edge;

import org.ta4j.core.*;
import org.ta4j.core.analysis.ResultsAnalysis;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.DateTimeIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.Range;
import org.ta4j.core.indicators.mine.OmarRangeIndicator;
import org.ta4j.core.indicators.mine.Opening5MinsRangeIndicator;
import org.ta4j.core.indicators.mine.OpeningDriveRangeIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.TimeRangeRule;
import org.ta4j.core.rules.mine.Opening5MinRangeTrendDownRule;
import org.ta4j.core.rules.mine.OpeningDriveTrendDownRule;
import org.ta4j.core.rules.mine.TakeProfitRangePercentageRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.MarketTimeRanges;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Backtest
 * Buy Rule:
 *  -OMAR High = 930-935 High
 *  -Below VWAP and WVWAP
 *
 *  Sell Rule:
 *  -By provided time range
 *
 *  The purpose of this Edge class is to try new things. Once done implementing a backtest,
 *  archive to the omar package.
 */
public interface EdgeOmarHodLodTrendBacktest {

    enum TrendToTest {
        UP,
        DOWN;
    }
}

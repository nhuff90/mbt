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
package ta4jexamples.nate.stats;

import nate.stats.NHodBy30mPeriodAfterBreakingLowOfPeriodStats;
import nate.stats.domain.TrueFalseDailyMgiAndPeriodOhlcResults;
import org.junit.Test;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;
import org.ta4j.core.rules.nate.DailyMgi;

import java.time.LocalDate;
import java.util.Map;

public class NHodBy30mPeriodAfterBreakingLowOfPeriodStatsTest {

    @Test
    public void test() throws InterruptedException {

        NHodBy30mPeriodAfterBreakingLowOfPeriodStats.main(null);

        /*
        Continuation Tests
         */
        final Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> continuationMap =
                NHodBy30mPeriodAfterBreakingLowOfPeriodStats.resultMap.getPeriodContinuationMap();

        // True Tests
        assert isOhlcPresent(continuationMap.get(Period30m.B).getTrueMap(), LocalDate.of ( 2023, 3, 29));
        assert isOhlcPresent(continuationMap.get(Period30m.J).getTrueMap(), LocalDate.of ( 2023, 3, 22));
        assert isOhlcPresent(continuationMap.get(Period30m.B).getTrueMap(), LocalDate.of ( 2023, 3, 21));
        assert isOhlcPresent(continuationMap.get(Period30m.D).getTrueMap(), LocalDate.of ( 2023, 2, 13));
        // False Tests
        assert isOhlcPresent(continuationMap.get(Period30m.B).getFalseMap(), LocalDate.of ( 2023, 3, 28));
        assert isOhlcPresent(continuationMap.get(Period30m.D).getFalseMap(), LocalDate.of ( 2023, 3, 14));
        assert isOhlcPresent(continuationMap.get(Period30m.E).getFalseMap(), LocalDate.of ( 2023, 3, 10));
        assert isOhlcPresent(continuationMap.get(Period30m.D).getFalseMap(), LocalDate.of ( 2023, 3, 13));
        assert isOhlcPresent(continuationMap.get(Period30m.B).getFalseMap(), LocalDate.of ( 2023, 2, 27));
        // No NHOD/NLOD tests
        assert !isOhlcPresent(continuationMap.get(Period30m.D).getFalseMap(), LocalDate.of ( 2023, 3, 29));
        assert !isOhlcPresent(continuationMap.get(Period30m.C).getFalseMap(), LocalDate.of ( 2023, 3, 1));
        assert !isOhlcPresent(continuationMap.get(Period30m.B).getFalseMap(), LocalDate.of ( 2023, 3, 9));

        /*
        Reversal Tests
         */
        final Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> reversalMap =
                NHodBy30mPeriodAfterBreakingLowOfPeriodStats.resultMap.getPeriodReversalMap();
        // True Tests
        assert isOhlcPresent(reversalMap.get(Period30m.J).getTrueMap(), LocalDate.of ( 2023, 3, 22));
        assert isOhlcPresent(reversalMap.get(Period30m.D).getTrueMap(), LocalDate.of ( 2023, 3, 10));
        // False Tests
        assert isOhlcPresent(reversalMap.get(Period30m.D).getFalseMap(), LocalDate.of ( 2023, 3, 13));
        assert isOhlcPresent(reversalMap.get(Period30m.D).getFalseMap(), LocalDate.of ( 2023, 3, 2));
        // No NHOD/NLOD Tests
        assert !isOhlcPresent(reversalMap.get(Period30m.B).getTrueMap(), LocalDate.of ( 2023, 3, 27));
        assert !isOhlcPresent(reversalMap.get(Period30m.D).getTrueMap(), LocalDate.of ( 2023, 3, 3));
    }

    private boolean isOhlcPresent(Map<DailyMgi, OHLCIndicator> periodNHODResultsMap, LocalDate date) {
        for (Map.Entry<DailyMgi, OHLCIndicator> entry : periodNHODResultsMap.entrySet()) {
            DailyMgi dailyMgi = entry.getKey();
            OHLCIndicator ohlcIndicator = entry.getValue();
             if (dailyMgi.getRthOhlc().getOpen().getDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }
}

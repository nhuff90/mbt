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

import nate.stats.MicroBreakoutToOmarStats;
import nate.stats.NLodBy30mPeriodStats;
import nate.stats.domain.TrueFalseDailyMgiAndPeriodOhlcResults;
import nate.stats.domain.TrueFalseDailyMgiResults;
import org.junit.Test;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;
import org.ta4j.core.rules.nate.DailyMgi;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MicroBreakoutToOmarStatsTest {

    @Test
    public void test() throws InterruptedException {

        MicroBreakoutToOmarStats.main(null);

        /*
        Micro High Breakout Tests
         */
        final TrueFalseDailyMgiResults trueFalseMicroHighBreakoutResults =
                MicroBreakoutToOmarStats.trueFalseMicroHighBreakout;

        // True Tests
        assert isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 27));
        assert isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 2, 23));
        assert isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 1, 19));

        // False Tests
        assert isPresent(trueFalseMicroHighBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2023, 1, 3));
        assert isPresent(trueFalseMicroHighBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2023, 2, 8));
        assert isPresent(trueFalseMicroHighBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2022, 12, 28));
        // No NHOD/NLOD tests
        assert !isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 22));
        assert !isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 20));
        assert !isPresent(trueFalseMicroHighBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 1, 24));


        /*
        Micro Low Breakout Tests
         */
        final TrueFalseDailyMgiResults trueFalseMicroLowBreakoutResults =
                MicroBreakoutToOmarStats.trueFalseMicroLowBreakout;

        // True Tests
        assert isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 23));
        assert isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 14));
        assert isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 6));
        // False Tests
        assert isPresent(trueFalseMicroLowBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2023, 3, 13));
        assert isPresent(trueFalseMicroLowBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2023, 2, 13));
        assert isPresent(trueFalseMicroLowBreakoutResults.getFalseDailyMgiList(), LocalDate.of ( 2023, 1, 23));
        // No NHOD/NLOD tests
        assert !isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 22));
        assert !isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 3, 20));
        assert !isPresent(trueFalseMicroLowBreakoutResults.getTrueDailyMgiList(), LocalDate.of ( 2023, 1, 24));

    }

    private boolean isPresent(List<DailyMgi> resultMap, LocalDate date) {
        for (DailyMgi dailyMgi : resultMap) {
            if (dailyMgi.getRthOhlc().getOpen().getDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }
}

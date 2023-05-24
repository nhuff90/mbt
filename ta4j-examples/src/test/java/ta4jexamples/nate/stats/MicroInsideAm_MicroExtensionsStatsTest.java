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

import nate.stats.MicroInsideAm_MicroExtensionsStats;
import org.junit.Test;
import org.ta4j.core.rules.nate.DailyMgi;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MicroInsideAm_MicroExtensionsStatsTest {

    @Test
    public void test() throws InterruptedException {

        Double extensionToTest = 1.0;
        MicroInsideAm_MicroExtensionsStats.main(Arrays.asList(extensionToTest));

        final MicroInsideAm_MicroExtensionsStats.Results results =
                MicroInsideAm_MicroExtensionsStats.microInsideAmResultMap.get(MicroInsideAm_MicroExtensionsStats.getExtensionString(extensionToTest));

        // Up Extension Hit Tests
        assert isPresent(results.getTrendUp(), LocalDate.of(2023, 1, 26));
        assert isPresent(results.getTrendUp(), LocalDate.of(2023, 2, 28));

        // Down Extension Hit Tests
        assert isPresent(results.getTrendDown(), LocalDate.of(2022, 11, 15));
        assert isPresent(results.getTrendDown(), LocalDate.of(2022, 11, 30));

        // Neither Extension Hit Tests
        assert isPresent(results.getRange(), LocalDate.of(2022, 12, 22));
        assert isPresent(results.getRange(), LocalDate.of(2023, 3, 15));


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

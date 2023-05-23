///**
// * The MIT License (MIT)
// * <p>
// * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2021 Ta4j Organization & respective
// * authors (see AUTHORS)
// * <p>
// * Permission is hereby granted, free of charge, to any person obtaining a copy of
// * this software and associated documentation files (the "Software"), to deal in
// * the Software without restriction, including without limitation the rights to
// * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
// * the Software, and to permit persons to whom the Software is furnished to do so,
// * subject to the following conditions:
// * <p>
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// * <p>
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
// * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
// * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
// * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// */
//package ta4jexamples.nate.stats;
//
//import nate.stats.RangeExtBasedOnOpeningRangesStats;
//import org.junit.Test;
//import org.ta4j.core.rules.nate.DailyMgi;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class RangeExtBasedOnOpeningRangesStatsTest {
//
//    @Test
//    public void test() throws InterruptedException {
//
//        RangeExtBasedOnOpeningRangesStats.main(null);
//
//        runAMTests();
//        runO15mTests();
//        runO5mTests();
//        runOMARTests();
//    }
//
//    private void runAMTests() {
//        /*
//        AM+IB Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults amIbHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.amIbHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 15));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(amIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 24));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(amIbHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 23));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(amIbHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 28));
//        // Lows Eq - Range
//        assert isOhlcPresent(amIbHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 14));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 27));
//
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(amIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 10));
//
//        /*
//        End AM+IB Tests
//         */
//
//    }
//
//    private void runO15mTests() {
//        /*
//        o15m+IB Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults o15mIbHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.o15mIbHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 15));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 24));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 23));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 28));
//        // Lows Eq - Range
//        assert isOhlcPresent(o15mIbHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 14));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 27));
//
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(o15mIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 10));
//
//        /*
//        End o15m+IB Tests
//         */
//
//        /*
//        o15m+AM Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults o15mAmHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.o15mAmHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 22));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 15));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 8));
//        // Highs Eq - Range
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 24));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 17));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 23));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 23));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 28));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 9));
//        // Lows Eq - Range
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 14));
//        assert isOhlcPresent(o15mAmHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 1, 17));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 27));
//
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 10));
//        assert !isOhlcPresent(o15mAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 2, 10));
//        /*
//        End o15m+AM Tests
//         */
//
//    }
//
//    private void runO5mTests() {
//        /*
//        o5m+IB Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults o5mIbHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.o5mIbHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 10));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2022, 12, 27));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 16));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 28));
//        // Lows Eq - Range
//        assert isOhlcPresent(o5mIbHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 14));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(o5mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o5mIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//
//        /*
//        End o5m+IB Tests
//         */
//
//        /*
//        o5m+AM Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults o5mAmHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.o5mAmHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 10));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2022, 12, 27));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 16));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 28));
//        // Lows Eq - Range
//        assert isOhlcPresent(o5mAmHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 14));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(o5mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(o5mAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        /*
//        End o5m+AM Tests
//         */
//
//        /*
//        o5m+o15m Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults o5mO15mHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.o5mO15mHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 10));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getHighEqRange(), LocalDate.of(2022, 12, 27));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 16));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 29));
//        // Lows Eq - Range
//        assert isOhlcPresent(o5mO15mHighLowEqualsResults.getLowEqRange(), LocalDate.of(2022, 12, 27));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(o5mO15mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 24));
//        assert !isOhlcPresent(o5mO15mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 22));
//        /*
//        End o5m+o15m Tests
//         */
//
//    }
//
//    private void runOMARTests() {
//        /*
//        omar+IB Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults omarIbHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.omarIbHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2022, 11, 2));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 1, 12));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2022, 12, 14));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 1, 30));
//        // Lows Eq - Range
//        assert isOhlcPresent(omarIbHighLowEqualsResults.getLowEqRange(), LocalDate.of(2023, 2, 3));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(omarIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(omarIbHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//
//        /*
//        End omar+IB Tests
//         */
//
//        /*
//        omar+AM Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults omarAmHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.omarAmHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 22));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 2, 9));
//        // Highs Eq - Range
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 1, 12));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 2, 3));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 1, 30));
//        // Lows Eq - Range
//        assert isOhlcPresent(omarAmHighLowEqualsResults.getLowEqRange(), LocalDate.of(2022, 11, 23));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(omarAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 27));
//        assert !isOhlcPresent(omarAmHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        /*
//        End omar+AM Tests
//         */
//
//                /*
//        omar+o15m Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults omarO15mHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.omarO15mHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 28));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 3, 29));
//        // Highs Eq - Range
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getHighEqRange(), LocalDate.of(2023, 1, 12));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 2, 3));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 21));
//        // Lows Eq - Range
//        assert isOhlcPresent(omarO15mHighLowEqualsResults.getLowEqRange(), LocalDate.of(2022, 12, 7));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(omarO15mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        /*
//        End omar+o15m Tests
//         */
//
//                        /*
//        omar+o5m Tests
//         */
//        final RangeExtBasedOnOpeningRangesStats.HighLowEqualsResults omarO5mHighLowEqualsResults =
//                RangeExtBasedOnOpeningRangesStats.omarO5mHighLowEqualsResults;
//
//        // Highs Eq - Trend Up
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 3, 28));
//        // Highs Eq - Trend Low
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getHighEqTrendDown(), LocalDate.of(2023, 3, 29));
//        // Highs Eq - Range
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getHighEqRange(), LocalDate.of(2022, 12, 27));
//
//        // Lows Eq - Trend Up
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getLowEqTrendUp(), LocalDate.of(2023, 3, 23));
//        // Lows Eq - Trend Low
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getLowEqTrendDown(), LocalDate.of(2023, 3, 21));
//        // Lows Eq - Range
//        assert isOhlcPresent(omarO5mHighLowEqualsResults.getLowEqRange(), LocalDate.of(2022, 12, 7));
//
//        // Highs/Lows != Highs/Lows (neg test)
//        assert !isOhlcPresent(omarO5mHighLowEqualsResults.getHighEqTrendUp(), LocalDate.of(2023, 2, 10));
//        /*
//        End omar+o5m Tests
//         */
//
//    }
//
//    private boolean isOhlcPresent(List<DailyMgi> list, LocalDate date) {
//        for (DailyMgi dailyMgi : list) {
//            if (dailyMgi.getRthOhlc().getOpen().getDate().isEqual(date)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}

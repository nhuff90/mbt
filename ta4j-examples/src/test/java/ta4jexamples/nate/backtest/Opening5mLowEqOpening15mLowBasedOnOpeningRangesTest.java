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
package ta4jexamples.nate.backtest;

import nate.backtests.Opening5mLowEqOpening15mLowBasedOnOpeningRanges;
import org.junit.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.Trade;

import java.time.LocalDate;
import java.util.List;

public class Opening5mLowEqOpening15mLowBasedOnOpeningRangesTest extends BacktestTest{

    @Test
    public void test() throws InterruptedException {

        Opening5mLowEqOpening15mLowBasedOnOpeningRanges.main(1.0, 1.0, .5);
        List<Position> positions = Opening5mLowEqOpening15mLowBasedOnOpeningRanges.tradingRecord.getPositions();
        BarSeries barSeries = Opening5mLowEqOpening15mLowBasedOnOpeningRanges.series;

        /*
        Profitable Trades
         */
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 3, 16), Trade.TradeType.BUY, true);
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 3, 22), Trade.TradeType.BUY, true);

        /*
        Not Profitable Trades
         */
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 3, 28), Trade.TradeType.BUY, false);
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 3, 29), Trade.TradeType.BUY, false);

        /*
        No trade taken tests
         */
        // 5m Low != 15m low
        assert !wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2022, 3, 27), Trade.TradeType.BUY);


    }

}

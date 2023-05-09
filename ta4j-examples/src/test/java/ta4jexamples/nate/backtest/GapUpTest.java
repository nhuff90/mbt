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

import nate.backtests.GapUpBackTest;
import org.junit.Test;
import org.ta4j.core.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GapUpTest extends BacktestTest{

    @Test
    public void test() throws InterruptedException {

        GapUpBackTest.main(null);
        List<Position> positions = GapUpBackTest.tradingRecord.getPositions();
        BarSeries barSeries = GapUpBackTest.series;

        /*
        Profitable Trades
         */
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 2, 27), Trade.TradeType.SELL, true);

        /*
        Not Profitable Trades
         */
        assert wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 3, 29), Trade.TradeType.SELL, false);

        /*
        No trade taken tests
         */
        //No gap up
        assert !wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2022, 1, 5), Trade.TradeType.SELL);

        // No overnight high look and fail
        assert !wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 2, 9), Trade.TradeType.SELL);

        // Does not cross back under open
        assert !wasTradeTakenOnDay(positions, barSeries, LocalDate.of(2023, 2, 13), Trade.TradeType.SELL);

    }

}

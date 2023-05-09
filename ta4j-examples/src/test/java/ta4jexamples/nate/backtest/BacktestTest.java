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
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.Trade;

import java.time.LocalDate;
import java.util.List;

public class BacktestTest {

    protected boolean wasTradeTakenOnDay(List<Position> positions, BarSeries series, LocalDate date, Trade.TradeType tradeType, Boolean profitable) {
        boolean tradeTaken = false;
        for (Position pos : positions) {
            Bar entryBar = series.getBar(pos.getEntry().getIndex());
            Bar exitBar = series.getBar(pos.getExit().getIndex());

            if (entryBar.getLocalDate().isEqual(date) && pos.getEntry().getType().equals(tradeType)) {
                if (profitable != null && profitable == pos.getProfit().doubleValue() >= 0) {
                    tradeTaken = true;
                    break;
                } else {
                    tradeTaken = true;
                    break;
                }

            }
        }

        return tradeTaken;
    }

    protected boolean wasTradeTakenOnDay(List<Position> positions, BarSeries series, LocalDate date, Trade.TradeType tradeType) {
        return wasTradeTakenOnDay(positions, series, date, tradeType, null);
    }
}

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
package org.ta4j.core.rules.nate;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.DateTimePrice;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;

import java.time.LocalTime;

/**
 * Tracks daily MGI. This Rule is always satisfied=true
 */
public class DailyMgiBuyRule {

    public static BarSeries series;

    protected static OHLCIndicator rthOhlc = new OHLCIndicator();
    protected static OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator omarOhlc = new OHLCIndicator();
    protected static OHLCIndicator overnightRthOhlc = new OHLCIndicator();
    // OMAR
    // AM Range
    // Micro Range
    // PM Range
    // Initial Balance

    public static boolean dailyTradeTaken = false;

    public static void updateDailyMgi(int index, TradingRecord tradingRecord) {
        int previousIndex = index - 1;
        if (index <= 0 || series.getBar(previousIndex) == null) {
            return;
        }

        /**
         * Set RTH fields
         */
        if (TimeUtils.is(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.RTH_0931.getLocalTime())) {
            //Set open price
            rthOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set OMAR
            omarOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getClosePrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (TimeUtils.is(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.ETH_1601.getLocalTime())) {
            //Set open price
            rthOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        // Bar is between RTH start and RTH end
        if (TimeUtils.isBetweenTimes(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.RTH_0931.getLocalTime(), MarketTime.ETH_1601.getLocalTime())) {
            if (rthOhlc.getHigh() == null) {
                rthOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            } else if (rthOhlc.getHigh().getPrice().isLessThan(series.getBar(previousIndex).getHighPrice())) {
                rthOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            }
        }
        if (TimeUtils.isBetweenTimes(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.RTH_0931.getLocalTime(), MarketTime.ETH_1601.getLocalTime())) {
            if (rthOhlc.getLow() == null) {
                rthOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            } else if (rthOhlc.getLow().getPrice().isGreaterThan(series.getBar(previousIndex).getLowPrice())) {
                rthOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            }
        }

        // RTH Start.
        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime())) {
            //Set current RTH OHLC to previous day OHLC
            priorDayRthOhlc = new OHLCIndicator(rthOhlc.getOpen(), rthOhlc.getHigh(), rthOhlc.getLow(), rthOhlc.getClose());

            //Reset all other fields
            rthOhlc = new OHLCIndicator();
            omarOhlc = new OHLCIndicator();
            dailyTradeTaken = false;
        }

        /**
         * End of Set RTH fields
         */


        /**
         * Set Overnight fields
         */
        // Bar is Opening overnight bar
        if (TimeUtils.is(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.ETH_1601.getLocalTime())) {
            overnightRthOhlc = new OHLCIndicator();
            //Set open price
            overnightRthOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }
        // Bar is close
        if (TimeUtils.is(series.getBar(previousIndex).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime())) {
            overnightRthOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getClosePrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        // Bar is in overnight
        if (isOvernight(series.getBar(previousIndex).getEndTime().toLocalTime())) {
            // Set high
            if (overnightRthOhlc.getHigh() == null) {
                overnightRthOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            } else if (overnightRthOhlc.getHigh().getPrice().isLessThan(series.getBar(previousIndex).getHighPrice())) {
                overnightRthOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            }

            // Set low
            if (overnightRthOhlc.getLow() == null) {
                overnightRthOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            } else if (overnightRthOhlc.getLow().getPrice().isGreaterThan(series.getBar(previousIndex).getLowPrice())) {
                overnightRthOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            }
        }

        /**
         * End of Set Overnight fields
         */


//        /**
//         * Update daily OHLC to prior day OHLC
//         */
//        // 1 candle before open. Update prior day OHLC and reset rthOhlc
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime())) {
//            priorDayRthOhlc = new OHLCIndicator(rthOhlc.getOpen(), rthOhlc.getHigh(), rthOhlc.getLow(), rthOhlc.getClose());
//            rthOhlc = new OHLCIndicator();
//            dailyTradeTaken = false;
//        }
//
//        /**
//         * Reset overnight OHLC
//         */
//        //
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_END_TIME.getLocalTime())) {
//            overnightRthOhlc = new OHLCIndicator();
//        }
//
//        /**
//         * Set daily fields
//         */
//        // Bar is OMAR
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime())) {
//            //Set open price
//            rthOhlc.setOpen(new DateTimePrice(series.getBar(index).getOpenPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            omarOhlc.setOpen(new DateTimePrice(series.getBar(index).getOpenPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            omarOhlc.setHigh(new DateTimePrice(series.getBar(index).getHighPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            omarOhlc.setLow(new DateTimePrice(series.getBar(index).getLowPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            omarOhlc.setClose(new DateTimePrice(series.getBar(index).getClosePrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//        }
//
//        // Bar is close
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_END_TIME.getLocalTime())) {
//            rthOhlc.setClose(new DateTimePrice(series.getBar(index).getClosePrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//        }
//
//        // Bar is between RTH start and RTH end
//        if (TimeUtils.isBetweenTimes(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime(), MarketTime.RTH_END_TIME.getLocalTime())) {
//            if (rthOhlc.getHigh() == null) {
//                rthOhlc.setHigh(new DateTimePrice(series.getBar(index).getHighPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            } else if (rthOhlc.getHigh().getPrice().isLessThan(series.getBar(index).getHighPrice())) {
//                rthOhlc.setHigh(new DateTimePrice(series.getBar(index).getHighPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            }
//        }
//        if (TimeUtils.isBetweenTimes(series.getBar(index).getEndTime().toLocalTime(), MarketTime.RTH_START_TIME.getLocalTime(), MarketTime.RTH_END_TIME.getLocalTime())) {
//            if (rthOhlc.getLow() == null) {
//                rthOhlc.setLow(new DateTimePrice(series.getBar(index).getLowPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            } else if (rthOhlc.getLow().getPrice().isGreaterThan(series.getBar(index).getLowPrice())) {
//                rthOhlc.setLow(new DateTimePrice(series.getBar(index).getLowPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            }
//        }
//
//        /**
//         * Set overnight fields
//         */
//        // Bar is Opening overnight bar
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.ETH_1601.getLocalTime())) {
//            //Set open price
//            overnightRthOhlc.setOpen(new DateTimePrice(series.getBar(index).getOpenPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//        }
//        // Bar is close
//        if (TimeUtils.is(series.getBar(index).getEndTime().toLocalTime(), MarketTime.ETH_0929.getLocalTime())) {
//            overnightRthOhlc.setClose(new DateTimePrice(series.getBar(index).getClosePrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//        }
//
//        // Bar is between RTH start and RTH end
//        if (isOvernight(series.getBar(index).getEndTime().toLocalTime())) {
//            if (overnightRthOhlc.getHigh() == null) {
//                overnightRthOhlc.setHigh(new DateTimePrice(series.getBar(index).getHighPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            } else if (overnightRthOhlc.getHigh().getPrice().isLessThan(series.getBar(index).getHighPrice())) {
//                overnightRthOhlc.setHigh(new DateTimePrice(series.getBar(index).getHighPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            }
//        }
//        if (isOvernight(series.getBar(index).getEndTime().toLocalTime())) {
//            if (overnightRthOhlc.getLow() == null) {
//                overnightRthOhlc.setLow(new DateTimePrice(series.getBar(index).getLowPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            } else if (overnightRthOhlc.getLow().getPrice().isGreaterThan(series.getBar(index).getLowPrice())) {
//                overnightRthOhlc.setLow(new DateTimePrice(series.getBar(index).getLowPrice(), series.getBar(index).getEndTime().toLocalDate(), series.getBar(index).getEndTime().toLocalTime()));
//            }
//        }
    }

    private static boolean isOvernight(LocalTime localTime) {
        return (TimeUtils.isBetweenTimes(localTime, MarketTime.ETH_1601.getLocalTime(), MarketTime.ETH_2359.getLocalTime()) ||
                TimeUtils.isBetweenTimes(localTime, MarketTime.ETH_0000.getLocalTime(), MarketTime.ETH_0929.getLocalTime()));
    }
}

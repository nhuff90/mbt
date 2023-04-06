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
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tracks daily MGI.
 */
public class DailyMgiBuyRule {

    public static BarSeries series;

    // todo - store all daily MGI in map.
    protected static Map<LocalDate, DailyMgi> historicalDailyMgi = new LinkedHashMap<>();

    protected static OHLCIndicator rthOhlc = new OHLCIndicator();
    protected static OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator omarOhlc = new OHLCIndicator();
    protected static OHLCIndicator overnightRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator amRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator microRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator pmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postAmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postMicroRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postPmRangeOhlc = new OHLCIndicator();
    // Initial Balance

    public static boolean dailyTradeTaken = false;

    public static Map<LocalDate, DailyMgi> getHistoricalDailyMgi() {
        return historicalDailyMgi;
    }

    public static void updateDailyMgi(int index, TradingRecord tradingRecord) {
        int previousIndex = index - 1;
        if (index <= 0 || series.getBar(previousIndex) == null) {
            return;
        }

        /**
         * Store historical daily MGI
         */
        if (MarketTime.isEndOfRthSession(series.getBar(previousIndex))) {
            // Add to historical daily MGI
            DailyMgi dailyMgi = new DailyMgi();
            dailyMgi.setRthOhlc(rthOhlc);
            dailyMgi.setOmarOhlc(omarOhlc);
            dailyMgi.setPriorDayRthOhlc(priorDayRthOhlc);
            dailyMgi.setAmRangeOhlc(amRangeOhlc);
            dailyMgi.setMicroRangeOhlc(microRangeOhlc);
            dailyMgi.setPmRangeOhlc(pmRangeOhlc);
            dailyMgi.setOvernightRthOhlc(overnightRthOhlc);
            dailyMgi.setPostAmRangeOhlc(postAmRangeOhlc);
            dailyMgi.setPostMicroRangeOhlc(postMicroRangeOhlc);
            dailyMgi.setPostPmRangeOhlc(postPmRangeOhlc);
            historicalDailyMgi.put(rthOhlc.getOpen().getDate(), dailyMgi);

            // Set current RTH OHLC to previous day OHLC
            priorDayRthOhlc = new OHLCIndicator(rthOhlc.getOpen(), rthOhlc.getHigh(), rthOhlc.getLow(), rthOhlc.getClose());

            //Reset all fields
            rthOhlc = new OHLCIndicator();
            omarOhlc = new OHLCIndicator();
            amRangeOhlc = new OHLCIndicator();
            microRangeOhlc = new OHLCIndicator();
            pmRangeOhlc = new OHLCIndicator();
            postAmRangeOhlc = new OHLCIndicator();
            postMicroRangeOhlc = new OHLCIndicator();
            postPmRangeOhlc = new OHLCIndicator();
            overnightRthOhlc = new OHLCIndicator();

            dailyTradeTaken = false;
        }

        if (MarketTime.isStartOfRthSession(series.getBar(previousIndex))) {
            // Set RTH open price
            rthOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set OMAR OHLC
            omarOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            omarOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getClosePrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfRthSession(series.getBar(previousIndex))) {
            rthOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set close for all Post-Session Ranges
            postAmRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInRthSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, rthOhlc);
        }

        if (MarketTime.isStartOfAmSession(series.getBar(previousIndex))) {
            // Set AM Range Open
            amRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfAmSession(series.getBar(previousIndex))) {
            // Set AM Range Close
            amRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set post-AM Session Open
            postAmRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInAmSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, amRangeOhlc);
        }

        if (MarketTime.isStartOfMicroSession(series.getBar(previousIndex))) {
            microRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfMicroSession(series.getBar(previousIndex))) {
            microRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInMicroSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, microRangeOhlc);
        }

        if (MarketTime.isStartOfPmSession(series.getBar(previousIndex))) {
            pmRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfPmSession(series.getBar(previousIndex))) {
            pmRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInPmSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, pmRangeOhlc);
        }

        if (MarketTime.isStartOfOvernightSession(series.getBar(previousIndex))) {
            overnightRthOhlc = new OHLCIndicator();
            //Set open price
            overnightRthOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfOvernightSession(series.getBar(previousIndex))) {
            overnightRthOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getClosePrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInOvernightSession(series.getBar(index))) {
            setHighAndLowOfSession(previousIndex, overnightRthOhlc);
        }

        if (MarketTime.isPostAmSessionToRthEnd(series.getBar(index))) {
            setHighAndLowOfSession(previousIndex, postAmRangeOhlc);
        }
    }

    private static void setHighAndLowOfSession(int previousIndex, OHLCIndicator sessionOhlc) {
        if (sessionOhlc.getHigh() == null) {
            sessionOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (sessionOhlc.getHigh().getPrice().isLessThan(series.getBar(previousIndex).getHighPrice())) {
            sessionOhlc.setHigh(new DateTimePrice(series.getBar(previousIndex).getHighPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (sessionOhlc.getLow() == null) {
            sessionOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (sessionOhlc.getLow().getPrice().isGreaterThan(series.getBar(previousIndex).getLowPrice())) {
            sessionOhlc.setLow(new DateTimePrice(series.getBar(previousIndex).getLowPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }
    }
}

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

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tracks daily MGI.
 */
public class DailyMgiBuyRule {

    public static BarSeries series;

    // todo - store all daily MGI in map.
    protected static Map<LocalDate, DailyMgi> historicalDailyMgi = new LinkedHashMap<>();

    // Sessions
    protected static OHLCIndicator rthOhlc = new OHLCIndicator();
    protected static OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator omarOhlc = new OHLCIndicator();
    protected static OHLCIndicator overnightRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator amRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator microRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator pmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator ibRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator opening15mRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator opening5mRangeOhlc = new OHLCIndicator();

    // Post Sessions
    protected static OHLCIndicator postAmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postMicroRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postPmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postIBRangeOhlc = new OHLCIndicator();
    // End of Session

    // 30 min ranges
    protected static OHLCIndicator aPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator bPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator cPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator dPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator ePeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator fPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator gPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator hPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator iPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator jPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator kPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator lPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator mPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preBPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preCPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preDPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preEPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preFPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preGPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preHPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preIPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preJPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preKPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preLPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator preMPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postAPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postBPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postCPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postDPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postEPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postFPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postGPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postHPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postIPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postJPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postKPeriodOhlc = new OHLCIndicator();
    protected static OHLCIndicator postLPeriodOhlc = new OHLCIndicator();
    // End 30 min ranges

    // Other time ranges
    protected static OHLCIndicator preMarket0915To0929Ohlc = new OHLCIndicator();
    // End of other time ranges.

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
        if (MarketTime.isStartOfRthSession(series.getBar(previousIndex))) {
            // Clear Ohlc's that should be reset at start of Rth
            rthStartActions();
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

        if (MarketTime.isStartOfIbSession(series.getBar(previousIndex))) {
            ibRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfIbSession(series.getBar(previousIndex))) {
            ibRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInIbSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, ibRangeOhlc);
        }

        if (MarketTime.isStartOfOpening15mSession(series.getBar(previousIndex))) {
            opening15mRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfOpening15mSession(series.getBar(previousIndex))) {
            opening15mRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set post-AM Session Open
//            postAmRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isStartOfOpening5mSession(series.getBar(previousIndex))) {
            opening5mRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfOpening5mSession(series.getBar(previousIndex))) {
            opening5mRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));

            // Set post-AM Session Open
//            postAmRangeOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInOpening15mSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, opening15mRangeOhlc);
        }

        if (MarketTime.isInOpening5mSession(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, opening5mRangeOhlc);
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

        if (MarketTime.isPostMicroSessionToRthEnd(series.getBar(index))) {
            setHighAndLowOfSession(previousIndex, postMicroRangeOhlc);
        }

        if (MarketTime.isPostPmSessionToRthEnd(series.getBar(index))) {
            setHighAndLowOfSession(previousIndex, postPmRangeOhlc);
        }

        if (MarketTime.isPostIbSessionToRthEnd(series.getBar(index))) {
            setHighAndLowOfSession(previousIndex, postIBRangeOhlc);
        }

        if (MarketTime.isEndOfAmSession(series.getBar(previousIndex))) {
            amRangeOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }


        setOtherTimeRanges(previousIndex);

        setAll30mPeriod(previousIndex);

        /*
        Keep this as last in the function
         */
        if (MarketTime.isEndOfRthSession(series.getBar(previousIndex))) {
            rthEndActions();
        }

    }

    private static void setOtherTimeRanges(int previousIndex) {
        /*
        0915-0929 Session
         */
        if (MarketTime.isInBetweenTimes(series.getBar(previousIndex), MarketTime.ETH_0915.getLocalTime(), MarketTime.ETH_END_TIME_0929.getLocalTime())) {
            setHighAndLowOfSession(previousIndex, preMarket0915To0929Ohlc);
        }

        if (MarketTime.isTime(series.getBar(previousIndex), MarketTime.ETH_0915.getLocalTime())) {
            preMarket0915To0929Ohlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isTime(series.getBar(previousIndex), MarketTime.ETH_END_TIME_0929.getLocalTime())) {
            preMarket0915To0929Ohlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }
        /*
        END 0915-0929 Session
         */
    }

    private static void setAll30mPeriod(int previousIndex) {

        /*
        Set 30m periods
         */
        if (MarketTime.isStartOfAPeriod(series.getBar(previousIndex))) {
            aPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfBPeriod(series.getBar(previousIndex))) {
            bPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfCPeriod(series.getBar(previousIndex))) {
            cPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfDPeriod(series.getBar(previousIndex))) {
            dPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfEPeriod(series.getBar(previousIndex))) {
            ePeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfFPeriod(series.getBar(previousIndex))) {
            fPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfGPeriod(series.getBar(previousIndex))) {
            gPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfHPeriod(series.getBar(previousIndex))) {
            hPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfIPeriod(series.getBar(previousIndex))) {
            iPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfJPeriod(series.getBar(previousIndex))) {
            jPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfKPeriod(series.getBar(previousIndex))) {
            kPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfLPeriod(series.getBar(previousIndex))) {
            lPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfMPeriod(series.getBar(previousIndex))) {
            mPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isEndOfAPeriod(series.getBar(previousIndex))) {
            aPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfBPeriod(series.getBar(previousIndex))) {
            bPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfCPeriod(series.getBar(previousIndex))) {
            cPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfDPeriod(series.getBar(previousIndex))) {
            dPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfEPeriod(series.getBar(previousIndex))) {
            ePeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfFPeriod(series.getBar(previousIndex))) {
            fPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfGPeriod(series.getBar(previousIndex))) {
            gPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfHPeriod(series.getBar(previousIndex))) {
            hPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfIPeriod(series.getBar(previousIndex))) {
            iPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfJPeriod(series.getBar(previousIndex))) {
            jPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfKPeriod(series.getBar(previousIndex))) {
            kPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfLPeriod(series.getBar(previousIndex))) {
            lPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isEndOfMPeriod(series.getBar(previousIndex))) {
            mPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        if (MarketTime.isInAPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, aPeriodOhlc);
        }
        if (MarketTime.isInBPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, bPeriodOhlc);
        }
        if (MarketTime.isInCPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, cPeriodOhlc);
        }
        if (MarketTime.isInDPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, dPeriodOhlc);
        }
        if (MarketTime.isInEPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, ePeriodOhlc);
        }
        if (MarketTime.isInFPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, fPeriodOhlc);
        }
        if (MarketTime.isInGPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, gPeriodOhlc);
        }
        if (MarketTime.isInHPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, hPeriodOhlc);
        }
        if (MarketTime.isInIPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, iPeriodOhlc);
        }
        if (MarketTime.isInJPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, jPeriodOhlc);
        }
        if (MarketTime.isInKPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, kPeriodOhlc);
        }
        if (MarketTime.isInLPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, lPeriodOhlc);
        }
        if (MarketTime.isInMPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, mPeriodOhlc);
        }

        /*
        Set start of Pre-30m periods
         */
        if (MarketTime.isStartOfRthSession(series.getBar(previousIndex))) {
            preBPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preCPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preDPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preEPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preFPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preGPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preHPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preIPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preJPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preKPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preLPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            preMPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        /*
        Set end of Pre-30m periods
         */
        if (MarketTime.isStartOfBPeriod(series.getBar(previousIndex))) {
            preBPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfCPeriod(series.getBar(previousIndex))) {
            preCPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfDPeriod(series.getBar(previousIndex))) {
            preDPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfEPeriod(series.getBar(previousIndex))) {
            preEPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfFPeriod(series.getBar(previousIndex))) {
            preFPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfGPeriod(series.getBar(previousIndex))) {
            preGPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfHPeriod(series.getBar(previousIndex))) {
            preHPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfIPeriod(series.getBar(previousIndex))) {
            preIPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfJPeriod(series.getBar(previousIndex))) {
            preJPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfKPeriod(series.getBar(previousIndex))) {
            preKPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfLPeriod(series.getBar(previousIndex))) {
            preLPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfMPeriod(series.getBar(previousIndex))) {
            preMPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }


        /*
        Is In Pre-30m periods
         */

        if (MarketTime.isInPreBPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preBPeriodOhlc);
        }
        if (MarketTime.isInPreCPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preCPeriodOhlc);
        }
        if (MarketTime.isInPreDPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preDPeriodOhlc);
        }
        if (MarketTime.isInPreEPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preEPeriodOhlc);
        }
        if (MarketTime.isInPreFPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preFPeriodOhlc);
        }
        if (MarketTime.isInPreGPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preGPeriodOhlc);
        }
        if (MarketTime.isInPreHPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preHPeriodOhlc);
        }
        if (MarketTime.isInPreIPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preIPeriodOhlc);
        }
        if (MarketTime.isInPreJPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preJPeriodOhlc);
        }
        if (MarketTime.isInPreKPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preKPeriodOhlc);
        }
        if (MarketTime.isInPreLPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preLPeriodOhlc);
        }
        if (MarketTime.isInPreMPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, preMPeriodOhlc);
        }


        /*
        Post-30m periods
        Set starts of Post-30m periods
         */
        if (MarketTime.isStartOfBPeriod(series.getBar(previousIndex))) {
            postAPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfCPeriod(series.getBar(previousIndex))) {
            postBPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfDPeriod(series.getBar(previousIndex))) {
            postCPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfEPeriod(series.getBar(previousIndex))) {
            postDPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfFPeriod(series.getBar(previousIndex))) {
            postEPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfGPeriod(series.getBar(previousIndex))) {
            postFPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfHPeriod(series.getBar(previousIndex))) {
            postGPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfIPeriod(series.getBar(previousIndex))) {
            postHPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfJPeriod(series.getBar(previousIndex))) {
            postIPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfKPeriod(series.getBar(previousIndex))) {
            postJPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfLPeriod(series.getBar(previousIndex))) {
            postKPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        } else if (MarketTime.isStartOfMPeriod(series.getBar(previousIndex))) {
            postLPeriodOhlc.setOpen(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }

        /*
        Is In Post-30m periods
         */

//        // todo remove
//        if (MarketTime.isInPmSession(series.getBar(previousIndex))) {
//            System.out.println("test");
//        }

        if (MarketTime.isInPostAPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postAPeriodOhlc);
        }
        if (MarketTime.isInPostBPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postBPeriodOhlc);
        }
        if (MarketTime.isInPostCPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postCPeriodOhlc);
        }
        if (MarketTime.isInPostDPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postDPeriodOhlc);
        }
        if (MarketTime.isInPostEPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postEPeriodOhlc);
        }
        if (MarketTime.isInPostFPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postFPeriodOhlc);
        }
        if (MarketTime.isInPostGPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postGPeriodOhlc);
        }
        if (MarketTime.isInPostHPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postHPeriodOhlc);
        }
        if (MarketTime.isInPostIPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postIPeriodOhlc);
        }
        if (MarketTime.isInPostJPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postJPeriodOhlc);
        }
        if (MarketTime.isInPostKPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postKPeriodOhlc);
        }
        if (MarketTime.isInPostLPeriod(series.getBar(previousIndex))) {
            setHighAndLowOfSession(previousIndex, postLPeriodOhlc);
        }


        /*
        Set ends of Post-30m periods
         */
        if (MarketTime.isEndOfRthSession(series.getBar(previousIndex))) {
            postAPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postBPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postCPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postDPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postEPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postFPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postGPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postHPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postIPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postJPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postKPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
            postLPeriodOhlc.setClose(new DateTimePrice(series.getBar(previousIndex).getOpenPrice(), series.getBar(previousIndex).getEndTime().toLocalDate(), series.getBar(previousIndex).getEndTime().toLocalTime()));
        }


    }

    private static void rthStartActions() {

        //Reset all fields
        rthOhlc = new OHLCIndicator();
        omarOhlc = new OHLCIndicator();
        amRangeOhlc = new OHLCIndicator();
        microRangeOhlc = new OHLCIndicator();
        pmRangeOhlc = new OHLCIndicator();
        ibRangeOhlc = new OHLCIndicator();
        opening15mRangeOhlc = new OHLCIndicator();
        opening5mRangeOhlc = new OHLCIndicator();

        postAmRangeOhlc = new OHLCIndicator();
        postMicroRangeOhlc = new OHLCIndicator();
        postPmRangeOhlc = new OHLCIndicator();
        postIBRangeOhlc = new OHLCIndicator();

        aPeriodOhlc = new OHLCIndicator();
        bPeriodOhlc = new OHLCIndicator();
        cPeriodOhlc = new OHLCIndicator();
        dPeriodOhlc = new OHLCIndicator();
        ePeriodOhlc = new OHLCIndicator();
        fPeriodOhlc = new OHLCIndicator();
        gPeriodOhlc = new OHLCIndicator();
        hPeriodOhlc = new OHLCIndicator();
        iPeriodOhlc = new OHLCIndicator();
        jPeriodOhlc = new OHLCIndicator();
        kPeriodOhlc = new OHLCIndicator();
        lPeriodOhlc = new OHLCIndicator();
        mPeriodOhlc = new OHLCIndicator();

        preBPeriodOhlc = new OHLCIndicator();
        preCPeriodOhlc = new OHLCIndicator();
        preDPeriodOhlc = new OHLCIndicator();
        preEPeriodOhlc = new OHLCIndicator();
        preFPeriodOhlc = new OHLCIndicator();
        preGPeriodOhlc = new OHLCIndicator();
        preHPeriodOhlc = new OHLCIndicator();
        preIPeriodOhlc = new OHLCIndicator();
        preJPeriodOhlc = new OHLCIndicator();
        preKPeriodOhlc = new OHLCIndicator();
        preLPeriodOhlc = new OHLCIndicator();
        preMPeriodOhlc = new OHLCIndicator();

        postAPeriodOhlc = new OHLCIndicator();
        postBPeriodOhlc = new OHLCIndicator();
        postCPeriodOhlc = new OHLCIndicator();
        postDPeriodOhlc = new OHLCIndicator();
        postEPeriodOhlc = new OHLCIndicator();
        postFPeriodOhlc = new OHLCIndicator();
        postGPeriodOhlc = new OHLCIndicator();
        postHPeriodOhlc = new OHLCIndicator();
        postIPeriodOhlc = new OHLCIndicator();
        postJPeriodOhlc = new OHLCIndicator();
        postKPeriodOhlc = new OHLCIndicator();
        postLPeriodOhlc = new OHLCIndicator();

        dailyTradeTaken = false;
    }

    private static void rthEndActions() {
        // Add to historical daily MGI
        DailyMgi dailyMgi = new DailyMgi();
        dailyMgi.setRthOhlc(rthOhlc);
        dailyMgi.setOmarOhlc(omarOhlc);
        dailyMgi.setPriorDayRthOhlc(priorDayRthOhlc);
        dailyMgi.setAmRangeOhlc(amRangeOhlc);
        dailyMgi.setMicroRangeOhlc(microRangeOhlc);
        dailyMgi.setPmRangeOhlc(pmRangeOhlc);
        dailyMgi.setIbOhlc(ibRangeOhlc);
        dailyMgi.setOvernightRthOhlc(overnightRthOhlc);
        dailyMgi.setOpening15MinRangeOhlc(opening15mRangeOhlc);
        dailyMgi.setOpening5MinRangeOhlc(opening5mRangeOhlc);

        dailyMgi.setPostAmRangeOhlc(postAmRangeOhlc);
        dailyMgi.setPostMicroRangeOhlc(postMicroRangeOhlc);
        dailyMgi.setPostPmRangeOhlc(postPmRangeOhlc);
        dailyMgi.setPostIbOhlc(postIBRangeOhlc);
        dailyMgi.setPreMarket0915To0929Ohlc(preMarket0915To0929Ohlc);

        dailyMgi.setaPeriodOhlc(aPeriodOhlc);
        dailyMgi.setbPeriodOhlc(bPeriodOhlc);
        dailyMgi.setcPeriodOhlc(cPeriodOhlc);
        dailyMgi.setdPeriodOhlc(dPeriodOhlc);
        dailyMgi.setePeriodOhlc(ePeriodOhlc);
        dailyMgi.setfPeriodOhlc(fPeriodOhlc);
        dailyMgi.setgPeriodOhlc(gPeriodOhlc);
        dailyMgi.sethPeriodOhlc(hPeriodOhlc);
        dailyMgi.setiPeriodOhlc(iPeriodOhlc);
        dailyMgi.setjPeriodOhlc(jPeriodOhlc);
        dailyMgi.setkPeriodOhlc(kPeriodOhlc);
        dailyMgi.setlPeriodOhlc(lPeriodOhlc);
        dailyMgi.setmPeriodOhlc(mPeriodOhlc);

        dailyMgi.setPreBPeriodOhlc(preBPeriodOhlc);
        dailyMgi.setPreCPeriodOhlc(preCPeriodOhlc);
        dailyMgi.setPreDPeriodOhlc(preDPeriodOhlc);
        dailyMgi.setPreEPeriodOhlc(preEPeriodOhlc);
        dailyMgi.setPreFPeriodOhlc(preFPeriodOhlc);
        dailyMgi.setPreGPeriodOhlc(preGPeriodOhlc);
        dailyMgi.setPreHPeriodOhlc(preHPeriodOhlc);
        dailyMgi.setPreIPeriodOhlc(preIPeriodOhlc);
        dailyMgi.setPreJPeriodOhlc(preJPeriodOhlc);
        dailyMgi.setPreKPeriodOhlc(preKPeriodOhlc);
        dailyMgi.setPreLPeriodOhlc(preLPeriodOhlc);
        dailyMgi.setPreMPeriodOhlc(preMPeriodOhlc);

        dailyMgi.setPostAPeriodOhlc(postAPeriodOhlc);
        dailyMgi.setPostBPeriodOhlc(postBPeriodOhlc);
        dailyMgi.setPostCPeriodOhlc(postCPeriodOhlc);
        dailyMgi.setPostDPeriodOhlc(postDPeriodOhlc);
        dailyMgi.setPostEPeriodOhlc(postEPeriodOhlc);
        dailyMgi.setPostFPeriodOhlc(postFPeriodOhlc);
        dailyMgi.setPostGPeriodOhlc(postGPeriodOhlc);
        dailyMgi.setPostHPeriodOhlc(postHPeriodOhlc);
        dailyMgi.setPostIPeriodOhlc(postIPeriodOhlc);
        dailyMgi.setPostJPeriodOhlc(postJPeriodOhlc);
        dailyMgi.setPostKPeriodOhlc(postKPeriodOhlc);
        dailyMgi.setPostLPeriodOhlc(postLPeriodOhlc);

        historicalDailyMgi.put(rthOhlc.getOpen().getDate(), dailyMgi);

        // Set current RTH OHLC to previous day OHLC
        priorDayRthOhlc = new OHLCIndicator(rthOhlc.getOpen(), rthOhlc.getHigh(), rthOhlc.getLow(), rthOhlc.getClose());

        //Reset all fields
        rthOhlc = new OHLCIndicator();
        omarOhlc = new OHLCIndicator();
        amRangeOhlc = new OHLCIndicator();
        microRangeOhlc = new OHLCIndicator();
        pmRangeOhlc = new OHLCIndicator();
        ibRangeOhlc = new OHLCIndicator();
        opening15mRangeOhlc = new OHLCIndicator();
        opening5mRangeOhlc = new OHLCIndicator();

        postAmRangeOhlc = new OHLCIndicator();
        postMicroRangeOhlc = new OHLCIndicator();
        postPmRangeOhlc = new OHLCIndicator();
        postIBRangeOhlc = new OHLCIndicator();
        overnightRthOhlc = new OHLCIndicator();
        preMarket0915To0929Ohlc = new OHLCIndicator();

        aPeriodOhlc = new OHLCIndicator();
        bPeriodOhlc = new OHLCIndicator();
        cPeriodOhlc = new OHLCIndicator();
        dPeriodOhlc = new OHLCIndicator();
        ePeriodOhlc = new OHLCIndicator();
        fPeriodOhlc = new OHLCIndicator();
        gPeriodOhlc = new OHLCIndicator();
        hPeriodOhlc = new OHLCIndicator();
        iPeriodOhlc = new OHLCIndicator();
        jPeriodOhlc = new OHLCIndicator();
        kPeriodOhlc = new OHLCIndicator();
        lPeriodOhlc = new OHLCIndicator();
        mPeriodOhlc = new OHLCIndicator();

        preBPeriodOhlc = new OHLCIndicator();
        preCPeriodOhlc = new OHLCIndicator();
        preDPeriodOhlc = new OHLCIndicator();
        preEPeriodOhlc = new OHLCIndicator();
        preFPeriodOhlc = new OHLCIndicator();
        preGPeriodOhlc = new OHLCIndicator();
        preHPeriodOhlc = new OHLCIndicator();
        preIPeriodOhlc = new OHLCIndicator();
        preJPeriodOhlc = new OHLCIndicator();
        preKPeriodOhlc = new OHLCIndicator();
        preLPeriodOhlc = new OHLCIndicator();
        preMPeriodOhlc = new OHLCIndicator();

        postAPeriodOhlc = new OHLCIndicator();
        postBPeriodOhlc = new OHLCIndicator();
        postCPeriodOhlc = new OHLCIndicator();
        postDPeriodOhlc = new OHLCIndicator();
        postEPeriodOhlc = new OHLCIndicator();
        postFPeriodOhlc = new OHLCIndicator();
        postGPeriodOhlc = new OHLCIndicator();
        postHPeriodOhlc = new OHLCIndicator();
        postIPeriodOhlc = new OHLCIndicator();
        postJPeriodOhlc = new OHLCIndicator();
        postKPeriodOhlc = new OHLCIndicator();
        postLPeriodOhlc = new OHLCIndicator();

        dailyTradeTaken = false;
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

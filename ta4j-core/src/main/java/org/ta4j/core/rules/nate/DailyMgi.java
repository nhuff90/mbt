package org.ta4j.core.rules.nate;

import org.ta4j.core.indicators.nate.OHLCIndicator;

public class DailyMgi {
    protected static OHLCIndicator rthOhlc = new OHLCIndicator();
    protected static OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected static OHLCIndicator overnightRthOhlc = new OHLCIndicator();

    protected static OHLCIndicator omarOhlc = new OHLCIndicator();
    protected static OHLCIndicator postOmarOhlc = new OHLCIndicator();

    protected static OHLCIndicator opening5MinRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postOpening5MinRangeOhlc = new OHLCIndicator();

    protected static OHLCIndicator opening15MinRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postOpening15MinRangeOhlc = new OHLCIndicator();

    protected static OHLCIndicator initialBalanceOhlc = new OHLCIndicator();
    protected static OHLCIndicator postInitialBalanceOhlc = new OHLCIndicator();

    protected static OHLCIndicator amRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postAmRangeOhlc = new OHLCIndicator();

    protected static OHLCIndicator microRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postMicroRangeOhlc = new OHLCIndicator();

    protected static OHLCIndicator pmRangeOhlc = new OHLCIndicator();
    protected static OHLCIndicator postPmRangeOhlc = new OHLCIndicator();

}

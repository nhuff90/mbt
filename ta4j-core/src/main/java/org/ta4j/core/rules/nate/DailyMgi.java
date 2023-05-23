package org.ta4j.core.rules.nate;

import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DailyMgi {
    List<OHLCIndicator> oneMinOhlcList = new ArrayList<>();

    DailyTrend dailyTrend = DailyTrend.RANGE;

    // Sessions
    protected OHLCIndicator rthOhlc = new OHLCIndicator();
    protected OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected OHLCIndicator overnightRthOhlc = new OHLCIndicator();

    protected OHLCIndicator omarOhlc = new OHLCIndicator();
    protected OHLCIndicator postOmarOhlc = new OHLCIndicator();

    protected OHLCIndicator opening5MinRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postOpening5MinRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator opening15MinRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postOpening15MinRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator opening10MinRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postOpening10MinRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator ibOhlc = new OHLCIndicator();
    protected OHLCIndicator postIbOhlc = new OHLCIndicator();

    protected OHLCIndicator amRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postAmRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator microRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postMicroRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator pmRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postPmRangeOhlc = new OHLCIndicator();
    // End of Sessions

    // 30m Periods
    protected OHLCIndicator aPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator bPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator cPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator dPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator ePeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator fPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator gPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator hPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator iPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator jPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator kPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator lPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator mPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preAPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preBPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preCPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preDPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preEPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preFPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preGPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preHPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preIPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preJPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preKPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preLPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator preMPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postAPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postBPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postCPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postDPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postEPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postFPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postGPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postHPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postIPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postJPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postKPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postLPeriodOhlc = new OHLCIndicator();
    protected OHLCIndicator postMPeriodOhlc = new OHLCIndicator();
    // End of 30m Periods

    // Other Time Ranges
    protected OHLCIndicator preMarket0915To0929Ohlc = new OHLCIndicator();
    // End of Other Time Ranges

    public DailyMgi() {
    }

    public OHLCIndicator getRthOhlc() {
        return rthOhlc;
    }

    public void setRthOhlc(OHLCIndicator rthOhlc) {
        this.rthOhlc = rthOhlc;
    }

    public OHLCIndicator getPriorDayRthOhlc() {
        return priorDayRthOhlc;
    }

    public void setPriorDayRthOhlc(OHLCIndicator priorDayRthOhlc) {
        this.priorDayRthOhlc = priorDayRthOhlc;
    }

    public OHLCIndicator getOvernightRthOhlc() {
        return overnightRthOhlc;
    }

    public void setOvernightRthOhlc(OHLCIndicator overnightRthOhlc) {
        this.overnightRthOhlc = overnightRthOhlc;
    }

    public OHLCIndicator getOmarOhlc() {
        return omarOhlc;
    }

    public void setOmarOhlc(OHLCIndicator omarOhlc) {
        this.omarOhlc = omarOhlc;
    }

    public OHLCIndicator getPostOmarOhlc() {
        return postOmarOhlc;
    }

    public void setPostOmarOhlc(OHLCIndicator postOmarOhlc) {
        this.postOmarOhlc = postOmarOhlc;
    }

    public OHLCIndicator getOpening5MinRangeOhlc() {
        return opening5MinRangeOhlc;
    }

    public void setOpening5MinRangeOhlc(OHLCIndicator opening5MinRangeOhlc) {
        this.opening5MinRangeOhlc = opening5MinRangeOhlc;
    }

    public OHLCIndicator getPostOpening5MinRangeOhlc() {
        return postOpening5MinRangeOhlc;
    }

    public void setPostOpening5MinRangeOhlc(OHLCIndicator postOpening5MinRangeOhlc) {
        this.postOpening5MinRangeOhlc = postOpening5MinRangeOhlc;
    }

    public OHLCIndicator getOpening15MinRangeOhlc() {
        return opening15MinRangeOhlc;
    }

    public void setOpening15MinRangeOhlc(OHLCIndicator opening15MinRangeOhlc) {
        this.opening15MinRangeOhlc = opening15MinRangeOhlc;
    }

    public OHLCIndicator getOpening10MinRangeOhlc() {
        return opening10MinRangeOhlc;
    }

    public void setOpening10MinRangeOhlc(OHLCIndicator opening10MinRangeOhlc) {
        this.opening10MinRangeOhlc = opening10MinRangeOhlc;
    }

    public OHLCIndicator getPostOpening15MinRangeOhlc() {
        return postOpening15MinRangeOhlc;
    }

    public void setPostOpening15MinRangeOhlc(OHLCIndicator postOpening15MinRangeOhlc) {
        this.postOpening15MinRangeOhlc = postOpening15MinRangeOhlc;
    }

    public OHLCIndicator getIbOhlc() {
        return ibOhlc;
    }

    public void setIbOhlc(OHLCIndicator ibOhlc) {
        this.ibOhlc = ibOhlc;
    }

    public OHLCIndicator getPostIbOhlc() {
        return postIbOhlc;
    }

    public void setPostIbOhlc(OHLCIndicator postIbOhlc) {
        this.postIbOhlc = postIbOhlc;
    }

    public OHLCIndicator getAmRangeOhlc() {
        return amRangeOhlc;
    }

    public void setAmRangeOhlc(OHLCIndicator amRangeOhlc) {
        this.amRangeOhlc = amRangeOhlc;
    }

    public OHLCIndicator getPostAmRangeOhlc() {
        return postAmRangeOhlc;
    }

    public void setPostAmRangeOhlc(OHLCIndicator postAmRangeOhlc) {
        this.postAmRangeOhlc = postAmRangeOhlc;
    }

    public OHLCIndicator getMicroRangeOhlc() {
        return microRangeOhlc;
    }

    public void setMicroRangeOhlc(OHLCIndicator microRangeOhlc) {
        this.microRangeOhlc = microRangeOhlc;
    }

    public OHLCIndicator getPostMicroRangeOhlc() {
        return postMicroRangeOhlc;
    }

    public void setPostMicroRangeOhlc(OHLCIndicator postMicroRangeOhlc) {
        this.postMicroRangeOhlc = postMicroRangeOhlc;
    }

    public OHLCIndicator getPmRangeOhlc() {
        return pmRangeOhlc;
    }

    public void setPmRangeOhlc(OHLCIndicator pmRangeOhlc) {
        this.pmRangeOhlc = pmRangeOhlc;
        calculateAndSetDailyTrend();
    }

    public OHLCIndicator getPostPmRangeOhlc() {
        return postPmRangeOhlc;
    }

    public void setPostPmRangeOhlc(OHLCIndicator postPmRangeOhlc) {
        this.postPmRangeOhlc = postPmRangeOhlc;
    }

    public OHLCIndicator getaPeriodOhlc() {
        return aPeriodOhlc;
    }

    public void setaPeriodOhlc(OHLCIndicator aPeriodOhlc) {
        this.aPeriodOhlc = aPeriodOhlc;
    }

    public OHLCIndicator getbPeriodOhlc() {
        return bPeriodOhlc;
    }

    public void setbPeriodOhlc(OHLCIndicator bPeriodOhlc) {
        this.bPeriodOhlc = bPeriodOhlc;
    }

    public OHLCIndicator getcPeriodOhlc() {
        return cPeriodOhlc;
    }

    public void setcPeriodOhlc(OHLCIndicator cPeriodOhlc) {
        this.cPeriodOhlc = cPeriodOhlc;
    }

    public OHLCIndicator getdPeriodOhlc() {
        return dPeriodOhlc;
    }

    public void setdPeriodOhlc(OHLCIndicator dPeriodOhlc) {
        this.dPeriodOhlc = dPeriodOhlc;
    }

    public OHLCIndicator getePeriodOhlc() {
        return ePeriodOhlc;
    }

    public void setePeriodOhlc(OHLCIndicator ePeriodOhlc) {
        this.ePeriodOhlc = ePeriodOhlc;
    }

    public OHLCIndicator getfPeriodOhlc() {
        return fPeriodOhlc;
    }

    public void setfPeriodOhlc(OHLCIndicator fPeriodOhlc) {
        this.fPeriodOhlc = fPeriodOhlc;
    }

    public OHLCIndicator getgPeriodOhlc() {
        return gPeriodOhlc;
    }

    public void setgPeriodOhlc(OHLCIndicator gPeriodOhlc) {
        this.gPeriodOhlc = gPeriodOhlc;
    }

    public OHLCIndicator gethPeriodOhlc() {
        return hPeriodOhlc;
    }

    public void sethPeriodOhlc(OHLCIndicator hPeriodOhlc) {
        this.hPeriodOhlc = hPeriodOhlc;
    }

    public OHLCIndicator getiPeriodOhlc() {
        return iPeriodOhlc;
    }

    public void setiPeriodOhlc(OHLCIndicator iPeriodOhlc) {
        this.iPeriodOhlc = iPeriodOhlc;
    }

    public OHLCIndicator getjPeriodOhlc() {
        return jPeriodOhlc;
    }

    public void setjPeriodOhlc(OHLCIndicator jPeriodOhlc) {
        this.jPeriodOhlc = jPeriodOhlc;
    }

    public OHLCIndicator getkPeriodOhlc() {
        return kPeriodOhlc;
    }

    public void setkPeriodOhlc(OHLCIndicator kPeriodOhlc) {
        this.kPeriodOhlc = kPeriodOhlc;
    }

    public OHLCIndicator getlPeriodOhlc() {
        return lPeriodOhlc;
    }

    public void setlPeriodOhlc(OHLCIndicator lPeriodOhlc) {
        this.lPeriodOhlc = lPeriodOhlc;
    }

    public OHLCIndicator getmPeriodOhlc() {
        return mPeriodOhlc;
    }

    public void setmPeriodOhlc(OHLCIndicator mPeriodOhlc) {
        this.mPeriodOhlc = mPeriodOhlc;
    }

    public OHLCIndicator getPostAPeriodOhlc() {
        return postAPeriodOhlc;
    }

    public void setPostAPeriodOhlc(OHLCIndicator postAPeriodOhlc) {
        this.postAPeriodOhlc = postAPeriodOhlc;
    }

    public OHLCIndicator getPostBPeriodOhlc() {
        return postBPeriodOhlc;
    }

    public void setPostBPeriodOhlc(OHLCIndicator postBPeriodOhlc) {
        this.postBPeriodOhlc = postBPeriodOhlc;
    }

    public OHLCIndicator getPostCPeriodOhlc() {
        return postCPeriodOhlc;
    }

    public void setPostCPeriodOhlc(OHLCIndicator postCPeriodOhlc) {
        this.postCPeriodOhlc = postCPeriodOhlc;
    }

    public OHLCIndicator getPostDPeriodOhlc() {
        return postDPeriodOhlc;
    }

    public void setPostDPeriodOhlc(OHLCIndicator postDPeriodOhlc) {
        this.postDPeriodOhlc = postDPeriodOhlc;
    }

    public OHLCIndicator getPostEPeriodOhlc() {
        return postEPeriodOhlc;
    }

    public void setPostEPeriodOhlc(OHLCIndicator postEPeriodOhlc) {
        this.postEPeriodOhlc = postEPeriodOhlc;
    }

    public OHLCIndicator getPostFPeriodOhlc() {
        return postFPeriodOhlc;
    }

    public void setPostFPeriodOhlc(OHLCIndicator postFPeriodOhlc) {
        this.postFPeriodOhlc = postFPeriodOhlc;
    }

    public OHLCIndicator getPostGPeriodOhlc() {
        return postGPeriodOhlc;
    }

    public void setPostGPeriodOhlc(OHLCIndicator postGPeriodOhlc) {
        this.postGPeriodOhlc = postGPeriodOhlc;
    }

    public OHLCIndicator getPostHPeriodOhlc() {
        return postHPeriodOhlc;
    }

    public void setPostHPeriodOhlc(OHLCIndicator postHPeriodOhlc) {
        this.postHPeriodOhlc = postHPeriodOhlc;
    }

    public OHLCIndicator getPostIPeriodOhlc() {
        return postIPeriodOhlc;
    }

    public void setPostIPeriodOhlc(OHLCIndicator postIPeriodOhlc) {
        this.postIPeriodOhlc = postIPeriodOhlc;
    }

    public OHLCIndicator getPostJPeriodOhlc() {
        return postJPeriodOhlc;
    }

    public void setPostJPeriodOhlc(OHLCIndicator postJPeriodOhlc) {
        this.postJPeriodOhlc = postJPeriodOhlc;
    }

    public OHLCIndicator getPostKPeriodOhlc() {
        return postKPeriodOhlc;
    }

    public void setPostKPeriodOhlc(OHLCIndicator postKPeriodOhlc) {
        this.postKPeriodOhlc = postKPeriodOhlc;
    }

    public OHLCIndicator getPostLPeriodOhlc() {
        return postLPeriodOhlc;
    }

    public void setPostLPeriodOhlc(OHLCIndicator postLPeriodOhlc) {
        this.postLPeriodOhlc = postLPeriodOhlc;
    }

    public OHLCIndicator getPostMPeriodOhlc() {
        return postMPeriodOhlc;
    }

    public void setPostMPeriodOhlc(OHLCIndicator postMPeriodOhlc) {
        this.postMPeriodOhlc = postMPeriodOhlc;
    }

    public OHLCIndicator getPreAPeriodOhlc() {
        return preAPeriodOhlc;
    }

    public void setPreAPeriodOhlc(OHLCIndicator preAPeriodOhlc) {
        this.preAPeriodOhlc = preAPeriodOhlc;
    }

    public OHLCIndicator getPreBPeriodOhlc() {
        return preBPeriodOhlc;
    }

    public void setPreBPeriodOhlc(OHLCIndicator preBPeriodOhlc) {
        this.preBPeriodOhlc = preBPeriodOhlc;
    }

    public OHLCIndicator getPreCPeriodOhlc() {
        return preCPeriodOhlc;
    }

    public void setPreCPeriodOhlc(OHLCIndicator preCPeriodOhlc) {
        this.preCPeriodOhlc = preCPeriodOhlc;
    }

    public OHLCIndicator getPreDPeriodOhlc() {
        return preDPeriodOhlc;
    }

    public void setPreDPeriodOhlc(OHLCIndicator preDPeriodOhlc) {
        this.preDPeriodOhlc = preDPeriodOhlc;
    }

    public OHLCIndicator getPreEPeriodOhlc() {
        return preEPeriodOhlc;
    }

    public void setPreEPeriodOhlc(OHLCIndicator preEPeriodOhlc) {
        this.preEPeriodOhlc = preEPeriodOhlc;
    }

    public OHLCIndicator getPreFPeriodOhlc() {
        return preFPeriodOhlc;
    }

    public void setPreFPeriodOhlc(OHLCIndicator preFPeriodOhlc) {
        this.preFPeriodOhlc = preFPeriodOhlc;
    }

    public OHLCIndicator getPreGPeriodOhlc() {
        return preGPeriodOhlc;
    }

    public void setPreGPeriodOhlc(OHLCIndicator preGPeriodOhlc) {
        this.preGPeriodOhlc = preGPeriodOhlc;
    }

    public OHLCIndicator getPreHPeriodOhlc() {
        return preHPeriodOhlc;
    }

    public void setPreHPeriodOhlc(OHLCIndicator preHPeriodOhlc) {
        this.preHPeriodOhlc = preHPeriodOhlc;
    }

    public OHLCIndicator getPreIPeriodOhlc() {
        return preIPeriodOhlc;
    }

    public void setPreIPeriodOhlc(OHLCIndicator preIPeriodOhlc) {
        this.preIPeriodOhlc = preIPeriodOhlc;
    }

    public OHLCIndicator getPreJPeriodOhlc() {
        return preJPeriodOhlc;
    }

    public void setPreJPeriodOhlc(OHLCIndicator preJPeriodOhlc) {
        this.preJPeriodOhlc = preJPeriodOhlc;
    }

    public OHLCIndicator getPreKPeriodOhlc() {
        return preKPeriodOhlc;
    }

    public void setPreKPeriodOhlc(OHLCIndicator preKPeriodOhlc) {
        this.preKPeriodOhlc = preKPeriodOhlc;
    }

    public OHLCIndicator getPreLPeriodOhlc() {
        return preLPeriodOhlc;
    }

    public void setPreLPeriodOhlc(OHLCIndicator preLPeriodOhlc) {
        this.preLPeriodOhlc = preLPeriodOhlc;
    }

    public OHLCIndicator getPreMPeriodOhlc() {
        return preMPeriodOhlc;
    }

    public void setPreMPeriodOhlc(OHLCIndicator preMPeriodOhlc) {
        this.preMPeriodOhlc = preMPeriodOhlc;
    }

    public List<OHLCIndicator> getOneMinOhlcList() {
        return oneMinOhlcList;
    }

    public void setOneMinOhlcList(List<OHLCIndicator> oneMinOhlcList) {
        this.oneMinOhlcList = oneMinOhlcList;
    }

    public void setDailyTrend(DailyTrend dailyTrend) {
        this.dailyTrend = dailyTrend;
    }

    public Map<Period30m, OHLCIndicator> getMapOf30mPeriods() {
        Map<Period30m, OHLCIndicator> period30mMap = new LinkedHashMap<>();
        period30mMap.put(Period30m.A, aPeriodOhlc);
        period30mMap.put(Period30m.B, bPeriodOhlc);
        period30mMap.put(Period30m.C, cPeriodOhlc);
        period30mMap.put(Period30m.D, dPeriodOhlc);
        period30mMap.put(Period30m.E, ePeriodOhlc);
        period30mMap.put(Period30m.F, fPeriodOhlc);
        period30mMap.put(Period30m.G, gPeriodOhlc);
        period30mMap.put(Period30m.H, hPeriodOhlc);
        period30mMap.put(Period30m.I, iPeriodOhlc);
        period30mMap.put(Period30m.J, jPeriodOhlc);
        period30mMap.put(Period30m.K, kPeriodOhlc);
        period30mMap.put(Period30m.L, lPeriodOhlc);
        period30mMap.put(Period30m.M, mPeriodOhlc);

        return period30mMap;
    }

    public OHLCIndicator getPreMarket0915To0929Ohlc() {
        return preMarket0915To0929Ohlc;
    }

    public void setPreMarket0915To0929Ohlc(OHLCIndicator preMarket0915To0929Ohlc) {
        this.preMarket0915To0929Ohlc = preMarket0915To0929Ohlc;
    }

    public DailyTrend getDailyTrend() {
        return dailyTrend;
    }

    public OHLCIndicator getOhlcOfPeriod(Period30m period30m) {
        Map<Period30m, OHLCIndicator> period30mMap = new LinkedHashMap<>();
        period30mMap.put(Period30m.A, aPeriodOhlc);
        period30mMap.put(Period30m.B, bPeriodOhlc);
        period30mMap.put(Period30m.C, cPeriodOhlc);
        period30mMap.put(Period30m.D, dPeriodOhlc);
        period30mMap.put(Period30m.E, ePeriodOhlc);
        period30mMap.put(Period30m.F, fPeriodOhlc);
        period30mMap.put(Period30m.G, gPeriodOhlc);
        period30mMap.put(Period30m.H, hPeriodOhlc);
        period30mMap.put(Period30m.I, iPeriodOhlc);
        period30mMap.put(Period30m.J, jPeriodOhlc);
        period30mMap.put(Period30m.K, kPeriodOhlc);
        period30mMap.put(Period30m.L, lPeriodOhlc);
        period30mMap.put(Period30m.M, mPeriodOhlc);

        return period30mMap.get(period30m);
    }

    private void calculateAndSetDailyTrend() {
        if (rthOhlc.getHigh() != null && rthOhlc.getLow() != null) {
            /*
            Trend up
            1. AMH < MicroH < PMH
            2. Open < PML
            */
            /*
            Trend down
            1. AML > MicroL > PMH
            2. Open > PMH
            */
            /*
            Am Ext Up
            1. 150% AM Extension Hit
            */
            /*
            Am Ext Down
            1. 150% AM Extension Hit
            */
            if (amRangeOhlc.getLow() != null && microRangeOhlc.getLow() != null && rthOhlc.getOpen() != null && rthOhlc.getClose() != null &&
                    amRangeOhlc.getLow().getPrice().isGreaterThan(microRangeOhlc.getLow().getPrice()) &&
                    microRangeOhlc.getLow().getPrice().isGreaterThan(pmRangeOhlc.getLow().getPrice()) &&
                    rthOhlc.getOpen().getPrice().isGreaterThan(pmRangeOhlc.getHigh().getPrice())) {
                dailyTrend = DailyTrend.TREND_DOWN;

            } else if (amRangeOhlc.getHigh() != null && microRangeOhlc.getHigh() != null && rthOhlc.getOpen() != null && rthOhlc.getClose() != null &&
                    amRangeOhlc.getHigh().getPrice().isLessThan(microRangeOhlc.getHigh().getPrice()) &&
                    microRangeOhlc.getHigh().getPrice().isLessThan(pmRangeOhlc.getHigh().getPrice()) &&
                    rthOhlc.getOpen().getPrice().isLessThan(pmRangeOhlc.getLow().getPrice())) {
                dailyTrend = DailyTrend.TREND_UP;

            } else if (amRangeOhlc.getLow() != null && rthOhlc.getOpen() != null &&
                    rthOhlc.getHigh().getPrice().isLessThanOrEqual(amRangeOhlc.getExtensionOfRange(1.5, false))) {
                dailyTrend = DailyTrend.AM_EXT_DOWN;
            } else if (amRangeOhlc.getLow() != null && rthOhlc.getOpen() != null &&
                    rthOhlc.getHigh().getPrice().isLessThanOrEqual(amRangeOhlc.getExtensionOfRange(1.5, false))) {
                dailyTrend = DailyTrend.AM_EXT_DOWN;
            } else {
                dailyTrend = DailyTrend.RANGE;
            }
        }
    }
}
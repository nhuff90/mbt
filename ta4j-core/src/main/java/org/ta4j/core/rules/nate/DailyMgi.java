package org.ta4j.core.rules.nate;

import org.ta4j.core.indicators.nate.OHLCIndicator;

public class DailyMgi {
    protected OHLCIndicator rthOhlc = new OHLCIndicator();
    protected OHLCIndicator priorDayRthOhlc = new OHLCIndicator();
    protected OHLCIndicator overnightRthOhlc = new OHLCIndicator();

    protected OHLCIndicator omarOhlc = new OHLCIndicator();
    protected OHLCIndicator postOmarOhlc = new OHLCIndicator();

    protected OHLCIndicator opening5MinRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postOpening5MinRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator opening15MinRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postOpening15MinRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator initialBalanceOhlc = new OHLCIndicator();
    protected OHLCIndicator postInitialBalanceOhlc = new OHLCIndicator();

    protected OHLCIndicator amRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postAmRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator microRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postMicroRangeOhlc = new OHLCIndicator();

    protected OHLCIndicator pmRangeOhlc = new OHLCIndicator();
    protected OHLCIndicator postPmRangeOhlc = new OHLCIndicator();

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

    public OHLCIndicator getPostOpening15MinRangeOhlc() {
        return postOpening15MinRangeOhlc;
    }

    public void setPostOpening15MinRangeOhlc(OHLCIndicator postOpening15MinRangeOhlc) {
        this.postOpening15MinRangeOhlc = postOpening15MinRangeOhlc;
    }

    public OHLCIndicator getInitialBalanceOhlc() {
        return initialBalanceOhlc;
    }

    public void setInitialBalanceOhlc(OHLCIndicator initialBalanceOhlc) {
        this.initialBalanceOhlc = initialBalanceOhlc;
    }

    public OHLCIndicator getPostInitialBalanceOhlc() {
        return postInitialBalanceOhlc;
    }

    public void setPostInitialBalanceOhlc(OHLCIndicator postInitialBalanceOhlc) {
        this.postInitialBalanceOhlc = postInitialBalanceOhlc;
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
    }

    public OHLCIndicator getPostPmRangeOhlc() {
        return postPmRangeOhlc;
    }

    public void setPostPmRangeOhlc(OHLCIndicator postPmRangeOhlc) {
        this.postPmRangeOhlc = postPmRangeOhlc;
    }
}

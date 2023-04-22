package nate.stats.domain;

import nate.stats.NHodBy30mPeriodAfterBreakingLowOfPeriodStats;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;
import org.ta4j.core.rules.nate.DailyMgi;

import java.util.LinkedHashMap;
import java.util.Map;

public class Period30mResultMap {
    public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodContinuationMap = new LinkedHashMap<>();
    public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodReversalMap = new LinkedHashMap<>();

    public Period30mResultMap() {
        periodContinuationMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodContinuationMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());

        periodReversalMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
        periodReversalMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());
    }

    public void addToPeriodContinuationTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
        TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodContinuationMap.get(period30m);
        trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
        periodContinuationMap.put(period30m, trueFalseDailyMgiResults);
    }

    public void addToPeriodContinuationFalseMap(Period30m period30m, DailyMgi dailyMgi) {
        TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodContinuationMap.get(period30m);
        trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
        periodContinuationMap.put(period30m, trueFalseDailyMgiResults);
    }

    public void addToPeriodReversalTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
        TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodReversalMap.get(period30m);
        trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
        periodReversalMap.put(period30m, trueFalseDailyMgiResults);
    }

    public void addToPeriodReversalFalseMap(Period30m period30m, DailyMgi dailyMgi) {
        TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodReversalMap.get(period30m);
        trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
        periodReversalMap.put(period30m, trueFalseDailyMgiResults);
    }

    public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodContinuationMap() {
        return periodContinuationMap;
    }

    public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodReversalMap() {
        return periodReversalMap;
    }

    public void setPeriodContinuationMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodContinuationMap) {
        NHodBy30mPeriodAfterBreakingLowOfPeriodStats.PeriodResultsMap.periodContinuationMap = periodContinuationMap;
    }

    public void setPeriodReversalMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodReversalMap) {
        NHodBy30mPeriodAfterBreakingLowOfPeriodStats.PeriodResultsMap.periodReversalMap = periodReversalMap;
    }
}

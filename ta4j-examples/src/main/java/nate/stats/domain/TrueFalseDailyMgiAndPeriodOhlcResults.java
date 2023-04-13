package nate.stats.domain;

import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.nate.DailyMgi;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrueFalseDailyMgiAndPeriodOhlcResults {
    Map<DailyMgi, OHLCIndicator> trueMap = new LinkedHashMap<>();
    Map<DailyMgi, OHLCIndicator> falseMap = new LinkedHashMap<>();

    public void addToTrueMap(DailyMgi dailyMgi, OHLCIndicator periodOhlc) {
        trueMap.put(dailyMgi, periodOhlc);
    }

    public void addToFalseMap(DailyMgi dailyMgi, OHLCIndicator periodOhlc) {
        falseMap.put(dailyMgi, periodOhlc);
    }

    public Map<DailyMgi, OHLCIndicator> getTrueMap() {
        return trueMap;
    }

    public Map<DailyMgi, OHLCIndicator> getFalseMap() {
        return falseMap;
    }

    public int getTotalOfTrueAndFalseMaps() {
        return trueMap.size() + falseMap.size();
    }
}

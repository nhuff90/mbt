package nate.stats.domain;

import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.rules.nate.DailyMgi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrendVsRangeDailyMgiOhlcResults {
    Map<DailyMgi, OHLCIndicator> trendUpMap = new LinkedHashMap<>();
    Map<DailyMgi, OHLCIndicator> trendDownMap = new LinkedHashMap<>();
    Map<DailyMgi, OHLCIndicator> rangeMap = new LinkedHashMap<>();
    Map<DailyMgi, OHLCIndicator> allMap = new LinkedHashMap<>();

    public void addToTrendUpMap(DailyMgi dailyMgi, OHLCIndicator ohlcIndicator) {
        trendUpMap.put(dailyMgi, ohlcIndicator);
        allMap.put(dailyMgi, ohlcIndicator);
    }

    public void addToTrendDownMap(DailyMgi dailyMgi, OHLCIndicator ohlcIndicator) {
        trendDownMap.put(dailyMgi, ohlcIndicator);
        allMap.put(dailyMgi, ohlcIndicator);
    }

    public void addToRangeMap(DailyMgi dailyMgi, OHLCIndicator ohlcIndicator) {
        rangeMap.put(dailyMgi, ohlcIndicator);
        allMap.put(dailyMgi, ohlcIndicator);
    }

    public Map<DailyMgi, OHLCIndicator> getTrendUpMap() {
        return trendUpMap;
    }

    public Map<DailyMgi, OHLCIndicator> getTrendDownMap() {
        return trendDownMap;
    }

    public Map<DailyMgi, OHLCIndicator> getRangeMap() {
        return rangeMap;
    }

    public int getTotalEntries() {
        return rangeMap.size() + trendUpMap.size() + trendDownMap.size();
    }

    public Map<DailyMgi, OHLCIndicator> getAllMap() {
        return allMap;
    }
}

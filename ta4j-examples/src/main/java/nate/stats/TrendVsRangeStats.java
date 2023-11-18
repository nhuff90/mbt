package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyTrend;

import java.time.LocalDate;
import java.util.Map;

public abstract class TrendVsRangeStats extends Stats {

    TrendVsRangeDailyMgiOhlcResults populateTrendMap(Map<LocalDate, DailyMgi> dailyMgiMap) {
        TrendVsRangeDailyMgiOhlcResults trendMap = new TrendVsRangeDailyMgiOhlcResults();
        dailyMgiMap.forEach((date, dailyMgi) -> {

            if (dailyMgi.getRthOhlc().getHigh() != null && dailyMgi.getRthOhlc().getLow() != null) {
                if (dailyMgi.getDailyTrend() == DailyTrend.TREND_DOWN) {
//                    System.out.println(date + " Trend_Down");
                    trendMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.TREND_UP) {
//                    System.out.println(date + " Trend_Up");
                    trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.RANGE) {
//                    System.out.println(date + " Range");
                    trendMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.BULLISH_RANGE) {
//                    System.out.println(date + " BULLISH_RANGE");
                    trendMap.addToBullishRangeMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.BEARISH_RANGE) {
//                    System.out.println(date + " BEARISH_RANGE");
                    trendMap.addToBearishRangeMap(dailyMgi, dailyMgi.getRthOhlc());

                }
            }
        });
        return trendMap;
    }
}

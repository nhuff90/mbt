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
                if (dailyMgi.getDailyTrend() == DailyTrend.TREND_DOWN) {
                    System.out.println(date + " Trend_Down");
                    trendMap.addToTrendDownMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.TREND_UP) {
                    System.out.println(date + " Trend_Up");
                    trendMap.addToTrendUpMap(dailyMgi, dailyMgi.getRthOhlc());

                } else if (dailyMgi.getDailyTrend() == DailyTrend.RANGE) {
                    System.out.println(date + " Range");
                    trendMap.addToRangeMap(dailyMgi, dailyMgi.getRthOhlc());

                }
            }
        });
        return trendMap;
    }
}

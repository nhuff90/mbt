package nate.stats;

import nate.stats.domain.TrendVsRangeDailyMgiOhlcResults;
import nate.stats.domain.TrueFalseDailyMgiAndPeriodOhlcResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * If there is a NHOD after the opening 15m range, what is the % chance for a trend up day.
 */
public class NHodAfter15mRangeStats extends TrendVsRangeStats {

    //todo - This is not done
    //todo - Create unit test!!

    public static TrendVsRangeDailyMgiOhlcResults trendMap = new TrendVsRangeDailyMgiOhlcResults();
    public static TrueFalseDailyMgiAndPeriodOhlcResults resultsMap = new TrueFalseDailyMgiAndPeriodOhlcResults();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateNHodStats(dailyMgiMap);
    }

    private void evaluateNHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {
        trendMap = populateTrendMap(dailyMgiMap);
        runStats(dailyMgiMap);
        periodResultsMap();
    }

    private void runStats(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
           /*
           If NHOD is put in after Opening 15m,
            */

        });
    }


    private void periodResultsMap() {

    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 8), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        createRulesAndRunBackTest(series);

        NHodAfter15mRangeStats nHodBy30mPeriodStatsTest = new NHodAfter15mRangeStats();
        nHodBy30mPeriodStatsTest.evaluate();
    }
}

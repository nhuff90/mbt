package nate.stats;

import nate.stats.domain.TrueFalseDailyMgiResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MicroBreakoutToOmarStats extends Stats {

    public static TrueFalseDailyMgiResults trueFalseMicroHighBreakout;
    public static TrueFalseDailyMgiResults trueFalseMicroLowBreakout;

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateMicroBreach(dailyMgiMap);

    }

    private void evaluateMicroBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        /*
        Target MicroH Breach
         */

        trueFalseMicroHighBreakout = calculateStatisticsFromMicroHighToOmarLow(dailyMgiMap);
        trueFalseMicroLowBreakout = calculateStatisticsFromMicroLowToOmarHigh(dailyMgiMap);

        printResultTableRow();

    }

    private void printResultTableRow() {

        double microHBreakoutTruePercentage = (double) trueFalseMicroHighBreakout.getTrueDailyMgiList().size() / (double) trueFalseMicroHighBreakout.getTotalOfTrueAndFalseLists();
        double microLBreakoutTruePercentage = (double) trueFalseMicroLowBreakout.getTrueDailyMgiList().size() / (double) trueFalseMicroLowBreakout.getTotalOfTrueAndFalseLists();

        System.out.println("MicroH Break and Hit OMAR Low Hit, " + microHBreakoutTruePercentage);
        System.out.println("MicroL Break and Hit OMAR High Hit, " + microLBreakoutTruePercentage);

//        System.out.println("MicroL Breakout and Hit OMAR High:");
//        for (DailyMgi dailyMgi : trueFalseMicroLowBreakout.getTrueDailyMgiList()) {
//            System.out.println(dailyMgi.getAmRangeOhlc().getOpen().getDate());
//        }
//
//        System.out.println("MicroL Breakout and Hit OMAR High FALSE:");
//        for (DailyMgi dailyMgi : trueFalseMicroLowBreakout.getFalseDailyMgiList()) {
//            System.out.println(dailyMgi.getAmRangeOhlc().getOpen().getDate());
//        }
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromMicroHighToOmarLow(Map<LocalDate, DailyMgi> dailyMgiMap) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getMicroRangeOhlc().getHigh() != null && dailyMgi.getPostMicroRangeOhlc().getHigh() != null &&
                    dailyMgi.getMicroRangeOhlc().getLow() != null && dailyMgi.getPostMicroRangeOhlc().getLow() != null &&
                    dailyMgi.getPostMicroRangeOhlc().getHigh() != null && dailyMgi.getPostMicroRangeOhlc().getHigh() != null &&
                    dailyMgi.getOmarOhlc().getLow() != null && dailyMgi.getOmarOhlc().getLow() != null &&
                    dailyMgi.getAmRangeOhlc().getLow() != null && dailyMgi.getAmRangeOhlc().getLow() != null) {

                if (dailyMgi.getMicroRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getLow().getPrice()) &&
                        dailyMgi.getMicroRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {

                    // Micro is below AM Range
                    if (dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getMicroRangeOhlc().getHigh().getPrice())) {
                        // Post Micro High is greater than MicroH
                        if (dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getOmarOhlc().getLow().getPrice())) {
                            // Post MicroH is greater than or equal to OMAR Low
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            results.addToFalseDailyMgiList(dailyMgi);
                        }
                    }
                }

            }
        });

        return results;
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromMicroLowToOmarHigh(Map<LocalDate, DailyMgi> dailyMgiMap) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getMicroRangeOhlc().getHigh() != null && dailyMgi.getPostMicroRangeOhlc().getHigh() != null &&
                    dailyMgi.getMicroRangeOhlc().getLow() != null && dailyMgi.getPostMicroRangeOhlc().getLow() != null &&
                    dailyMgi.getPostMicroRangeOhlc().getLow() != null && dailyMgi.getPostMicroRangeOhlc().getLow() != null &&
                    dailyMgi.getOmarOhlc().getHigh() != null && dailyMgi.getOmarOhlc().getHigh() != null &&
                    dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getAmRangeOhlc().getHigh() != null) {

                if (dailyMgi.getMicroRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getHigh().getPrice()) &&
                        dailyMgi.getMicroRangeOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getHigh().getPrice())) {

                    // Micro is above AM Range
                    if (dailyMgi.getPostMicroRangeOhlc().getLow().getPrice().isLessThanOrEqual(dailyMgi.getMicroRangeOhlc().getLow().getPrice())) {
                        // Post Micro Low is less than MicroL
                        if (dailyMgi.getPostMicroRangeOhlc().getLow().getPrice().isLessThanOrEqual(dailyMgi.getOmarOhlc().getHigh().getPrice())) {
                            // Post MicroL is less than or equal to OMAR High
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            results.addToFalseDailyMgiList(dailyMgi);
                        }
                    }
                }

            }
        });

        return results;
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        LocalDate startDate = LocalDate.of(2022, 1, 1);

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(startDate, LocalTime.of(9, 30), ZoneId.of("America/New_York")));

        createRulesAndRunBackTest(series);

        MicroBreakoutToOmarStats microBreakoutStats = new MicroBreakoutToOmarStats();
        microBreakoutStats.evaluate();
    }


}

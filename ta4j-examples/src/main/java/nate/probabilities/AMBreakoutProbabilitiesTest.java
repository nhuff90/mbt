package nate.probabilities;

import nate.probabilities.domain.TrueFalseDailyMgiResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.DoubleFormatter;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class AMBreakoutProbabilitiesTest extends ProbabilityTest {

    @Override
    void evaluate() {
        // todo - this is test code
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();

        /*
        25% extension
         */
        double extensionPercent25 = 0.25;
        TrueFalseDailyMgiResults results = calculateProbabilities(dailyMgiMap, extensionPercent25);
        printResult(results, extensionPercent25);


        double extensionPercent50 = 0.5;
        results = calculateProbabilities(dailyMgiMap, extensionPercent50);
        printResult(results, extensionPercent50);

        double extensionPercent75 = 0.75;
        results = calculateProbabilities(dailyMgiMap, extensionPercent75);
        printResult(results, extensionPercent75);

        double extensionPercent100 = 1.0;
        results = calculateProbabilities(dailyMgiMap, extensionPercent100);
        printResult(results, extensionPercent100);

        double extensionPercent150 = 1.5;
        results = calculateProbabilities(dailyMgiMap, extensionPercent150);
        printResult(results, extensionPercent150);

        double extensionPercent200 = 2.0;
        results = calculateProbabilities(dailyMgiMap, extensionPercent200);
        printResult(results, extensionPercent200);

        double extensionPercent300 = 3.0;
        results = calculateProbabilities(dailyMgiMap, extensionPercent300);
        printResult(results, extensionPercent300);
    }

    private void printResult(TrueFalseDailyMgiResults results, double extensionPercent) {
        double percentChangeTrue;
        System.out.println("# of trues=" + results.getTrueDailyMgiList().size() + ", # of falses=" + results.getFalseDailyMgiList().size());
        percentChangeTrue = (double) results.getTrueDailyMgiList().size()/(double) results.getTotalOfTrueAndFalseLists();
        System.out.println("% chance " + DoubleFormatter.formatPercent(extensionPercent) +
                " AM High Extension is breached after AM High breach: " + DoubleFormatter.formatPercent(percentChangeTrue));
    }

    private TrueFalseDailyMgiResults calculateProbabilities(Map<LocalDate, DailyMgi> dailyMgiMap, double finalExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getPostAmRangeOhlc().getHigh() != null &&
                    dailyMgi.getAmRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostAmRangeOhlc().getHigh().getPrice())) {
                if (dailyMgi.getPostAmRangeOhlc() != null && dailyMgi.getPostAmRangeOhlc().getHigh() != null &&
                        dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThanOrEqual(dailyMgi.getAmRangeOhlc().getExtensionOfRange(finalExtensionPercent, true))) {
                    results.addToTrueDailyMgiList(dailyMgi);
                } else {
                    results.addToFalseDailyMgiList(dailyMgi);
                }
            } else {
                results.addToFalseDailyMgiList(dailyMgi);
            }
        });

        return results;
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);

        AMBreakoutProbabilitiesTest amBreakoutProbabilitiesTest = new AMBreakoutProbabilitiesTest();
        amBreakoutProbabilitiesTest.evaluate();
    }


}

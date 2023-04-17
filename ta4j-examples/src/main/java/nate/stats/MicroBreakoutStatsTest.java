package nate.stats;

import nate.stats.domain.TrueFalseDailyMgiResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.DoubleFormatter;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MicroBreakoutStatsTest extends StatsTest {

    double EXTENSION_PERCENT_0 = 0.0;
    double EXTENSION_PERCENT_25 = 0.25;
    double EXTENSION_PERCENT_50 = 0.5;
    double EXTENSION_PERCENT_75 = 0.75;
    double EXTENSION_PERCENT_100 = 1.0;
    double EXTENSION_PERCENT_150 = 1.5;
    double EXTENSION_PERCENT_200 = 2.0;
    double EXTENSION_PERCENT_300 = 3.0;
    double EXTENSION_PERCENT_400 = 4.0;

    Double[] targetExtensions = new Double[]{EXTENSION_PERCENT_0, EXTENSION_PERCENT_25, EXTENSION_PERCENT_50, EXTENSION_PERCENT_75,
            EXTENSION_PERCENT_100, EXTENSION_PERCENT_150, EXTENSION_PERCENT_200, EXTENSION_PERCENT_300, EXTENSION_PERCENT_400};

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateMicroHighBreach(dailyMgiMap);
        evaluateMicroLowBreach(dailyMgiMap);
//        evaluateAmHighAndLowBreach(dailyMgiMap);

    }

    private void evaluateMicroHighBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AHM Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "MicroH");

        /*
        Target AMH 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "MicroH");

        /*
        Target AMH 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "MicroH");

        /*
        Target AMH 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "MicroH");

        /*
        Target AMH 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "MicroH");

        /*
        Target AMH 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "MicroH");

        /*
        Target AMH 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "MicroH");

        /*
        Target AMH 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "MicroH");

        /*
        Target AMH 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroHighExtensionToMicroHighExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "MicroH");
    }

    private void evaluateMicroLowBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AML Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "MicroL");

        /*
        Target AML 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "MicroL");

        /*
        Target AML 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "MicroL");

        /*
        Target AML 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "MicroL");

        /*
        Target AML 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "MicroL");

        /*
        Target AML 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "MicroL");

        /*
        Target AML 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "MicroL");

        /*
        Target AML 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "MicroL");

        /*
        Target AML 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromMicroLowExtensionToMicroLowExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "MicroL");
    }

    private void printResultTableRow(List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList, Double targetExtensionPercent, String amString) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetExtensionPercent != null ? amString + "+" + DoubleFormatter.formatPercent(targetExtensionPercent, 0) : amString);
        sb.append(", ");
        trueFalseDailyMgiResultsList.forEach(result -> {
            double percentTrue = (double) result.getTrueDailyMgiList().size() / (double) result.getTotalOfTrueAndFalseLists();
            sb.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb.append(",");
        });

        String line = sb.substring(0, sb.length() - 1);
        System.out.println(line);
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromMicroHighExtensionToMicroHighExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getMicroRangeOhlc().getHigh() != null && dailyMgi.getPostMicroRangeOhlc().getHigh() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getMicroRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                        // AM High Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getMicroRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice())) {
                    // AM High was breached
                    // Calculate from starting extension
                    if (dailyMgi.getMicroRangeOhlc().getExtensionOfRange(startingExtensionPercent, true).isLessThanOrEqual(dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice())) {
                        // AM High Starting Extension was breached
                        if (dailyMgi.getPostMicroRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getMicroRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                            // AM High Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // AM High Target Extension NOT hit
                            results.addToFalseDailyMgiList(dailyMgi);
                        }
                    }
                }
            }
        });

        return results;
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromMicroLowExtensionToMicroLowExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getMicroRangeOhlc().getLow() != null && dailyMgi.getPostMicroRangeOhlc().getLow() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostMicroRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getMicroRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                        // AM Low Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getMicroRangeOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getPostMicroRangeOhlc().getLow().getPrice())) {
                    // AM Low was breached
                    // Calculate from starting extension
                    if (dailyMgi.getMicroRangeOhlc().getExtensionOfRange(startingExtensionPercent, false).isGreaterThanOrEqual(dailyMgi.getPostMicroRangeOhlc().getLow().getPrice())) {
                        // AM Low Starting Extension was breached
                        if (dailyMgi.getPostMicroRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getMicroRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                            // AM Low Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // AM Low Target Extension NOT hit
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
        LocalDate startDate = LocalDate.of(2018, 1, 1);

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(startDate, LocalTime.of(9, 30), ZoneId.of("America/New_York")));

        createRulesAndRunBackTest(series);

        MicroBreakoutStatsTest microBreakoutStatsTest = new MicroBreakoutStatsTest();
        microBreakoutStatsTest.evaluate();
    }


}

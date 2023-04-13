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

public class PmBreakoutStatsTest extends StatsTest {

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
        evaluatePmHighBreach(dailyMgiMap);
        evaluatePmLowBreach(dailyMgiMap);

    }

    private void evaluatePmHighBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AHM Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "PMH");

        /*
        Target PMH 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "PMH");

        /*
        Target PMH 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "PMH");

        /*
        Target PMH 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "PMH");

        /*
        Target PMH 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "PMH");

        /*
        Target PMH 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "PMH");

        /*
        Target PMH 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "PMH");

        /*
        Target PMH 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "PMH");

        /*
        Target PMH 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmHighExtensionToPmHighExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "PMH");
    }

    private void evaluatePmLowBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target PML Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "PML");

        /*
        Target PML 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "PML");

        /*
        Target PML 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "PML");

        /*
        Target PML 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "PML");

        /*
        Target PML 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "PML");

        /*
        Target PML 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "PML");

        /*
        Target PML 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "PML");

        /*
        Target PML 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "PML");

        /*
        Target PML 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromPmLowExtensionToPmLowExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "PML");
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

    private TrueFalseDailyMgiResults calculateStatisticsFromPmHighExtensionToPmHighExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getPmRangeOhlc().getHigh() != null && dailyMgi.getPostPmRangeOhlc().getHigh() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostPmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getPmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                        // PM High Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getPmRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostPmRangeOhlc().getHigh().getPrice())) {
                    // PM High was breached
                    // Calculate from starting extension
                    if (dailyMgi.getPmRangeOhlc().getExtensionOfRange(startingExtensionPercent, true).isLessThanOrEqual(dailyMgi.getPostPmRangeOhlc().getHigh().getPrice())) {
                        // PM High Starting Extension was breached
                        if (dailyMgi.getPostPmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getPmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                            // PM High Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // PM High Target Extension NOT hit
                            results.addToFalseDailyMgiList(dailyMgi);
                        }
                    }
                }
            }
        });

        return results;
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromPmLowExtensionToPmLowExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getPmRangeOhlc().getLow() != null && dailyMgi.getPostPmRangeOhlc().getLow() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostPmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getPmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                        // PM Low Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getPmRangeOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getPostPmRangeOhlc().getLow().getPrice())) {
                    // PM Low was breached
                    // Calculate from starting extension
                    if (dailyMgi.getPmRangeOhlc().getExtensionOfRange(startingExtensionPercent, false).isGreaterThanOrEqual(dailyMgi.getPostPmRangeOhlc().getLow().getPrice())) {
                        // PM Low Starting Extension was breached
                        if (dailyMgi.getPostPmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getPmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                            // PM Low Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // PM Low Target Extension NOT hit
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

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);

        PmBreakoutStatsTest microBreakoutProbabilitiesTest = new PmBreakoutStatsTest();
        microBreakoutProbabilitiesTest.evaluate();
    }


}

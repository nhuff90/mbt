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

public class IBBreakoutStatsTest extends StatsTest {

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
    void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateIBHighBreach(dailyMgiMap);
        evaluateIBLowBreach(dailyMgiMap);
    }

    private void evaluateIBHighBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AHM Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "IBH");

        /*
        Target IBH 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "IBH");

        /*
        Target IBH 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "IBH");

        /*
        Target IBH 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "IBH");

        /*
        Target IBH 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "IBH");

        /*
        Target IBH 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "IBH");

        /*
        Target IBH 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "IBH");

        /*
        Target IBH 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "IBH");

        /*
        Target IBH 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBHighExtensionToIBHighExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "IBH");
    }

    private void evaluateIBLowBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target IBL Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "IBL");

        /*
        Target IBL 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "IBL");

        /*
        Target IBL 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "IBL");

        /*
        Target IBL 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "IBL");

        /*
        Target IBL 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "IBL");

        /*
        Target IBL 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "IBL");

        /*
        Target IBL 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "IBL");

        /*
        Target IBL 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "IBL");

        /*
        Target IBL 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromIBLowExtensionToIBLowExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "IBL");
    }

    private void printResultTableRow(List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList, Double targetExtensionPercent, String rowName) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetExtensionPercent != null ? rowName + "+" + DoubleFormatter.formatPercent(targetExtensionPercent, 0) : rowName);
        sb.append(", ");
        trueFalseDailyMgiResultsList.forEach(result -> {
            double percentTrue = (double) result.getTrueDailyMgiList().size() / (double) result.getTotalOfTrueAndFalseLists();
            sb.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb.append(",");
        });

        String line = sb.substring(0, sb.length() - 1);
        System.out.println(line);
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromIBHighExtensionToIBHighExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getIbOhlc().getHigh() != null && dailyMgi.getPostIbOhlc().getHigh() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostIbOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getIbOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                        // IB High Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getIbOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostIbOhlc().getHigh().getPrice())) {
                    // IB High was breached
                    // Calculate from starting extension
                    if (dailyMgi.getIbOhlc().getExtensionOfRange(startingExtensionPercent, true).isLessThanOrEqual(dailyMgi.getPostIbOhlc().getHigh().getPrice())) {
                        // IB High Starting Extension was breached
                        if (dailyMgi.getPostIbOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getIbOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                            // IB High Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // IB High Target Extension NOT hit
                            results.addToFalseDailyMgiList(dailyMgi);
                        }
                    }
                }
            }
        });

        return results;
    }

    private TrueFalseDailyMgiResults calculateStatisticsFromIBLowExtensionToIBLowExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getIbOhlc().getLow() != null && dailyMgi.getPostIbOhlc().getLow() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostIbOhlc().getLow().getPrice().isLessThan(dailyMgi.getIbOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                        // IB Low Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getIbOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getPostIbOhlc().getLow().getPrice())) {
                    // IB Low was breached
                    // Calculate from starting extension
                    if (dailyMgi.getIbOhlc().getExtensionOfRange(startingExtensionPercent, false).isGreaterThanOrEqual(dailyMgi.getPostIbOhlc().getLow().getPrice())) {
                        // IB Low Starting Extension was breached
                        if (dailyMgi.getPostIbOhlc().getLow().getPrice().isLessThan(dailyMgi.getIbOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                            // IB Low Target Extension Hit
                            results.addToTrueDailyMgiList(dailyMgi);
                        } else {
                            // IB Low Target Extension NOT hit
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

        IBBreakoutStatsTest ibBreakoutStatsTest = new IBBreakoutStatsTest();
        ibBreakoutStatsTest.evaluate();
    }


}

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

public class AMBreakoutStatsTest extends StatsTest {

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
        evaluateAmHighBreach(dailyMgiMap);
        evaluateAmLowBreach(dailyMgiMap);
//        evaluateAmHighAndLowBreach(dailyMgiMap);

    }

    private void evaluateAmHighBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AHM Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "AMH");

        /*
        Target AMH 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "AMH");

        /*
        Target AMH 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "AMH");

        /*
        Target AMH 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "AMH");

        /*
        Target AMH 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "AMH");

        /*
        Target AMH 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "AMH");

        /*
        Target AMH 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "AMH");

        /*
        Target AMH 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "AMH");

        /*
        Target AMH 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighExtensionToAmHighExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "AMH");
    }

    private void evaluateAmLowBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
        /*
        Target AML Breach
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[0]));
        printResultTableRow(trueFalseDailyMgiResultsList, null, "AML");

        /*
        Target AML 25% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[1]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "AML");

        /*
        Target AML 50% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "AML");

        /*
        Target AML 75% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "AML");

        /*
        Target AML 100% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "AML");

        /*
        Target AML 150% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "AML");

        /*
        Target AML 200% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "AML");

        /*
        Target AML 300% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "AML");

        /*
        Target AML 400% Extension
         */
        trueFalseDailyMgiResultsList = new ArrayList<>();
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, null, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmLowExtensionToAmLowExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "AML");
    }

    private void evaluateAmHighAndLowBreach(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;
//        /*
//        Target AHM Breach
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[0]));
//        printResultTableRow(trueFalseDailyMgiResultsList, null, "(AMH || AML)");
//
//        /*
//        Target AMH 25% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[1]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[1]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[1], "(AMH || AML)");
//
//        /*
//        Target AMH 50% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[2]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[2]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[2]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[2], "(AMH || AML)");
//
//        /*
//        Target AMH 75% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[3]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[3]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[3]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[3]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[3], "(AMH || AML)");
//
//        /*
//        Target AMH 100% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[4]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[4]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[4]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[4]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[4]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[4], "(AMH || AML)");
//
//        /*
//        Target AMH 150% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[5]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[5]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[5]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[5]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[5]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[5]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[5], "(AMH || AML)");
//
//        /*
//        Target AMH 200% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[6]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[6]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[6], "(AMH || AML)");
//
//        /*
//        Target AMH 300% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[7]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[7]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[7], "(AMH || AML)");
//
//        /*
//        Target AMH 400% Extension
//         */
//        trueFalseDailyMgiResultsList = new ArrayList<>();
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, null, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_0, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_25, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_50, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_75, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_100, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_150, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_200, targetExtensions[8]));
//        trueFalseDailyMgiResultsList.add(calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(dailyMgiMap, EXTENSION_PERCENT_300, targetExtensions[8]));
//        printResultTableRow(trueFalseDailyMgiResultsList, targetExtensions[8], "(AMH || AML)");
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

    private TrueFalseDailyMgiResults calculateStatisticsFromAmHighExtensionToAmHighExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getAmRangeOhlc().getHigh() != null && dailyMgi.getPostAmRangeOhlc().getHigh() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                        // AM High Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getAmRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostAmRangeOhlc().getHigh().getPrice())) {
                    // AM High was breached
                    // Calculate from starting extension
                    if (dailyMgi.getAmRangeOhlc().getExtensionOfRange(startingExtensionPercent, true).isLessThanOrEqual(dailyMgi.getPostAmRangeOhlc().getHigh().getPrice())) {
                        // AM High Starting Extension was breached
                        if (dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
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

    private TrueFalseDailyMgiResults calculateStatisticsFromAmLowExtensionToAmLowExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getAmRangeOhlc().getLow() != null && dailyMgi.getPostAmRangeOhlc().getLow() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                        // AM Low Extension was breached
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getAmRangeOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getPostAmRangeOhlc().getLow().getPrice())) {
                    // AM Low was breached
                    // Calculate from starting extension
                    if (dailyMgi.getAmRangeOhlc().getExtensionOfRange(startingExtensionPercent, false).isGreaterThanOrEqual(dailyMgi.getPostAmRangeOhlc().getLow().getPrice())) {
                        // AM Low Starting Extension was breached
                        if (dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
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

    private TrueFalseDailyMgiResults calculateStatisticsFromAmHighAndLowExtensionToAmHighAndLowExtension(Map<LocalDate, DailyMgi> dailyMgiMap, Double startingExtensionPercent, Double targetExtensionPercent) {
        TrueFalseDailyMgiResults results = new TrueFalseDailyMgiResults();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getAmRangeOhlc().getLow() != null && dailyMgi.getPostAmRangeOhlc().getLow() != null) {
                if (startingExtensionPercent == null) {
                    // Calculate from open
                    if (dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                        // AM Low Extension was breached
                        if (dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                            // AM High Extension was breached
                            results.addToTrueDailyMgiList(dailyMgi);
                        }
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
                    }
                } else if (dailyMgi.getAmRangeOhlc().getHigh().getPrice().isLessThan(dailyMgi.getPostAmRangeOhlc().getHigh().getPrice()) &&
                        dailyMgi.getAmRangeOhlc().getLow().getPrice().isGreaterThan(dailyMgi.getPostAmRangeOhlc().getLow().getPrice())) {
                    // Both AMH and AML has been breached
                    boolean isExtensionBreached = false;
                    if (dailyMgi.getAmRangeOhlc().getExtensionOfRange(startingExtensionPercent, true).isLessThanOrEqual(dailyMgi.getPostAmRangeOhlc().getHigh().getPrice())) {
                        // AM High Starting Extension was breached
                        if (dailyMgi.getPostAmRangeOhlc().getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, true))) {
                            // AM High Target Extension Hit
                            isExtensionBreached = true;
                        }
                    }
                    if (dailyMgi.getAmRangeOhlc().getExtensionOfRange(startingExtensionPercent, false).isGreaterThanOrEqual(dailyMgi.getPostAmRangeOhlc().getLow().getPrice())) {
                        // AM Low Starting Extension was breached
                        if (dailyMgi.getPostAmRangeOhlc().getLow().getPrice().isLessThan(dailyMgi.getAmRangeOhlc().getExtensionOfRange(targetExtensionPercent, false))) {
                            // AM Low Target Extension Hit
                            isExtensionBreached = true;
                        }
                    }

                    if (isExtensionBreached) {
                        results.addToTrueDailyMgiList(dailyMgi);
                    } else {
                        results.addToFalseDailyMgiList(dailyMgi);
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

        AMBreakoutStatsTest amBreakoutStatsTest = new AMBreakoutStatsTest();
        amBreakoutStatsTest.evaluate();
    }


}

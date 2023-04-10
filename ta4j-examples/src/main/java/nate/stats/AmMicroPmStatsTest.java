package nate.stats;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.helper.DateTimePrice;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.DoubleFormatter;
import org.ta4j.core.utils.MarketTime;
import org.ta4j.core.utils.TimeUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AmMicroPmStatsTest extends StatsTest {

    Map<String, int[]> map = new LinkedHashMap<>();

    @Override
    void evaluate() {

    }

    private void printResultTableRow() {
//        Map<Integer, Integer> totalCountByDayMap = createTotalCountByDayMap();
//        map.forEach((dateRange, countArray) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(dateRange);
//            sb.append(", ");
//            final int[] i = {0};
//            Arrays.stream(countArray).forEach(count -> {
//                sb.append(DoubleFormatter.formatPercent((double) count/(double) totalCountByDayMap.get(i[0])));
//                sb.append(",");
//                i[0]++;
//            });
//            String line = sb.substring(0, sb.length() - 1);
//            System.out.println(line);
//        });
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

        AmMicroPmStatsTest amBreakoutProbabilitiesTest = new AmMicroPmStatsTest();
        amBreakoutProbabilitiesTest.evaluate();
    }


}

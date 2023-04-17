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
import java.util.*;

public class HodLodByDayAndTimeStatsTest extends StatsTest {

    Map<String, int[]> map = new LinkedHashMap<>();

    @Override
    public void evaluate() {
        initializeMap();
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        System.out.println("High of Day");
        evaluateHodByDayAndTime(dailyMgiMap);
        printResultTableRow();

        System.out.println("Low of Day");
        evaluateLodByDayAndTime(dailyMgiMap);
        printResultTableRow();
    }

    private void initializeMap() {
        map.put(MarketTime.RTH_START_TIME_0930.getLocalTime() + "-" + MarketTime.RTH_0959.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1000.getLocalTime() + "-" + MarketTime.RTH_1029.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1030.getLocalTime() + "-" + MarketTime.RTH_1059.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1100.getLocalTime() + "-" + MarketTime.RTH_1129.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1130.getLocalTime() + "-" + MarketTime.RTH_1159.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1200.getLocalTime() + "-" + MarketTime.RTH_1229.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1230.getLocalTime() + "-" + MarketTime.RTH_1259.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1300.getLocalTime() + "-" + MarketTime.RTH_1329.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1330.getLocalTime() + "-" + MarketTime.RTH_1359.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1400.getLocalTime() + "-" + MarketTime.RTH_1429.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1430.getLocalTime() + "-" + MarketTime.RTH_1459.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1500.getLocalTime() + "-" + MarketTime.RTH_1529.getLocalTime(), new int[]{0, 0, 0, 0, 0});
        map.put(MarketTime.RTH_1530.getLocalTime() + "-" + MarketTime.RTH_END_TIME_1559.getLocalTime(), new int[]{0, 0, 0, 0, 0});
    }

    private void evaluateHodByDayAndTime(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_START_TIME_0930.getLocalTime(), MarketTime.RTH_0959.getLocalTime())) {
                map.replace(MarketTime.RTH_START_TIME_0930.getLocalTime() + "-" + MarketTime.RTH_0959.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_START_TIME_0930.getLocalTime() + "-" + MarketTime.RTH_0959.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1000.getLocalTime(), MarketTime.RTH_1029.getLocalTime())) {
                map.replace(MarketTime.RTH_1000.getLocalTime() + "-" + MarketTime.RTH_1029.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1000.getLocalTime() + "-" + MarketTime.RTH_1029.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1030.getLocalTime(), MarketTime.RTH_1059.getLocalTime())) {
                map.replace(MarketTime.RTH_1030.getLocalTime() + "-" + MarketTime.RTH_1059.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1030.getLocalTime() + "-" + MarketTime.RTH_1059.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1100.getLocalTime(), MarketTime.RTH_1129.getLocalTime())) {
                map.replace(MarketTime.RTH_1100.getLocalTime() + "-" + MarketTime.RTH_1129.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1100.getLocalTime() + "-" + MarketTime.RTH_1129.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1130.getLocalTime(), MarketTime.RTH_1159.getLocalTime())) {
                map.replace(MarketTime.RTH_1130.getLocalTime() + "-" + MarketTime.RTH_1159.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1130.getLocalTime() + "-" + MarketTime.RTH_1159.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1200.getLocalTime(), MarketTime.RTH_1229.getLocalTime())) {
                map.replace(MarketTime.RTH_1200.getLocalTime() + "-" + MarketTime.RTH_1229.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1200.getLocalTime() + "-" + MarketTime.RTH_1229.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1230.getLocalTime(), MarketTime.RTH_1259.getLocalTime())) {
                map.replace(MarketTime.RTH_1230.getLocalTime() + "-" + MarketTime.RTH_1259.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1230.getLocalTime() + "-" + MarketTime.RTH_1259.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1300.getLocalTime(), MarketTime.RTH_1329.getLocalTime())) {
                map.replace(MarketTime.RTH_1300.getLocalTime() + "-" + MarketTime.RTH_1329.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1300.getLocalTime() + "-" + MarketTime.RTH_1329.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1330.getLocalTime(), MarketTime.RTH_1359.getLocalTime())) {
                map.replace(MarketTime.RTH_1330.getLocalTime() + "-" + MarketTime.RTH_1359.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1330.getLocalTime() + "-" + MarketTime.RTH_1359.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1400.getLocalTime(), MarketTime.RTH_1429.getLocalTime())) {
                map.replace(MarketTime.RTH_1400.getLocalTime() + "-" + MarketTime.RTH_1429.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1400.getLocalTime() + "-" + MarketTime.RTH_1429.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1430.getLocalTime(), MarketTime.RTH_1459.getLocalTime())) {
                map.replace(MarketTime.RTH_1430.getLocalTime() + "-" + MarketTime.RTH_1459.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1430.getLocalTime() + "-" + MarketTime.RTH_1459.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1500.getLocalTime(), MarketTime.RTH_1529.getLocalTime())) {
                map.replace(MarketTime.RTH_1500.getLocalTime() + "-" + MarketTime.RTH_1529.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1500.getLocalTime() + "-" + MarketTime.RTH_1529.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getHigh().getTime(), MarketTime.RTH_1530.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime())) {
                map.replace(MarketTime.RTH_1530.getLocalTime() + "-" + MarketTime.RTH_END_TIME_1559.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1530.getLocalTime() + "-" + MarketTime.RTH_END_TIME_1559.getLocalTime()), dailyMgi.getRthOhlc().getHigh()));
            }
        });
    }

    private void evaluateLodByDayAndTime(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_START_TIME_0930.getLocalTime(), MarketTime.RTH_0959.getLocalTime())) {
                map.replace(MarketTime.RTH_START_TIME_0930.getLocalTime() + "-" + MarketTime.RTH_0959.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_START_TIME_0930.getLocalTime() + "-" + MarketTime.RTH_0959.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1000.getLocalTime(), MarketTime.RTH_1029.getLocalTime())) {
                map.replace(MarketTime.RTH_1000.getLocalTime() + "-" + MarketTime.RTH_1029.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1000.getLocalTime() + "-" + MarketTime.RTH_1029.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1030.getLocalTime(), MarketTime.RTH_1059.getLocalTime())) {
                map.replace(MarketTime.RTH_1030.getLocalTime() + "-" + MarketTime.RTH_1059.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1030.getLocalTime() + "-" + MarketTime.RTH_1059.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1100.getLocalTime(), MarketTime.RTH_1129.getLocalTime())) {
                map.replace(MarketTime.RTH_1100.getLocalTime() + "-" + MarketTime.RTH_1129.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1100.getLocalTime() + "-" + MarketTime.RTH_1129.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1130.getLocalTime(), MarketTime.RTH_1159.getLocalTime())) {
                map.replace(MarketTime.RTH_1130.getLocalTime() + "-" + MarketTime.RTH_1159.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1130.getLocalTime() + "-" + MarketTime.RTH_1159.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1200.getLocalTime(), MarketTime.RTH_1229.getLocalTime())) {
                map.replace(MarketTime.RTH_1200.getLocalTime() + "-" + MarketTime.RTH_1229.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1200.getLocalTime() + "-" + MarketTime.RTH_1229.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1230.getLocalTime(), MarketTime.RTH_1259.getLocalTime())) {
                map.replace(MarketTime.RTH_1230.getLocalTime() + "-" + MarketTime.RTH_1259.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1230.getLocalTime() + "-" + MarketTime.RTH_1259.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1300.getLocalTime(), MarketTime.RTH_1329.getLocalTime())) {
                map.replace(MarketTime.RTH_1300.getLocalTime() + "-" + MarketTime.RTH_1329.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1300.getLocalTime() + "-" + MarketTime.RTH_1329.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1330.getLocalTime(), MarketTime.RTH_1359.getLocalTime())) {
                map.replace(MarketTime.RTH_1330.getLocalTime() + "-" + MarketTime.RTH_1359.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1330.getLocalTime() + "-" + MarketTime.RTH_1359.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1400.getLocalTime(), MarketTime.RTH_1429.getLocalTime())) {
                map.replace(MarketTime.RTH_1400.getLocalTime() + "-" + MarketTime.RTH_1429.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1400.getLocalTime() + "-" + MarketTime.RTH_1429.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1430.getLocalTime(), MarketTime.RTH_1459.getLocalTime())) {
                map.replace(MarketTime.RTH_1430.getLocalTime() + "-" + MarketTime.RTH_1459.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1430.getLocalTime() + "-" + MarketTime.RTH_1459.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1500.getLocalTime(), MarketTime.RTH_1529.getLocalTime())) {
                map.replace(MarketTime.RTH_1500.getLocalTime() + "-" + MarketTime.RTH_1529.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1500.getLocalTime() + "-" + MarketTime.RTH_1529.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            } else if (TimeUtils.isBetweenTimes(dailyMgi.getRthOhlc().getLow().getTime(), MarketTime.RTH_1530.getLocalTime(), MarketTime.RTH_END_TIME_1559.getLocalTime())) {
                map.replace(MarketTime.RTH_1530.getLocalTime() + "-" + MarketTime.RTH_END_TIME_1559.getLocalTime(), incrementCorrectDay(map.get(MarketTime.RTH_1530.getLocalTime() + "-" + MarketTime.RTH_END_TIME_1559.getLocalTime()), dailyMgi.getRthOhlc().getLow()));
            }
        });
    }

    private int[] incrementCorrectDay(int[] ints, DateTimePrice dateTimePrice) {
        int index = -1;
        if (dateTimePrice.getDate().getDayOfWeek() == DayOfWeek.MONDAY) {
            index = 0;
        } else if (dateTimePrice.getDate().getDayOfWeek() == DayOfWeek.TUESDAY) {
            index = 1;
        } else if (dateTimePrice.getDate().getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            index = 2;
        } else if (dateTimePrice.getDate().getDayOfWeek() == DayOfWeek.THURSDAY) {
            index = 3;
        } else if (dateTimePrice.getDate().getDayOfWeek() == DayOfWeek.FRIDAY) {
            index = 4;
        }

        ints[index] = ++ints[index];

        return ints;
    }

    private void printResultTableRow() {
        Map<Integer, Integer> totalCountByDayMap = createTotalCountByDayMap();
        map.forEach((dateRange, countArray) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(dateRange);
            sb.append(", ");
            final int[] i = {0};
            Arrays.stream(countArray).forEach(count -> {
                sb.append(DoubleFormatter.formatPercent((double) count/(double) totalCountByDayMap.get(i[0])));
                sb.append(",");
                i[0]++;
            });
            String line = sb.substring(0, sb.length() - 1);
            System.out.println(line);
        });
    }

    private Map<Integer, Integer> createTotalCountByDayMap() {
        Map<Integer, Integer> totalCountByDayMap = new HashMap<>();
        totalCountByDayMap.put(0, 0);
        totalCountByDayMap.put(1, 0);
        totalCountByDayMap.put(2, 0);
        totalCountByDayMap.put(3, 0);
        totalCountByDayMap.put(4, 0);

        map.forEach((dateRange, countArray) -> {
            totalCountByDayMap.merge(0, countArray[0], Integer::sum);
            totalCountByDayMap.merge(1, countArray[1], Integer::sum);
            totalCountByDayMap.merge(2, countArray[2], Integer::sum);
            totalCountByDayMap.merge(3, countArray[3], Integer::sum);
            totalCountByDayMap.merge(4, countArray[4], Integer::sum);
        });

        return totalCountByDayMap;
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

        HodLodByDayAndTimeStatsTest amBreakoutProbabilitiesTest = new HodLodByDayAndTimeStatsTest();
        amBreakoutProbabilitiesTest.evaluate();
    }


}

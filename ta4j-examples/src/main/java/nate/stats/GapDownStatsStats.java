package nate.stats;

import nate.stats.domain.DateTimePriceTriplet;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.DateTimePrice;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GapDownStatsStats extends TrendVsRangeStats {
    //todo - Create unit test!!

    //Gap down to gap close list
    List<DateTimePriceTriplet> gapList = new ArrayList<>();


    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        populateMap(dailyMgiMap);
        printResults();
    }

    protected void populateMap(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            if (dailyMgi.getPriorDayRthOhlc().getClose() != null && dailyMgi.getRthOhlc().getOpen() != null &&
                    dailyMgi.getRthOhlc().getOpen().getPrice().isLessThan(dailyMgi.getPriorDayRthOhlc().getClose().getPrice()) &&
                    dailyMgi.getRthOhlc().getOpen().getPrice().isLessThan(dailyMgi.getPriorDayRthOhlc().getLow().getPrice())) {
                // Gap down
                DateTimePriceTriplet dateTimePriceTriplet = new DateTimePriceTriplet();
                dateTimePriceTriplet.setGapStart(dailyMgi.getPriorDayRthOhlc().getClose());
                dateTimePriceTriplet.setGapEnd(dailyMgi.getRthOhlc().getOpen());
                dateTimePriceTriplet.setGapClosedEnd(findGapDownClose(dailyMgi.getRthOhlc().getOpen(),
                        dailyMgi.getPriorDayRthOhlc().getClose(), dailyMgiMap));
                gapList.add(dateTimePriceTriplet);
            }
        });
    }

    private DateTimePrice findGapDownClose(DateTimePrice gapClose, DateTimePrice gapOpen, Map<LocalDate, DailyMgi> dailyMgiMap) {
        for (Map.Entry<LocalDate, DailyMgi> entry : dailyMgiMap.entrySet()) {
            if (entry.getKey().isAfter(gapOpen.getDate())) {
                for (OHLCIndicator ohlcIndicator : entry.getValue().getOneMinOhlcList()) {
                    if (ZonedDateTime.of(ohlcIndicator.getOpen().getDate(), ohlcIndicator.getOpen().getTime(), ZoneId.of("America/New_York"))
                            .isAfter(ZonedDateTime.of(gapClose.getDate(), gapClose.getTime(), ZoneId.of("America/New_York"))) &&
                            ohlcIndicator.getHigh().getPrice().isGreaterThanOrEqual(gapOpen.getPrice())) {
                        return new DateTimePrice(ohlcIndicator.getLow().getPrice(), ohlcIndicator.getLow().getDate(), ohlcIndicator.getLow().getTime());
                    }
                }

            }
        }
        return null;
    }
    
    private void printResults() {
        double sum = 0;
        int count = 0;
        System.out.println("Gap Start DateTime, Gap End DateTime, Gap Close DateTime, Gap Duration (mins), Gap Duration (days)");
        for (DateTimePriceTriplet dateTimePriceTriplet : gapList) {
            if (dateTimePriceTriplet != null && dateTimePriceTriplet.getGapClosedEnd() == null) {
//                System.out.println("Gap not closed");
            }
            else if (dateTimePriceTriplet != null && dateTimePriceTriplet.getGapEnd() != null && dateTimePriceTriplet.getGapClosedEnd() != null) {
                ZonedDateTime gapStartDateTime = ZonedDateTime.of(dateTimePriceTriplet.getGapStart().getDate(),
                        dateTimePriceTriplet.getGapStart().getTime(), ZoneId.of("America/New_York"));
                ZonedDateTime gapEndDateTime = ZonedDateTime.of(dateTimePriceTriplet.getGapEnd().getDate(),
                        dateTimePriceTriplet.getGapEnd().getTime(), ZoneId.of("America/New_York"));
                ZonedDateTime gapClosedEndDateTime = ZonedDateTime.of(dateTimePriceTriplet.getGapClosedEnd().getDate(),
                        dateTimePriceTriplet.getGapClosedEnd().getTime(), ZoneId.of("America/New_York"));

                long gapDurationMins = Duration.between(gapEndDateTime, gapClosedEndDateTime).toMinutes();
                long gapDurationDays = Duration.between(gapEndDateTime, gapClosedEndDateTime).toDays();
                System.out.println(gapStartDateTime.toLocalDateTime() + ", " + gapEndDateTime.toLocalDateTime() + ", " +
                        gapClosedEndDateTime.toLocalDateTime() + ", " + gapDurationMins+ ", " + gapDurationDays);

                count++;
                sum=sum+Duration.between(gapEndDateTime, gapClosedEndDateTime).toDays();

            }
        }
        System.out.println("Average mins to close gap: " + sum/(double) count);
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        GapDownStatsStats gapDownStatsStats = new GapDownStatsStats();
        gapDownStatsStats.evaluate();
    }

}

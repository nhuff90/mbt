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
import java.util.*;

public class TwinPrimeNumbersStats extends Stats {
    //todo - Create unit test!!

    List<Integer> twinPrimeList = Arrays.asList(3, 5, 7, 11, 13, 17, 19, 29, 31, 41, 43, 59, 61, 71, 73);
    Map<Integer, Map<Integer, TrueFalseDailyMgiResults>> twinPrimesToUpResultsMap = new HashMap<>(twinPrimeList.size());
    Map<Integer, Boolean> twinPrimeActiveMap = new HashMap<>(twinPrimeList.size());


    @Override
    public void evaluate() {
        init();
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        System.out.println("test");

        runStats(dailyMgiMap);
    }

    public void init() {
        Map<Integer, TrueFalseDailyMgiResults> innerMap = new HashMap<>();
        twinPrimeList.forEach(e -> innerMap.put(e, new TrueFalseDailyMgiResults()));
        twinPrimeList.forEach(e -> twinPrimeActiveMap.put(e, false));

        twinPrimeList.forEach(e -> innerMap.put(e, new TrueFalseDailyMgiResults()));
    }

    private void runStats(Map<LocalDate, DailyMgi> dailyMgiMap) {
        dailyMgiMap.forEach((date, dailyMgi) -> {
            populateUpResults(date, dailyMgi);
        });
    }

    private void populateUpResults(LocalDate date, DailyMgi dailyMgi) {
        for (int i = 0; i < dailyMgi.getOneMinOhlcList().size(); i++) {
            OHLCIndicator ohlcIndicator = dailyMgi.getOneMinOhlcList().get(i);
            if (i > 4) {
                OHLCIndicator bar5mAgo = dailyMgi.getOneMinOhlcList().get(i - 5);
                OHLCIndicator prevBar = dailyMgi.getOneMinOhlcList().get(i - 1);
                OHLCIndicator currentBar = dailyMgi.getOneMinOhlcList().get(i);
                // If 5 bars ago open is less than prev bars close
                if (bar5mAgo.getOpen().getDate().isEqual(prevBar.getOpen().getDate()) &&
                        bar5mAgo.getOpen().getPrice().isLessThanOrEqual(prevBar.getOpen().getPrice())) {
                    double currentBarOpenMod = currentBar.getOpen().getPrice().doubleValue() % 100;
                    double currentBarHighMod = currentBar.getHigh().getPrice().doubleValue() % 100;
                    for (Integer twinPrime : twinPrimeList) {
                        if (currentBarOpenMod <= twinPrime && currentBarHighMod >= twinPrime) {
                            twinPrimeActiveMap.put(twinPrime, true);
                            System.out.println("This straddles a twin prime: " + twinPrime + " - Open: " +
                                    currentBar.getOpen().getPrice() +
                                    " | High: " + currentBar.getHigh().getPrice() +
                                    " | Date: " + currentBar.getOpen().getDate() +
                                    " | Time: " + currentBar.getOpen().getTime());
                        }
                    }
                }
            }
        }
    }

    private Boolean isFirstPriceBreakAfterTimeUp(DailyMgi dailyMgi, Num priceToBreakUp, Num priceToBreakDown, MarketTime startTime) {
        for (OHLCIndicator ohlcIndicator : dailyMgi.getOneMinOhlcList()) {
            if (TimeUtils.isAfter(ohlcIndicator.getOpen().getTime(), startTime)) {
                if (ohlcIndicator.getHigh().getPrice().isGreaterThanOrEqual(priceToBreakUp)) {
                    return true;
                } else if (ohlcIndicator.getLow().getPrice().isLessThanOrEqual(priceToBreakDown)) {
                    return false;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        TwinPrimeNumbersStats twinPrimeNumbersStats = new TwinPrimeNumbersStats();
        twinPrimeNumbersStats.evaluate();
    }


}
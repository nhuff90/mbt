package nate.backtests.inWork;

import nate.backtests.BackTestWithDailyMgi;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.Trade;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FiveMinInsideCandleUpInRthBackTest extends BackTestWithDailyMgi {
    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 29), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2021, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2022, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

    createRulesAndRunBackTest(series);
    }

    //todo - This still needs work
    private static void createRulesAndRunBackTest(BarSeries series) {

        /**
         * Buy Rules
         * 1. In RTH
         * AND
         * 2. 5 Min Inside Bar Close
         * AND
         * 3. Break out up of Engulfing candle high
         */

        /**
         * Sell Rules
         * 1. Stop at Low of Engulfing candle
         * OR
         * 2. TP at 1R (size of engulfing candle)
         * OR
         * 3. End of RTH
         */

        Rule buyingRule;

        // Sell Rule
        Rule sellingRule = new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500);


        /**
         * Results

         *
         */
        System.out.println("working.....");
        buyingRule = new MarketTimeRangeRule(series, MarketTime.RTH_START_TIME, MarketTime.RTH_1500).and(new FiveMinuteInsideCandleRule(series));
        run(series, buyingRule, sellingRule, Trade.TradeType.BUY);
        System.out.println("");

    }
}

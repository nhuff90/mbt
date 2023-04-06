package nate.backtests;

import org.ta4j.core.*;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GapUpBackTest extends BackTestWithDailyMgi {
    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2022, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);
    }

    private static void createRulesAndRunBackTest(BarSeries series) {

        /**
         * Buy Rules
         * 1. PDH <= RTH Open by more than 5 points
         * AND
         * 2. After first 15mins of RTH
         * AND
         * 3. Fall below RTH Open (Or OMAR Low?)
         */

        /**
         * Sell Rules
         * 1. Gap filled. Px <= PDH
         * OR
         * 2. Stop out on new HOD -- Px > RTH High
         * OR
         * 3. 1500 (Close before power hour)
         */


        Rule buyingRule;

        // Sell Rule
        Rule sellingRule = new GapUpFillRule(series, 1.0).or(new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500)).or(new NewHighOfDayRule(series));


        /**
         * Results
         * buyingRule = new GapUpRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new LookAndFailAboveOvernightHighRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenGreaterThanPriorDayHighRule(series));
         *
         *
         */
        System.out.println("buyingRule = new GapUpRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new LookAndFailAboveOvernightHighRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenGreaterThanPriorDayHighRule(series));");
        buyingRule = new GapUpRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingAboveOvernightHighRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenGreaterThanPriorDayHighRule(series));
        run(series, buyingRule, sellingRule, Trade.TradeType.SELL);
        System.out.println("");
    }
}

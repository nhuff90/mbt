package nate.backtests;

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

public class GapDownBackTest extends BackTestWithDailyMgi {
    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2021, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2022, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2023, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

    createRulesAndRunBackTest(series);
    }

    private static void createRulesAndRunBackTest(BarSeries series) {

        /**
         * Buy Rules
         * 1. PDL >= RTH Open by more than 5 points
         * AND
         * 2. After first 15mins of RTH
         * AND
         * 3. Break above OMAR High (Or RTH Open?)
         */

        /**
         * Sell Rules
         * 1. Gap filled. Px >= PDL
         * OR
         * 2. Stop out on new LOD -- Px < RTH Low
         * OR
         * 3. End of RTH
         */

        Rule buyingRule;

        // Sell Rule
        Rule sellingRule = new GapDownFillRule(series, 1.0).or(new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500)).or(new NewHighOfDayRule(series));


        /**
         * Results
         * buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));
         *
         * 01/01/2018 - 3/28/2023
         * ResultsAnalysis{EV= $2.48, Max Drawdown= 3.85%, Win%= 77.5%, #OfWins= 62.0, #OfLosses= 18.0}
         *
         * 01/01/2021 - 3/28/2023
         * ResultsAnalysis{EV= $4.90, Max Drawdown= 1.34%, Win%= 82.05%, #OfWins= 32.0, #OfLosses= 7.0}
         *
         * 01/01/2023 - 3/28/2023
         * ResultsAnalysis{EV= $6.25, Max Drawdown= 0.28%, Win%= 100.0%, #OfWins= 7.0, #OfLosses= 0.0}
         *
         */
        System.out.println("buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));");
        buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));
        run(series, buyingRule, sellingRule, Trade.TradeType.BUY);
        System.out.println("");

    }
}

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
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

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
        Rule sellingRule = new GapDownFillRule(series, 0.75).or(new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500)).or(new NewLowOfDayRule(series));


        /**
         * Results
         * buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));
         *
         *
         */
        System.out.println("buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));");
//        buyingRule = new GapDownRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new CrossingBelowOvernightLowRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenLessThanPriorDayLowRule(series));
        buyingRule = new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1130)
                .and(new GapDownRule(series))
                .and(new CrossingBelowOvernightLowRule(series))
                .and(new CrossingAboveRthOpenRule(series))
                .and(new RthOpenLessThanPriorDayLowRule(series))
                .and(new GapDownHasNotAlreadyFilledRule(series))
                .and(new RRisGreaterThan_CurrentPriceEntry_PriorDayLowTP_OvernightLowSL_Rule(series, 4)
                );
        run(series, buyingRule, sellingRule, Trade.TradeType.BUY);
        System.out.println("");

    }
}

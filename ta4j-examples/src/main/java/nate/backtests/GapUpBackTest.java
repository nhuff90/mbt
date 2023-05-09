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
        series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 3, 6), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        createRulesAndRunBackTest(series);
    }

    private static void createRulesAndRunBackTest(BarSeries series) {

        /**
         * Buy Rules
         * 1. PDH <= RTH Open by more than 5 points
         * AND
         * 2. After AM Range
         * AND
         * 3. Fall below RTH Open (Or OMAR Low?)
         * AND
         * 4. RR > 1
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
        Rule sellingRule = new GapUpFillRule(series, 0.75).or(new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500)).or(new NewHighOfDayRule(series));


        /**
         * Results
         * buyingRule = new GapUpRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new LookAndFailAboveOvernightHighRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenGreaterThanPriorDayHighRule(series));
         *
         *
         */
        System.out.println("buyingRule = new GapUpRule(series).and(new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)).and(new LookAndFailAboveOvernightHighRule(series)).and(new CrossingBelowRthOpenRule(series)).and(new RthOpenGreaterThanPriorDayHighRule(series));");
        buyingRule = new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500)
                .and(new GapUpRule(series))
                .and(new CrossingAboveOvernightHighRule(series))
                .and(new CrossingBelowRthOpenRule(series))
                .and(new RthOpenGreaterThanPriorDayHighRule(series))
                .and(new GapUpHasNotAlreadyFilledRule(series))
                .and(new RRisGreaterThan_CurrentPriceEntry_PriorDayHighTP_OvernightHighSL_Rule(series, 4)
                );
        run(series, buyingRule, sellingRule, Trade.TradeType.SELL);
        System.out.println("");
    }
}

package nate.backtests;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.Trade;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.DoubleFormatter;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class Opening5mHighEqOpening15mHighBasedOnOpeningRanges extends BackTestWithDailyMgi {
    //todo - create unit tests
    public static void main(String[] args) throws InterruptedException {
        series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 20), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        for (Double takeProfitsExtensionPercent : takeProfitsExtPercentList) {
            for (Double stopLossExtensionPercent : stopLossExtPercentList) {
                for (Double retracePercent : retracePercentList) {
                    createRulesAndRunBackTest(series, takeProfitsExtensionPercent, stopLossExtensionPercent, retracePercent);
                }
            }
        }
    }

    public static void main(double takeProfitsExtensionPercent, double stopLossExtensionPercent, double retracePercent) throws InterruptedException {
        series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 20), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

        createRulesAndRunBackTest(series, takeProfitsExtensionPercent, stopLossExtensionPercent, retracePercent);
    }

//    private static final List<Double> takeProfitsExtPercentList = Arrays.asList(0.5);
    private static final List<Double> takeProfitsExtPercentList = Arrays.asList(0.5, 1.0, 1.5, 2.0);
//    private static final List<Double> stopLossExtPercentList = Arrays.asList(0.5);
    private static final List<Double> stopLossExtPercentList = Arrays.asList(0.5, 1.0, 1.5, 2.0);
    private static final List<Double> retracePercentList = Arrays.asList(0.05, 0.1, .25, .5);

    private static void createRulesAndRunBackTest(BarSeries series, Double takeProfitsExtensionPercent, Double stopLossExtensionPercent, Double retracePercent) {

        Rule buyingRule;

        // Sell Rule
        Rule sellingRule = new RangeExtensionHitRule(series, Range.OPENING_15_MIN, takeProfitsExtensionPercent, false)
                .or(new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0945, MarketTime.RTH_1500)).or(new RangeExtensionHitRule(series, Range.OPENING_15_MIN, stopLossExtensionPercent, true));
        String sellingRuleStr = "(TP: " + DoubleFormatter.formatPercent(takeProfitsExtensionPercent)
                + " Extension Lower of O15m Extension Hit) OR (SL: " + DoubleFormatter.formatPercent(stopLossExtensionPercent) + " Extension Higher of O15m Extension Hit ) OR (Outside of 0945-1500)";

        buyingRule = new DailyTradeNotTakenRule().and(new MarketTimeRangeRule(series, MarketTime.RTH_0945, MarketTime.RTH_1500))
                .and(new RangeHighLowEqualsRange(series, Range.OPENING_5_MIN, Range.OPENING_15_MIN, true))
                .and(new RangeRetracementHitRule(series, Range.OPENING_15_MIN, retracePercent, false));
        String buyingRuleStr = "(Daily trade not taken) AND (Between 0945-1500) AND (O5m Range High = O15m Range High) AND (" + DoubleFormatter.formatPercent(retracePercent) +  " Retrace of O15m Range)";

        run(series, buyingRule, sellingRule, buyingRuleStr, sellingRuleStr, Trade.TradeType.SELL);
//        System.out.println("");
    }
}

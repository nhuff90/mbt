package nate.stats;

import nate.backtests.BackTestWithDailyMgi;
import org.ta4j.core.*;
import org.ta4j.core.rules.nate.*;
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * abstract class for testing the % probability of given tests.
 */
public abstract class Stats extends BackTestWithDailyMgi {

    public Stats() {
    }

    public abstract void evaluate();

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

    /**
     * Generic run method to load daily MGI
     * @param series
     * @param buyingRules
     * @param sellingRules
     * @param tradeType
     * @return
     */
    protected static TradingRecord run(BarSeries series, Rule buyingRules, Rule sellingRules, Trade.TradeType tradeType) {
        // Run backtest
        BarSeriesWithDailyMgiManager seriesManager = new BarSeriesWithDailyMgiManager(series);
        return seriesManager.run(new BaseStrategy(buyingRules, sellingRules), tradeType);
    }

    /**
     * Generic backtest to load daily MGI data
     * @param series
     */
    protected static void createRulesAndRunBackTest(BarSeries series) {
        Rule buyingRule;
        Rule sellingRule = new OutsideOfMarketTimeRangeRule(series, MarketTime.RTH_0935, MarketTime.RTH_1500);
        buyingRule = new MarketTimeRangeRule(series, MarketTime.RTH_1005, MarketTime.RTH_1500);
        run(series, buyingRule, sellingRule, Trade.TradeType.SELL);
    }

}

package org.ta4j.core;

import org.ta4j.core.num.Num;
import org.ta4j.core.rules.mine.StopLossInPointsRule;
import org.ta4j.core.rules.mine.TakeProfitInPointsRule;

public class AverageBarTakeProfitStopLossAbstract extends TakeProfitStopLossAbstract {
    public AverageBarTakeProfitStopLossAbstract(TakeProfitInPointsRule takeProfitInPointsRule, StopLossInPointsRule stopLossInPointsRule) {
        super(takeProfitInPointsRule, stopLossInPointsRule);
    }

    public AverageBarTakeProfitStopLossAbstract() {
        super(null, null); //todo fix this - should not be sending in nulls
    }

    @Override
    public Num getExitPrice(Strategy strategy, TradingRecord tradingRecord, BarSeries barSeries, int i) {
        return barSeries.getBar(i).calculateAverageBarPrice();
    }
}

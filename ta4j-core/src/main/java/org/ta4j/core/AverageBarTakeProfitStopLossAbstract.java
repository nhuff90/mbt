package org.ta4j.core;

import org.ta4j.core.num.Num;
import org.ta4j.core.rules.nate.StopLossInPointsRule;
import org.ta4j.core.rules.nate.TakeProfitInPointsRule;

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

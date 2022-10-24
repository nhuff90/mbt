package org.ta4j.core;

import org.ta4j.core.num.Num;
import org.ta4j.core.rules.mine.StopLossRule;
import org.ta4j.core.rules.mine.TakeProfitRule;

public class AverageBarTakeProfitStopLosAbstract extends TakeProfitStopLossAbstract {
    public AverageBarTakeProfitStopLosAbstract(TakeProfitRule takeProfitRule, StopLossRule stopLossRule) {
        super(takeProfitRule, stopLossRule);
    }

    public AverageBarTakeProfitStopLosAbstract() {
        super(null, null); //todo fix this - should not be sending in nulls
    }

    @Override
    public Num getExitPrice(Strategy strategy, TradingRecord tradingRecord, BarSeries barSeries, int i) {
        return barSeries.getBar(i).calculateAverageBarPrice();
    }
}

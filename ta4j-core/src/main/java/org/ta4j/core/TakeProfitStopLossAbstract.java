package org.ta4j.core;

import org.ta4j.core.num.Num;
import org.ta4j.core.rules.mine.StopLossRule;
import org.ta4j.core.rules.mine.TakeProfitRule;

public abstract class TakeProfitStopLossAbstract {
    TakeProfitRule takeProfitRule;

    public TakeProfitRule getTakeProfitRule() {
        return takeProfitRule;
    }

    public StopLossRule getStopLossRule() {
        return stopLossRule;
    }

    StopLossRule stopLossRule;

    public TakeProfitStopLossAbstract() {

    }
    public TakeProfitStopLossAbstract(TakeProfitRule takeProfitRule, StopLossRule stopLossRule) {
        this.takeProfitRule = takeProfitRule;
        this.stopLossRule = stopLossRule;
    }

    public abstract Num getExitPrice(Strategy strategy, TradingRecord tradingRecord, BarSeries barSeries, int i);
}

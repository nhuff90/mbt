package org.ta4j.core;

import org.ta4j.core.num.Num;
import org.ta4j.core.rules.nate.StopLossInPointsRule;
import org.ta4j.core.rules.nate.TakeProfitInPointsRule;

public abstract class TakeProfitStopLossAbstract {
    TakeProfitInPointsRule takeProfitInPointsRule;

    public TakeProfitInPointsRule getTakeProfitRule() {
        return takeProfitInPointsRule;
    }

    public StopLossInPointsRule getStopLossRule() {
        return stopLossInPointsRule;
    }

    StopLossInPointsRule stopLossInPointsRule;

    public TakeProfitStopLossAbstract() {

    }
    public TakeProfitStopLossAbstract(TakeProfitInPointsRule takeProfitInPointsRule, StopLossInPointsRule stopLossInPointsRule) {
        this.takeProfitInPointsRule = takeProfitInPointsRule;
        this.stopLossInPointsRule = stopLossInPointsRule;
    }

    public abstract Num getExitPrice(Strategy strategy, TradingRecord tradingRecord, BarSeries barSeries, int i);
}

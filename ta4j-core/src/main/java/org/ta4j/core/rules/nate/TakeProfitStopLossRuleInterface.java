package org.ta4j.core.rules.nate;

import org.ta4j.core.rules.AbstractRule;

public abstract class TakeProfitStopLossRuleInterface extends AbstractRule {
    private boolean satisfied;

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

}

package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Bar;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;

public class Range {
    Num high = DecimalNum.valueOf(Integer.MIN_VALUE);
    Num low = DecimalNum.valueOf(Integer.MAX_VALUE);;
    Num middle;
    Num twentyFivePercent;
    Num seventyFivePercent;

    public Range() {
    }

    public Range(Num high, Num low, Num middle, Num twentyFivePercent, Num seventyFivePercent) {
        this.high = high;
        this.low = low;
        this.middle = middle;
        this.twentyFivePercent = twentyFivePercent;
        this.seventyFivePercent = seventyFivePercent;
    }

    public Range(Bar bar) {
        setRangeValues(bar);
    }

    public void setRangeValues(Bar bar) {
        // If 930 bar, reset high and low of range
        if (MarketTime.isStartOfAm(bar.getEndTime())) {
            this.high = bar.getHighPrice();
            this.low = bar.getLowPrice();
        } else {
            this.high = this.high.max(bar.getHighPrice());
            this.low = this.low.min(bar.getLowPrice());
        }
        this.middle = this.high.plus(this.low).dividedBy(DecimalNum.valueOf(2));
        this.twentyFivePercent = this.low.plus(middle).dividedBy(DecimalNum.valueOf(2));
        this.seventyFivePercent = this.high.plus(middle).dividedBy(DecimalNum.valueOf(2));
    }

    public Num getPercentileFromRange(double percentage) {
        double percentOffset = (this.high.doubleValue()-this.low.doubleValue())*percentage;
        return this.low.plus(DecimalNum.valueOf(percentOffset));
    }

    public Num getHigh() {
        return high;
    }

    public Num getLow() {
        return low;
    }

    public Num getMiddle() {
        return middle;
    }

    public Num getTwentyFivePercent() {
        return twentyFivePercent;
    }

    public Num getSeventyFivePercent() {
        return seventyFivePercent;
    }
}

package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Bar;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;


public class Range {
    Num high;
    Num low;
    Num middle;
    Num twentyFivePercent;
    Num seventyFivePercent;
    Num open;
    Num close;

    public Range() {
    }

    public Range(Num high, Num low, Num middle, Num twentyFivePercent, Num seventyFivePercent) {
        this.high = high;
        this.low = low;
        this.middle = middle;
        this.twentyFivePercent = twentyFivePercent;
        this.seventyFivePercent = seventyFivePercent;
    }

    public Range(Bar bar, MarketTime startTimeOfRange, MarketTime endTimeOfRange) {
        setRangeValues(bar, startTimeOfRange, endTimeOfRange);
    }

    public void setRangeValues(Bar bar, MarketTime startTimeOfRange, MarketTime endTimeOfRange) {
        if (!bar.getEndTime().toLocalTime().isBefore(startTimeOfRange.getLocalTime()) && !bar.getEndTime().toLocalTime().isAfter(endTimeOfRange.getLocalTime())) {
            // If start bar, reset high and low of range
            if (startTimeOfRange.getLocalTime().equals(bar.getEndTime().toLocalTime())) {
                this.high = bar.getHighPrice();
                this.low = bar.getLowPrice();
                this.open = bar.getOpenPrice();
            } else {
                // todo remove
                if (this.high == null) {
                    System.out.println("Debug");
                }
                this.high = this.high.max(bar.getHighPrice());
                this.low = this.low.min(bar.getLowPrice());
            }
            this.middle = this.high.plus(this.low).dividedBy(DecimalNum.valueOf(2));
            this.twentyFivePercent = this.low.plus(middle).dividedBy(DecimalNum.valueOf(2));
            this.seventyFivePercent = this.high.plus(middle).dividedBy(DecimalNum.valueOf(2));

            if (endTimeOfRange.getLocalTime().equals(bar.getEndTime().toLocalTime())) {
                this.close = bar.getClosePrice();
            }
        }
    }

    public Num getPercentileFromRange(double percentage) {
        if (this.high != null && this.low != null) {
            double percentOffset = (this.high.doubleValue() - this.low.doubleValue()) * percentage;
            return this.low.plus(DecimalNum.valueOf(percentOffset));
        } else {
            return null;
        }
    }

    public Num getHighPrice() {
        return high;
    }

    public Num getLowPrice() {
        return low;
    }

    public Num getMiddlePrice() {
        return middle;
    }

    public Num getTwentyFivePercentPrice() {
        return twentyFivePercent;
    }

    public Num getSeventyFivePercentPrice() {
        return seventyFivePercent;
    }

    public Num getOpenPrice() {
        return open;
    }

    public Num getClosePrice() {
        return close;
    }

    public Num getRangeSize() {
        return high.minus(low);
    }
}

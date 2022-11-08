package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Bar;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.utils.MarketTime;


public class Range {
    Bar highBar;
    Bar lowBar;
    Bar openBar;
    Bar closeBar;

    public Range() {
    }

    public Range(Bar bar, MarketTime startTimeOfRange, MarketTime endTimeOfRange) {
        setRangeValues(bar, startTimeOfRange, endTimeOfRange);
    }

    public void setRangeValues(Bar bar, MarketTime startTimeOfRange, MarketTime endTimeOfRange) {
        if (!bar.getEndTime().toLocalTime().isBefore(startTimeOfRange.getLocalTime()) && !bar.getEndTime().toLocalTime().isAfter(endTimeOfRange.getLocalTime())) {
            // If start bar, reset high and low of range
            if (startTimeOfRange.getLocalTime().equals(bar.getEndTime().toLocalTime())) {
                this.highBar = bar;
                this.lowBar = bar;
                this.openBar = bar;
            } else {
                try {
                    this.highBar = (this.highBar.getHighPrice().isGreaterThan(bar.getHighPrice()) ? this.highBar : bar);
                    this.lowBar = (this.lowBar.getLowPrice().isLessThan(bar.getLowPrice()) ? this.lowBar : bar);
                } catch (Exception e) {
                    System.out.println("test");
                }
            }

            if (endTimeOfRange.getLocalTime().equals(bar.getEndTime().toLocalTime())) {
                this.closeBar = bar;
            }
        }
    }

    public Num getPercentileFromRange(double percentage) {
        if (this.highBar != null && this.lowBar != null) {
            double percentOffset = (this.highBar.getHighPrice().doubleValue() - this.lowBar.getLowPrice().doubleValue()) * percentage;
            return this.lowBar.getLowPrice().plus(DecimalNum.valueOf(percentOffset));
        } else {
            return null;
        }
    }

    public Num getHighPrice() {
        return (highBar != null ? highBar.getHighPrice() : null);
    }

    public Num getLowPrice() {
        return (lowBar != null ? lowBar.getLowPrice() : null);
    }

    public Num getMiddlePrice() {
        return this.highBar.getHighPrice().plus(this.lowBar.getLowPrice()).dividedBy(DecimalNum.valueOf(2));
    }

    public Num getTwentyFivePercentPrice() {
        return this.lowBar.getLowPrice().plus(getMiddlePrice()).dividedBy(DecimalNum.valueOf(2));
    }

    public Num getSeventyFivePercentPrice() {
        return this.highBar.getHighPrice().plus(getMiddlePrice()).dividedBy(DecimalNum.valueOf(2));
    }

    public Num getOpenPrice() {
        return (openBar != null ? openBar.getOpenPrice(): null);
    }

    public Num getClosePrice() {
        return (closeBar != null ? closeBar.getClosePrice(): null);
    }

    public Num getRangeSize() {
        return highBar.getHighPrice().minus(lowBar.getLowPrice());
    }

    // This is used to determine if the range is incomplete
    public boolean isRangeIncomplete() {
        return (openBar == null) || (closeBar == null);
    }
}

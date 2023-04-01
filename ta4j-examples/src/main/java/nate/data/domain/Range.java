package com.sma.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Range {
    double high;
    double low;
    double middle;
    double twentyFivePercent;
    double seventyFivePercent;

    public Range(double high, double low) {
        this.high = high;
        this.low = low;
        calcuateValues(high, low);
    }

    public void setValues(double high, double low) {
        this.high = Math.max(this.high, high);
        this.low = Math.min(this.low, low);
        calcuateValues(high, low);
    }

    private void calcuateValues(double high, double low) {
        this.middle = (high + low)/2;
        this.twentyFivePercent = high - ((high - low)*0.25);
        this.seventyFivePercent = high - ((high - low)*0.75);
    }
}

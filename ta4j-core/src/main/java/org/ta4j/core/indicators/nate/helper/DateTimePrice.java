package org.ta4j.core.indicators.nate.helper;

import org.ta4j.core.num.Num;

import java.time.LocalTime;

public class TimeAndPrice {
    Num price;
    LocalTime time;

    public TimeAndPrice(Num price, LocalTime time) {
        this.price = price;
        this.time = time;
    }

    public Num getPrice() {
        return price;
    }

    public void setPrice(Num price) {
        this.price = price;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

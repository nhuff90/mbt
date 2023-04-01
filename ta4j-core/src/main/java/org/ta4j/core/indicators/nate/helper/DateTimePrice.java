package org.ta4j.core.indicators.nate.helper;

import org.ta4j.core.num.Num;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimePrice {
    Num price;
    LocalDate date;
    LocalTime time;

    public DateTimePrice(Num price, LocalDate date, LocalTime time) {
        this.price = price;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

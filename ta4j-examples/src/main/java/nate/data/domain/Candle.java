package com.sma.v01.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public
class Candle {
    private LocalDate date;
    private LocalTime time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer volume;
    private CandleLength length;

    public String[] toStringArray() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return new String[]{date.toString(),
                time.format(dtf),
                open.toString(),
                high.toString(),
                low.toString(),
                close.toString(),
                volume.toString()};
    }
}
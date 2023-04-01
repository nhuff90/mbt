package nate.data.domain;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public Candle(LocalDate date, LocalTime time, Double open, Double high, Double low, Double close, Integer volume, CandleLength length) {
        this.date = date;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.length = length;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public CandleLength getLength() {
        return length;
    }

    public void setLength(CandleLength length) {
        this.length = length;
    }
}
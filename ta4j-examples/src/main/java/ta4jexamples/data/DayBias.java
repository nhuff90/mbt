package ta4jexamples.data;

public enum DayBias {
    UNKNOWN("UNKNOWN"),
    TREND_UP("Trend Up Day"),
    TREND_DOWN("Trend Down Day"),
    RANGE_DAY("Range Day");

    final String dayBias;

    DayBias(String s) {
        this.dayBias = s;
    }
}

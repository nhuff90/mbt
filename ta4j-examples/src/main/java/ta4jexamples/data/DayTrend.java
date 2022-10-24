package ta4jexamples.data;

public enum DayBias {
    NA("N/A"),
    UNPREDICTABLE("Unpredictable"),
    TREND_UP("Trend Day Up"),
    TREND_DOWN("Trend Day Down"),
    RANGE_UP("Range Day Up"),
    RANGE_DOWN("Range Day Down"),
    EXPANDED_RANGE_UP("Expanded Range Day Up"),
    EXPANDED_RANGE_DOWN("Expanded Range Day Down");

    final String dayBias;

    DayBias(String s) {
        this.dayBias = s;
    }
}

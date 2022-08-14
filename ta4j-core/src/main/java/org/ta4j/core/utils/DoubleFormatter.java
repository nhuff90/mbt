package org.ta4j.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class DoubleFormatter {
    private static NumberFormat dollarFormatter = NumberFormat.getCurrencyInstance();
    private static NumberFormat percentFormatter = NumberFormat.getPercentInstance();

    public static String formatDollar(Double num) {
        return dollarFormatter.format(num);
    }

    public static String formatPercent(Double num) {
        return (new BigDecimal(num*100).setScale(2, RoundingMode.HALF_UP).doubleValue()) + "%";
    }
}

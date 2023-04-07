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

    /**
     * Default scale is 2
     * @param num
     * @return
     */
    public static String formatPercent(Double num) {
        return formatPercent(num, 2);
    }

    public static String formatPercent(Double num, int scale) {
        return (new BigDecimal(num*100).setScale(scale, RoundingMode.HALF_UP).doubleValue()) + "%";
    }
}

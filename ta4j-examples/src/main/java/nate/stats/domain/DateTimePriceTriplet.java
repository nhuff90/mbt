package nate.stats.domain;

import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.helper.DateTimePrice;

public class DateTimePriceTriplet {
    private DateTimePrice gapStart;
    private DateTimePrice gapEnd;
    private DateTimePrice gapClosedEnd;

    public DateTimePriceTriplet() {
    }

    public DateTimePrice getGapStart() {
        return gapStart;
    }

    public void setGapStart(DateTimePrice gapStart) {
        this.gapStart = gapStart;
    }

    public DateTimePrice getGapEnd() {
        return gapEnd;
    }

    public void setGapEnd(DateTimePrice gapEnd) {
        this.gapEnd = gapEnd;
    }

    public DateTimePrice getGapClosedEnd() {
        return gapClosedEnd;
    }

    public void setGapClosedEnd(DateTimePrice gapClosedEnd) {
        this.gapClosedEnd = gapClosedEnd;
    }
}

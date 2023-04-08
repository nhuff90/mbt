package nate.stats.domain;

import org.ta4j.core.rules.nate.DailyMgi;

import java.util.ArrayList;
import java.util.List;

public class TrueFalseDailyMgiResults {
    List<DailyMgi> trueDailyMgiList = new ArrayList<>();
    List<DailyMgi> falseDailyMgiList = new ArrayList<>();

    public void addToTrueDailyMgiList(DailyMgi dailyMgi) {
        trueDailyMgiList.add(dailyMgi);
    }

    public void addToFalseDailyMgiList(DailyMgi dailyMgi) {
        falseDailyMgiList.add(dailyMgi);
    }

    public List<DailyMgi> getTrueDailyMgiList() {
        return trueDailyMgiList;
    }

    public List<DailyMgi> getFalseDailyMgiList() {
        return falseDailyMgiList;
    }

    public int getTotalOfTrueAndFalseLists() {
        return trueDailyMgiList.size() + falseDailyMgiList.size();
    }
}

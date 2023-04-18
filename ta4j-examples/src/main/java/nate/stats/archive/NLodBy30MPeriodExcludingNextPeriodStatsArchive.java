package nate.stats.archive;

import nate.stats.Stats;
import nate.stats.domain.TrueFalseDailyMgiAndPeriodOhlcResults;
import nate.stats.domain.TrueFalseDailyMgiResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.PreInPostOHLCIndicator;
import org.ta4j.core.indicators.nate.helper.Period30m;
import org.ta4j.core.rules.nate.DailyMgi;
import org.ta4j.core.rules.nate.DailyMgiBuyRule;
import org.ta4j.core.utils.DoubleFormatter;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class NLodBy30MPeriodExcludingNextPeriodStatsArchive extends Stats {

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateNlodStats(dailyMgiMap);
    }

    private void evaluateNlodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;

        PeriodNlodResultsMap periodNlodResultsMap = calculatePeriodLodStats(dailyMgiMap);
        periodResultsMap(periodNlodResultsMap);
    }


    private void periodResultsMap(PeriodNlodResultsMap periodResultsMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("% Chance for NLod after Period excluding next Period,");
        periodResultsMap.getPeriodNlodAndNlodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
//            trueFalseDailyMgiResults.getFalseDailyMgiList().forEach(s -> {
//                System.out.println(period + " period false dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            trueFalseDailyMgiResults.getTrueDailyMgiList().forEach(s -> {
//                System.out.println(period + " period true dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            System.out.println(period + " total count: " + trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists());
            double percentTrue = (double) trueFalseDailyMgiResults.getTrueMap().size() / (double) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps();
            sb.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb.append(",");
        });

        String line = sb.substring(0, sb.length() - 1);
        System.out.println(line);

        StringBuilder sb2 = new StringBuilder();
        sb.append("Count true/total,");
        periodResultsMap.getPeriodNlodAndNlodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
            sb2.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
            sb2.append(",");
        });

        String line2 = sb2.substring(0, sb2.length() - 1);
        System.out.println(line2);
    }

    private PeriodNlodResultsMap calculatePeriodLodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        PeriodNlodResultsMap periodNlodResultsMap = new PeriodNlodResultsMap();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            // Map of 30m period to post-30m period
            Map<Period30m, PreInPostOHLCIndicator> periodsMap = new LinkedHashMap<>();
            periodsMap.put(Period30m.A, new PreInPostOHLCIndicator(dailyMgi.getPreAPeriodOhlc(), dailyMgi.getaPeriodOhlc(), dailyMgi.getPostAPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.B, new PreInPostOHLCIndicator(dailyMgi.getPreBPeriodOhlc(), dailyMgi.getbPeriodOhlc(), dailyMgi.getPostBPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.C, new PreInPostOHLCIndicator(dailyMgi.getPreCPeriodOhlc(), dailyMgi.getcPeriodOhlc(), dailyMgi.getPostCPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.D, new PreInPostOHLCIndicator(dailyMgi.getPreDPeriodOhlc(), dailyMgi.getdPeriodOhlc(), dailyMgi.getPostDPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.E, new PreInPostOHLCIndicator(dailyMgi.getPreEPeriodOhlc(), dailyMgi.getePeriodOhlc(), dailyMgi.getPostEPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.F, new PreInPostOHLCIndicator(dailyMgi.getPreFPeriodOhlc(), dailyMgi.getfPeriodOhlc(), dailyMgi.getPostFPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.G, new PreInPostOHLCIndicator(dailyMgi.getPreGPeriodOhlc(), dailyMgi.getgPeriodOhlc(), dailyMgi.getPostGPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.H, new PreInPostOHLCIndicator(dailyMgi.getPreHPeriodOhlc(), dailyMgi.gethPeriodOhlc(), dailyMgi.getPostHPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.I, new PreInPostOHLCIndicator(dailyMgi.getPreIPeriodOhlc(), dailyMgi.getiPeriodOhlc(), dailyMgi.getPostIPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.J, new PreInPostOHLCIndicator(dailyMgi.getPreJPeriodOhlc(), dailyMgi.getjPeriodOhlc(), dailyMgi.getPostJPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.K, new PreInPostOHLCIndicator(dailyMgi.getPreKPeriodOhlc(), dailyMgi.getkPeriodOhlc(), dailyMgi.getPostKPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.L, new PreInPostOHLCIndicator(dailyMgi.getPreLPeriodOhlc(), dailyMgi.getlPeriodOhlc(), dailyMgi.getPostLPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));
            periodsMap.put(Period30m.M, new PreInPostOHLCIndicator(dailyMgi.getPreMPeriodOhlc(), dailyMgi.getmPeriodOhlc(), dailyMgi.getPostMPeriodOhlc(), dailyMgi.getMapOf30mPeriods()));


            periodsMap.forEach((period30m, dailyOhlcs) -> {
                if (period30m != Period30m.A && period30m != Period30m.M) {
                    OHLCIndicator prePeriodOhlc = dailyOhlcs.getPreOhlc();
                    OHLCIndicator periodOhlc = dailyOhlcs.getOhlc();

                    if (prePeriodOhlc.getLow() == null || periodOhlc.getLow() == null) {
//                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    } else if (!prePeriodOhlc.getLow().getDate().isEqual(periodOhlc.getLow().getDate())) {
                        // Dates do not match. Most likely a shortened trading day or bad data.
//                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    }

                    if (periodOhlc.getLow().getPrice().isLessThan(prePeriodOhlc.getLow().getPrice())) {
                        AtomicBoolean nlodFound = new AtomicBoolean(false);
                        AtomicReference<OHLCIndicator> nlodOhlc = new AtomicReference<>(new OHLCIndicator());
                        // New LoD in this period
                        Map<Period30m, OHLCIndicator> postOhlcs = dailyOhlcs.getOhlcsAfterPeriod(period30m);
                        postOhlcs.forEach((period, postPeriodOhlc) -> {
                            if (postPeriodOhlc.getLow() != null && periodOhlc.getLow() != null &&
                                    postPeriodOhlc.getLow().getPrice().isGreaterThan(periodOhlc.getLow().getPrice()) &&
                                    period30m.next().isPresent() && !period30m.next().get().equals(period)) {
                                nlodFound.set(true);
                                nlodOhlc.set(postPeriodOhlc);
                                return;
                            }
                        });
                        if (nlodFound.get()) {
                            periodNlodResultsMap.addToNlodAfterIsTrueMap(period30m, nlodOhlc.get(), dailyMgi);
                        } else {
                            periodNlodResultsMap.addToNlodAfterIsFalseMap(period30m, dailyMgi);
                        }
                    }
                }
            });
        });
        return periodNlodResultsMap;
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);

        NLodBy30MPeriodExcludingNextPeriodStatsArchive nlodBy30mPeriodStatsTest = new NLodBy30MPeriodExcludingNextPeriodStatsArchive();
        nlodBy30mPeriodStatsTest.evaluate();
    }

    private class PeriodNlodResultsMap {
        Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNlodAndNlodAfterResultsMap = new LinkedHashMap<>();

        public PeriodNlodResultsMap() {
            periodNlodAndNlodAfterResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNlodAfterResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());
        }

        public void addToNlodAfterIsTrueMap(Period30m period30m, OHLCIndicator nlod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nlod30mOhlc);
            periodNlodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNlodAfterIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodNlodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodNlodAndNlodAfterResultsMap() {
            return periodNlodAndNlodAfterResultsMap;
        }
    }
}

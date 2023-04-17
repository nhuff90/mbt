package nate.stats;

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

public class NLodBy30mPeriodAfterFallingIntoAMRangeStatsTest extends StatsTest {

    PeriodNhodResultsMap periodNLODResultsMap = new PeriodNhodResultsMap();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateNHodStats(dailyMgiMap);
    }

    private void evaluateNHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;

        calculatePeriodHodStats(dailyMgiMap);
        periodResultsMap();
    }


    private void periodResultsMap() {
        /*
        Print NLODs
         */
        StringBuilder sb = new StringBuilder();
        sb.append("% Chance for NLod after Period (continuation),");
        periodNLODResultsMap.getPeriodNlodAndNlodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
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

        /*
        Print out NLOD counts
         */
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Count true/total,");
        periodNLODResultsMap.getPeriodNlodAndNlodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
            sb2.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
            sb2.append(",");
        });
        String line2 = sb2.substring(0, sb2.length() - 1);
        System.out.println(line2);

        /*
        Print NHODs
         */
        StringBuilder sb3 = new StringBuilder();
        sb3.append("% Chance for NHod after Period (reversal),");
        periodNLODResultsMap.getPeriodNlodAndNhodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
//            trueFalseDailyMgiResults.getFalseDailyMgiList().forEach(s -> {
//                System.out.println(period + " period false dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            trueFalseDailyMgiResults.getTrueDailyMgiList().forEach(s -> {
//                System.out.println(period + " period true dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            System.out.println(period + " total count: " + trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists());
            double percentTrue = (double) trueFalseDailyMgiResults.getTrueMap().size() / (double) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps();
            sb3.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb3.append(",");
        });

        String line3 = sb3.substring(0, sb3.length() - 1);
        System.out.println(line3);

        /*
        Print out NHOD counts
         */

        StringBuilder sb4 = new StringBuilder();
        sb4.append("Count true/total,");
        periodNLODResultsMap.getPeriodNlodAndNhodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
            sb4.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
            sb4.append(",");
        });

        String line4 = sb4.substring(0, sb4.length() - 1);
        System.out.println(line4);

        /*
        Print out counts
         */
//        StringBuilder sb2 = new StringBuilder();
//        sb.append("Count true/total,");
//        periodResultsMap.getPeriodNhodAndNhodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
//            sb2.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
//            sb2.append(",");
//        });

        /*
        Print % of NHOD period
         */
//        String line2 = sb2.substring(0, sb2.length() - 1);
//        System.out.println(line2);
//
//        // Most likely 30m period for NHod
//        sb.append("Count true/total,");
//
//        periodResultsMap.getPeriodNhodAndNhodAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
//            AtomicInteger totalCount = new AtomicInteger();
//            StringBuilder sb3 = new StringBuilder();
//            sb3.append(period30m + ",");
//            LinkedHashMap<Period30m, Integer> periodsMap = new LinkedHashMap<>();
//            periodsMap.put(Period30m.A, 0);
//            periodsMap.put(Period30m.B, 0);
//            periodsMap.put(Period30m.C, 0);
//            periodsMap.put(Period30m.D, 0);
//            periodsMap.put(Period30m.E, 0);
//            periodsMap.put(Period30m.F, 0);
//            periodsMap.put(Period30m.G, 0);
//            periodsMap.put(Period30m.H, 0);
//            periodsMap.put(Period30m.I, 0);
//            periodsMap.put(Period30m.J, 0);
//            periodsMap.put(Period30m.K, 0);
//            periodsMap.put(Period30m.L, 0);
//            periodsMap.put(Period30m.M, 0);
//            trueFalseDailyMgiResults.getTrueMap().forEach((dailyMgi, ohlcIndicator) -> {
//                Optional<Period30m> nhodPeriod = MarketTime.get30mPeriodFromTime(ohlcIndicator.getHigh().getTime());
//                if (nhodPeriod.isPresent()) {
//                    Integer count = periodsMap.get(nhodPeriod.get());
//                    count++;
//                    periodsMap.put(nhodPeriod.get(), count);
//                    totalCount.getAndIncrement();
//                }
//            });
//            periodsMap.forEach((period, periodCount) -> {
//                double percentTrue = (double) periodCount / (double) totalCount.get();
//                sb3.append(DoubleFormatter.formatPercent(percentTrue, 0));
//                sb3.append(",");
//            });
//            String line3 = sb3.substring(0, sb3.length() - 1);
//            System.out.println(line3);
//        });
    }

    private void calculatePeriodHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

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
                        /*
                        NLOD continuation
                         */
                        AtomicBoolean nlodFound = new AtomicBoolean(false);
                        AtomicReference<OHLCIndicator> nlodOhlc = new AtomicReference<>(new OHLCIndicator());
                        AtomicBoolean fellBackIntoAMRAngeAfterNLOD = new AtomicBoolean(false);
                        OHLCIndicator fellBackIntoAMRangeOhlc = new OHLCIndicator();
                        // NLOD in this period
                        Map<Period30m, OHLCIndicator> postOhlcs = dailyOhlcs.getOhlcsAfterPeriod(period30m);
                        for (Map.Entry<Period30m, OHLCIndicator> entry : postOhlcs.entrySet()) {
                            Period30m postPeriod = entry.getKey();
                            OHLCIndicator postPeriodOhlc = entry.getValue();
                            if (postPeriodOhlc.getHigh() != null && postPeriodOhlc.getHigh().getPrice().isGreaterThan(dailyMgi.getAmRangeOhlc().getLow().getPrice())) {
                                if (!fellBackIntoAMRAngeAfterNLOD.get()) {
                                    fellBackIntoAMRAngeAfterNLOD.set(true);
                                    fellBackIntoAMRangeOhlc = postPeriodOhlc;
                                }
                            }

                            if (fellBackIntoAMRAngeAfterNLOD.get() &&
                                    postPeriodOhlc.getLow() != null && periodOhlc.getLow() != null &&
                                    postPeriodOhlc.getLow().getPrice().isLessThan(periodOhlc.getLow().getPrice())) {
                                if (fellBackIntoAMRangeOhlc.getHigh().getTime().isBefore(postPeriodOhlc.getLow().getTime())) {
                                    nlodFound.set(true);
                                    nlodOhlc.set(postPeriodOhlc);
                                    break;
                                }
                            }
                        }
                        if (nlodFound.get()) {
                            periodNLODResultsMap.addToNlodAfterIsTrueMap(period30m, nlodOhlc.get(), dailyMgi);
                        } else {
                            periodNLODResultsMap.addToNlodAfterIsFalseMap(period30m, dailyMgi);
                        }

                        /*
                        NHOD Reversal
                         */
                        AtomicBoolean nhodFound = new AtomicBoolean(false);
                        AtomicReference<OHLCIndicator> nhodOhlcR = new AtomicReference<>(new OHLCIndicator());
                        postOhlcs.forEach((period, postPeriodOhlc) -> {
                            if (postPeriodOhlc.getHigh() != null && periodOhlc.getHigh() != null &&
                                    postPeriodOhlc.getHigh().getPrice().isGreaterThan(periodOhlc.getHigh().getPrice()) &&
                                    postPeriodOhlc.getHigh().getPrice().isGreaterThan(prePeriodOhlc.getHigh().getPrice())) {
                                nhodFound.set(true);
                                nhodOhlcR.set(postPeriodOhlc);
                                return;
                            }
                        });
                        if (nhodFound.get()) {
                            periodNLODResultsMap.addToNhodAfterIsTrueMap(period30m, nhodOhlcR.get(), dailyMgi);
                        } else {
                            periodNLODResultsMap.addToNhodAfterIsFalseMap(period30m, dailyMgi);
                        }
                    }
                }
            });
        });
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        LocalDate startDate = LocalDate.of(2022, 1, 1);

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(startDate, LocalTime.of(9, 30), ZoneId.of("America/New_York")));

        createRulesAndRunBackTest(series);

        NLodBy30mPeriodAfterFallingIntoAMRangeStatsTest nHodBy30mPeriodStatsTest = new NLodBy30mPeriodAfterFallingIntoAMRangeStatsTest();
        nHodBy30mPeriodStatsTest.evaluate();
    }

    private class PeriodNhodResultsMap {
        Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNlodAndNlodAfterResultsMap = new LinkedHashMap<>();
        Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNlodAndNhodAfterResultsMap = new LinkedHashMap<>();

        public PeriodNhodResultsMap() {
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

            periodNlodAndNhodAfterResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodNlodAndNhodAfterResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());
        }

        public void addToNlodAfterIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodNlodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNlodAfterIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodNlodAndNlodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNhodAfterIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNhodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodNlodAndNhodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToNhodAfterIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodNlodAndNhodAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodNlodAndNhodAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodNlodAndNlodAfterResultsMap() {
            return periodNlodAndNlodAfterResultsMap;
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodNlodAndNhodAfterResultsMap() {
            return periodNlodAndNhodAfterResultsMap;
        }
    }
}

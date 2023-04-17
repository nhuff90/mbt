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
import org.ta4j.core.utils.MarketTime;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NLodBy30mPeriodAfterBreakingHighOfPeriodStatsTest extends StatsTest {

    public static PeriodNlodResultsMap resultMap = new PeriodNlodResultsMap();

    @Override
    public void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateStats(dailyMgiMap);
    }

    private void evaluateStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;

        calculatePeriodHodStats(dailyMgiMap);
        periodResultsMap();
    }


    private void periodResultsMap() {
        /*
        Print NLODs
         */
        StringBuilder sb = new StringBuilder();
        sb.append("% Chance for NLOD after Period (continuation),");
        resultMap.getPeriodContinuationResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
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
        resultMap.getPeriodContinuationResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
            sb2.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
            sb2.append(",");
        });
        String line2 = sb2.substring(0, sb2.length() - 1);
        System.out.println(line2);

        /*
        Print NLODs
         */
        StringBuilder sb3 = new StringBuilder();
        sb2.append("% Chance for NLod after Period (reversal),");
        resultMap.getPeriodReversalAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
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
        Print NLOD counts
         */
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Count true/total,");
        resultMap.getPeriodReversalAfterResultsMap().forEach((period30m, trueFalseDailyMgiResults) -> {
            sb4.append((int) trueFalseDailyMgiResults.getTrueMap().size() + "/" + (int) trueFalseDailyMgiResults.getTotalOfTrueAndFalseMaps());
            sb4.append(",");
        });

        String line4 = sb4.substring(0, sb4.length() - 1);
        System.out.println(line4);

        /*
        Print % of NHOD period
         */

        // Most likely 30m period for NHod
        sb.append("Count true/total,");

        resultMap.getPeriodContinuationResultsMap().forEach((period30m, trueFalseDailyMgiAndPeriodOhlcResults) -> {
            AtomicInteger totalCount = new AtomicInteger();
            StringBuilder sb5 = new StringBuilder();
            sb3.append(period30m + ",");
            LinkedHashMap<Period30m, Integer> periodsMap = new LinkedHashMap<>();
            periodsMap.put(Period30m.A, 0);
            periodsMap.put(Period30m.B, 0);
            periodsMap.put(Period30m.C, 0);
            periodsMap.put(Period30m.D, 0);
            periodsMap.put(Period30m.E, 0);
            periodsMap.put(Period30m.F, 0);
            periodsMap.put(Period30m.G, 0);
            periodsMap.put(Period30m.H, 0);
            periodsMap.put(Period30m.I, 0);
            periodsMap.put(Period30m.J, 0);
            periodsMap.put(Period30m.K, 0);
            periodsMap.put(Period30m.L, 0);
            periodsMap.put(Period30m.M, 0);
            trueFalseDailyMgiAndPeriodOhlcResults.getTrueMap().forEach((dailyMgi, ohlcIndicator) -> {
                Optional<Period30m> nhodPeriod = MarketTime.get30mPeriodFromTime(ohlcIndicator.getHigh().getTime());
                if (nhodPeriod.isPresent()) {
                    Integer count = periodsMap.get(nhodPeriod.get());
                    count++;
                    periodsMap.put(nhodPeriod.get(), count);
                    totalCount.getAndIncrement();
                }
            });
            periodsMap.forEach((period, periodCount) -> {
                double percentTrue = (double) periodCount / (double) totalCount.get();
                sb5.append(DoubleFormatter.formatPercent(percentTrue, 0));
                sb5.append(",");
            });
            String line5 = sb5.substring(0, sb5.length() - 1);
            System.out.println(line5);
        });
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
                        AtomicBoolean continuationFound = new AtomicBoolean(false);
                        AtomicBoolean fellAboveCurrentPeriodHigh = new AtomicBoolean(false);
                        AtomicReference<OHLCIndicator> continuationOhlc = new AtomicReference<>(new OHLCIndicator());
                        OHLCIndicator fellAboveCurrentPeriodHighOhlc = new OHLCIndicator();
                        // New LoD in this period
                        Map<Period30m, OHLCIndicator> postOhlcs = dailyOhlcs.getOhlcsAfterPeriod(period30m);
                        for (Map.Entry<Period30m, OHLCIndicator> entry : postOhlcs.entrySet()) {
                            Period30m postPeriod = entry.getKey();
                            OHLCIndicator postPeriodOhlc = entry.getValue();
                            if (postPeriodOhlc.getHigh() != null && postPeriodOhlc.getHigh().getPrice().isGreaterThan(periodOhlc.getHigh().getPrice())) {
                                if (!fellAboveCurrentPeriodHigh.get()) {
                                    fellAboveCurrentPeriodHigh.set(true);
                                    fellAboveCurrentPeriodHighOhlc = postPeriodOhlc;
                                }
                            } else if (period30m.previous().isPresent() &&
                                    periodOhlc.getHigh().getPrice().isGreaterThan(dailyMgi.getOhlcOfPeriod(period30m.previous().get()).getHigh().getPrice()) &&
                                    periodOhlc.getHigh().getTime().isAfter(periodOhlc.getLow().getTime())) {
                                // Is the current period low after the current period high and less than the prior candle low?
                                if (!fellAboveCurrentPeriodHigh.get()) {
                                    fellAboveCurrentPeriodHigh.set(true);
                                    fellAboveCurrentPeriodHighOhlc = postPeriodOhlc;
                                }
                            }
                            if (fellAboveCurrentPeriodHigh.get() &&
                                    postPeriodOhlc.getLow() != null && periodOhlc.getLow() != null &&
                                    postPeriodOhlc.getLow().getPrice().isLessThan(periodOhlc.getLow().getPrice())) {
                                if (fellAboveCurrentPeriodHighOhlc.getHigh().getTime().isBefore(postPeriodOhlc.getLow().getTime())) {
                                    // make sure NHOD is after falling under current 30m low
                                    continuationFound.set(true);
                                    continuationOhlc.set(postPeriodOhlc);
                                    break;
                                }
                            }
                        }
                        if (continuationFound.get()) {
//                            //todo remove
//                            System.out.println(dailyMgi.getRthOhlc().getOpen().getDate() + " Period: " + period30m + " true -- Continutation:" + continuationOhlc.get().getHigh().getTime());
                            resultMap.addToContinuationIsTrueMap(period30m, continuationOhlc.get(), dailyMgi);
                        } else if (fellAboveCurrentPeriodHigh.get()) {
//                            //todo remove
//                            System.out.println(dailyMgi.getRthOhlc().getOpen().getDate() + " Period: " + period30m + " false");
                            resultMap.addToContinuationIsFalseMap(period30m, dailyMgi);
                        }

                        /*
                        NLOD Reversal
                         */
                        AtomicBoolean reversalFound = new AtomicBoolean(false);

                        AtomicReference<OHLCIndicator> reversalOhlc = new AtomicReference<>(new OHLCIndicator());
                        postOhlcs.forEach((period, postPeriodOhlc) -> {
                            if (postPeriodOhlc.getLow() != null && periodOhlc.getLow() != null &&
                                    postPeriodOhlc.getLow().getPrice().isLessThan(periodOhlc.getLow().getPrice()) &&
                                    postPeriodOhlc.getLow().getPrice().isLessThan(prePeriodOhlc.getLow().getPrice())) {
                                reversalFound.set(true);
                                reversalOhlc.set(postPeriodOhlc);
                                return;
                            }
                        });
                        if (reversalFound.get()) {
                            resultMap.addToReversalIsTrueMap(period30m, reversalOhlc.get(), dailyMgi);
                        } else {
                            resultMap.addToReversalIsFalseMap(period30m, dailyMgi);
                        }
                    }
                }
            });
        });
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2023, 2, 13), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));


        createRulesAndRunBackTest(series);

        NLodBy30mPeriodAfterBreakingHighOfPeriodStatsTest nLodBy30mPeriodAfterBreakingHighOfPeriodStatsTest =
                new NLodBy30mPeriodAfterBreakingHighOfPeriodStatsTest();
        nLodBy30mPeriodAfterBreakingHighOfPeriodStatsTest.evaluate();
    }

    public static class PeriodNlodResultsMap {
        public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodContinuationResultsMap = new LinkedHashMap<>();
        public static Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodReversalAfterResultsMap = new LinkedHashMap<>();

        public PeriodNlodResultsMap() {
            periodContinuationResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodContinuationResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());

            periodReversalAfterResultsMap.put(Period30m.A, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.B, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.C, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.D, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.E, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.F, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.G, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.H, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.I, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.J, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.K, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.L, new TrueFalseDailyMgiAndPeriodOhlcResults());
            periodReversalAfterResultsMap.put(Period30m.M, new TrueFalseDailyMgiAndPeriodOhlcResults());
        }

        public void addToContinuationIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodContinuationResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodContinuationResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToContinuationIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodContinuationResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodContinuationResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToReversalIsTrueMap(Period30m period30m, OHLCIndicator nhod30mOhlc, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodReversalAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToTrueMap(dailyMgi, nhod30mOhlc);
            periodReversalAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public void addToReversalIsFalseMap(Period30m period30m, DailyMgi dailyMgi) {
            TrueFalseDailyMgiAndPeriodOhlcResults trueFalseDailyMgiResults = periodReversalAfterResultsMap.get(period30m);
            trueFalseDailyMgiResults.addToFalseMap(dailyMgi, null);
            periodReversalAfterResultsMap.put(period30m, trueFalseDailyMgiResults);
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodContinuationResultsMap() {
            return periodContinuationResultsMap;
        }

        public Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> getPeriodReversalAfterResultsMap() {
            return periodReversalAfterResultsMap;
        }

        public void setPeriodContinuationAfterResultsMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNhodAfterResultsMap) {
            PeriodNlodResultsMap.periodContinuationResultsMap = periodNhodAndNhodAfterResultsMap;
        }

        public void setPeriodReversalAfterResultsMap(Map<Period30m, TrueFalseDailyMgiAndPeriodOhlcResults> periodNhodAndNlodAfterResultsMap) {
            PeriodNlodResultsMap.periodReversalAfterResultsMap = periodNhodAndNlodAfterResultsMap;
        }
    }
}

package nate.stats;

import nate.stats.domain.TrueFalseDailyMgiResults;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.nate.OHLCIndicator;
import org.ta4j.core.indicators.nate.PreInPostOHLCIndicator;
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

public class NLodBy30mPeriodStatsTest extends StatsTest {

    enum Period {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11), M(12);

        Period(int i) {
        }


    }

    @Override
    void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateNLodStats(dailyMgiMap);
    }

    private void evaluateNLodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;

        PeriodNlodResultsMap periodNlodResultsMap = calculatePeriodHodStats(dailyMgiMap);
        periodResultsMap(periodNlodResultsMap);
    }


    private void periodResultsMap(PeriodNlodResultsMap periodResultsMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("% Chance for NLod after Period,");
        periodResultsMap.getPeriodNlodAndNlodAfterResultsMap().forEach((period, trueFalseDailyMgiResults) -> {
//            trueFalseDailyMgiResults.getFalseDailyMgiList().forEach(s -> {
//                System.out.println(period + " period false dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            trueFalseDailyMgiResults.getTrueDailyMgiList().forEach(s -> {
//                System.out.println(period + " period true dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            System.out.println(period + " total count: " + trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists());
            double percentTrue = (double) trueFalseDailyMgiResults.getTrueDailyMgiList().size() / (double) trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists();
            sb.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb.append(",");
        });

        String line = sb.substring(0, sb.length() - 1);
        System.out.println(line);

        StringBuilder sb2 = new StringBuilder();
        sb2.append("% Chance for NHod after Period,");
        periodResultsMap.getPeriodNlodAndNhodAfterResultsMap().forEach((period, trueFalseDailyMgiResults) -> {
//            trueFalseDailyMgiResults.getFalseDailyMgiList().forEach(s -> {
//                System.out.println(period + " period false dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            trueFalseDailyMgiResults.getTrueDailyMgiList().forEach(s -> {
//                System.out.println(period + " period true dates: " + s.getRthOhlc().getOpen().getDate());
//            });
//            System.out.println(period + " total count: " + trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists());
            double percentTrue = (double) trueFalseDailyMgiResults.getTrueDailyMgiList().size() / (double) trueFalseDailyMgiResults.getTotalOfTrueAndFalseLists();
            sb2.append(DoubleFormatter.formatPercent(percentTrue, 0));
            sb2.append(",");
        });

        String line2 = sb2.substring(0, sb2.length() - 1);
        System.out.println(line2);
    }

    private PeriodNlodResultsMap calculatePeriodHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        PeriodNlodResultsMap periodNLODResultsMap = new PeriodNlodResultsMap();

        dailyMgiMap.forEach((date, dailyMgi) -> {
            // Map of 30m period to post-30m period
            Map<Period, PreInPostOHLCIndicator> periodsMap = new LinkedHashMap<>();
            periodsMap.put(Period.A, new PreInPostOHLCIndicator(dailyMgi.getPreAPeriodOhlc(), dailyMgi.getaPeriodOhlc(), dailyMgi.getPostAPeriodOhlc()));
            periodsMap.put(Period.B, new PreInPostOHLCIndicator(dailyMgi.getPreBPeriodOhlc(), dailyMgi.getbPeriodOhlc(), dailyMgi.getPostBPeriodOhlc()));
            periodsMap.put(Period.C, new PreInPostOHLCIndicator(dailyMgi.getPreCPeriodOhlc(), dailyMgi.getcPeriodOhlc(), dailyMgi.getPostCPeriodOhlc()));
            periodsMap.put(Period.D, new PreInPostOHLCIndicator(dailyMgi.getPreDPeriodOhlc(), dailyMgi.getdPeriodOhlc(), dailyMgi.getPostDPeriodOhlc()));
            periodsMap.put(Period.E, new PreInPostOHLCIndicator(dailyMgi.getPreEPeriodOhlc(), dailyMgi.getePeriodOhlc(), dailyMgi.getPostEPeriodOhlc()));
            periodsMap.put(Period.F, new PreInPostOHLCIndicator(dailyMgi.getPreFPeriodOhlc(), dailyMgi.getfPeriodOhlc(), dailyMgi.getPostFPeriodOhlc()));
            periodsMap.put(Period.G, new PreInPostOHLCIndicator(dailyMgi.getPreGPeriodOhlc(), dailyMgi.getgPeriodOhlc(), dailyMgi.getPostGPeriodOhlc()));
            periodsMap.put(Period.H, new PreInPostOHLCIndicator(dailyMgi.getPreHPeriodOhlc(), dailyMgi.gethPeriodOhlc(), dailyMgi.getPostHPeriodOhlc()));
            periodsMap.put(Period.I, new PreInPostOHLCIndicator(dailyMgi.getPreIPeriodOhlc(), dailyMgi.getiPeriodOhlc(), dailyMgi.getPostIPeriodOhlc()));
            periodsMap.put(Period.J, new PreInPostOHLCIndicator(dailyMgi.getPreJPeriodOhlc(), dailyMgi.getjPeriodOhlc(), dailyMgi.getPostJPeriodOhlc()));
            periodsMap.put(Period.K, new PreInPostOHLCIndicator(dailyMgi.getPreKPeriodOhlc(), dailyMgi.getkPeriodOhlc(), dailyMgi.getPostKPeriodOhlc()));
            periodsMap.put(Period.L, new PreInPostOHLCIndicator(dailyMgi.getPreLPeriodOhlc(), dailyMgi.getlPeriodOhlc(), dailyMgi.getPostLPeriodOhlc()));
            periodsMap.put(Period.M, new PreInPostOHLCIndicator(dailyMgi.getPreMPeriodOhlc(), dailyMgi.getmPeriodOhlc(), dailyMgi.getPostMPeriodOhlc()));


            periodsMap.forEach((period, preInPostOhlc) -> {
                if (period != Period.A && period != Period.M) {
                    OHLCIndicator prePeriodOhlc = preInPostOhlc.getPreOhlc();
                    OHLCIndicator periodOhlc = preInPostOhlc.getOhlc();
                    OHLCIndicator postPeriodOhlc = preInPostOhlc.getPostOhlc();

                    if (prePeriodOhlc.getLow() == null || periodOhlc.getLow() == null || postPeriodOhlc.getLow() == null) {
//                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    } else if (!prePeriodOhlc.getLow().getDate().isEqual(periodOhlc.getLow().getDate()) || !periodOhlc.getLow().getDate().isEqual(postPeriodOhlc.getLow().getDate())) {
                        // Dates do not match. Most likely a shortened trading day or bad data.
//                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    }

                    if (periodOhlc.getLow().getPrice().isLessThan(prePeriodOhlc.getLow().getPrice())) {
                        // New LoD in this period
                        if (postPeriodOhlc.getLow().getPrice().isLessThan(periodOhlc.getLow().getPrice())) {
                            // New LoD after this period
                            periodNLODResultsMap.addToNlodAfterIsTrueMap(period, dailyMgi);
//                            //todo remove
//                            if (period == NLodBy30mPeriodStatsTest.Period.E) {
//                                System.out.println("E Period TRUE Date: " + dailyMgi.getRthOhlc().getOpen().getDate());
//                            }
                        } else {
                            // No new Hod after this period
                            periodNLODResultsMap.addToNlodAfterIsFalseMap(period, dailyMgi);
//                            //todo remove
//                            if (period == NLodBy30mPeriodStatsTest.Period.E) {
//                                System.out.println("E Period FALSE Date: " + dailyMgi.getRthOhlc().getOpen().getDate());
//                            }
                        }

                        if (postPeriodOhlc.getHigh().getPrice().isGreaterThan(periodOhlc.getHigh().getPrice()) &&
                                postPeriodOhlc.getHigh().getPrice().isGreaterThan(prePeriodOhlc.getHigh().getPrice())) {
                            // New HOD after period
                            periodNLODResultsMap.addToNhodAfterIsTrueMap(period, dailyMgi);
                        } else {
                            periodNLODResultsMap.addToNhodAfterIsFalseMap(period, dailyMgi);
                        }
                    }
                }
            });
        });
        return periodNLODResultsMap;
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);

        NLodBy30mPeriodStatsTest nHodBy30mPeriodStatsTest = new NLodBy30mPeriodStatsTest();
        nHodBy30mPeriodStatsTest.evaluate();
    }

    private class PeriodNlodResultsMap {
        Map<Period, TrueFalseDailyMgiResults> periodNlodAndNlodAfterResultsMap = new LinkedHashMap<>();
        Map<Period, TrueFalseDailyMgiResults> periodNlodAndNhodAfterResultsMap = new LinkedHashMap<>();

        public PeriodNlodResultsMap() {
            periodNlodAndNlodAfterResultsMap.put(Period.A, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.B, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.C, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.D, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.E, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.F, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.G, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.H, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.I, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.J, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.K, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.L, new TrueFalseDailyMgiResults());
            periodNlodAndNlodAfterResultsMap.put(Period.M, new TrueFalseDailyMgiResults());

            periodNlodAndNhodAfterResultsMap.put(Period.A, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.B, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.C, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.D, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.E, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.F, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.G, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.H, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.I, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.J, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.K, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.L, new TrueFalseDailyMgiResults());
            periodNlodAndNhodAfterResultsMap.put(Period.M, new TrueFalseDailyMgiResults());
        }

        public void addToNlodAfterIsTrueMap(Period period, DailyMgi dailyMgi) {
            TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period);
            trueFalseDailyMgiResults.addToTrueDailyMgiList(dailyMgi);
            periodNlodAndNlodAfterResultsMap.put(period, trueFalseDailyMgiResults);
        }
        public void addToNlodAfterIsFalseMap(Period period, DailyMgi dailyMgi) {
            TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNlodAndNlodAfterResultsMap.get(period);
            trueFalseDailyMgiResults.addToFalseDailyMgiList(dailyMgi);
            periodNlodAndNlodAfterResultsMap.put(period, trueFalseDailyMgiResults);
        }

        public void addToNhodAfterIsTrueMap(Period period, DailyMgi dailyMgi) {
            TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNlodAndNhodAfterResultsMap.get(period);
            trueFalseDailyMgiResults.addToTrueDailyMgiList(dailyMgi);
            periodNlodAndNhodAfterResultsMap.put(period, trueFalseDailyMgiResults);
        }
        public void addToNhodAfterIsFalseMap(Period period, DailyMgi dailyMgi) {
            TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNlodAndNhodAfterResultsMap.get(period);
            trueFalseDailyMgiResults.addToFalseDailyMgiList(dailyMgi);
            periodNlodAndNhodAfterResultsMap.put(period, trueFalseDailyMgiResults);
        }


        public Map<Period, TrueFalseDailyMgiResults> getPeriodNlodAndNlodAfterResultsMap() {
            return periodNlodAndNlodAfterResultsMap;
        }

        public Map<Period, TrueFalseDailyMgiResults> getPeriodNlodAndNhodAfterResultsMap() {
            return periodNlodAndNhodAfterResultsMap;
        }
    }
}

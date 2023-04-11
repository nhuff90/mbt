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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NHodBy30mPeriodStatsTest extends StatsTest {

    enum Period {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11), M(12);

        Period(int i) {
        }


    }

    @Override
    void evaluate() {
        Map<LocalDate, DailyMgi> dailyMgiMap = DailyMgiBuyRule.getHistoricalDailyMgi();
        evaluateNHodStats(dailyMgiMap);
    }

    private void evaluateNHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        List<TrueFalseDailyMgiResults> trueFalseDailyMgiResultsList;

        Map<Period, TrueFalseDailyMgiResults> periodResultsMap = calculatePeriodHodStats(dailyMgiMap);
        periodResultsMap(periodResultsMap);
    }


    private void periodResultsMap(Map<Period, TrueFalseDailyMgiResults> periodResultsMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("% Chance for NHod after Period,");
        periodResultsMap.forEach((period, trueFalseDailyMgiResults) -> {
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
    }

    private Map<Period, TrueFalseDailyMgiResults> calculatePeriodHodStats(Map<LocalDate, DailyMgi> dailyMgiMap) {

        Map<Period, TrueFalseDailyMgiResults> periodNHODResultsMap = new LinkedHashMap<>();
        periodNHODResultsMap.put(Period.A, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.B, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.C, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.D, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.E, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.F, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.G, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.H, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.I, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.J, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.K, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.L, new TrueFalseDailyMgiResults());
        periodNHODResultsMap.put(Period.M, new TrueFalseDailyMgiResults());

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

                    if (prePeriodOhlc.getHigh() == null || periodOhlc.getHigh() == null || postPeriodOhlc.getHigh() == null) {
                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    } else if (!prePeriodOhlc.getHigh().getDate().isEqual(periodOhlc.getHigh().getDate()) || !periodOhlc.getHigh().getDate().isEqual(postPeriodOhlc.getHigh().getDate())) {
                        // Dates do not match. Most likely a shortened trading day or bad data.
                        System.out.println("Bad date: " + dailyMgi.getRthOhlc().getOpen().getDate());
                        return;
                    }

                    if (periodOhlc.getHigh().getPrice().isGreaterThan(prePeriodOhlc.getHigh().getPrice())) {
                        // New HoD in this period
                        TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNHODResultsMap.get(period);
                        if (postPeriodOhlc.getHigh().getPrice().isGreaterThan(periodOhlc.getHigh().getPrice())) {
                            // New HoD after this period
                            trueFalseDailyMgiResults.addToTrueDailyMgiList(dailyMgi);
//                            //todo remove
//                            if (period == NHodBy30mPeriodStatsTest.Period.E) {
//                                System.out.println("E Period TRUE Date: " + dailyMgi.getRthOhlc().getOpen().getDate());
//                            }
                        } else {
                            // No new Hod after this period
                            trueFalseDailyMgiResults.addToFalseDailyMgiList(dailyMgi);
//                            //todo remove
//                            if (period == NHodBy30mPeriodStatsTest.Period.E) {
//                                System.out.println("E Period FALSE Date: " + dailyMgi.getRthOhlc().getOpen().getDate());
//                            }
                        }
                        periodNHODResultsMap.put(period, trueFalseDailyMgiResults);
                    }
                }
            });
        });
        return periodNHODResultsMap;
    }


    public static void main(String[] args) throws InterruptedException {
        // Getting a bar series (from any provider: CSV, web service, etc.)

//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 12), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
//                ZonedDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")),
//                ZonedDateTime.of(LocalDate.of(2021, 12, 31), LocalTime.of(16, 00), ZoneId.of("America/New_York")));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear(ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(9, 30), ZoneId.of("America/New_York")));
        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        createRulesAndRunBackTest(series);

        NHodBy30mPeriodStatsTest nHodBy30mPeriodStatsTest = new NHodBy30mPeriodStatsTest();
        nHodBy30mPeriodStatsTest.evaluate();
    }

    private class periodNhodResultsMap {
        Map<Period, TrueFalseDailyMgiResults> periodNhodAndNhodAfterResultsMap;
        Map<Period, TrueFalseDailyMgiResults> periodNhodAndNlodAfterResultsMap;

        public periodNhodResultsMap() {
            periodNhodAndNhodAfterResultsMap.put(Period.A, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.B, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.C, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.D, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.E, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.F, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.G, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.H, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.I, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.J, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.K, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.L, new TrueFalseDailyMgiResults());
            periodNhodAndNhodAfterResultsMap.put(Period.M, new TrueFalseDailyMgiResults());

            periodNhodAndNlodAfterResultsMap.put(Period.A, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.B, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.C, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.D, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.E, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.F, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.G, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.H, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.I, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.J, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.K, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.L, new TrueFalseDailyMgiResults());
            periodNhodAndNlodAfterResultsMap.put(Period.M, new TrueFalseDailyMgiResults());
        }

        public void addToNhodAfterTrueMap(Period period, DailyMgi dailyMgi) {
            TrueFalseDailyMgiResults trueFalseDailyMgiResults = periodNhodAndNhodAfterResultsMap.get(period);
        }
    }
}

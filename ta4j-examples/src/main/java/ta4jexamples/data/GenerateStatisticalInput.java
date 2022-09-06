package ta4jexamples.data;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.utils.DoubleFormatter;
import org.ta4j.core.utils.MapUtils;
import ta4jexamples.loaders.CsvBarsLoader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class GenerateStatisticalInput {

    /**
     * Reads in data and prints out a CSV file with specific data
     * @param args
     */
    public static void main(String[] args) {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries( ZonedDateTime.of ( LocalDate.of ( 2022, 6, 29 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesBetweenYears(
                ZonedDateTime.of ( LocalDate.of ( 2020, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        System.out.println(series);

        CsvBarsLoader.writeDailyInfoToCSV(populateDayInformation(series));

//        printResults(populateDayInformation(series));

    }

//    private static void printResults(List<DailyInformation> populatedDayInformation) {
//
//        double backToCenterTotalCount = 0;
//        double backToCenterYesCount = 0;
//        List<Double> highToLowList = new ArrayList<>();
//        List<Double> pmStartPriceToLowList = new ArrayList<>();
//
//        List<Double> PMEndPriceToLowList = new ArrayList<>();
//        List<Double> percentPmEndRangeList = new ArrayList<>();
//        List<Double> priceChangeWithinPmRangeList = new ArrayList<>();
//        List<Double> ClosePriceVsPmStartList = new ArrayList<>();
//        List<Double> avgPercentMoveRelToOpenList = new ArrayList<>();
//        List<Double> avgPercentCloseMoveRelToPmStartList = new ArrayList<>();
//
//
//        List<Double> percentPmStartMinusPmEndRangeList20 = new ArrayList<>();
//        List<Double> percentPmStartMinusPmEndRangeList40 = new ArrayList<>();
//        List<Double> percentPmStartMinusPmEndRangeList60 = new ArrayList<>();
//        List<Double> percentPmStartMinusPmEndRangeList80 = new ArrayList<>();
//        List<Double> percentPmStartMinusPmEndRangeList100 = new ArrayList<>();
//
//
//        // Correlation vars
//        List<Double> percentPmStartMinusPmEndRangeList = new ArrayList<>();
//        List<Double> percentPmStartRangeList = new ArrayList<>();
//
//        List<Double> percentPmMoveRelToOpenAbove80List = new ArrayList<>();
//        List<Double> percentPmMoveRelToOpenBelow40List = new ArrayList<>();
//        for (DailyInformation dailyInformation : populatedDayInformation) {
//            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
//            && dailyInformation.getPMRangeOpenPrice()!= null && dailyInformation.getPMRangeClosePrice() != null) {
//                double rthToPMHigh = dailyInformation.getRTHToPMHighPrice().doubleValue();
//                double rthToPMLow = dailyInformation.getRTHToPMLowPrice().doubleValue();
//                double rthToPmRange = rthToPMHigh - rthToPMLow;
//                highToLowList.add(rthToPmRange);
//
//                double pmStartPriceToRthLow = dailyInformation.getPMStartPriceToRthLow();
//                pmStartPriceToLowList.add(pmStartPriceToRthLow);
//                double percentPMStartRange = pmStartPriceToRthLow / rthToPmRange;
//                percentPmStartRangeList.add(percentPMStartRange);
//
//                double pmEndPriceToRthLow = dailyInformation.getPMEndPriceToRthLow();
//                PMEndPriceToLowList.add(pmEndPriceToRthLow);
//                double percentPMEndRange = pmEndPriceToRthLow / rthToPmRange;
//                percentPmEndRangeList.add(percentPMEndRange);
//
//                percentPmStartMinusPmEndRangeList.add(percentPMStartRange-percentPMEndRange);
//
//                double priceChangeInPmRange = dailyInformation.getPMRangeOpenPrice().doubleValue() - dailyInformation.getPMRangeClosePrice().doubleValue();
//                priceChangeWithinPmRangeList.add(priceChangeInPmRange);
//
//                double closePriceVsPmStart = dailyInformation.getDailyClosePrice().doubleValue() - dailyInformation.getPMRangeOpenPrice().doubleValue();
//                ClosePriceVsPmStartList.add(closePriceVsPmStart);
//
//                double avgPercentMoveRelToOpenPrice = priceChangeInPmRange / dailyInformation.getDailyOpenPrice().doubleValue();
//                avgPercentMoveRelToOpenList.add(avgPercentMoveRelToOpenPrice);
//
//                double avgPercentCloseMoveRelToPmStart = dailyInformation.getPMRangeClosePrice().doubleValue() / dailyInformation.getPMRangeOpenPrice().doubleValue();
//                avgPercentCloseMoveRelToPmStartList.add(avgPercentCloseMoveRelToPmStart);
//
////                System.out.println((percentPMStartRange*100) + " " + (avgPercentMoveRelToOpenPrice*100));
//
//                if (percentPMStartRange >= 0.8) {
//                    percentPmMoveRelToOpenAbove80List.add(avgPercentMoveRelToOpenPrice);
//                }
//
//                if (percentPMStartRange <= 0.4) {
//                    percentPmMoveRelToOpenBelow40List.add(avgPercentMoveRelToOpenPrice);
//                }
//
//
//                if (dailyInformation.getRTHToPMAtPercentilePrice(0.0) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.2) != null
//                        && dailyInformation.getPMRangeOpenPrice() != null) {
//                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.0).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
//                            && dailyInformation.getRTHToPMAtPercentilePrice(0.2).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
//                        percentPmStartMinusPmEndRangeList20.add(percentPMStartRange-percentPMEndRange);
//                    }
//                }
//
//                if (dailyInformation.getRTHToPMAtPercentilePrice(0.2) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.4) != null
//                        && dailyInformation.getPMRangeOpenPrice() != null) {
//                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.2).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
//                            && dailyInformation.getRTHToPMAtPercentilePrice(0.4).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
//                        percentPmStartMinusPmEndRangeList40.add(percentPMStartRange-percentPMEndRange);
//                    }
//                }
//
//                if (dailyInformation.getRTHToPMAtPercentilePrice(0.4) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.6) != null
//                        && dailyInformation.getPMRangeOpenPrice() != null) {
//                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.4).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
//                            && dailyInformation.getRTHToPMAtPercentilePrice(0.6).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
//                        percentPmStartMinusPmEndRangeList60.add(percentPMStartRange-percentPMEndRange);
//                    }
//                }
//
//                if (dailyInformation.getRTHToPMAtPercentilePrice(0.6) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.8) != null
//                        && dailyInformation.getPMRangeOpenPrice() != null) {
//                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.6).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
//                            && dailyInformation.getRTHToPMAtPercentilePrice(0.8).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
//                        percentPmStartMinusPmEndRangeList80.add(percentPMStartRange-percentPMEndRange);
//                    }
//                }
//
//                if (dailyInformation.getRTHToPMAtPercentilePrice(0.8) != null && dailyInformation.getRTHToPMAtPercentilePrice(1.0) != null
//                        && dailyInformation.getPMRangeOpenPrice() != null) {
//                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.8).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
//                            && dailyInformation.getRTHToPMAtPercentilePrice(1.0).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
//                        percentPmStartMinusPmEndRangeList100.add(percentPMStartRange-percentPMEndRange);
//                    }
//                }
//
//
//
//
//
//                            // 2. Good Chance we go back to center?
//            if (dailyInformation.getRTHToPMCenterPrice() != null && dailyInformation.getPMRangeOpenPrice() != null) {
//                double prePmRangeCenter = dailyInformation.getRTHToPMCenterPrice().doubleValue();
//                double pmOpen = dailyInformation.getPMRangeOpenPrice().doubleValue();
//
//                if (pmOpen >= prePmRangeCenter) {
//                    // opened above prePM Center
//                    backToCenterTotalCount++;
//                    if (dailyInformation.getPMLowPrice().doubleValue() < prePmRangeCenter) {
//                        backToCenterYesCount++;
//                    }
//                } else if (pmOpen < prePmRangeCenter) {
//                    // opened below prePM Center
//                    backToCenterTotalCount++;
//                    if (dailyInformation.getPMHighPrice().doubleValue() > prePmRangeCenter) {
//                        backToCenterYesCount++;
//                    }
//                }
//            }
//            }
//        }
//
//
//        double standardDeviationOfPercentMoves = calculateSD(percentPmStartMinusPmEndRangeList);
//
////        System.out.println("Average Movement in PM Range: " + DoubleFormatter.formatDollar(priceChangeWithinPmRangeList.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("% Chance we move back to center in PM Range: " + DoubleFormatter.formatPercent(backToCenterYesCount/backToCenterTotalCount));
////        System.out.println("What is the average move % within the PM range?: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("What is the average move % if the PM range is at 0-20%: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList20.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("What is the average move % if the PM range is at 20-40%: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList40.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("What is the average move % if the PM range is at 40-60%: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList60.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("What is the average move % if the PM range is at 60-80%: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList80.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
////        System.out.println("What is the average move % if the PM range is at 80-100%: " + DoubleFormatter.formatPercent(percentPmStartMinusPmEndRangeList100.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN)));
//
//
////        System.out.println("Mean of % move in PM Range: " + mean);
////        System.out.println("Standard Deviation of % move in PM Range: " + DoubleFormatter.formatPercent(standardDeviationOfPercentMoves));
//
//
//
//
//
////    for (Double num : percentPmStartMinusPmEndRangeList) {
////        System.out.println(num);
////    }
////        for (Double num : percentPmStartRangeList) {
////        System.out.println(num);
////    }
//
//        System.out.println("Average % Move when PM start range is above 80%: " + DoubleFormatter.formatPercent(percentPmMoveRelToOpenAbove80List.stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(Double.NaN)));
//        System.out.println("Average % Move when PM start range is below 40%: " + DoubleFormatter.formatPercent(percentPmMoveRelToOpenBelow40List.stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(Double.NaN)));
//
//
//
//
//
//
////        double pmMoveSum = 0;
////        int pmMoveCount = 0;
////        double backToCenterTotalCount = 0;
////        double backToCenterYesCount = 0;
////        List<Double> pmPercentsOfRthToPMRange = new ArrayList<>();
////        List<Double> pmPercentsOfRthToPMRange20Percent = new ArrayList<>();
////        List<Double> pmPercentsOfRthToPMRange40Percent = new ArrayList<>();
////        List<Double> pmPercentsOfRthToPMRange60Percent = new ArrayList<>();
////        List<Double> pmPercentsOfRthToPMRange80Percent = new ArrayList<>();
////        List<Double> pmPercentsOfRthToPMRange100Percent = new ArrayList<>();
////        for (DailyInformation dailyInformation : populatedDayInformation) {
////            // 1. Average Movement in PM Range
////            if (dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                pmMoveSum += dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                pmMoveCount++;
////            }
////
////
////            // 2. Good Chance we go back to center?
////            if (dailyInformation.getRTHToPMCenterPrice() != null && dailyInformation.getPMRangeOpenPrice() != null) {
////                double prePmRangeCenter = dailyInformation.getRTHToPMCenterPrice().doubleValue();
////                double pmOpen = dailyInformation.getPMRangeOpenPrice().doubleValue();
////
////                if (pmOpen >= prePmRangeCenter) {
////                    // opened above prePM Center
////                    backToCenterTotalCount++;
////                    if (dailyInformation.getPMLowPrice().doubleValue() < prePmRangeCenter) {
////                        backToCenterYesCount++;
////                    }
////                } else if (pmOpen < prePmRangeCenter) {
////                    // opened below prePM Center
////                    backToCenterTotalCount++;
////                    if (dailyInformation.getPMHighPrice().doubleValue() > prePmRangeCenter) {
////                        backToCenterYesCount++;
////                    }
////                }
////            }
////
////
////            // 3. Given the % results of the PM range starts, what is the distribution? Show the histogram and summary statistics (mean/standard deviation)
////            // todo
////
////
////            // 4. What is the average move % within the PM range? -- % PM Range compared to rthToPm range?
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                double rthToPMRangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                double pMRangeSize = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                pmPercentsOfRthToPMRange.add(pMRangeSize / rthToPMRangeSize);
////            }
////
////
////            //What is the average move % if the PM range is at 0-20%
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                if (dailyInformation.getRTHToPMAtPercentilePrice(0.0) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.2) != null
////                        && dailyInformation.getPMRangeOpenPrice() != null) {
////                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.0).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
////                            && dailyInformation.getRTHToPMAtPercentilePrice(0.2).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
////                        double rthToPM20RangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                        double pMRange20Size = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                        pmPercentsOfRthToPMRange20Percent.add(pMRange20Size / rthToPM20RangeSize);
////                    }
////                }
////            }
////
////
////            //What is the average move % if the PM range is at 20-40%
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                if (dailyInformation.getRTHToPMAtPercentilePrice(0.2) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.4) != null
////                        && dailyInformation.getPMRangeOpenPrice() != null) {
////                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.2).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
////                            && dailyInformation.getRTHToPMAtPercentilePrice(0.4).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
////                        double rthToPM20RangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                        double pMRange20Size = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                        pmPercentsOfRthToPMRange40Percent.add(pMRange20Size / rthToPM20RangeSize);
////                    }
////                }
////            }
////
////            //What is the average move % if the PM range is at 40-60%
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                if (dailyInformation.getRTHToPMAtPercentilePrice(0.4) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.6) != null
////                        && dailyInformation.getPMRangeOpenPrice() != null) {
////                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.4).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
////                            && dailyInformation.getRTHToPMAtPercentilePrice(0.6).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
////                        double rthToPM20RangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                        double pMRange20Size = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                        pmPercentsOfRthToPMRange60Percent.add(pMRange20Size / rthToPM20RangeSize);
////                    }
////                }
////            }
////
////            //What is the average move % if the PM range is at 60-80%
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                if (dailyInformation.getRTHToPMAtPercentilePrice(0.6) != null && dailyInformation.getRTHToPMAtPercentilePrice(0.8) != null
////                        && dailyInformation.getPMRangeOpenPrice() != null) {
////                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.6).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
////                            && dailyInformation.getRTHToPMAtPercentilePrice(0.8).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
////                        double rthToPM20RangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                        double pMRange20Size = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                        pmPercentsOfRthToPMRange80Percent.add(pMRange20Size / rthToPM20RangeSize);
////                    }
////                }
////            }
////
////            //What is the average move % if the PM range is at 80-100%
////            if (dailyInformation.getRTHToPMHighPrice() != null && dailyInformation.getRTHToPMLowPrice() != null
////                    && dailyInformation.getPMHighPrice() != null && dailyInformation.getPMLowPrice() != null) {
////                if (dailyInformation.getRTHToPMAtPercentilePrice(0.8) != null && dailyInformation.getRTHToPMAtPercentilePrice(1.0) != null
////                        && dailyInformation.getPMRangeOpenPrice() != null) {
////                    if (dailyInformation.getRTHToPMAtPercentilePrice(0.8).doubleValue() < dailyInformation.getPMRangeOpenPrice().doubleValue()
////                            && dailyInformation.getRTHToPMAtPercentilePrice(1.0).doubleValue() > dailyInformation.getPMRangeOpenPrice().doubleValue()) {
////                        double rthToPM20RangeSize = dailyInformation.getRTHToPMHighPrice().minus(dailyInformation.getRTHToPMLowPrice()).doubleValue();
////                        double pMRange20Size = dailyInformation.getPMHighPrice().minus(dailyInformation.getPMLowPrice()).doubleValue();
////                        pmPercentsOfRthToPMRange100Percent.add(pMRange20Size / rthToPM20RangeSize);
////                    }
////                }
////            }
////        }
////
////        double avgPmMove = pmMoveSum/pmMoveCount;
////        System.out.println("Average Movement in PM Range: " + avgPmMove);
////        System.out.println("% Chance we move back to center in PM Range: " + backToCenterYesCount/backToCenterTotalCount);
////        System.out.println("What is the average move % within the PM range: " + pmPercentsOfRthToPMRange.stream().mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
////        System.out.println("What is the average move % if the PM range is at 0-20%: " + pmPercentsOfRthToPMRange20Percent.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
////        System.out.println("What is the average move % if the PM range is at 20-40%: " + pmPercentsOfRthToPMRange40Percent.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
////        System.out.println("What is the average move % if the PM range is at 40-60%: " + pmPercentsOfRthToPMRange60Percent.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
////        System.out.println("What is the average move % if the PM range is at 60-80%: " + pmPercentsOfRthToPMRange80Percent.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
////        System.out.println("What is the average move % if the PM range is at 80-100%: " + pmPercentsOfRthToPMRange100Percent.stream()
////                .mapToDouble(Double::doubleValue)
////                .average()
////                .orElse(Double.NaN));
//
//    }


    private static List<DailyInformation> populateDayInformation(BarSeries series) {
        List<DailyInformation> dailyInformationList = new ArrayList<>();
        Map<LocalDate, List<Bar>> dateBarMap = MapUtils.getMap(series.getBars(), Bar::getLocalDate, LinkedHashMap::new, ArrayList::new);
        for (Map.Entry<LocalDate, List<Bar>> entry : dateBarMap.entrySet()) {
            dailyInformationList.add(new DailyInformation(entry.getKey(), entry.getValue()));
        }

        return dailyInformationList;
    }

    public static double calculateSD(List<Double> numArray)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.size();

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    private static void printPercentiles(List<Double> array) {
        Collections.sort(array);
        int percentile20 = (int) (array.size() * 0.2);
        int percentile40 = (int) (array.size() * 0.4);
        int percentile60 = (int) (array.size() * 0.6);
        int percentile80 = (int) (array.size() * 0.8);

        System.out.println("0 Percentile: " + array.get(0));
        System.out.println("20 Percentile: " + array.get(percentile20));
        System.out.println("40 Percentile: " + array.get(percentile40));
        System.out.println("60 Percentile: " + array.get(percentile60));
        System.out.println("80 Percentile: " + array.get(percentile80));
        System.out.println("100 Percentile: " + array.get(array.size()-1));
    }
}

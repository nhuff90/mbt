package ta4jexamples.data;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
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
//        BarSeries series = CsvBarsLoader.loadEs1MinSeries();
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 7, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2017, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
        BarSeries series = CsvBarsLoader.loadEs1MinSeriesBetweenYears(
                ZonedDateTime.of ( LocalDate.of ( 2017, 1, 6 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        System.out.println(series);

        CsvBarsLoader.writeDailyInfoToCSV(populateDayInformation(series));

    }


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

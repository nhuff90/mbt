package ta4jexamples.data;

import com.opencsv.CSVReader;
import ta4jexamples.loaders.CsvBarsLoader;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsolidateDataFiles {

    /**
     * Reads in data and prints out a CSV file with specific data
     * @param args
     */
    public static void main(String[] args) {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries seriesSMA = CsvBarsLoader.loadEs1MinSeriesFromSmaApp( ZonedDateTime.of ( LocalDate.of ( 2022, 7, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries seriesJay = CsvBarsLoader.loadAllEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 7, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2017, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesBetweenYears(
//                ZonedDateTime.of ( LocalDate.of ( 2017, 1, 6 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
//                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        System.out.println(series);

        Map<ZonedDateTime, Candle> map2 = loadESHistoricalCsvSeriesBetweenYears("es_1min_historical_data.csv", "es-1min", null, null);
        Map<ZonedDateTime, Candle> map1 = loadCsvSeries("es-1min.csv", "es-1min", null);
        Map<ZonedDateTime, Candle> mergedMap = mergeMaps(map1,map2);
        writeDailyInfoToCSV(mergedMap);

    }

    private static Map<ZonedDateTime, Candle> mergeMaps(Map<ZonedDateTime, Candle> map1, Map<ZonedDateTime, Candle> map2) {
        Map<ZonedDateTime, Candle> map3 = new TreeMap<>(map1);
        map2.forEach(
                (key, value) -> map3.merge(key, value, (v1, v2) -> v2));
        return map3;
    }

    public static void writeDailyInfoToCSV(Map<ZonedDateTime, Candle> map) {
        String outputFileName = "C:\\workspace\\nate\\mbt\\ta4j-examples\\src\\main\\resources\\es-1min-master.csv";
        CsvBarsLoader.clearFileContents(outputFileName);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

        String str = "date, time, open, high, low, close, volume\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
            writer.append(str);

            for (Map.Entry<ZonedDateTime, Candle> entry : map.entrySet()) {
                writer.append(entry.getKey().format(formatter2)
                        + ", " + entry.getValue().getOpen()
                        + ", " + entry.getValue().getHigh()
                        + ", " + entry.getValue().getLow()
                        + ", " + entry.getValue().getClose()
                        + ", " + entry.getValue().getVolume()
                        + "\n"
                );

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<ZonedDateTime, Candle> loadESHistoricalCsvSeriesBetweenYears(String filename, String barSeriesName, ZonedDateTime startDate, ZonedDateTime endDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        Map<ZonedDateTime, Candle> candleMapByDateTime = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
//                ZonedDateTime date = formatDateAndTime(line[0], line[1]);
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
                if (((startDate == null) || (date.getYear() >= startDate.getYear()))
                        && ((endDate == null) || (date.getYear() <= endDate.getYear()))) {
                    double open = Double.parseDouble(line[2].trim());
                    double high = Double.parseDouble(line[3].trim());
                    double low = Double.parseDouble(line[4].trim());
                    double close = Double.parseDouble(line[5].trim());
                    int volume = Integer.parseInt(line[6].trim());

                    candleMapByDateTime.put(date, new Candle(date, open, high, low, close, volume));
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }
        return candleMapByDateTime;
    }

    private static Map<ZonedDateTime, Candle> loadCsvSeries(String filename, String barSeriesName, ZonedDateTime filteredDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

//        BarSeries series = new BaseBarSeries(barSeriesName);
        Map<ZonedDateTime, Candle> candleMapByDateTime = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
                if ((filteredDate == null) || (date.getYear() == filteredDate.getYear() && date.getMonthValue() == filteredDate.getMonthValue() &&
                        date.getDayOfYear() == filteredDate.getDayOfYear())) {
                    double open = Double.parseDouble(line[2].trim());
                    double high = Double.parseDouble(line[3].trim());
                    double low = Double.parseDouble(line[4].trim());
                    double close = Double.parseDouble(line[5].trim());
                    int volume = Integer.parseInt(line[6].trim());

                    candleMapByDateTime.put(date, new Candle(date, open, high, low, close, volume));
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }
        return candleMapByDateTime;
    }
}

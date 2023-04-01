package ta4jexamples.data;

import com.opencsv.CSVReader;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.utils.MapUtils;
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

public class GenerateDailyDataFile {

    /**
     * Reads in data and prints out a CSV file with specific data
     * @param args
     */
    public static void main(String[] args) {
        // Getting a bar series (from any provider: CSV, web service, etc.)
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeries();
//        BarSeries seriesSMA = CsvBarsLoader.loadEs1MinSeriesFromSmaApp( ZonedDateTime.of ( LocalDate.of ( 2022, 7, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadEs1MinSeriesSpecificDate( ZonedDateTime.of ( LocalDate.of ( 2022, 10, 18 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesAfterYear( ZonedDateTime.of ( LocalDate.of ( 2017, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));
//        BarSeries series = CsvBarsLoader.loadAllEs1MinSeriesBetweenYears(
//                ZonedDateTime.of ( LocalDate.of ( 2017, 1, 6 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )),
//                ZonedDateTime.of ( LocalDate.of ( 2021, 1, 1 ), LocalTime.of ( 9, 30 ), ZoneId.of ( "America/New_York" )));

//        System.out.println(series);

        BarSeries series = CsvBarsLoader.loadEs1MinSeries();

        writeDailyInfoToCSV(populateDayInformation(series));

    }

    public static List<DailyInformation> populateDayInformation(BarSeries series) {
        List<DailyInformation> dailyInformationList = new ArrayList<>();
        Map<LocalDate, List<Bar>> dateBarMap = MapUtils.getMap(series.getBars(), Bar::getLocalDate, LinkedHashMap::new, ArrayList::new);
        for (Map.Entry<LocalDate, List<Bar>> entry : dateBarMap.entrySet()) {
            dailyInformationList.add(new DailyInformation(entry.getKey(), entry.getValue()));
        }

        return dailyInformationList;
    }

    public static void writeDailyInfoToCSV(List<DailyInformation> dailyInformationList) {
        String outputFileName = "C:\\workspace\\nate\\mbt\\ta4j-examples\\src\\main\\resources\\es-daily-info.csv";
        CsvBarsLoader.clearFileContents(outputFileName);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");

        String str = "Date, " +
                "Daily Open, Daily High, Daily Low, Daily Close, Daily HB, " +
                "OD Open, OD High, OD Low, OD Close, OD HB, " +
                "AM Open, AM High, AM Low, AM Close, AM HB, " +
                "Micro Open, Micro High, Micro Low, Micro Close, Micro HB, " +
                "PM Open, PM High, PM Low, PM Close, PM HB, " +
                "Up to PM Open, Up to PM High, Up to PM Low, Up to PM Close, Up to PM HB, " +
                "Power Hour Open, Power Hour High, Power Hour Low, Power Hour Close, Power Hour HB, " +
//                "Prior Day Open, Prior Day High, Prior Day Low, Prior Day Close, Prior Day HB, " +
                "Trend\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
            writer.append(str);

            dailyInformationList.forEach(dailyInformation -> {
                try {
                    if (dailyInformation.getDayTrend() != null && !dailyInformation.getDayTrend().equals(DayTrend.NA)) {
                        writer.append(dailyInformation.getDate() + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.RTH) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.OPENING_DRIVE) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.AM) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.MICRO) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.PM) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.RTH_TO_PM) + ", " +
                                        getOHLCString(dailyInformation, DailyInformation.DailyRange.POWER_HOUR) + ", " +
//                        getOHLCString(dailyInformation, DailyInformation.DailyRange.PRIOR_DAY_RTH) + ", " +
                                        (dailyInformation.getDayTrend() != null ? dailyInformation.getDayTrend() : "")
                        );
                        writer.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getOHLCString(DailyInformation dailyInformation, DailyInformation.DailyRange range) {

        return (dailyInformation.getOpenPrice(range) != null ? dailyInformation.getOpenPrice(range) : "") + ", " +
                (dailyInformation.getHighPrice(range) != null ? dailyInformation.getHighPrice(range) : "") + ", " +
                (dailyInformation.getLowPrice(range) != null ? dailyInformation.getLowPrice(range) : "") + ", " +
                (dailyInformation.getClosePrice(range) != null ? dailyInformation.getClosePrice(range) : "") + ", " +
                (dailyInformation.getHalfBackPrice(range) != null ? dailyInformation.getHalfBackPrice(range) : "");
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

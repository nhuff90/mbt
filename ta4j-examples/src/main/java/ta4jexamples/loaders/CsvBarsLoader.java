/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2021 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ta4jexamples.loaders;

import java.io.*;
import java.nio.charset.Charset;
import java.time.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.opencsv.CSVReader;
import ta4jexamples.data.DailyInformation;

/**
 * This class build a Ta4j bar series from a CSV file containing bars.
 */
public class CsvBarsLoader {

//    private static String ES_1_MIN_FILENAME = "data/es/1min/es-1min-master.csv";
//    private static String ES_1_MIN_FILENAME = "data/es/1min/es-1min-master-2022-temp.csv";
    private static String ES_1_MIN_FILENAME = "data/es/1min/es-1min-master-2022.csv";

    /**
     * @return the bar series from Apple Inc. bars.
     */
    public static BarSeries loadAppleIncSeries() {
        return loadCsvSeries("archive/appleinc_bars_from_20130101_usd.csv", "apple_bars", null);
    }

    /**
     * @return the bar series from SPX.
     */
    public static BarSeries loadSpx1MinSeries() {
        return loadCsvSeries("spx-1min.csv", "spx-1min", null);
    }

    /**
     * @return the bar series from ES.
     */
    public static BarSeries loadEs1MinSeries() {
        return loadCsvSeries(ES_1_MIN_FILENAME, "es-1min", null);
    }

    /**
     * @return the bar series from ES for single date.
     */
    public static BarSeries loadEs1MinSeriesSpecificDate(ZonedDateTime filteredDate) {
        return loadCsvSeries(ES_1_MIN_FILENAME, "es-1min", filteredDate);
    }

    /**
     * @return the bar series from ES after a specific year.
     */
    public static BarSeries loadEs1MinSeriesAfterYear(ZonedDateTime filteredDate) {
        return loadCsvSeriesAfterYear(ES_1_MIN_FILENAME, "es-1min", filteredDate);
    }

    /**
     * @return the bar series of ES between 2 specified years. (Inclusive/Inclusive)
     */
    public static BarSeries loadEs1MinSeriesBetweenYears(ZonedDateTime startDate, ZonedDateTime endDate) {
        return loadCsvSeriesBetweenYears(ES_1_MIN_FILENAME, "es-1min", startDate, endDate);
    }

    private static BarSeries loadCsvSeries(String filename, String barSeriesName, ZonedDateTime filteredDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        BarSeries series = new BaseBarSeries(barSeriesName);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
                if ((filteredDate == null) || (date.getYear() == filteredDate.getYear() && date.getMonthValue() == filteredDate.getMonthValue() &&
                        date.getDayOfYear() == filteredDate.getDayOfYear())) {
                    double open = Double.parseDouble(line[2]);
                    double high = Double.parseDouble(line[3]);
                    double low = Double.parseDouble(line[4]);
                    double close = Double.parseDouble(line[5]);
                    double volume = Double.parseDouble(line[6]);

                    series.addBar(date, open, high, low, close, volume);
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }
        return series;
    }

    private static BarSeries loadCsvSeriesAfterYear(String filename, String barSeriesName, ZonedDateTime filteredDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        BarSeries series = new BaseBarSeries(barSeriesName);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
                if ((filteredDate == null) || (date.getYear() >= filteredDate.getYear())) {
                    double open = Double.parseDouble(line[2]);
                    double high = Double.parseDouble(line[3]);
                    double low = Double.parseDouble(line[4]);
                    double close = Double.parseDouble(line[5]);
                    double volume = Double.parseDouble(line[6]);

                    series.addBar(date, open, high, low, close, volume);
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }
        return series;
    }

    private static BarSeries loadCsvSeriesBetweenYears(String filename, String barSeriesName, ZonedDateTime startDate, ZonedDateTime endDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        BarSeries series = new BaseBarSeries(barSeriesName);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
                if (((startDate == null) || (date.getYear() >= startDate.getYear()))
                        && ((endDate == null) || (date.getYear() <= endDate.getYear()))) {
                    double open = Double.parseDouble(line[2]);
                    double high = Double.parseDouble(line[3]);
                    double low = Double.parseDouble(line[4]);
                    double close = Double.parseDouble(line[5]);
                    double volume = Double.parseDouble(line[6]);

                    series.addBar(date, open, high, low, close, volume);
                }
            }
        } catch (IOException ioe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
        }
        return series;
    }

//    private static BarSeries loadESHistoricalCsvSeriesAfterYear(String filename, String barSeriesName, ZonedDateTime filteredDate) {
//
//        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);
//
//        BarSeries series = new BaseBarSeries(barSeriesName);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
//                1)) {
//            String[] line;
//            while ((line = csvReader.readNext()) != null) {
////                ZonedDateTime date = formatDateAndTime(line[0], line[1]);
//                ZonedDateTime date = ZonedDateTime
//                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
//                if ((filteredDate == null) || (date.getYear() >= filteredDate.getYear())) {
//                    double open = Double.parseDouble(line[2]);
//                    double high = Double.parseDouble(line[3]);
//                    double low = Double.parseDouble(line[4]);
//                    double close = Double.parseDouble(line[5]);
//                    double volume = Double.parseDouble(line[6]);
//
//                    series.addBar(date, open, high, low, close, volume);
//                }
//            }
//        } catch (IOException ioe) {
//            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
//        } catch (NumberFormatException nfe) {
//            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
//        }
//        return series;
//    }
//
//    private static BarSeries loadESHistoricalCsvSeriesBetweenYears(String filename, String barSeriesName, ZonedDateTime startDate, ZonedDateTime endDate) {
//
//        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);
//
//        BarSeries series = new BaseBarSeries(barSeriesName);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
//                1)) {
//            String[] line;
//            while ((line = csvReader.readNext()) != null) {
////                ZonedDateTime date = formatDateAndTime(line[0], line[1]);
//                ZonedDateTime date = ZonedDateTime
//                        .of(LocalDate.parse(line[0].trim()), LocalTime.parse(line[1].trim()), ZoneId.of ( "America/New_York" ));
//                if (((startDate == null) || (date.getYear() >= startDate.getYear()))
//                && ((endDate == null) || (date.getYear() <= endDate.getYear()))) {
//                    double open = Double.parseDouble(line[2]);
//                    double high = Double.parseDouble(line[3]);
//                    double low = Double.parseDouble(line[4]);
//                    double close = Double.parseDouble(line[5]);
//                    double volume = Double.parseDouble(line[6]);
//
//                    series.addBar(date, open, high, low, close, volume);
//                }
//            }
//        } catch (IOException ioe) {
//            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Unable to load bars from CSV", ioe);
//        } catch (NumberFormatException nfe) {
//            Logger.getLogger(CsvBarsLoader.class.getName()).log(Level.SEVERE, "Error while parsing value", nfe);
//        }
//        return series;
//    }

    public static void main(String[] args) {
        BarSeries series = CsvBarsLoader.loadAppleIncSeries();

        System.out.println("Series: " + series.getName() + " (" + series.getSeriesPeriodDescription() + ")");
        System.out.println("Number of bars: " + series.getBarCount());
        System.out.println("First bar: \n" + "\tVolume: " + series.getBar(0).getVolume() + "\n" + "\tOpen price: "
                + series.getBar(0).getOpenPrice() + "\n" + "\tClose price: " + series.getBar(0).getClosePrice());

    }

    public static void writeDailyInfoToCSV(List<DailyInformation> dailyInformationList) {
        String outputFileName = "C:\\workspace\\nate\\mbt\\ta4j-examples\\src\\main\\resources\\output.csv";
        clearFileContents(outputFileName);

            String str = "Date, Open Price, High Price up to PM, Low Price up to PM, Price at PM range Start, Price at PM range End, Close Price\n";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
                writer.append(str);


                dailyInformationList.forEach(dailyInformation -> {
                    try {
                        if (dailyInformation.getOpenPrice(DailyInformation.DailyRange.RTH) != null && dailyInformation.getClosePrice(DailyInformation.DailyRange.RTH) != null) {
                            writer.append(dailyInformation.getDate()
                                    + ", " + (dailyInformation.getOpenPrice(DailyInformation.DailyRange.RTH_END_AT_1558) != null ? dailyInformation.getOpenPrice(DailyInformation.DailyRange.RTH_END_AT_1558) : "")
                                    + ", " + (dailyInformation.getHighPrice(DailyInformation.DailyRange.RTH_TO_PM) != null ? dailyInformation.getHighPrice(DailyInformation.DailyRange.RTH_TO_PM) : "")
                                    + ", " + (dailyInformation.getLowPrice(DailyInformation.DailyRange.RTH_TO_PM) != null ? dailyInformation.getLowPrice(DailyInformation.DailyRange.RTH_TO_PM) : "")
                                    + ", " + (dailyInformation.getOpenPrice(DailyInformation.DailyRange.PM) != null ? dailyInformation.getOpenPrice(DailyInformation.DailyRange.PM) : "")
                                    + ", " + (dailyInformation.getClosePrice(DailyInformation.DailyRange.PM) != null ? dailyInformation.getClosePrice(DailyInformation.DailyRange.PM) : "")
                                    + ", " + (dailyInformation.getClosePrice(DailyInformation.DailyRange.RTH_END_AT_1558) != null ? dailyInformation.getClosePrice(DailyInformation.DailyRange.RTH_END_AT_1558) : ""));
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

    public static void clearFileContents(String outputFileName) {
        try {
            new FileWriter(outputFileName, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

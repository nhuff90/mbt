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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.opencsv.CSVReader;

/**
 * This class build a Ta4j bar series from a CSV file containing bars.
 */
public class CsvBarsLoader {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @return the bar series from Apple Inc. bars.
     */
    public static BarSeries loadAppleIncSeries() {
        return loadCsvSeries("appleinc_bars_from_20130101_usd.csv", "apple_bars", null);
    }

    /**
     * @return the bar series from SPX.
     */
    public static BarSeries loadSpx1MinSeries() {
        return loadCsvSeries("spx-1min.csv", "spx-1min", null);
    }

    /**
     * @return the bar series from SPX for single date.
     */
    public static BarSeries loadSpx1MinSeries(ZonedDateTime filteredDate) {
        return loadCsvSeries("spx-1min.csv", "spx-1min", filteredDate);
    }

    /**
     * @return the bar series from SPX for single date.
     */
    public static BarSeries loadEs1MinSeries(ZonedDateTime filteredDate) {
        return loadCsvSeries("es-1min.csv", "es-1min", filteredDate);
    }

    /**
     * @return the bar series from SPX.
     */
    public static BarSeries loadEs1MinSeries() {
        return loadCsvSeries("es-1min.csv", "es-1min", null);
    }

    /**
     * @return the bar series from SPX.
     */
    public static BarSeries loadAllEs1MinSeries() {
        return loadESHistoricalCsvSeries("ES_backvolume_adjusted_1min_data.csv", "es-1min", null);
    }

    /**
     * @return the bar series from SPX for single date.
     */
    public static BarSeries loadAllEs1MinSeries(ZonedDateTime filteredDate) {
//        return loadESHistoricalCsvSeries("ES_back_single_date.csv", "es-1min", filteredDate);
        return loadESHistoricalCsvSeries("ES_backvolume_adjusted_1min_data.csv", "es-1min", filteredDate);
    }

    public static BarSeries loadCsvSeries(String filename, String barSeriesName, ZonedDateTime filteredDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        BarSeries series = new BaseBarSeries(barSeriesName);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = ZonedDateTime
                        .of(LocalDate.parse(line[0]), LocalTime.parse(line[1]), ZoneId.systemDefault());
                if ((filteredDate == null) || (date.getYear() == filteredDate.getYear() && date.getMonthValue() == filteredDate.getMonthValue() &&
                        date.getDayOfYear() == filteredDate.getDayOfYear())) {
                    double open = Double.parseDouble(line[3]);
                    double high = Double.parseDouble(line[4]);
                    double low = Double.parseDouble(line[5]);
                    double close = Double.parseDouble(line[6]);
                    double volume = Double.parseDouble(line[7]);

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

    public static BarSeries loadESHistoricalCsvSeries(String filename, String barSeriesName, ZonedDateTime filteredDate) {

        InputStream stream = CsvBarsLoader.class.getClassLoader().getResourceAsStream(filename);

        BarSeries series = new BaseBarSeries(barSeriesName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',', '"',
                1)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = formatDateAndTime(line[0], line[1]);
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

    private static ZonedDateTime formatDateAndTime(String date, String time){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("u/M/d");
        LocalDate formattedDate =  LocalDate.parse(date, dateFormatter);

        String formattedTime = ((time.split("\\.")[0]).trim()); // Plus 1 hour because data is in central time
        String day = formattedDate.toString();
        return LocalDateTime.parse(day + " " + formattedTime, DateTimeFormatter.ofPattern( "u-M-d HH:mm:ss" )).atZone(ZoneId.of( "America/Chicago" ) ).withZoneSameInstant(ZoneId.of( "America/New_York" ) );
    }

    public static void main(String[] args) {
        BarSeries series = CsvBarsLoader.loadAppleIncSeries();

        System.out.println("Series: " + series.getName() + " (" + series.getSeriesPeriodDescription() + ")");
        System.out.println("Number of bars: " + series.getBarCount());
        System.out.println("First bar: \n" + "\tVolume: " + series.getBar(0).getVolume() + "\n" + "\tOpen price: "
                + series.getBar(0).getOpenPrice() + "\n" + "\tClose price: " + series.getBar(0).getClosePrice());
    }
}

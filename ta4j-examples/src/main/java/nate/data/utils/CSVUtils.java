package nate.data.utils;

import com.opencsv.CSVWriter;
import nate.data.domain.Candle;
import nate.data.domain.CandleLength;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class CSVUtils {
    public static Map<String, Candle> readDataCsv(String fileName) {
        Path pathToFile = Paths.get(fileName);
        Map<String, Candle> map = new HashMap<>();

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file, skip first 2 lines
            String line = br.readLine();
            if (line != null && line.equalsIgnoreCase("Empty DataFrame")) {
                return null;
            }
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");
                // Remove blank elements
                List<String> list = new ArrayList<>(Arrays.asList(attributes));
                list.removeAll(Arrays.asList("", null));
                String[] cleanedAttributes = new String[list.size()];
                cleanedAttributes = list.toArray(cleanedAttributes);
                Candle candle = createCandle(cleanedAttributes);
                if (!map.containsKey(candle.getDate() + " " + candle.getTime())) {
                    map.put(candle.getDate() + " " + candle.getTime(), candle);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return map;
    }

    public static Map<String, Candle> readSpaceSeperatedCSV(String fileName) {
        Path pathToFile = Paths.get(fileName);
        Map<String, Candle> map = new HashMap<>();

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file, skip first 2 lines
            String line = br.readLine();
            if (line != null && line.equalsIgnoreCase("Empty DataFrame")) {
                return null;
            }
            line = br.readLine();
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(" ");
                // Remove blank elements
                List<String> list = new ArrayList<>(Arrays.asList(attributes));
                list.removeAll(Arrays.asList("", null));
                String[] cleanedAttributes = new String[list.size()];
                cleanedAttributes = list.toArray(cleanedAttributes);
                Candle candle = createCandle(cleanedAttributes);
                if (!map.containsKey(candle.getDate() + " " + candle.getTime())) {
                    map.put(candle.getDate() + " " + candle.getTime(), candle);
                }

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return map;
    }

    public static void writeMapToCsv(List<Candle> candles, String fileName) {
        // specified by filepath
        File file = new File(fileName);

        try {

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        List<String> dataSetList = new ArrayList<>();

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);


        // adding data to csv
        // adding header to csv
        String[] header = { "date", "time","open","high","low","close","volume" };
        writer.writeNext(header, false);
        candles.forEach(candle -> writer.writeNext(candle.toStringArray(), false));


        // closing writer connection
        writer.close();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    private static Candle createCandle(String[] metadata) {
        LocalDate date = null;
        LocalTime time = null;
        Double open = null;
        Double high = null;
        Double low = null;
        Double close = null;
        Integer volume = null;
        try {
            int i = 0;
            date = LocalDate.parse(removeQuotes(metadata[i++]));
            time = LocalTime.parse(addStartingZero(removeQuotes(metadata[i++])).toString().split("-")[0]);
            if (removeQuotes(metadata[i]).startsWith("CME")) {
                i++;
                //todo - this should be a temp thing. delete once file is consolidated.
            }
            open = Double.parseDouble(removeQuotes(metadata[i++]));
            high = Double.parseDouble(removeQuotes(metadata[i++]));
            low = Double.parseDouble(removeQuotes(metadata[i++]));
            close = Double.parseDouble(removeQuotes(metadata[i++]));
            volume = new BigDecimal(removeQuotes(metadata[i])).intValue();
        }
        catch (Exception e) {
            System.out.println("Error here");
        }

            CandleLength candleLength = CandleLength.FIVE_MIN;
        // create and return book of this metadata
        return new Candle(date, time, open, high, low, close, volume, candleLength);
    }

    private static Candle createCandleFromDataCsv(String[] metadata) {
        LocalDate date = null;
        LocalTime time = null;
        Double open = null;
        Double high = null;
        Double low = null;
        Double close = null;
        Integer volume = null;
        try {
            date = LocalDate.parse(removeQuotes(metadata[0]));
            time = LocalTime.parse(removeQuotes(metadata[1]));
            open = Double.parseDouble(removeQuotes(metadata[2]));
            high = Double.parseDouble(removeQuotes(metadata[3]));
            low = Double.parseDouble(removeQuotes(metadata[4]));
            close = Double.parseDouble(removeQuotes(metadata[5]));
            volume = new BigDecimal(removeQuotes(metadata[6])).intValue();
        }
        catch (Exception e) {
            System.out.println("Error here");
        }

        CandleLength candleLength = CandleLength.FIVE_MIN;
        // create and return book of this metadata
        return new Candle(date, time, open, high, low, close, volume, candleLength);
    }

    public static String removeQuotes(String metadatum) {
        if (metadatum.startsWith("\"")) {
            metadatum = metadatum.substring(1);
        }
        if (metadatum.endsWith("\"")) {
            metadatum = metadatum.substring(0, metadatum.length()-1);
        }

        return metadatum;
    }

    private static CharSequence addStartingZero(String metadatum) {
        StringBuilder metadatumBuilder = new StringBuilder(metadatum);
        while(metadatumBuilder.length() < 8) {
            metadatumBuilder.insert(0, "0");
        }
        return metadatumBuilder.toString();
    }
}

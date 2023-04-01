package nate.data;

import nate.data.domain.Candle;
import nate.data.utils.CSVUtils;

import java.io.File;
import java.util.*;

public class DataConsolidator {

    private static final String HISTORICAL_DATA_DIR = "C:\\workspace\\nate\\mbt\\ta4j-examples\\src\\main\\resources\\data";
    private static final String ES = "es";
    private static final String NQ = "nq";
    private static final String SPX = "spx";
    private static final String VIX = "vix";

    public static void main(String[] args) {
        cleanHistoricalFiles(ES);
//        cleanHistoricalFiles(NQ);
//        cleanHistoricalFiles(SPX);
//        cleanHistoricalFiles(VIX);
    }

    public static void cleanHistoricalFiles(String ticker) {
        File dir = new File(HISTORICAL_DATA_DIR + "\\" + ticker);
        File[] directoryListing = dir.listFiles();
        assert directoryListing != null;
        List<Map<String, Candle>> mapsList = new ArrayList<>();
        for (File file : directoryListing) {
            File[] subDirListing = file.listFiles();
            assert subDirListing != null;
            for (File subFile : subDirListing) {
                Map<String, Candle> map = null;
                if (subFile.toString().endsWith(".csv")) {
                    map = CSVUtils.readDataCsv(subFile.toString());
                } else if (subFile.toString().endsWith(".bak")) {
                    continue;
                }else {
                    map = CSVUtils.readSpaceSeperatedCSV(subFile.toString());
                }
                if (map != null) {
                    mapsList.add(map);
                }
            }

            if (mapsList.size() == 0) {
                continue;
            }
            List<Candle> cleanedMap = mergeLists(mapsList);
            // write it back out
            CSVUtils.writeMapToCsv(cleanedMap, file.getAbsolutePath() + "\\es-1min-master.csv");

            mapsList.clear();
        }
    }

    private static List<Candle> mergeLists(List<Map<String, Candle>> esMapsList) {
        Map<String, Candle> map1 = null;
        if (esMapsList.get(0) != null) {
            map1 = new HashMap<>(esMapsList.get(0));
        }

        for (int i = 1; i < esMapsList.size(); i++) {
            for (Map.Entry<String, Candle> entry : esMapsList.get(i).entrySet()) {
                assert map1 != null;
                if (!map1.containsKey(entry.getKey())) {
                    map1.put(entry.getKey(), entry.getValue());
                }
            }
        }

        List<Candle> candles = new ArrayList<>();
        assert map1 != null;
        SortedSet<String> keys = new TreeSet<>(map1.keySet());
        for (String key : keys) {
            Candle value = map1.get(key);
            candles.add(value);
        }

        return candles;
    }
}

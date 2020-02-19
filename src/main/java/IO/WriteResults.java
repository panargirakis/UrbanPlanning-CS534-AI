package IO;

import Algorithms.Metrics;
import Map.Terrain;
import Map.UrbanMap;

import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

public class WriteResults {

    public static void writeResults(UrbanMap map, Metrics metrics, String fileName) throws IOException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(fileName));

                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"Max Score: ", Integer.toString(metrics.getMaxScore()),
                                    "Time achieved:", Integer.toString(metrics.getTimeAchieved())};
            csvWriter.writeNext(headerRecord);

            // TODO: write the map
        }
    }
}
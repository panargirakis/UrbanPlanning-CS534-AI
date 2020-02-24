package IO;

import Algorithms.Metrics;

import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class WriteResults {

    public static void writeResults(Metrics metrics, String fileName) throws IOException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(fileName));

                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"Max Score: " + Integer.toString(metrics.getScore()),
                                    " Time achieved (seconds): " + Double.toString(metrics.getTimeAchieved())};
            csvWriter.writeNext(headerRecord);

            for (List<String> row : metrics.getMap()) {
                csvWriter.writeNext(row.toArray(new String[0]));
            }

        }
    }
}
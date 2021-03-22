package util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderCSV {

    public List<List<String>> getCSVContent(String file) {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                List<String> line = Arrays.asList(values);
                line = line.stream().map(String::trim).collect(Collectors.toList());
                records.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        // remove csv header line
        records.remove(0);
        return records;
    }

}

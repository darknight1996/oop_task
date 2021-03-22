package repository.impl;

import repository.ToyRepository;
import toy.AbstractToy;
import util.ReaderCSV;
import util.ToyConverter;

import java.util.ArrayList;
import java.util.List;

public class CSVToyRepository implements ToyRepository {

    private final String CSV_FILE_PATH = "src/main/resources/toys.csv";

    private final ReaderCSV reader;

    private final ToyConverter converter;

    private List<AbstractToy> toys = new ArrayList<>();

    public CSVToyRepository(ReaderCSV reader, ToyConverter converter) {
        this.reader = reader;
        this.converter = converter;
        init();
    }

    private void init() {
        List<List<String>> content = reader.getCSVContent(CSV_FILE_PATH);
        content.forEach(contentLine -> {
            try {
                toys.add(converter.convert(contentLine));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<AbstractToy> getToys() {
        return toys;
    }
}

import repository.ToyRepository;
import repository.impl.CSVToyRepository;
import toy.AbstractToy;
import util.ReaderCSV;
import util.ToyConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ReaderCSV reader = new ReaderCSV();
        ToyConverter converter = new ToyConverter();
        ToyRepository toyRepository = new CSVToyRepository(reader, converter);
        List<AbstractToy> toys = toyRepository.getToys();

        System.out.println("Enter money: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int money = Integer.parseInt(bufferedReader.readLine());

        Playroom playroom = new Playroom(toys, money);
    }

}

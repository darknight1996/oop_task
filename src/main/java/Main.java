import repository.ToyRepository;
import repository.impl.CSVToyRepository;
import toy.AbstractToy;
import util.ReaderCSV;
import util.ToyConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) throws Exception {

        ReaderCSV reader = new ReaderCSV();
        ToyConverter converter = new ToyConverter();
        ToyRepository toyRepository = new CSVToyRepository(reader, converter);
        List<AbstractToy> toys = toyRepository.getToys();

        System.out.println("Enter money: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int money = Integer.parseInt(bufferedReader.readLine());


        Playroom playroom = new Playroom(toys, money);

        // sort by parameter
        List<AbstractToy> roomToys = playroom.getToys();
        System.out.println("----- SORT -----");
        System.out.println("Enter sort parameter (size, cost): ");
        String sortParameter = bufferedReader.readLine();
        switch (sortParameter) {
            case "size":
                roomToys.sort(Comparator.comparing(AbstractToy::getToySize));
                playroom.getToys().forEach(System.out::println);
                break;
            case "cost":
                roomToys.sort(Comparator.comparing(AbstractToy::getCost));
                playroom.getToys().forEach(System.out::println);
                break;
            default: throw new Exception("Incorrect sort parameter!");
        }

        // find by toy by parameter
        System.out.println("----- FIND -----");
        System.out.println("Enter type: ");
        String toyType = bufferedReader.readLine();
        System.out.println("Enter size: ");
        String toySize = bufferedReader.readLine();
        System.out.println("Enter cost:");
        int toyCost = Integer.parseInt(bufferedReader.readLine());

        AbstractToy targetToy = null;
        for (AbstractToy toy: playroom.getToys()) {
            if (toy.getClass().getName().equals("toy." + toyType) && toy.getCost() == toyCost && toy.getToySize().name().equals(toySize)) {
                targetToy = toy;
                break;
            }
        }

        System.out.println(targetToy);
    }

}

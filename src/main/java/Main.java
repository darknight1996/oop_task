import repository.ToyRepository;
import repository.impl.CSVToyRepository;
import toy.AbstractToy;
import util.ReaderCSV;
import util.ToyConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ReaderCSV reader = new ReaderCSV();
        ToyConverter converter = new ToyConverter();
        ToyRepository toyRepository = new CSVToyRepository(reader, converter);
        List<AbstractToy> toys = toyRepository.getToys();

        // ----- create rooms -----
        List<Playroom> playrooms = new ArrayList<>();
        playrooms.add(new Playroom("room1", 3, toys.subList(0, 3)));
        playrooms.add(new Playroom("room2", 4, toys.subList(3, 6)));
        playrooms.add(new Playroom("room3", 5, toys.subList(6, 9)));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while (!exit) {
            // ----- enter child data -----
            System.out.println("Enter child name: ");
            String childName = bufferedReader.readLine();
            System.out.println("Enter child age: ");
            int childAge = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Enter child money: ");
            int childMoney = Integer.parseInt(bufferedReader.readLine());
            Child child = new Child(childName, childAge, childMoney);

            // ----- print available rooms -----
            playrooms.forEach(System.out::println);
            // ----- select a room -----
            System.out.println("Enter room name: ");
            String roomName = bufferedReader.readLine();
            if (playrooms.stream().noneMatch(room -> room.getName().equals(roomName))) {
                throw new Exception("Invalid room name!");
            }

            for (Playroom playroom : playrooms) {
                if (playroom.getName().equals(roomName)) {
                    try {
                        playroom.setChild(child);

                        // ---- toys sort -----
                        System.out.println("Enter sort parameter('cost', 'type', 'size'): ");
                        String sortParameter = bufferedReader.readLine();
                        switch (sortParameter) {
                            case ("cost"):
                                playroom.getToys().sort(Comparator.comparing(AbstractToy::getCost));
                                break;
                            case ("type"):
                                playroom.getToys().sort(Comparator.comparing(AbstractToy::getType));
                                break;
                            case ("size"):
                                playroom.getToys().sort(Comparator.comparing(AbstractToy::getToySize));
                                break;
                            default:
                                throw new Exception("Invalid sort parameter!");
                        }
                        System.out.println("Sorted toys:");
                        playroom.getToys().forEach(System.out::println);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("type 'exit' to exit or any to continue");
            exit = bufferedReader.readLine().equalsIgnoreCase("exit");
        }

    }

}

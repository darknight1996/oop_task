import repository.ToyRepository;
import repository.impl.FileToyRepository;
import toy.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter role (client, owner):");
            String role = bufferedReader.readLine();
            if (role.equals("client")) {
                clientWorkflow(bufferedReader);
            } else if (role.equals("owner")) {
                ownerWorkflow(bufferedReader);
            } else {
                throw new Exception("Invalid role!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }

    }

    private static void clientWorkflow(BufferedReader bufferedReader) throws Exception {
        ToyRepository toyRepository = new FileToyRepository();
        List<AbstractToy> toys = toyRepository.getToys();

        // ----- create rooms -----
        List<Playroom> playrooms = new ArrayList<>();
        playrooms.add(new Playroom("room1", 3, new ArrayList<>()));
        playrooms.add(new Playroom("room2", 4, new ArrayList<>()));
        playrooms.add(new Playroom("room3", 5, new ArrayList<>()));

        int i = 0;
        for (AbstractToy toy : toys) {
            playrooms.get(i).getToys().add(toy);
            if (i == 2) {
                i = 0;
            } else {
                i++;
            }
        }

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

    private static void ownerWorkflow(BufferedReader bufferedReader) throws Exception {
        ToyRepository toyRepository = new FileToyRepository();

        boolean exit = false;

        while (!exit) {
            System.out.println("Enter action(add toy, show toys):");
            String action = bufferedReader.readLine();
            switch (action) {
                case ("add toy"):
                    System.out.println("Enter toy type (Ball, Car, Cubes, Doll):");
                    String toyType = bufferedReader.readLine();
                    System.out.println("Enter toy size (SMALL, MEDIUM, LARGE):");
                    ToySize toySize = ToySize.valueOf(bufferedReader.readLine());
                    System.out.println("Enter toy cost:");
                    int toyCost = Integer.parseInt(bufferedReader.readLine());
                    AbstractToy newToy = null;
                    switch (toyType) {
                        case ("Ball"):
                            newToy = new Ball(toySize, toyCost);
                            break;
                        case ("Car"):
                            newToy = new Car(toySize, toyCost);
                            break;
                        case ("Cubes"):
                            newToy = new Cubes(toySize, toyCost);
                            break;
                        case ("Doll"):
                            newToy = new Doll(toySize, toyCost);
                            break;
                    }
                    List<AbstractToy> toys = toyRepository.getToys();
                    toys.add(newToy);
                    toyRepository.updateToys(toys);
                    break;
                case ("show toys"):
                    toyRepository.reload();
                    System.out.println(toyRepository.getToys());
                    break;
                default:
                    throw new Exception("Invalid action!");
            }

            System.out.println("type 'exit' to exit or any to continue");
            exit = bufferedReader.readLine().equalsIgnoreCase("exit");
        }
    }

}

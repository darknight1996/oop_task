package ui;

import playroom.Playroom;
import repository.PlayroomRepository;
import repository.impl.FilePlayroomRepository;
import toy.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OwnerMainFrame extends RoomsFrame {

    private TextField toyTypeTextField;

    private TextField toySizeTextField;

    private TextField toyCostTextField;

    private JList<String> playroomNamesList;

    private TextField toyIdTextField;

    public OwnerMainFrame() {
        init();
    }

    private void init() {
        this.setTitle("Owner");

        panel = new JPanel();
        this.getContentPane().add(panel);

        // init inputs
        toyTypeTextField = new TextField("toy type");
        panel.add(toyTypeTextField);

        toySizeTextField = new TextField("toy size");
        panel.add(toySizeTextField);

        toyCostTextField = new TextField("toy cost");
        panel.add(toyCostTextField);

        PlayroomRepository playroomRepository = new FilePlayroomRepository();
        playroomNamesList = new JList<>(
                playroomRepository.getPlayrooms().stream()
                        .map(Playroom::getName)
                        .collect(Collectors.toList())
                        .toArray(String[]::new)
        );
        playroomNamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(playroomNamesList);

        // init buttons
        JButton enterRoomButton = new JButton("add toy");
        enterRoomButton.addActionListener(e -> addToy());
        panel.add(enterRoomButton);

        this.initRooms();

        toyIdTextField = new TextField("toy id");
        panel.add(toyIdTextField);

        JButton deleteToyButton = new JButton("delete toy");
        deleteToyButton.addActionListener(e -> deleteToy());
        panel.add(deleteToyButton);

        this.setVisible(true);
    }

    private void addToy() {

        AbstractToy toy;
        switch (toyTypeTextField.getText()) {
            case ("Ball"):
                toy = new Ball(getNextIdForToy(), ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Car"):
                toy = new Car(getNextIdForToy(), ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Cubes"):
                toy = new Cubes(getNextIdForToy(), ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Doll"):
                toy = new Doll(getNextIdForToy(), ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + toyTypeTextField.getText());
        }

        PlayroomRepository playroomRepository = new FilePlayroomRepository();
        List<Playroom> playrooms = playroomRepository.getPlayrooms();

        Playroom playroomToUpdate = null;
        List<AbstractToy> newToysForRoom = new ArrayList<>();

        for (Playroom playroom : playrooms) {
            if (playroom.getName().equals(playroomNamesList.getSelectedValue())) {
                playroomToUpdate = playroom;
                newToysForRoom.addAll(playroom.getToys());
                newToysForRoom.add(toy);
            }
        }
        playroomToUpdate.setToys(newToysForRoom);
        playroomRepository.updatePlayrooms(playrooms);

        initRooms();
        JOptionPane.showMessageDialog(this, "You have successfully added the toy");
    }

    private void deleteToy() {
        PlayroomRepository playroomRepository = new FilePlayroomRepository();
        List<Playroom> playrooms = playroomRepository.getPlayrooms();

        Playroom playroomToUpdate = null;
        int toyId = Integer.parseInt(toyIdTextField.getText());

        for (Playroom playroom : playrooms) {
            for (AbstractToy toy : playroom.getToys()) {
                if (toy.getId() == toyId) {
                    playroomToUpdate = playroom;
                    break;
                }
            }
        }

        List<AbstractToy> newToys = new ArrayList<>();
        for (AbstractToy toy : playroomToUpdate.getToys()) {
            if (toy.getId() != toyId) {
                newToys.add(toy);
            }
        }
        playroomToUpdate.setToys(newToys);
        playroomRepository.updatePlayrooms(playrooms);

        initRooms();
        JOptionPane.showMessageDialog(this, "You have successfully deleted the toy");
    }


    private int getNextIdForToy() {
        int max = 0;
        for (Playroom playroom : playrooms) {
            for (AbstractToy toy : playroom.getToys()) {
                if (toy.getId() > max) {
                    max = toy.getId();
                }
            }
        }
        return max + 1;
    }

}

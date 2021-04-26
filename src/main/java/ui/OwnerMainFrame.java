package ui;

import repository.ToyRepository;
import repository.impl.FileToyRepository;
import toy.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OwnerMainFrame extends RoomsFrame {

    private TextField toyTypeTextField;

    private TextField toySizeTextField;

    private TextField toyCostTextField;

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

        // init buttons
        JButton enterRoomButton = new JButton("add toy");
        enterRoomButton.addActionListener(e -> addToy());
        panel.add(enterRoomButton);


        this.initRooms();

        this.setVisible(true);
    }

    private void addToy() {
        AbstractToy toy;
        switch (toyTypeTextField.getText()) {
            case ("Ball"):
                toy = new Ball(ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Car"):
                toy = new Car(ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Cubes"):
                toy = new Cubes(ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            case ("Doll"):
                toy = new Doll(ToySize.valueOf(toySizeTextField.getText()),
                        Integer.parseInt(toyCostTextField.getText()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + toyTypeTextField.getText());
        }
        
        ToyRepository toyRepository = new FileToyRepository();
        List<AbstractToy> toys = toyRepository.getToys();
        toys.add(toy);
        toyRepository.updateToys(toys);

        initRooms();
    }

}

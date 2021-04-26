package ui;

import child.Child;
import playroom.Playroom;

import javax.swing.*;
import java.awt.*;

public class ClientMainFrame extends RoomsFrame {

    private Child child;

    private TextField childNameTextField;

    private TextField childAgeTextField;

    private TextField childMoneyTextField;

    private TextField roomNameTextField;

    public ClientMainFrame() {
        init();
    }

    private void init() {
        this.setTitle("Client");

        // init inputs
        childNameTextField = new TextField("name");
        panel.add(childNameTextField);

        childAgeTextField = new TextField("age");
        panel.add(childAgeTextField);

        childMoneyTextField = new TextField("money");
        panel.add(childMoneyTextField);

        roomNameTextField = new TextField("room name");
        panel.add(roomNameTextField);

        // init buttons
        JButton enterRoomButton = new JButton("enter room");
        enterRoomButton.addActionListener(e -> initChildAndEnterRoom());
        panel.add(enterRoomButton);

        initRooms();

        this.setVisible(true);
    }

    private void initChildAndEnterRoom() {
        child = new Child(childNameTextField.getText(),
                Integer.parseInt(childAgeTextField.getText()),
                Integer.parseInt(childMoneyTextField.getText()));

        String roomName = roomNameTextField.getText();
        if (playrooms.stream().noneMatch(room -> room.getName().equals(roomName))) {
            JOptionPane.showMessageDialog(this, "Invalid room name!");
        }

        for (Playroom playroom : playrooms) {
            if (playroom.getName().equals(roomName)) {
                try {
                    playroom.setChild(child);
                    JOptionPane.showMessageDialog(this, "You have successfully entered the room");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }

    }

}

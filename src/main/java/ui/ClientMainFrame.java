package ui;

import child.Child;
import playroom.Playroom;
import repository.ToyRepository;
import repository.impl.FileToyRepository;
import toy.AbstractToy;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientMainFrame extends DefaultFrame {

    private JPanel panel;

    private List<Playroom> playrooms;

    private Child child;

    private TextField childNameTextField;

    private TextField childAgeTextField;

    private TextField childMoneyTextField;

    private TextField roomNameTextField;

    private JTree roomsTree;

    public ClientMainFrame() {
        init();
    }

    private void init() {
        this.setSize(400, 400);
        this.setLocation(600, 300);
        this.setTitle("Client");

        panel = new JPanel();
        this.getContentPane().add(panel);

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


        this.setVisible(true);
        initRooms();
    }

    private void initRooms() {
        ToyRepository toyRepository = new FileToyRepository();
        List<AbstractToy> toys = toyRepository.getToys();

        // ----- create rooms -----
        playrooms = new ArrayList<>();
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

        // create rooms tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("rooms");
        for (Playroom playroom : playrooms) {
            DefaultMutableTreeNode playroomNode = new DefaultMutableTreeNode(playroom.treeContent());
            for (AbstractToy toy : playroom.getToys()) {
                playroomNode.add(new DefaultMutableTreeNode(
                        toy.getType()
                                + ", toy cost: " + toy.getCost()
                                + ", toy size: " + toy.getToySize()
                ));
            }
            root.add(playroomNode);
        }
        roomsTree = new JTree(root);
        panel.add(roomsTree);
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

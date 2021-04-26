package ui;

import playroom.Playroom;
import repository.ToyRepository;
import repository.impl.FileToyRepository;
import toy.AbstractToy;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class RoomsFrame extends DefaultFrame {

    List<Playroom> playrooms;

    JTree roomsTree;

    public RoomsFrame() {
        super();
    }

    void initRooms() {
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
        if (roomsTree != null) {
            ((DefaultTreeModel)roomsTree.getModel()).setRoot(root);
        } else {
            roomsTree = new JTree(root);
            panel.add(roomsTree);
        }
    }
}

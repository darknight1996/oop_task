package ui;

import playroom.Playroom;
import repository.PlayroomRepository;
import repository.impl.FilePlayroomRepository;
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
        PlayroomRepository playroomRepository = new FilePlayroomRepository();
        playrooms = playroomRepository.getPlayrooms();

        // create rooms tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("rooms");
        for (Playroom playroom : playrooms) {
            DefaultMutableTreeNode playroomNode = new DefaultMutableTreeNode(playroom.treeContent());
            for (AbstractToy toy : playroom.getToys()) {
                playroomNode.add(new DefaultMutableTreeNode(
                        toy.getId()
                                + ", " + toy.getType()
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

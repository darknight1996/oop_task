package ui;

import javax.swing.*;

public class MainFrame extends DefaultFrame {

    public MainFrame() {
        init();
    }

    private void init() {
        this.setSize(400, 400);
        this.setLocation(600, 300);
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);

        // create buttons
        JButton clientButton = new JButton("Enter as a client");
        clientButton.addActionListener(e -> {
            new ClientMainFrame();
        });
        panel.add(clientButton);

        JButton ownerButton = new JButton("Enter as an owner");
        ownerButton.addActionListener(e -> {
            new OwnerMainFrame();
        });
        panel.add(ownerButton);

        this.setVisible(true);
    }

}

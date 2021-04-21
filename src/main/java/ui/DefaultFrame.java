package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultFrame extends JFrame {
    public DefaultFrame() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int activeCount = 0;
                for (Frame frame : JFrame.getFrames()) {
                    if (frame.isVisible()) {
                        activeCount++;
                    }
                }
                if (activeCount <= 1) {
                    System.exit(0);
                }
            }
        });
    }
}


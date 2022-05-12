package pl.lukasito;

import javax.swing.*;

public class Window {
    protected JFrame frame;
    AlgAndPanel panel;

    Window() {
        frame = new JFrame("QuadTree Visualization");

        panel = new AlgAndPanel(frame.getContentPane());

        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}

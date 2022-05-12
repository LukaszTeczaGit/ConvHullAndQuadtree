package pl.lukasito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlgAndPanel extends JPanel implements ActionListener {

    Rectangle rectangle;
    QuadTree quadTree;
    DrawArea drawArea;
    JButton clear, load, save, quadtreePicture, print_QT, blackkPoints, switchBut;
    int capacity = 4;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clear) {
                drawArea.clear();
            } else if (e.getSource() == save) {
                drawArea.saveImage();
            } else if (e.getSource() == load) {
                drawArea.loadImage();
            } else if (e.getSource() == quadtreePicture) {
                drawArea.drawQT();
            } else if (e.getSource() == print_QT) {
                drawArea.printQT();
            } else if (e.getSource() == blackkPoints) {
                drawArea.blackPoints();
            } else if (e.getSource() == switchBut) {
                drawArea.setSwittch();
            }
        }
    };

    AlgAndPanel(Container con) {
        this.setPreferredSize(new Dimension(1000, 1000));
        Container content = con;
        content.setLayout(new BorderLayout());

        rectangle = new Rectangle(500, 500, 500, 500);
        quadTree = new QuadTree(rectangle, capacity);

        drawArea = new DrawArea(quadTree, rectangle, capacity);

        //INSERING RANDOM POINTS TO QUADTREE
        /*for (int i = 0; i < 50; i++){
            Random random = new Random();
            Point newone = new Point(random.nextInt(800) - 400, random.nextInt(random.nextInt(800)) - 400);
            //System.out.println(newone);
            quadTree.inssert(newone);
        }*/
        //System.out.println(quadTree);
        //System.out.println("HASHCODE FIRST: " + quadTree.hashCode());


        content.add(drawArea, BorderLayout.CENTER);
        JPanel controls = new JPanel();

        clear = new JButton("Clear and restart convex hull");
        clear.addActionListener(actionListener);

        save = new JButton("Save image");
        save.addActionListener(actionListener);

        load = new JButton("Load Image");
        load.addActionListener(actionListener);

        quadtreePicture = new JButton("Quadtree that");
        quadtreePicture.addActionListener(actionListener);

        print_QT = new JButton("Print info");
        print_QT.addActionListener(actionListener);

        blackkPoints = new JButton("Detect black line");
        blackkPoints.addActionListener(actionListener);

        switchBut = new JButton("On/Off convex hull");
        switchBut.addActionListener(actionListener);

        controls.add(clear);
        controls.add(save);
        controls.add(load);
        controls.add(quadtreePicture);
        controls.add(print_QT);
        controls.add(blackkPoints);
        controls.add(switchBut);

        content.add(controls, BorderLayout.NORTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

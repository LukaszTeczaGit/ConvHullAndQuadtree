package pl.lukasito;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DrawArea extends JComponent {
    ArrayList<Point> pointss = new ArrayList<>();
    QuadTree qt;
    Rectangle rectangle;
    int capacity;
    ArrayList<Point> blackPointsdetected;
    Convex_Hull convex_hull;
    Color blackColor = new Color(0, 0, 0);
    Color defaultcolor;
    int black = blackColor.getRGB();
    Boolean swittch;
    private Image image;
    private Graphics2D g2d;
    private int currentX, currentY, oldX, oldY;

    public DrawArea(QuadTree qt, Rectangle rec, int cap) {
        this.qt = qt;
        this.convex_hull = new Convex_Hull(3, 1000, 1000);
        swittch = true;
        //IMPORTING RANDOM POINTS
        /*for (int i = 0; i < 4; i++){
            Random random = new Random();
            Point newone = new Point(random.nextInt(950) + 50 , random.nextInt(random.nextInt(950))+50);
            pointss.add(newone);
            //System.out.println(newone);
            this.qt.inssert(newone);
        }*/

        rectangle = rec;
        capacity = cap;
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                insertPoint(oldX, oldY);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                insertPoint(currentX, currentY);

                if (g2d != null) {
                    g2d.drawLine(oldX, oldY, currentX, currentY);
                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage buffimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = buffimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return buffimage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2d = (Graphics2D) image.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, getSize().width, getSize().height);
            g2d.setPaint(Color.black);
            repaint();
        } else {
            g2d = (Graphics2D) image.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(Color.black);
            repaint();

        }

        g.drawImage(image, 0, 0, null);
        defaultcolor = g2d.getColor();

        if (blackPointsdetected != null) {
            for (int i = 0; i < blackPointsdetected.size(); i++) {
                drawCenteredCircle(g2d, blackPointsdetected.get(i).getX(), blackPointsdetected.get(i).getY(), 7);
            }
        }

        if (!swittch) {
            g2d.setPaint(new Color(255, 255, 255));
        }
        if (swittch)
            g2d.setPaint(Color.RED);

        if (convex_hull.points != null) {
            for (int i = 0; i < convex_hull.points.size(); i++) {
                for (int j = 0; j < convex_hull.points.get(i).size() - 1; j++) {
                    drawCenteredCircle(g2d, convex_hull.points.get(i).get(j).getX(), convex_hull.points.get(i).get(j).getY(), 2);
                }
            }
        }
        if (swittch)
            g2d.setPaint(Color.BLACK);

        if (swittch)
            g2d.setColor(blackColor);
        if (convex_hull.pointsToDraw != null) {
            for (int i = 0; i < convex_hull.pointsToDraw.size(); i++) {
                for (int j = 0; j < convex_hull.pointsToDraw.get(i).size() - 1; j++) {
                    g2d.drawLine(convex_hull.pointsToDraw.get(i).get(j).getX(), convex_hull.pointsToDraw.get(i).get(j).getY(), convex_hull.pointsToDraw.get(i).get(j + 1).getX(), convex_hull.pointsToDraw.get(i).get(j + 1).getY());
                    if (j == convex_hull.pointsToDraw.get(i).size() - 2) {
                        g2d.drawLine(convex_hull.pointsToDraw.get(i).get(convex_hull.pointsToDraw.get(i).size() - 1).getX(), convex_hull.pointsToDraw.get(i).get(convex_hull.pointsToDraw.get(i).size() - 1).getY(), convex_hull.pointsToDraw.get(i).get(0).getX(), convex_hull.pointsToDraw.get(i).get(0).getY());
                    }
                }
            }
        }
        g2d.setColor(defaultcolor);
    }

    public void clear() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.setPaint(Color.black);
        qt = null;
        this.qt = new QuadTree(rectangle, capacity);
        convex_hull = null;
        this.convex_hull = new Convex_Hull(3, 1000, 1000);
        //System.out.println("REC = " + rectangle + " capacity = " + capacity);
        pointss = null;
        pointss = new ArrayList<>();
        repaint();
    }

    public void blackPoints() {
        BufferedImage img = toBufferedImage(image);
        try {
            for (int i = 0; i < img.getWidth(null); i++) {
                for (int j = 0; j < img.getHeight(null); j++) {
                    if (black == img.getRGB(i, j)) {
                        insertPoint(i, j);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setSwittch() {
        if (swittch == true) {
            swittch = false;
        } else swittch = true;

        //System.out.println(swittch);
        repaint();
    }

    public void loadImage() {

        try {
            JFrame jFrame = new JFrame("Wczytywanie pliku");
            String getMessage = JOptionPane.showInputDialog(jFrame, "Podaj nazwe obrazka");
            Image img = ImageIO.read(new File(getMessage));
            image = img;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void insertPoint(int x, int y) {
        if (this.qt == null) {
            this.qt = new QuadTree(rectangle, capacity);
        }
        this.qt.inssert(new Point(x, y));
    }

    public void drawQT() {

        g2d.setColor(Color.green);
        g2d.drawRect(qt.rectangle.x - (qt.rectangle.width), qt.rectangle.y - (qt.rectangle.height), qt.rectangle.width * 2, qt.rectangle.height * 2);
        if (qt.divided) {
            qt.northwest.drawMe(g2d);
            qt.northeast.drawMe(g2d);
            qt.southwest.drawMe(g2d);
            qt.southeast.drawMe(g2d);
        }
        g2d.setColor(Color.green);
    }

    public void printQT() {
        System.out.println("PRINTING QT " + qt);
        if (image != null)
            System.out.println("PICTURE WIDTH: " + image.getWidth(null) + "\n" + "PICTURE HEIGHT: " + image.getHeight(null));
    }

    public void saveImage() {
        try {
            JFrame jFrame = new JFrame("Zapisywanie pliku");
            String getMessage = JOptionPane.showInputDialog(jFrame, "Podaj nazwe obrazka");
            BufferedImage bi = toBufferedImage(image);
            File outputfile = new File(new String(getMessage + ".png"));
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }
}

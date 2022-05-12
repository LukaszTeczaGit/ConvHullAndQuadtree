package pl.lukasito;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class QuadTree {
    ArrayList<Point> points;
    int capacity;
    Rectangle rectangle;
    QuadTree northeast;
    QuadTree northwest;
    QuadTree southeast;
    QuadTree southwest;

    Boolean divided;

    QuadTree(Rectangle rectangle, int capacity) {
        this.rectangle = rectangle;
        this.capacity = capacity;
        this.divided = false;
        this.points = new ArrayList<Point>();
    }

    @Override
    public String toString() {
        return "QuadTree{" +
                "points=" + points +
                ", capacity=" + capacity +
                ", rectangle=" + rectangle +
                ", northeast=" + northeast +
                ", northwest=" + northwest +
                ", southeast=" + southeast +
                ", southwest=" + southwest +
                ", divided=" + divided +
                '}';
    }

    public int sizeOfPoints() {
        if (this.points.isEmpty()) {
            return 0;
        } else {
            return this.points.size();
        }
    }

    public void inssert(Point point) {
        if (!rectangle.contains(point)) {
            //System.out.println("RETURNED");
            return;
        }

        if (sizeOfPoints() < this.capacity) {
            this.points.add(point);
            /*System.out.println(point + "added");
            System.out.println(points);
            System.out.println("SIZE OF POINTS: " + points.size());*/
        } else {
            if (!this.divided) {
                subdevide();
            }
            northeast.inssert(point);
            northwest.inssert(point);
            southeast.inssert(point);
            southwest.inssert(point);
        }
    }

    public void subdevide() {
        Rectangle ne = new Rectangle(rectangle.x + (rectangle.width / 2), rectangle.y + (rectangle.height / 2), rectangle.width / 2, rectangle.height / 2);
        northeast = new QuadTree(ne, capacity);

        Rectangle nw = new Rectangle(rectangle.x - (rectangle.width / 2), rectangle.y + (rectangle.height / 2), rectangle.width / 2, rectangle.height / 2);
        northwest = new QuadTree(nw, capacity);

        Rectangle se = new Rectangle(rectangle.x + (rectangle.width / 2), rectangle.y - (rectangle.height / 2), rectangle.width / 2, rectangle.height / 2);
        southeast = new QuadTree(se, capacity);

        Rectangle sw = new Rectangle(rectangle.x - (rectangle.width / 2), rectangle.y - (rectangle.height / 2), rectangle.width / 2, rectangle.height / 2);
        southwest = new QuadTree(sw, capacity);
        this.divided = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuadTree)) return false;
        QuadTree quadTree = (QuadTree) o;
        return capacity == quadTree.capacity && Objects.equals(points, quadTree.points) && Objects.equals(rectangle, quadTree.rectangle) && Objects.equals(northeast, quadTree.northeast) && Objects.equals(northwest, quadTree.northwest) && Objects.equals(southeast, quadTree.southeast) && Objects.equals(southwest, quadTree.southwest) && Objects.equals(divided, quadTree.divided);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, capacity, rectangle, northeast, northwest, southeast, southwest, divided);
    }

    public void drawMe(Graphics2D g2d) {
        //g2d.translate(500,500);
        //System.out.println("HERE2");
        //g2d.drawRect((rectangle.width/2)+ rectangle.x,-(rectangle.y-(rectangle.height/2)), rectangle.width, rectangle.height);
        g2d.drawRect(rectangle.x - rectangle.width, rectangle.y - rectangle.height, rectangle.width * 2, rectangle.height * 2);
        if (this.divided) {
            this.northwest.drawMe(g2d);
            this.northeast.drawMe(g2d);
            this.southwest.drawMe(g2d);
            this.southeast.drawMe(g2d);
        }
    }
}

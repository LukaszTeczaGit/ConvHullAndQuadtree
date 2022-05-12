package pl.lukasito;

public class Rectangle {
    int x, y, width, height;

    Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Point point) {
        if (point.getX() < (x + width) && point.getX() > (x - width) && point.getY() < (y + height) && point.getY() > (y - height)) {
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

package pl.lukasito;

import java.util.ArrayList;
import java.util.Random;

public class Convex_Hull {
    ArrayList<ArrayList<Point>> points = new ArrayList<>();
    ArrayList<ArrayList<Point>> pointsToDraw = new ArrayList<>();
    ArrayList<Integer> filledAreas;
    Random random = new Random();
    int n;  //qty of filled areas / max 9 (0 - 8)
    int height, width, chheight, chwidth;

    Convex_Hull(int n, int width, int height) {
        this.width = width;
        this.height = height;
        this.n = n;
        filledAreas = new ArrayList<>();
        chheight = height / n;
        chwidth = width / n;

        for (int i = 0; i < n; i++) {
            int rnd = random.nextInt(8);
            if (filledAreas.contains(rnd)) {
                i -= 1;
            } else {
                filledAreas.add(rnd);
            }
        }

        for (int i = 0; i < filledAreas.size(); i++) {
            randomPointsForArea(whichArea(filledAreas.get(i)));
        }

        Conv();

    }

    public Point whichArea(int w) {
        if (w == 0) {
            return new Point(0, 0);
        } else if (w == 1) {
            return new Point(334, 0);
        } else if (w == 2) {
            return new Point(667, 0);
        } else if (w == 3) {
            return new Point(0, 334);
        } else if (w == 4) {
            return new Point(334, 334);
        } else if (w == 5) {
            return new Point(667, 334);
        } else if (w == 6) {
            return new Point(0, 667);
        } else if (w == 7) {
            return new Point(334, 667);
        } else {
            return new Point(667, 667);
        }
    }

    public void randomPointsForArea(Point leftCorner) {
        int buffor = 15;
        ArrayList<Point> ans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ans.add(new Point(random.nextInt(300 - buffor) + buffor + leftCorner.getX(),
                    random.nextInt(250 - buffor) + buffor + leftCorner.getY()));
        }
        ans.add(leftCorner);
        points.add(ans);
    }

    public int comparision(Point p, Point q, Point r) {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - ((q.getX() - p.getX()) * (r.getY() - q.getY()));
        if (val == 0) {
            return 0;
        } else if (val > 0) {
            return 1;
        } else return 2;
    }

    public void Conv() {

        for (int i = 0; i < points.size(); i++) {
            ArrayList<Point> ans = new ArrayList<>();
            if (points.get(i).size() < 4) continue;

            int leftMostX = 0;

            int arrayLen = points.get(i).size() - 1;

            for (int j = 0; j < arrayLen; j++) {
                if (points.get(i).get(j).getX() < points.get(i).get(leftMostX).getX()) {
                    leftMostX = j;
                }
            }

            int p = leftMostX, q;

            do {
                ans.add(points.get(i).get(p));
                q = (p + 1) % arrayLen;

                for (int j = 0; j < arrayLen; j++) {
                    if (comparision(points.get(i).get(p), points.get(i).get(j), points.get(i).get(q)) == 2) {
                        q = j;
                    }

                }
                p = q;
            } while (p != leftMostX);
            pointsToDraw.add(ans);

        }

    }

}

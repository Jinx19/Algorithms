package assignment;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by mac on 21/10/2017.
 */
public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y) return Double.NEGATIVE_INFINITY;
        else if (that.y == this.y) return 0;
        else if (that.x == this.x) return Double.POSITIVE_INFINITY;
        else {
            double numerator;//分子
            double denominator;//分母
            numerator = (double) (that.y - this.y);
            denominator = (double) (that.x - this.x);
            return numerator / denominator;
        }

    }

    public Comparator<Point> slopeOrder() {
        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (slopeTo(o1) < slopeTo(o2)) return -1;
                else if (slopeTo(o1) > slopeTo(o2)) return 1;
                else return 0;
            }
        };
        return comparator;

    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        else return 1;
    }

    public static void main(String[] args) {
        Point p = new Point(8, 9);
        Point q = new Point(8, 9);
        Point r = new Point(3, 4);
        System.out.println(p.slopeOrder().compare(q, r));
        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeTo(r));
    }
}

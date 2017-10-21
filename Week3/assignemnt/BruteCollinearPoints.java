package assignment;

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by mac on 21/10/2017.
 */
public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    private static boolean nearlyEqual(float a, float b, float epsilon) {
        final float absA = Math.abs(a);
        final float absB = Math.abs(b);
        final float diff = Math.abs(a - b);

        if (a == b) { // shortcut, handles infinities
            return true;
        } else if (a == 0 || b == 0 || diff < Float.MIN_NORMAL) {
            // a or b is zero or both are extremely close to it
            // relative error is less meaningful here
            return diff < (epsilon * Float.MIN_NORMAL);
        } else { // use relative error
            return diff / Math.min((absA + absB), Float.MAX_VALUE) < epsilon;
        }
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            else {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                }
            }
        }
        Merge.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length; j++)
                for (int n = j + 1; n < points.length; n++)
                    for (int m = n + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[n];
                        Point s = points[m];
                        double slopepq = p.slopeTo(q);
                        double slopeqr = q.slopeTo(r);
                        double slopers = r.slopeTo(s);
                        float epsilon = (float) 0.0000000000001;
                        if (nearlyEqual((float) slopepq, (float) slopeqr, epsilon) && nearlyEqual((float) slopers, (float) slopeqr, epsilon)) {
                            LineSegment line = new LineSegment(p, s);
                            lineSegments.add(line);
                        }
                    }
        }


    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] array;
        if (numberOfSegments() != 0) {
            array = new LineSegment[numberOfSegments()];
            lineSegments.toArray(array);
        } else {
            array = new LineSegment[0];
        }
        return array;
    }

    public static void main(String[] args) {
        BufferedReader in = null;
        int i = 0;
        try {
            in = new BufferedReader(new FileReader("/Users/mac/Desktop/collinear/random.txt"));
            Scanner reader = new Scanner(in);
            int n = reader.nextInt();
            Point[] points = new Point[n];
            while (reader.hasNext()) {
                int x = reader.nextInt();
                int y = reader.nextInt();
                points[i++] = new Point(x, y);
            }
            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();

            // print and draw the line segments
            BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
            System.out.println(bruteCollinearPoints.numberOfSegments());
            for (LineSegment segment : bruteCollinearPoints.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

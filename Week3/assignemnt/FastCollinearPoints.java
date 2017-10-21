package assignment;


import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by mac on 21/10/2017.
 */
public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            else {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                }
            }
        }
        for (int i = 0; i < points.length - 3; i++) {
            Point[] restPoints = Arrays.copyOfRange(points, i + 1, points.length);
            MergeX.sort(restPoints, points[i].slopeOrder());
            for (int j = 0; j < restPoints.length - 2; j++) {
                Point[] point = {points[i], restPoints[j], restPoints[j + 1], restPoints[j + 2]};
                double slope0 = points[i].slopeTo(restPoints[j]);
                double slope1 = points[i].slopeTo(restPoints[j + 1]);
                double slope2 = points[i].slopeTo(restPoints[j + 2]);
                if (slope0 == slope1 && slope2 == slope1) {
                    Arrays.sort(point);
                    LineSegment line = new LineSegment(point[0], point[3]);
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
            in = new BufferedReader(new FileReader("/Users/mac/Desktop/collinear/input6.txt"));
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
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

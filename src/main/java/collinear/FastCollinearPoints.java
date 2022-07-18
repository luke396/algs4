package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author luke
 * @date 2022/7/16 15:20
 */


public class FastCollinearPoints {
    private final Queue<Point[]> collinearPoints = new Queue<>();
    private int numOfSegment;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        valid(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Comparator<Point> slopeOrder = p.slopeOrder();
            Point[] lefts = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(lefts, slopeOrder);
            addCollinearOfPoint(lefts, p);
        }
    }


    private void valid(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                valid(points[i], points[j]);
            }
        }
    }

    // is points repetition or null?
    private void valid(Point a, Point b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Input point can't be null!");
        }
        if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) { // if a and b are same
            throw new IllegalArgumentException("Input point can't repeat.");
        }
    }

    // find and enqueue all combination of collinear with p
    private void addCollinearOfPoint(Point[] points, Point p) {
        for (int i = 0; i < points.length; i++) {
            int numOfSameSlope = 0;
            while (p.slopeTo(points[i]) == p.slopeTo(points[i + numOfSameSlope + 1])) {
                numOfSameSlope += 1;
            }
            if (numOfSameSlope >= 4) {
                Point[] collinear = Arrays.copyOfRange(points, i, i + numOfSameSlope);
                collinearPoints.enqueue(collinear);
                numOfSegment += 1;
            }
        }
    }

    public int numberOfSegments() {        // the number of line segments
        return numOfSegment;
    }

    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[numOfSegment];
        int num = 0;
        while (!collinearPoints.isEmpty()) {
            Point[] collinear = collinearPoints.dequeue();
            LineSegment segment = new LineSegment(collinear[0], collinear[collinear.length - 1]);
            if (!isIn(segments, segment)) {
                segments[num] = segment;
                num++;
            }
        }
        return segments;
    }

    private static boolean isIn(LineSegment[] segments, LineSegment willIn) {
        for (LineSegment segment : segments) {
            if (segment != null && segment.toString().equals(willIn.toString())) return true;
        }
        return false;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
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
    }
}


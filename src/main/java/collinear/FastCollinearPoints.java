package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Luke on 2022/7/16 15:20
 *
 * <p>
 * Changed by luke on 13:52
 */


public class FastCollinearPoints {
    private final Queue<Point[]> collinearPoints = new Queue<>();
    private int numOfSegment;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException("Input point can't be null!");
        valid(points);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Comparator<Point> slopeOrder = p.slopeOrder();
            Point[] lefts = Arrays.copyOfRange(points, i, points.length); // "i" is the point p
            Arrays.sort(lefts, slopeOrder);
            addCollinearOfPoint(lefts, p);
        }
    }

    private void valid(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Input point can't be null!");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point a = points[i];
                Point b = points[j];
                if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Input point can't repeat.");
                }
            }
        }
    }

    // find and enqueue all combination of collinear with p
    private void addCollinearOfPoint(Point[] points, Point p) {
        for (int i = 1; i < points.length; i++) { // 0 is the p
            int numOfSameSlope = 0;
            while (i + numOfSameSlope + 1 < points.length && p.slopeTo(points[i]) == p.slopeTo(points[i + numOfSameSlope + 1])) {
                numOfSameSlope += 1;
                if (numOfSameSlope >= 3) {
                    // to include point p
                    Point[] collinear = Arrays.copyOfRange(points, i - 1, i + numOfSameSlope);
                    if (i - 1 != 0) {
                        collinear[0] = p;
                    }
                    collinearPoints.enqueue(collinear);
                    numOfSegment += 1;
                }
            }
        }
    }

    public int numberOfSegments() {        // the number of line segments
        return numOfSegment;
    }

    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[numOfSegment];
        int num = 0;
        for (Point[] collinear : collinearPoints) {
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
            if (segment != null && segment.equals(willIn)) return true;
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


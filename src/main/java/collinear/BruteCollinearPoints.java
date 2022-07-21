package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author luke
 */
public class BruteCollinearPoints {

    /**
     * combination of four points
     */

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    /**
     * finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        valid(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point p = points[i];
                    Point q = points[j];
                    Point r = points[k];
                    // if three points are not collinear, the forth is no matter
                    // so continue to change r.
                    // if "break", it will change q, ignore "r" left points.
                    if (!isCollinear(p, q, r)) {
                        continue;
                    }
                    for (int m = k + 1; m < points.length; m++) {
                        Point s = points[m];
                        if (isCollinear(p, q, r, s)) {
                            Point[] segment = new Point[]{p, q, r, s};
                            Arrays.sort(segment);
                            lineSegments.add(new LineSegment(segment[0], segment[3]));
                        }
                    }
                }
            }
        }
    }

    private void valid(Point[] points) {
        // to avoid java.lang.NullPointerException
        if (points == null) {
            throw new IllegalArgumentException("Input point can't be null!");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Input point can't be null!");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                // equal is the easiest way to test tow value same or not.
                // diff between "==":
                // https://stackoverflow.com/questions/7520432/what-is-the-difference-between-and-equals-in-java
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("Input point can't repeat.");
                }
            }
        }
    }


    /**
     * is three points collinear?
     */
    private boolean isCollinear(Point p, Point q, Point r) {
        return (p.slopeTo(q) == p.slopeTo(r));
    }

    /**
     * is four points collinear?
     */
    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        return (p.slopeTo(q) == p.slopeTo(r)) && (p.slopeTo(r) == p.slopeTo(s));
    }


    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }


    /**
     * the line segments
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
        }
    }
}

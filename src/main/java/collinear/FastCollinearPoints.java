package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author luke
 */

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private final ArrayList<Point[]> collinearPoints = new ArrayList<>();

    /**
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        valid(points);
        Point[] pointsSorted = points.clone();
        for (int i = 0; i < pointsSorted.length; i++) {
            Point p = pointsSorted[i];
            // lefts not contain "p"
            Point[] lefts = Arrays.copyOfRange(pointsSorted, i + 1, pointsSorted.length);
            Arrays.sort(lefts, p.slopeOrder());
            addCollinearOfPoint(lefts, p);
        }
    }


    private void valid(Point[] points) {
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
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("Input point can't repeat.");
                }
            }
        }
    }

    private void addCollinearOfPoint(Point[] points, Point p) {
        Point used = new Point(-1, -1);
        for (int i = 0; i < points.length; i++) {

            if (points[i].equals(used)) {
                continue;
            }

            // find points with same slope
            ArrayList<Point> sameSlope = new ArrayList<>();
            sameSlope.add(p);
            sameSlope.add(points[i]);
            int j = 1 + i;
            while (j < points.length && p.slopeTo(points[i]) == p.slopeTo(points[j])) {
                sameSlope.add(points[j]);
                j++;
            }

            if (sameSlope.size() >= 4) {

                Point[] collinear = sameSlope.toArray(new Point[0]);
                Arrays.sort(collinear);

                if (newCollinear(collinear)) {
                    collinearPoints.add(collinear);
                    LineSegment segment = new LineSegment(collinear[0], collinear[collinear.length - 1]);
                    lineSegments.add(segment);

                    // set points to used, avoiding repetition
                    // j has increased out of range when j to end
                    for (int m = j - 1; m > 0; m--) {
                        points[m] = used;
                    }
                }


            }
        }
    }

    /**
     * to test the collinear has in or not
     */
    private boolean newCollinear(Point[] collinear) {
        for (Point[] oldCollinear : collinearPoints) {
            boolean hasStart = false;
            boolean hasEnd = false;
            for (Point point : oldCollinear) {
                if (point.equals(collinear[0])) {
                    hasStart = true;
                } else if (point.equals(collinear[collinear.length - 1])) {
                    hasEnd = true;
                }
            }
            if (hasStart && hasEnd) {
                return false;
            }
        }
        return true;
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

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
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}


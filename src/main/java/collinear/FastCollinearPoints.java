package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author luke
 */

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();


    /**
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        valid(points);
        Point[] pointsSorted = points.clone();
        for (Point p : points) {
            Arrays.sort(pointsSorted, p.slopeOrder());
            addCollinearOfPoint(pointsSorted, p);
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
        int pointsLength = points.length;
        for (int j, i = 0; i < pointsLength; i = j) {
            // "i" is the back index
            // "j" is the front index
            // find points with same slope
            j = 1 + i;
            double iSlope = p.slopeTo(points[i]);
            while (j < points.length && (iSlope == p.slopeTo(points[j]))) {
                j++;
            }
            int numOfAdjacent = j - i;
            if (numOfAdjacent >= 3) {
                Point[] collinear = new Point[numOfAdjacent + 1];
                collinear[0] = p;
                System.arraycopy(points, i, collinear, 1, j - i);
                Arrays.sort(collinear);
                if (collinear[0] == p) {
                    lineSegments.add(new LineSegment(collinear[0], collinear[collinear.length - 1]));
                }
            }
        }
        // the difference between do-while and while is do-while loop guarantees one execution of the logic whereas the while does not.
    }


    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
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
            StdDraw.show();
        }
    }
}


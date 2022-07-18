// package collinear;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * @author luke
 */
public class BruteCollinearPoints {
    private int numOfSegments;

    /**
     * combination of four points
     */
    private final Queue<Point[]> collinearPoints = new Queue<>();

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        valid(points);
        // 规范提示用while替换for，但是我觉得for的可读性比while更好，不知道是不受我的偏见
        int pIndex = 0;
        while (pIndex < points.length) {
            Point p = points[pIndex];
            int qIndex = pIndex + 1;
            while (qIndex < points.length) {
                Point q = points[qIndex];
                int rIndex = qIndex + 1;
                while (rIndex < points.length) {
                    Point r = points[rIndex];
                    int sIndex = rIndex + 1;
                    if (!isCollinear(p, q, r)) { // if three points is not collinear, the four is not matter
                        break;
                    } else {
                        while (sIndex < points.length) {
                            Point s = points[sIndex];
                            if (isCollinear(p, q, r, s)) {
                                Point[] collinear;
                                collinear = new Point[]{p, q, r, s};
                                collinearPoints.enqueue(collinear);
                                numOfSegments += 1;
                            }
                            sIndex++;
                        }
                    }
                    rIndex++;
                }
                qIndex++;
            }
            pIndex++;
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
        if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException("Input point can't repeat.");
        }
    }

    private boolean isCollinear(Point p, Point q, Point r) { // is three points collinear?
        return (p.slopeTo(q) == p.slopeTo(r));
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) { // is four points collinear?
        return (p.slopeTo(q) == p.slopeTo(r)) && (p.slopeTo(r) == p.slopeTo(s) && (p.slopeTo(q) == p.slopeTo(s)));
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numOfSegments;
    }

    public LineSegment[] segments() {                // the line segments
        LineSegment[] segments = new LineSegment[numOfSegments];
        for (int i = 0; i < numOfSegments; i++) {
            Point[] collinear = collinearPoints.dequeue();
            // sort with x_label
            Arrays.sort(collinear);
            LineSegment segment = new LineSegment(collinear[0], collinear[collinear.length - 1]);
            if (!isIn(segments, segment)) {
                segments[i] = segment;
            }
        }
        return segments;
    }

    // is it already in? (to reduce repetition)
    private static boolean isIn(LineSegment[] segments, LineSegment willIn) {
        for (LineSegment segment : segments) {
            if (segment != null && segment.toString().equals(willIn.toString())) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(3, 3);
        Point p3 = new Point(4, 4);
        Point p4 = new Point(7, 7);
        Point p5 = new Point(6, 5);
        Point p6 = new Point(5, 5);
        Point p7 = new Point(7, 5);
        Point[] points;
        points = new Point[]{p1, p2, p3, p4, p5, p6, p7};
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("# of segments is:");
        StdOut.println(collinear.numOfSegments);
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            if (segment != null) {
                segment.draw();
                StdOut.println(segment.toString());
            }
        }
    }
}

package collinear;

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
        Arrays.sort(points); // sort by x_label
        Queue<Point[]> combinationsOf4 = combinationsOf4(points);
        Point p;
        Point q;
        Point r;
        Point s;
        while (!combinationsOf4.isEmpty()) {
            Point[] combination = combinationsOf4.dequeue();
            p = combination[0];
            q = combination[1];
            r = combination[2];
            s = combination[3];
            if (isCollinear(p, q, r, s)) {
                addCollinearPoints(p, q, r, s);
            }
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

    // find all collinear combinations of 4 from points
    private Queue<Point[]> combinationsOf4(Point[] points) {
        Queue<Point[]> combinationsOf4 = new Queue<>();
        int pIndex = 0;
        while (pIndex < points.length) {
            int qIndex = pIndex + 1;
            while (qIndex < points.length) {
                int rIndex = qIndex + 1;
                while (rIndex < points.length) {
                    Point p = points[pIndex];
                    Point q = points[qIndex];
                    Point r = points[rIndex];
                    // if three points is not collinear, the four is not matter
                    if (isCollinear(p, q, r)) {
                        int sIndex = rIndex + 1;
                        while (sIndex < points.length) {
                            Point s = points[sIndex];
                            Point[] combination = new Point[4];
                            combination[0] = p;
                            combination[1] = q;
                            combination[2] = r;
                            combination[3] = s;
                            combinationsOf4.enqueue(combination);
                            sIndex++;
                        }
                    }
                    rIndex++;
                }
                qIndex++;
            }
            pIndex++;
        }
        return combinationsOf4;
    }

    private boolean isCollinear(Point p, Point q, Point r) { // is three points collinear?
        return (p.slopeTo(q) == p.slopeTo(r));
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) { // is four points collinear?
        return (p.slopeTo(q) == p.slopeTo(r)) && (p.slopeTo(r) == p.slopeTo(s) && (p.slopeTo(q) == p.slopeTo(s)));
    }

    // add unrepeated collinear to collinearPoints.
    private void addCollinearPoints(Point p, Point q, Point r, Point s) {
        Point[] collinear;
        collinear = new Point[]{p, q, r, s};
        collinearPoints.enqueue(collinear);
        numOfSegments += 1;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numOfSegments;
    }

    public LineSegment[] segments() {                // the line segments
        LineSegment[] segments = new LineSegment[numOfSegments];
        int i = 0;
        while (!collinearPoints.isEmpty()) {
            Point[] collinear = collinearPoints.dequeue();
            LineSegment segment = new LineSegment(collinear[0], collinear[collinear.length - 1]);
            if (!isIn(segments, segment)) {
                segments[i] = segment;
                i++;
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
        for (Point point : points) {
            StdOut.println(point.toString());
        }
        StdOut.println("# of segments is:");
        StdOut.println(collinear.numOfSegments);
        StdOut.println("Segments finally is:");
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            if (segment != null) {
                StdOut.println(segment.toString());
            }
        }
    }
}

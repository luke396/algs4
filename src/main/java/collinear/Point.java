package collinear;


import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author luke
 */
public class Point implements Comparable<Point> {

    /**
     * *x-coordinate of this point*&#47;
     */
    private final int x;
    /**
     * * y-coordinate of this point*&#47;
     */
    private final int y;


    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double slope;
        if (this.x == that.x && this.y == that.y) {
            slope = Double.NEGATIVE_INFINITY;
        } else if (this.x != that.x && this.y == that.y) {
            slope = 0.0;
        } else if (this.x == that.x) {
            slope = Double.POSITIVE_INFINITY;
        } else {
            slope = (double) (that.y - this.y) / (that.x - this.x);
        }
        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        boolean equal = this.x == that.x && this.y == that.y;
        boolean less = (this.y < that.y) || (this.y == that.y && this.x < that.x);
        if (equal) {
            return 0;
        } else if (less) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */

    public Comparator<Point> slopeOrder() {
        return Comparator.comparingDouble(this::slopeTo);
        // lambda expression for short implement
        // reference:
        // https://stackoverflow.com/questions/20001427/double-colon-operator-in-java-8
    }

    /**
     * Returns a string representation of this point.
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point[] p = new Point[10];
        // ++i and i++ when use in loop like this, there is no difference.
        for (int i = 0; i < 10; ++i) {
            p[i] = new Point(i, i * i - 10 * i + 25);
        }
        for (int i = 0; i < 9; ++i) {
            StdOut.println(p[i].slopeTo(new Point(0, i * i)));
        }

        for (int i = 0; i < 10; ++i) {
            StdOut.println(p[i]);
        }
        StdOut.println();
        Arrays.sort(p, p[5].slopeOrder());
        for (int i = 0; i < 10; ++i) {
            StdOut.println(p[i]);
        }
    }
}
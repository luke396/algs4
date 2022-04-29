/******************************************************************************
 *  Compilation:  javac Point2D.java
 *  Execution:    java Point2D x0 y0 n
 *  Dependencies: StdDraw.java StdRandom.java
 *
 *  Immutable point data type for points in the plane.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * The {@code Point} class is an immutable data type to encapsulate a
 * two-dimensional point with real-value coordinates.
 * <p>
 * Note: in order to deal with the difference behavior of double and
 * Double with respect to -0.0 and +0.0, the Point2D constructor converts
 * any coordinates that are -0.0 to +0.0.
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

// java -cp "D:\Java\apache-maven-3.6.3\maven_repository\edu\princeton\cs\algs4\1.0.4\algs4-1.0.4.jar" src/main/java/Point2D.java 5 5 44
public final class Point2D implements Comparable<Point2D> {
    // implements 声明类Point2D要实现Comparable接口，实现接口中的所有方法(这里只有CompareTo)
    /*
     * 静态方法类似于函数，为了实现函数；实例方法，实现对数据类型的操作
     * 实例方法的存在，是依附于一个特定对象的，只针对这个对象进行操作（改变或者仅仅进行访问）
     * Comparable 是一个interface(接口)，用来描述类应该做什么而不去定义怎么做
     *
     *
     * Java是一种强类型语言，在调用方法时，编译器会确认方法是否存在，
     */
    /**
     * Compares two points by x-coordinate.
     */
    public static final Comparator<Point2D> X_ORDER = new XOrder(); // XOder类(在下文被写出，从Comparable继承)的实例，
    // 但好像并未被使用，设计出也许是为了方便后续外部调用
    // final 完结器, 表示不可变的

    /**
     * Compares two points by y-coordinate.
     */
    public static final Comparator<Point2D> Y_ORDER = new YOrder();

    /**
     * Compares two points by polar radius.
     */
    public static final Comparator<Point2D> R_ORDER = new ROrder();

    private final double x;    // x coordinate private, 如果需要查看则需要调用public方法
    private final double y;    // y coordinate

    /**
     * Initializes a new point (x, y).
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @throws IllegalArgumentException if either {@code x} or {@code y}
     *                                  is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY} or
     *                                  {@code Double.NEGATIVE_INFINITY}
     */
    public Point2D(double x, double y) { // 实例方法
        // 考虑输入(x, y)inf或者Nan的可能
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");

        // this 对类的当前实例的引用，有些类似python中的self
        if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
        else this.x = x; // 这里的x 就是上文的 private final double x;

        if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
        else this.y = y;
    }

    // 这几个返回值的方法应该是方便其他调用

    /**
     * Returns the x-coordinate.
     *
     * @return the x-coordinate
     */
    public double x() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return the y-coordinate
     */
    public double y() {
        return y;
    }

    /**
     * Returns the polar radius of this point.
     *
     * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
     */
    public double r() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the angle of this point in polar coordinates.
     *
     * @return the angle (in radians) of this point in polar coordiantes (between –&pi; and &pi;)
     */
    public double theta() {
        return Math.atan2(y, x);
    }

    /**
     * Returns the angle between this point and that point.
     *
     * @return the angle in radians (between –&pi; and &pi;) between this point and that point (0 if equal)
     */
    private double angleTo(Point2D that) { // that是另一个输入的 (x, y)
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        return Math.atan2(dy, dx);
    }

    /**
     * Returns true if a→b→c is a counterclockwise turn.
     *
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a→b→c is a { clockwise, collinear; counterclocwise } turn.
     */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = area2(a, b, c);
        if (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else return 0;
    }

    /**
     * Returns twice the signed area of the triangle a-b-c.
     *
     * @param a first point
     * @param b second point
     * @param c third point
     * @return twice the signed area of the triangle a-b-c
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        // 这个 area2 不能被当作方法被上一个静态函数调用吗？
        // 我修改了一下，看起来没啥差别，class中的静态函数应该是可以互相调用的
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x); // 以此来判断三点的方向
    }

    /**
     * Returns the Euclidean distance between this point and that point.
     *
     * @param that the other point
     * @return the Euclidean distance between this point and that point
     */
    public double distanceTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns the square of the Euclidean distance between this point and that point.
     *
     * @param that the other point
     * @return the square of the Euclidean distance between this point and that point
     */
    public double distanceSquaredTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx * dx + dy * dy;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
     * if and only if either {@code y0 < y1} or if {@code y0 == y1} and {@code x0 < x1}.
     *
     * @param that the other point
     * @return the value {@code 0} if this string is equal to the argument
     * string (precisely when {@code equals()} returns {@code true});
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point2D that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    /**
     * Compares two points by polar angle (between 0 and 2&pi;) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }

    /**
     * Compares two points by atan2() angle (between –&pi; and &pi;) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> atan2Order() {
        return new Atan2Order();
    }

    /**
     * Compares two points by distance to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> distanceToOrder() {
        return new DistanceToOrder();
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }

    // compare points according to their polar radius
    private static class ROrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double delta = (p.x * p.x + p.y * p.y) - (q.x * q.x + q.y * q.y);
            if (delta < 0) return -1;
            if (delta > 0) return +1;
            return 0;
        }
    }

    // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
    private class Atan2Order implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            if (angle1 < angle2) return -1;
            else if (angle1 > angle2) return +1;
            else return 0;
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else return 0;
            } else return -ccw(Point2D.this, q1, q2);     // both above or below

            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    // compare points according to their distance to this point
    private class DistanceToOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            if (dist1 < dist2) return -1;
            else if (dist1 > dist2) return +1;
            else return 0;
        }
    }


    /**
     * Compares this point to the specified point.
     *
     * @param other the other point
     * @return {@code true} if this point equals {@code other};
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Point2D that = (Point2D) other;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Return a string representation of this point.
     *
     * @return a string representation of this point in the format (x, y)
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns an integer hash code for this point.
     *
     * @return an integer hash code for this point
     */
    @Override
    public int hashCode() {
        int hashX = ((Double) x).hashCode();
        int hashY = ((Double) y).hashCode();
        return 31 * hashX + hashY;
    }

    /**
     * Plot this point using standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Plot a line from this point to that point using standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point2D that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    /**
     * Unit tests the point data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(800, 800); // set the size of the drawing canvas
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y); // 生成n个point 2D
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.polarOrder());
        for (int i = 0; i < n; i++) {
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(100); // 毫秒(milliseconds)为单位进行等待
        }
    }
}

/******************************************************************************
 *  Compilation:  javac Sattolo.java
 *  Execution:    java Sattolo < list.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/11model/cards.txt
 *
 *  Reads in a list of strings and prints a uniformly random cycle
 *  using Sattolo's algorithm under the assumption that Math.random()
 *  generates independent and uniformly distributed numbers between
 *  0 and 1.
 *
 *  %  echo 0 1 2 3 4 | java Sattolo
 *  1
 *  2
 *  4
 *  0
 *  3
 * The {@code Sattolo} class provides a client for reading a sequence
 * of <em>n</em> strings and computing a <em>unifomly random cycle</em>
 * of length <em>n</em> using Sattolo's algorithm.
 * This algorithm guarantees to produce a unifomly random cycle under
 * the assumption that {@code Math.random()} generates independent and
 * uniformly distributed numbers between 0 and 1.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 ******************************************************************************/


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Sattolo {

    // this class should not be instantiated
    // 不应实例化此类
    private Sattolo() {
    }

    // 将代码相关文档写在方法上方

    /**
     * Rearranges an array of objects to be a uniformly random cycle
     * (under the assumption that {@code Math.random()} generates independent
     * and uniformly distributed numbers between 0 and 1).
     *
     * @param a the array to be rearranged
     * @see StdRandom
     */
    public static void cycle(Object[] a) {
        int n = a.length;
        // i-- 先赋值，再减一
        for (int i = n; i > 1; i--) {
            // choose index uniformly in [0, i-1)
            // 从<0到i-1>中选择一个随机数, 即随机选择一个数组下标
            int r = (int) (Math.random() * (i - 1));
            Object swap = a[r]; // swap - 交换
            a[r] = a[i - 1];
            a[i - 1] = swap;
            // a[i-1]也就是最后一个元素，随机选取一个元素，交换到最后一位，直到所有元素都被遍历
        }
    }

    /**
     * Reads in a sequence of strings from standard input, shuffles
     * them, and prints out the results.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // read in the data from command-line's input
        String[] a = StdIn.readAllStrings();

        // shuffle the array
        Sattolo.cycle(a);

        // print results.
        // for (int i = 0; i < a.length; i++)
        //    StdOut.println(a[i]);
        for (String s : a) StdOut.println(s); // 推荐的增强版for循环
    }
}

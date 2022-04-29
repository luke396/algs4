import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] perOfOpen; // the percentage of open site when percolation


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        perOfOpen = new double[trials];
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        Percolation perc = new Percolation(n);

        for (int i = 0; i < trials; i++) {
            for (int j = 0; j < Math.pow(n, 2); j++) {
                int row = StdRandom.uniform(1, n + 1); // random location uniformly [1, n+1)
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                } // 不要在for循环内改变控制循环的变量
                if (perc.percolates()) { // when percolating, record the num.
                    perOfOpen[i] = perc.numberOfOpenSites() / Math.pow(n, 2);
                    break;
                }
            }
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        // x is the fraction fo the open site
        return StdStats.mean(perOfOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(perOfOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double stddev = stddev();
        double mean = mean();
        return mean - CONFIDENCE_95 * stddev / Math.sqrt(perOfOpen.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double stddev = stddev();
        double mean = mean();
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(perOfOpen.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) { // 如果参数不是2个，抛出异常
            throw new IllegalArgumentException("Usage: java PercolationStats <n> <trials>");
        } else {
            int n = StdIn.readInt();
            int t = StdIn.readInt();
            PercolationStats test = new PercolationStats(n, t);
            StdOut.printf("mean=%.5f\n", test.mean());
            StdOut.printf("stddev=%.5f\n", test.stddev());
            StdOut.printf("95%% confidence interval=[%.5f, %.5f]\n", test.confidenceLo(), test.confidenceHi()); // 这里的%需要’%%‘进行转义
        }
    }
}
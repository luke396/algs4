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

        for (int i = 0; i < trials; i++) { // i to control the number of trials
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1); // random location uniformly [1, n+1)
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            // when percolating, record the num.
            perOfOpen[i] = (double) perc.numberOfOpenSites() / (n * n);
            // to ensure the percentage is a double, or the '/' with tow int will be int
        }
    }


    // sample mean of percolation threshold
    public double mean() {
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
        if (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            int t = StdIn.readInt();
            if (n <= 0 || t <= 0) {
                throw new IllegalArgumentException("n and t must be greater than 0");
            }
            PercolationStats test = new PercolationStats(n, t);
            StdOut.printf("mean=%.5f\n", test.mean());
            StdOut.printf("stddev=%.5f\n", test.stddev());
            StdOut.printf("95%% confidence interval=[%.5f, %.5f]\n", test.confidenceLo(), test.confidenceHi()); // 这里的%需要’%%‘进行转义
        }
    }
}
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] perOfOpen; // the percentage of open site when percolation


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        perOfOpen = new double[trials];

        for (int i = 0; i < trials; i++) { // i to control the number of trials
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1); // random location uniformly [1, n+1)
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            // when percolating, record the num.
            perOfOpen[i] = (double) percolation.numberOfOpenSites() / (n * n);
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
        int n = Integer.parseInt(args[0]); // for no command line argument
        int t = Integer.parseInt(args[1]);

        if (!StdIn.isEmpty()) {
            n = StdIn.readInt();
            t = StdIn.readInt();
        }

        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.printf("mean=%.5f\n", percolationStats.mean());
        StdOut.printf("stddev=%.5f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval=[%.5f, %.5f]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi()); // 这里的%需要’%%‘进行转义
    }
}

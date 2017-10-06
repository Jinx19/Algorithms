import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by mac on 06/10/2017.
 */
public class PercolationStats {
    private final int trials;
    private final double[] x;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }

            x[i] = percolation.numberOfOpenSites() / (double) (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        double mean = StdStats.mean(x);
        double stddev = StdStats.stddev(x);
        return mean - (1.96 * stddev / Math.sqrt(trials));

    }

    public double confidenceHi() {
        double mean = StdStats.mean(x);
        double stddev = StdStats.stddev(x);
        return mean + (1.96 * stddev / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException();
        int n = new Integer(args[0]).intValue();
        int trials = new Integer(args[0]).intValue();
        PercolationStats percolationSats = new PercolationStats(n, trials);
        System.out.println("mean=" + percolationSats.mean());
        System.out.println("stddev=" + percolationSats.stddev());
        System.out.println("95% confidence interval = ["
                + percolationSats.confidenceLo()
                + "," + percolationSats.confidenceHi()
                + "]");
    }
}

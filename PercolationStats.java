/* ****************************************************************************
 *  Name: Vu Tuan Anh
 *  Date: June 17
 *  Description: Assignment 1
 *****************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final int n;
    private final int trials;
    private final double[] x;
    private double cachedMean;
    private double cachedStd;

    public PercolationStats(int n,
                            int trials) {
        this.n = n;
        this.trials = trials;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trails must be possitive");

        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            x[i] = simulate();
        }

        cachedMean = -1.0;
        cachedStd = -1.0;
    }

    private double simulate() {
        Percolation percolation = new Percolation(this.n);

        int openedSites = 0;
        while (!percolation.percolates()) {
            // random uniformly
            int uniformIdx = StdRandom.uniform(this.n * this.n);

            // open
            int idxToOpen = uniformIdx + 1 + this.n;
            int row = (int) Math.floor((idxToOpen - 1) / (double) this.n);
            int col = (idxToOpen - 1) % this.n + 1;

            if (percolation.isOpen(row, col))
                continue;

            percolation.open(row, col);
            openedSites++;
        }
        return openedSites / (double) (this.n * this.n);
    }

    public double mean() {
        if (cachedMean < 0)
            cachedMean = StdStats.mean(x);

        return cachedMean;
    }

    public double stddev() {
        if (cachedStd < 0)
            cachedStd = StdStats.stddev(x);

        return cachedStd;
    }

    public double confidenceLo() {
        if (cachedMean < 0)
            mean();

        if (cachedStd < 0)
            stddev();

        return cachedMean - CONFIDENCE_95 * cachedStd / Math.sqrt(trials);
    }

    public double confidenceHi() {
        if (cachedMean < 0)
            mean();

        if (cachedStd < 0)
            stddev();

        return cachedMean + CONFIDENCE_95 * cachedStd / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = 10;
        int trials = 100;
        if (args.length >= 1)
            // Parse the string argument into an integer value.
            n = Integer.parseInt(args[0]);
        if (args.length == 2)
            trials = Integer.parseInt(args[1]);
        PercolationStats stat = new PercolationStats(n, trials);
        System.out.printf("%-25s = %f%n", "mean", stat.mean());
        System.out.printf("%-25s = %f%n", "stddev", stat.stddev());
        System.out.printf("%-25s = [%f, %f]%n", "95% confidence interval",
                          stat.confidenceLo(),
                          stat.confidenceHi());
    }
}

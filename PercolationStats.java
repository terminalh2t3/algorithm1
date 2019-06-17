/* *****************************************************************************
 *  Name: Vu Tuan Anh
 *  Date: June 17
 *  Description: Assignment 1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;
import java.util.List;

public class PercolationStats {
    private int n;
    private int trials;
    private double[] x;

    public PercolationStats(int n,
                            int trials)    // perform trials independent experiments on an n-by-n grid
    {
        this.n = n;
        this.trials = trials;
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException("n and trails must be possitive");

        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            x[i] = simulate();
        }
    }

    private double simulate() {
        Percolation percolation = new Percolation(this.n);
        List<Integer> blockedSites = new ArrayList<Integer>();
        for (int i = 1; i <= this.n * this.n; i++) {
            blockedSites.add(i);
        }


        int openedSites = 0;
        while (!percolation.percolates()) {
            // random uniformly
            int uniformIdx = StdRandom.uniform(blockedSites.size());
            // open
            percolation.open(blockedSites.get(uniformIdx));
            // remove from blockedSites
            blockedSites.remove(uniformIdx);
            openedSites++;
        }
        return openedSites / (double) (this.n * this.n);
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(x);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(x);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double xMean = mean();
        double xStd = stddev();
        return xMean - 1.96 * xStd / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double xMean = mean();
        double xStd = stddev();
        return xMean + 1.96 * xStd / Math.sqrt(trials);
    }

    public static void main(String[] args)        // test client (described below)
    {
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

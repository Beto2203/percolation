/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int numTrials;
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) {
            throw new IllegalArgumentException("Provided input must be greater than 0");
        }
        numTrials = trials;
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                perc.open(row, col);
            }
            results[i] = (double) perc.numberOfOpenSites() / n * n;
        }
    }

        // sample mean of percolation threshold
        public double mean() {
            return StdStats.mean(results);
        }

        // sample standard deviation of percolation threshold
        public double stddev() {
            return StdStats.stddev(results);
        }

        // low endpoint of 95% confidence interval
        public double confidenceLo() {
            return mean() - 1.960*(stddev()/Math.sqrt(numTrials));
        }

        // high endpoint of 95% confidence interval
        public double confidenceHi() {
            return mean() + 1.960*(stddev()/Math.sqrt(numTrials));
        }

        // test client (see below)
        public static void main(String[]args) {

        }

    }
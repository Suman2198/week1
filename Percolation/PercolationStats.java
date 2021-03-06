import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  
    private int trials;

 
    private double[] thresholdL;

    
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }

        if (trials < 1) {
            throw new IllegalArgumentException("You must run percolation at least once");
        }

        this.trials = trials;
        thresholdL = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                percolation.open(row, col);
            }

            thresholdL[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    
    public double mean()
    {
        return StdStats.mean(thresholdL);
    }

  
    public double stddev()
    {
        return StdStats.stddev(thresholdL);
    }

    
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

 
    public static void main(String[] args)
    {
        int gridL = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(gridL, trials);

        StdOut.println("mean = "+ stats.mean());
        StdOut.println("stddev = "+ stats.stddev());
        StdOut.println("95% confidence interval = "+ stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    
    private int gridL;

    
    private boolean[] sites;

    
    private int openSitesN;

    
    private int virtualTIndex;

    
    private int virtualBIndex;

   
  
    private WeightedQuickUnionUF ufForPercolation;

    
  
    private WeightedQuickUnionUF ufForFullness;

    
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }

        gridL = n;
        int gridSize = (n * n) + 2;
        sites = new boolean[gridSize];
        openSitesN = 0;

        
        virtualTIndex = 0;
        virtualBIndex = (gridL * gridL) + 1;
        sites[virtualTIndex] = true;
        sites[virtualBIndex] = false;

        ufForPercolation = new WeightedQuickUnionUF(gridSize);
        ufForFullness = new WeightedQuickUnionUF(gridSize);

        
        for (int col = 1; col <= gridL; col++) {
            int rowT = 1;
            int siteTIndex = getIndexByRowAndColumn(rowT, col);
            ufForPercolation.union(virtualTIndex, siteTIndex);
            ufForFullness.union(virtualTIndex, siteTIndex);

            int rowB = gridL;
            int siteBIndex = getIndexByRowAndColumn(rowB, col);
            ufForPercolation.union(virtualBIndex, siteBIndex);
        }
    }

    
    public void open(int row, int col)
    {
        int siteIndex = getIndexByRowAndColumn(row, col);
        if (sites[siteIndex]) {
            return;
        }

        openSitesN++;
        sites[siteIndex] = true;

        
        if (col > 1 && isOpen(row, col - 1)) {
            int siteLeftIndex = getIndexByRowAndColumn(row, col - 1);
            ufForPercolation.union(siteIndex, siteLeftIndex);
            ufForFullness.union(siteIndex, siteLeftIndex);
        }

     
        if (col < gridL && isOpen(row, col + 1)) {
            int siteLeftIndex = getIndexByRowAndColumn(row, col + 1);
            ufForPercolation.union(siteIndex, siteLeftIndex);
            ufForFullness.union(siteIndex, siteLeftIndex);
        }

    
        if (row > 1 && isOpen(row - 1, col)) {
            int siteLeftIndex = getIndexByRowAndColumn(row - 1, col);
            ufForPercolation.union(siteIndex, siteLeftIndex);
            ufForFullness.union(siteIndex, siteLeftIndex);
        }

     
        if (row < gridL && isOpen(row + 1, col)) {
            int siteLeftIndex = getIndexByRowAndColumn(row + 1, col);
            ufForPercolation.union(siteIndex, siteLeftIndex);
            ufForFullness.union(siteIndex, siteLeftIndex);
        }
    }

    
    public boolean isOpen(int row, int col)
    {
        int siteIndex = getIndexByRowAndColumn(row, col);

        return sites[siteIndex];
    }


    public boolean isFull(int row, int col)
    {
        int siteIndex = getIndexByRowAndColumn(row, col);

        return (isOpen(row, col) && ufForFullness.connected(virtualTIndex, siteIndex));
    }

   
    public int numberOfOpenSites()
    {
        return openSitesN;
    }

    
    public boolean percolates()
    {
        
        if (gridL == 1) {
            int siteIndex = getIndexByRowAndColumn(1, 1);
            return sites[siteIndex];
        }

        return ufForPercolation.connected(virtualTIndex, virtualBIndex);
    }

    private int getIndexByRowAndColumn(int row, int col)
    {
        validateBounds(row, col);

        return ((row - 1) * gridL) + col;
    }

    
    private void validateBounds(int row, int col)
    {
        if (row > gridL || row < 1) {
            throw new IndexOutOfBoundsException("Row index is out of bounds");
        }

        if (col > gridL || col < 1) {
            throw new IndexOutOfBoundsException("Column index is out of bounds");
        }
    }

  
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(2);

        StdOut.println("percolates = " + percolation.percolates());

        StdOut.println("isOpen(1, 2) = " + percolation.isOpen(1, 2));
        StdOut.println("isFull(1, 2) = " + percolation.isFull(1, 2));
        StdOut.println("open(1, 2)");
        percolation.open(1, 2);
        StdOut.println("isOpen(1, 2) = " + percolation.isOpen(1, 2));
        StdOut.println("isFull(1, 2) = " + percolation.isFull(1, 2));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());

        StdOut.println("isOpen(2, 1) = " + percolation.isOpen(2, 1));
        StdOut.println("isFull(2, 1) = " + percolation.isFull(2, 1));
        StdOut.println("open(2, 1)");
        percolation.open(2, 1);
        StdOut.println("isOpen(2, 1) = " + percolation.isOpen(2, 1));
        StdOut.println("isFull(2, 1) = " + percolation.isFull(2, 1));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());

        StdOut.println("isOpen(1, 1) = " + percolation.isOpen(1, 1));
        StdOut.println("isFull(1, 1) = " + percolation.isFull(1, 1));
        StdOut.println("open(1, 1)");
        percolation.open(1, 1);
        StdOut.println("isOpen(1, 1) = " + percolation.isOpen(1, 1));
        StdOut.println("isFull(1, 1) = " + percolation.isFull(1, 1));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());
    }
}

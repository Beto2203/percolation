/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int[][] grid;
    private int numOpen;
    private int nSize;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) { throw new IllegalArgumentException("Input must be bigger than zero"); }
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new int[n][n];
        nSize = n;
        for (int i = 1; i <= n; i++) {
            uf.union(0, 3);
            // uf.union(n*n + 1, (n*n - n) + i);
        }
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[row-1][col-1] = 1;
            numOpen++;

            if (row - 2 >= 0 && isOpen(row-1, col)) {
                uf.union((row * col), (row-1)*(col));
            }
            if (row < nSize && isOpen(row+1, col)) {
                uf.union((row * col), (row+1)*(col));
            }
            if (col - 2 >= 0 && isOpen(row, col-1)) {
                uf.union((row * col), (row)*(col-1));
            }
            if (col < nSize && isOpen(row, col+1)) {
                uf.union((row * col), (row)*(col+1));
            }
        }

    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[row-1][col-1] == 1;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        return uf.find(row*col) == uf.find(0);
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }
    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(nSize*nSize+1);
    }
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > nSize || col > nSize) {
            throw new IllegalArgumentException("Entered coordinates out of bounds");
        }
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation dummy = new Percolation(10);

        dummy.open(1, 1);
    }
}

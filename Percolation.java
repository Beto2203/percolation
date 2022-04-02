/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF checkPercolation;
    private int[] grid;
    private int numOpen;
    private int nSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Input must be bigger than zero");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        checkPercolation = new WeightedQuickUnionUF(n * n + 2);
        grid = new int[n * n + 1];
        nSize = n;
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int rowPosition = (row - 1) * nSize;
        if (!isOpen(row, col)) {
            grid[rowPosition + col] = 1;
            numOpen++;

            if (row - 1 > 0 && isOpen(row - 1, col)) {
                uf.union(rowPosition - nSize + col, rowPosition + col);
                checkPercolation.union(rowPosition - nSize + col, rowPosition + col);
            }
            if (row + 1 <= nSize && isOpen(row + 1, col)) {
                uf.union(rowPosition + nSize + col, rowPosition + col);
                checkPercolation.union(rowPosition + nSize + col, rowPosition + col);
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                uf.union(rowPosition + col, rowPosition + col - 1);
                checkPercolation.union(rowPosition + col, rowPosition + col - 1);
            }
            if (col + 1 <= nSize && isOpen(row, col + 1)) {
                uf.union(rowPosition + col, rowPosition + col + 1);
                checkPercolation.union(rowPosition + col, rowPosition + col + 1);
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[(row - 1) * nSize + col] == 1;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        return (isOpen(row, col) &&
                uf.find(0) == uf.find((row - 1) * nSize + col));
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }
    // does the system percolate?
    public boolean percolates() {
        return checkPercolation.find(0) == checkPercolation.find(nSize * nSize + 1);
    }
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > nSize || col > nSize) {
            throw new IllegalArgumentException("Entered coordinates out of bounds");
        }
    }
    // test client (optional)
    public static void main(String[] args) {
    }
}

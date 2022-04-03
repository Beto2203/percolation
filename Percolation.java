import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF checkPercolation;
    private int nSize;
    private boolean[] grid;
    private int numOpen;
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Input must be bigger than zero");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        checkPercolation = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n * n + 1];
        nSize = n;
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            checkPercolation.union(0, i);
            checkPercolation.union(n * n + 1, n * n - n + i);
        }
    }
    public void open(int row, int col) {
        validate(row, col);

        int rowPosition = (row - 1) * nSize;
        if (!isOpen(row, col)) {
            grid[rowPosition + col] = true;
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
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[(row - 1) * nSize + col];
    }
    public boolean isFull(int row, int col) {
        validate(row, col);

        return (isOpen(row, col) &&
                uf.find(0) == uf.find((row - 1) * nSize + col));
    }
    public int numberOfOpenSites() {
        return numOpen;
    }
    public boolean percolates() {
        return checkPercolation.find(0) == checkPercolation.find(nSize * nSize + 1) && numOpen > 0;
    }
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > nSize || col > nSize) {
            throw new IllegalArgumentException("Entered coordinates out of bounds");
        }
    }
}
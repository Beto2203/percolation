import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int nSize;
    private byte[] grid;
    private byte[] root;
    private int numOpen;
    private byte percolationChecker;
    public Percolation(int n) {
        if (n < 1) { throw new IllegalArgumentException("Input must be bigger than zero"); }
        uf = new WeightedQuickUnionUF(n * n + 1);
        grid = new byte[n * n + 1];
        root = new byte[n*n + 1];
        nSize = n;
        percolationChecker = 0;
        for (int i = 1; i <= n*n; i++) {
            root[i] = 0b0;
            grid[i] = 0b0;
            if (i <= n) {
                grid[i] |= 0b10;
                uf.union(0, i);
            }
            else if (i > n*(n-1)) { grid[i] |= 0b100; }
        }
    }
    public void open(int row, int col) {
        validate(row, col);
        byte updateRoot = 0;
        int rowPosition = (row - 1) * nSize;
        if (!isOpen(row, col)) {
            grid[rowPosition + col] |= 0b1;
            numOpen++;
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                updateRoot |= root[uf.find(rowPosition - nSize + col)];
                uf.union(rowPosition - nSize + col, rowPosition + col);
                grid[rowPosition + col] |= grid[rowPosition - nSize + col];
            }
            if (row + 1 <= nSize && isOpen(row + 1, col)) {
                updateRoot |= root[uf.find(rowPosition + nSize + col)];
                uf.union(rowPosition + nSize + col, rowPosition + col);
                grid[rowPosition + col] |= grid[rowPosition + nSize + col];
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                updateRoot |= root[uf.find(rowPosition + col - 1)];
                uf.union(rowPosition + col, rowPosition + col - 1);
                grid[rowPosition + col] |= grid[rowPosition + col - 1];
            }
            if (col + 1 <= nSize && isOpen(row, col + 1)) {
                updateRoot |= root[uf.find(rowPosition + col + 1)];
                uf.union(rowPosition + col, rowPosition + col + 1);
                grid[rowPosition + col] |= grid[rowPosition + col + 1];
            }
            updateRoot |= grid[rowPosition + col];
            root[uf.find(rowPosition + col)] |= updateRoot;

            if (row - 1 > 0 && isOpen(row - 1, col)) {
               root[uf.find(rowPosition - nSize + col)] |= updateRoot;

            }
            if (row + 1 <= nSize && isOpen(row + 1, col)) {
                root[uf.find(rowPosition + nSize + col)] |= updateRoot;

            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                root[uf.find(rowPosition + col - 1)] |= updateRoot;

            }
            if (col + 1 <= nSize && isOpen(row, col + 1)) {
                root[uf.find(rowPosition + col + 1)] |= updateRoot;
            }

            if (percolationChecker != 0b111) {
                percolationChecker = root[uf.find(rowPosition + col)];
            }
        }
    }
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return (grid[(row - 1) * nSize + col] & 0b1) == 0b1;
    }
    public boolean isFull(int row, int col) {
        validate(row, col);

        return isOpen(row, col) && uf.find(0) == uf.find((row-1)*nSize + col);
    }
    public int numberOfOpenSites() {
        return numOpen;
    }
    public boolean percolates() {
        return percolationChecker == 0b111 || (nSize == 1 && numOpen > 0);
    }
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > nSize || col > nSize) {
            throw new IllegalArgumentException("Entered coordinates out of bounds");
        }
    }
}
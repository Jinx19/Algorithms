/**
 * Created by mac on 06/10/2017.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int count;
    private boolean[][] site;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private boolean isPercolates;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        count = 0;
        this.n = n;
        site = new boolean[n + 1][n + 1];
        isPercolates = false;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                site[i][j] = false;
            }
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("(" + row + "," + col + ")is outside");
        }
        if (!isOpen(row, col)) {
            site[row][col] = true;
            int p = (row - 1) * n + col;
            if (row == 1) {
                weightedQuickUnionUF.union(p, 0);
            }
            if (row - 1 >= 1) {
                int q = (row - 2) * n + col;
                if (isOpen(row - 1, col)) {
                    weightedQuickUnionUF.union(p, q);
                }
            }
            if (row + 1 <= n) {
                int q = row * n + col;
                if (isOpen(row + 1, col)) {
                    weightedQuickUnionUF.union(p, q);
                }
            }
            if (col - 1 >= 1) {
                int q = p - 1;
                if (isOpen(row, col - 1)) {
                    weightedQuickUnionUF.union(p, q);
                }
            }
            if (col + 1 <= n) {
                int q = p + 1;
                if (isOpen(row, col + 1)) {
                    weightedQuickUnionUF.union(p, q);
                }
            }
            count++;
        }

    }

    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("(" + row + "," + col + ")is outside");
        }
        return site[row][col];
    }

    public boolean isFull(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException("(" + row + "," + col + ")is outside");
        }
        int index = ((row - 1) * n) + col;
        return weightedQuickUnionUF.connected(0, index);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i)) {
                isPercolates = true;
                break;
            }
        }
        return isPercolates;
    }
}


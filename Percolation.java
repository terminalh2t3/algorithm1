/* ****************************************************************************
 *  Name: Vu Tuan Anh
 *  Date: June 16
 *  Description: Assignment 1
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private boolean[] siteMap;
    private final int n;
    private final int virtualTopIdx;
    private final int virtualBotIdx;

    public Percolation(int n)  // create n-by-n grid, with all sites blocked
    {
        if (n < 1)
            throw new IllegalArgumentException();
        // We add 2 element here to create virtual top and bottom elements
        // Also we add more 2 virtual rows to corrects the visualization
        grid = new WeightedQuickUnionUF(n * n + 2 * n + 2);
        siteMap = new boolean[n * n + 2 * n + 2];

        virtualTopIdx = 0;
        virtualBotIdx = siteMap.length - 1;

        for (int i = 0; i < siteMap.length; i++) {
            siteMap[i] = false;  // blocked
        }

        // Connect virtual elements to top and bottom elements
        for (int i = 0; i < n; i++) {
            grid.union(virtualTopIdx, i + 1);
            grid.union(virtualBotIdx, virtualBotIdx - i - 1);
            siteMap[i + 1] = true;
            siteMap[virtualBotIdx - i - 1] = true;
        }
        this.n = n;
    }

    private int getIndex(int row, int col) {
        if (row < 0 || row > n + 1 || col < 1 || col > n) {
            return -1;
        }
        return n * row + col;
    }

    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int idx = getIndex(row, col);
        // System.out.println("Open index: " + idx);
        siteMap[idx] = true;
        int[] adjIndices = getAdjacentOpenSites(row, col);
        for (int adjIdx : adjIndices) {
            if (adjIdx != -1 && isOpen(adjIdx))
                grid.union(idx, adjIdx);
        }
    }

    private int[] getAdjacentOpenSites(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int top = getIndex(row - 1, col);
        int left = getIndex(row, col + 1);
        int bottom = getIndex(row + 1, col);
        int right = getIndex(row, col - 1);
        int[] adjSites = { top, left, bottom, right };

        return adjSites;
    }

    private boolean isOpen(int idx) {
        return siteMap[idx];
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        return siteMap[getIndex(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int idx = getIndex(row, col);
        return grid.connected(0, idx);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        int numOpenSites = 0;
        for (int i = 0; i < siteMap.length; i++) {
            if (siteMap[i])
                numOpenSites++;
        }
        return numOpenSites - 2 * n;
    }

    public boolean percolates()              // does the system percolate?
    {
        return grid.connected(virtualTopIdx, virtualBotIdx);
    }
}

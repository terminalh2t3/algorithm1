/* *****************************************************************************
 *  Name: Vu Tuan Anh
 *  Date: June 16
 *  Description: Assignment 1
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] siteMap;
    private int n;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        // We add 2 element here to create virtual top and bottom elements
        grid = new WeightedQuickUnionUF(n * n + 2);
        siteMap = new boolean[n * n + 2];
        for (int i = 0; i < siteMap.length; i++) {
            siteMap[i] = false;  // blocked
        }

        // Connect virtual elements to top and bottom elements
        for (int i = 0; i < n; i++) {
            grid.union(0, i + 1);
            grid.union(n * n + 1, n - i);
        }
        this.n = n;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        int idx = getIndex(row, col);
        siteMap[idx] = true;
        List<Integer> adjIndices = getAdjacentOpenSites(row, col);
        for (int adjIdx : adjIndices) {
            grid.union(idx, adjIdx);
        }
    }

    private int getIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            return -1;
        }
        return (row - 1) * n + col;
    }

    private List<Integer> getAdjacentOpenSites(int row, int col) {
        int top = getIndex(row - 1, col);
        int left = getIndex(row, col + 1);
        int bottom = getIndex(row + 1, col);
        int right = getIndex(row, col - 1);
        int[] adjSites = new int[] { top, left, bottom, right };

        List<Integer> adjOpenSites = new ArrayList<Integer>();

        for (int s : adjSites) {
            if (s != -1 && isOpen(s))
                adjOpenSites.add(s);
        }
        return adjOpenSites;
    }

    private boolean isOpen(int idx) {
        return siteMap[idx];
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return siteMap[getIndex(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
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
        return numOpenSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return grid.connected(0, n * n - 1);
    }
}

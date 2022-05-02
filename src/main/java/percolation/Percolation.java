package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // block is no connection, and open is connection
    // this is a dynamic programming problem
    // id is using to represent the uf id, id 1 is connected to the uf 1, uf 0 is the virtual top.

    // Remember to see the api of what will be used, it will reduce a lot of repetition.
    private final boolean[][] grid; // true is open, false is blocked
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        uf = new WeightedQuickUnionUF(n * n + 2); // initialize n closed sites. + two virtual sites
        // so the site is from 1 to n^2
        grid = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) { // By convention, row and col is from 1 to n
        validate(row, col);

        if (grid[row - 1][col - 1]) {
            return;
        }
        grid[row - 1][col - 1] = true; // open the site

        int thisId = toId(row, col);

        // if the site is on the top row, connect it to the virtual top
        if (row == 1) {
            uf.union(thisId, 0);
        }

        // if the site is on the bottom row, connect it to the virtual bottom
        if (row == grid.length) {
            uf.union(thisId, grid.length * grid.length + 1);
        }

        // to see if the site around open and connect to the open site
        int[] up = {row + 1, col};
        int[] down = {row - 1, col};
        int[] left = {row, col - 1};
        int[] right = {row, col + 1};

        connectAround(thisId, up);
        connectAround(thisId, down);
        connectAround(thisId, left);
        connectAround(thisId, right);
    }

    private void connectAround(int thisId, int[] aroundGrid) {
        if (isValidate(aroundGrid[0], aroundGrid[1]) && isOpen(aroundGrid[0], aroundGrid[1])) {
            uf.union(thisId, toId(aroundGrid[0], aroundGrid[1]));
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException("row or col is out of bounds");
        }
    }

    private boolean isValidate(int row, int col) { // check if the site is in the grid
        return row >= 1 && row <= grid.length && col >= 1 && col <= grid.length;
    }

    // 将gird转换为id
    private int toId(int row, int col) {
        return (row - 1) * grid.length + col; // because the id of 1 is the virtual top site
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // A full site is an open site that can be connected to
    // an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        // if the site is blocked, it is not full
        // if the site is connected to the virtual top site, it is full
        // 0 is not immutable
        // there is a method in uf, called connected, which is used to check if the two sites are connected
        return isOpen(row, col) && uf.find(toId(row, col)) == uf.find(0); // 0 is the virtual top
    }

    // returns the number of open sites
    public int numberOfOpenSites() { // there must a better way to avoid the for loop.
        int count = 0;
        for (boolean[] row : grid) {  // grid is a 2 dimensional boolean array
            for (boolean col : row) {
                count += col ? 1 : 0; // variable x = (expression) ? value if true : value if false
            } // ? is the ternary operator
        }
        return count;
    }

    // We say the system percolates if there is a full site in the bottom row.
    // does the system percolate?
    // We can introduce a virtual top row and virtual bottom row.
    // percolates iif the virtual top row and virtual bottom row are connected.
    // use the method of sorting
    public boolean percolates() {
        return uf.find(0) == uf.find(grid.length * grid.length + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        percolation.open(1, 1);

        if (percolation.isOpen(1, 1)) {
            StdOut.println("1,1 is open.");
        } else {
            StdOut.println("1,1 is blocked.");
        }
        if (percolation.isFull(1, 1)) {
            StdOut.println("1,1 is full.");
        } else {
            StdOut.println("1,1 is not full.");
        }

        percolation.open(2, 1);
        if (percolation.percolates()) {
            StdOut.println("The system percolates.");
        } else {
            StdOut.println("The system does not percolate.");
        }
        StdOut.printf("The number of open sites is %d.\n", percolation.numberOfOpenSites());
    }
}

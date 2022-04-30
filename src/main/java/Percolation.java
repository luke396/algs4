import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // block is no connection, and open is connection
    // this is a dynamic programming problem
    private final boolean[][] grid; // true is open, false is blocked
    private final WeightedQuickUnionUF uf;
    // id is the number from 0 to n^2-1

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        uf = new WeightedQuickUnionUF(n * n); // initialize n closed sites.

        grid = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) { // By convention, row and col is from 1 to n
        validate(row, col);

        if (grid[row - 1][col - 1]) {
            return;
        }
        grid[row - 1][col - 1] = true; // open the site

        // to see if the site around open and connect to the open site
        int thisId = toId(row, col);
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
        return (row - 1) * grid.length + col - 1;
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

        if (!isOpen(row, col)) { // if the site is blocked, it is not full
            return false;
        }

        int[] up = {row + 1, col};
        int[] down = {row - 1, col};
        int[] left = {row, col - 1};
        int[] right = {row, col + 1};
        if (row == 1) { // in the first row, if the site is open, it is full
            return true;
        } else {
            return isValidate(up[0], up[1]) && isFull(up[0], up[1]) ||
                    (isValidate(down[0], down[1]) && isFull(down[0], down[1])) ||
                    (isValidate(left[0], left[1]) && isFull(left[0], left[1]) ||
                            (isValidate(right[0], right[1]) && isFull(right[0], right[1])));
            // the symbol beside is recursion
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
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
    // use the method of sorting
    public boolean percolates() { // 是在验证是否连接 也就是问是否有一条路可以从上连接到下

        boolean[] lastRow = grid[grid.length - 1]; // last row
        for (int i = 0; i < lastRow.length; i++) {
            if (lastRow[i] && isFull(grid.length, i + 1)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(1);

        perc.open(1, 1);
        if (perc.isOpen(1, 1)) {
            StdOut.println("1,1 is open.");
        } else {
            StdOut.println("1,1 is blocked.");
        }
        if (perc.isFull(1, 1)) {
            StdOut.println("1,1 is full.");
        } else {
            StdOut.println("1,1 is not full.");
        }
        if (perc.percolates()) {
            StdOut.println("The system percolates.");
        } else {
            StdOut.println("The system does not percolate.");
        }

        StdOut.printf("The number of open sites is %d.\n", perc.numberOfOpenSites());
    }
}

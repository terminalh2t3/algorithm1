/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Test {
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        System.out.println("Test [1,1] isOpen? : " + percolation.isOpen(1, 1));
        System.out.println("Test [2,2] isFull? : " + percolation.isFull(2, 2));
        System.out.println("Percolate? : " + percolation.percolates());

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        System.out.println("Test [2,2] isFull? : " + percolation.isFull(2, 2));
        System.out.println("Percolate? : " + percolation.percolates());
        System.out.println("Num open site? : " + percolation.numberOfOpenSites());
    }
}

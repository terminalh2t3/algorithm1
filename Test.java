/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Test {
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        System.out.println("Test [2,2] isFull? : " + percolation.isFull(2, 2));
        System.out.println("Percolate? : " + percolation.percolates());

        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 2);
        System.out.println("Test [2,2] isFull? : " + percolation.isFull(2, 2));
        System.out.println("Percolate? : " + percolation.percolates());

        percolation.open(3, 2);
        System.out.println("Test [2,2] isFull? : " + percolation.isFull(2, 2));
        System.out.println("Percolate? : " + percolation.percolates());
    }
}

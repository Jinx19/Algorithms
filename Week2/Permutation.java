import edu.princeton.cs.algs4.StdIn;

/**
 * Created by mac on 15/10/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = new Integer(args[0]).intValue();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);

        }
        while (k > 0) {
            System.out.println(randomizedQueue.dequeue());
            k--;
        }
    }
}

package queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (k == 0) {
                return;
            } else if (randomizedQueue.size() < k) {
                randomizedQueue.enqueue(s);
            } else if (randomizedQueue.size() > k) {
                randomizedQueue.dequeue();
                randomizedQueue.enqueue(s);
            }
        }
        while (!randomizedQueue.isEmpty()) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}

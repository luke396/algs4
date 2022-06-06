package queues;
//takes an integer k as a command-line argument;
// reads a sequence of strings from standard inp ut using "StdIn.readString()";
// and prints exactly k of them, uniformly at random.
// Print each item from the sequence at most once.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);
        }
        for (String out : randomizedQueue) {
            StdOut.println(out);
        }
    }
}

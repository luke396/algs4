package queues;

// A randomized queue is similar to a stack or queue,
// except that the item removed is chosen uniformly at random among items in the data structure.

// This will implement by linked list, just for practice, not consider the question requirements.


import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8; // TODO: seem to be changed.
    private int count;
    private Item[] a;


    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Enqueue item can't be null!");
        if (count == a.length) resize(2 * a.length);
        a[count++] = item;
    }

    private void resize(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Cannot resize the capacity less than 0");
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(a, 0, copy, 0, count);
        a = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("randomized queue is empty!");
        int index = StdRandom.uniform(count--);
        Item out = a[index];
        a[index] = null;
        updateIndex(index);
        return out;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("randomized queue is empty!");
        return a[StdRandom.uniform(count)];
    }

    //update the index from discontinuous to continuous
    private void updateIndex(int index) {
        if (count - index >= 0) System.arraycopy(a, index + 1, a, index, count - index);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        Item[] forNext = a.clone(); // a copy for next().

        @Override
        public boolean hasNext() {
            return forNext[0] != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Next!");
            swap(forNext, a);
            Item out = dequeue();
            swap(forNext, a);
            return out;
        }

        private void swap(Item[] a, Item[] b) { // swap a and b.
            Item[] temp = a.clone();
            System.arraycopy(b, 0, a, 0, b.length);
            System.arraycopy(temp, 0, b, 0, temp.length);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        StdOut.println("Wish me luck!");
    }
}
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
        a[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("randomized queue is empty!");
        int index = StdRandom.uniform(count);
        Item out = a[index];
        a[index] = null;
        return out;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return a[StdRandom.uniform(count)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int nextCount;
        Item[] hasOut = (Item[]) new Object[count]; // collect has outed

        @Override
        public boolean hasNext() {
            return nextCount != count;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Next!");
            boolean notFind = true;

            while (notFind) {
                Item out = sample();
                for (Item temp : hasOut) {
                    if (out == temp) {
                        out = null;
                        break;
                    } else {
                        hasOut[nextCount++] = out;
                        return out;
                    }
                }
                if (out != null) notFind = false;
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        StdOut.println("Wish me luck!");
    }
}
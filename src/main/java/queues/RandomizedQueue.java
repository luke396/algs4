package queues;

// A randomized queue is similar to items stack or queue,
// except that the item removed is chosen uniformly at random among items in the data structure.

// This will implement by linked list, just for practice, not consider the question requirements.


import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] items;


    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        validate(item);
        if (size == items.length) resize(items.length << 1); // double the size
        items[size++] = item;
    }


    // remove and return items random item
    public Item dequeue() {
        validate();
        int index = StdRandom.uniform(size--);
        Item out = items[index];
        items[index] = null;
        updateIndex(index);
        if (size > 0 && size == items.length >> 2) { // divide by 4
            resize(items.length >> 1); // divide by 2
        }
        return out;
    }

    // return items random item (but do not remove it)
    public Item sample() {
        validate();
        return items[StdRandom.uniform(size)];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] randomIndexs;
        private int current;

        private RandomizedQueueIterator() {
            randomIndexs = new int[size];
            for (int i = 0; i < size; i++) {
                randomIndexs[i] = i;
            }
            StdRandom.shuffle(randomIndexs);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current != randomIndexs.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Next!");
            return items[randomIndexs[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void validate() {
        if (isEmpty()) throw new NoSuchElementException("randomized queue is empty!");
    }

    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException("Enqueue item can't be null!");
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, copy, 0, size);
        items = copy;
    }

    // update the index from discontinuous to continuous
    private void updateIndex(int index) {
        if (size - index >= 0) System.arraycopy(items, index + 1, items, index, size - index);
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
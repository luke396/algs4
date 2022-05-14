// A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue
// that supports adding and removing items from either the front or the back of the data structure.

// The first problem is deciding array or list links?
// This is based on the performance requirements.

// I decide it by memory use per iterator.
// The array based is call a[i--] to call next, maybe a constant.
// and the list links based need 'Item item = current.item', maybe linear in # fo items


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;

    private Item[] a; // array of items
    private int count; // number of elements
    private int front; // the location of front null
    private int back; // the location of back null


    // construct an empty deque
    public Deque() {
        a = (Item[]) new Object[INIT_CAPACITY]; // this cannot avoid
        front = INIT_CAPACITY / 2;
        back = front; // firstly, they are the same.
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // resize the underlying array holding the elements
    // it could bigger or smaller
    private void resize(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Cannot resize the capacity less than 0");
        }
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(a, back + 1, copy, back + 1, count); // back + 1 is the last element location
        a = copy;
    }

    // add the item to the front
    public void addFirst(Item item) {
        addIsNull(item);
        if (front - 1 == a.length) { // front - 1 is the first elements location
            resize(a.length * 2);
        }

        a[front] = item;
        front++; // front is always a null for next first.
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        addIsNull(item);
        if (back + 1 == 0) {
            resize(a.length * 2);
        }

        a[back] = item;
        back--;
        count++;
    }

    // if added is null
    private void addIsNull(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null");

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty, cannot remove someone!");

        Item remove = a[front - 1];
        a[front - 1] = null; // to avoid loitering
        count--;
        front--;
        if (count > 0 && front - 1 < a.length / 4) {
            resize(a.length / 2);
        }
        return remove;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty, cannot remove someone!");

        Item remove = a[back + 1];
        a[back + 1] = null; // to avoid loitering
        count--;
        back++;
        if (count > 0 && back + 1 > 3 * (a.length / 4)) {
            resize(a.length / 2);
        }
        return remove;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private int i;

        public DequeIterator() {
            i = front - 1;
        }

        public boolean hasNext() {
            return (i >= back && i >= 0);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Next!");

            return a[i--];
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("a")) deque.addLast(item); // only 'a' add last
            if (item.equals("+")) StdOut.print(deque.removeFirst() + "\n");
            if (item.equals("-")) StdOut.print(deque.removeLast() + "\n");
            else deque.addFirst(item);

            for (String i : deque) { // print to see performance
                StdOut.print(i);
            }
        }
    }
}

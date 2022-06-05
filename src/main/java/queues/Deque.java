package queues; // the package should be removed when submitting

// A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue
// that supports adding and removing items from either the front or the back of the data structure.

// The first problem is deciding array or list links?
// This is based on the performance requirements.

// I decide it by memory use per iterator.
// The array based is call a[i--] to call next, maybe a constant.
// and the list links based need 'Item item = current.item', maybe linear in num of items

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // The <Item> seems not obey some java rules, it should can't be named as 'Item'
    private static final int INIT_CAPACITY = 8;

    private Item[] a; // array of items

    // front is in the rightest, and bigger than back
    // which is the leftest, and bigger or equal to -1
    private int front; // the location of front null
    private int back; // the location of back null

    // construct an empty deque
    public Deque() {
        a = (Item[]) new Object[INIT_CAPACITY]; // this warning cannot avoid
        front = INIT_CAPACITY / 2;
        back = front - 1; // firstly, they are the same.
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return front - back - 1;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Cannot resize the capacity less than 0");
        Item[] copy = (Item[]) new Object[capacity];
        int count = size();
        System.arraycopy(a, back + 1, copy, 0, count);
        back = -1;
        front = back + 1 + count;
        a = copy;
    }

    // add the item to the front
    public void addFirst(Item item) {
        addIsNull(item);
        isNeedBiggerSize();
        a[front++] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        addIsNull(item);
        isNeedBiggerSize();
        a[back--] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty, cannot remove someone!");
        Item remove = a[--front];
        a[front] = null; // to avoid loitering
        isNeedSmallerSize();
        return remove;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty, cannot remove someone!");
        Item remove = a[++back];
        a[back] = null; // to avoid loitering
        isNeedSmallerSize();
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
            return i >= back + 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Next!");
            return a[i--];
        }
    }

    // is need resize to smaller?
    private void isNeedBiggerSize() {
        if (back + 1 == 0 && front - 1 == a.length) resize(a.length * 2);
    }

    private void isNeedSmallerSize() {
        int count = size();
        if (count > 0 && count < a.length / 4) resize(a.length / 2);
    }

    // if added is null
    private void addIsNull(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null");
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("a");
        deque.addLast("b");
        deque.addFirst("c");
        deque.addLast("d");
        deque.addFirst("e");
        deque.addLast("f");
        deque.addFirst("g");
        deque.addLast("h");

        deque.removeFirst();
        deque.removeLast();

        int count = deque.size();
        if (count == 6) StdOut.println("The size is right!");
        else StdOut.println("The size is wrong!");

        for (String item : deque) StdOut.println(item);

        StdOut.println("Wish me luck!");

    }
}

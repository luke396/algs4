package queues;

import org.junit.jupiter.api.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {
    RandomizedQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new RandomizedQueue<>();

    }

    @Test
    @DisplayName("repeat failed tests 1")
    void repeatFailedTests1() {
        queue.enqueue(1);
        queue.enqueue(31);
        queue.enqueue(1);
        queue.enqueue(14);
        queue.enqueue(46);
        assertEquals(5, queue.size());
        queue.enqueue(12);
        queue.enqueue(19);
        queue.enqueue(42);
        queue.enqueue(18);
        assertEquals(9, queue.size());
    }


    @RepeatedTest(value = 5, name = "repeat failed tests 2")
    void repeatFailedTests2() {
        queue.enqueue(30);
        queue.enqueue(13);
        assertNotEquals(null, queue.dequeue());
        queue.enqueue(15);
        queue.enqueue(1);
        queue.enqueue(46);
        queue.enqueue(12);
        queue.enqueue(19);
        queue.enqueue(42);
        assertNotEquals(null, queue.dequeue());
        queue.enqueue(18);
        assertNotEquals(null, queue.dequeue());
        assertNotEquals(null, queue.dequeue());
        assertNotEquals(null, queue.dequeue());
        assertNotEquals(null, queue.dequeue());
        assertNotEquals(null, queue.dequeue());
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Congratulations, you have passed this test!");
    }

    @Test
    void iterator() {
        queue.enqueue(1);
        queue.enqueue(31);
        queue.enqueue(1);
        queue.enqueue(14);
        queue.enqueue(46);
        assertFalse(queue.isEmpty());
        assertEquals(5, queue.size());

        Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(14, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(31, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(46, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
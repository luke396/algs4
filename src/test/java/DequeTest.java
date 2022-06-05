// This is my first test attempts.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import queues.Deque;

import static org.junit.jupiter.api.Assertions.*;

class InitiallyTest { // test without actions


    Deque<String> deque = new Deque<>();

    @Test
    @DisplayName("is empty")
    void isEmpty() {
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("size is 0")
    void size() {
        assertEquals(0, deque.size());
    }
}

class AddTest { // test with multiple adds

    Deque<String> deque = new Deque<>();


    @NullAndEmptySource
    @ParameterizedTest(name = "add {0}") // run test multiple times, with different arguments
    @ValueSource(strings = {"a", "+", "+1", "Bob", "\t", "\n"})
    void addFirstWithOne(String item) {
        if (item == null) {
            //assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage()); // to test the message
        } else {
            deque.addFirst(item);
            assertEquals(1, deque.size());
            assertFalse(deque.isEmpty());
        }
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "add {0}")
    @ValueSource(strings = {"a", "+", "+1", "Bob", "{", "null"})
    void addLastWithOne(String item) {
        if (item == null) {
            //assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
        } else {
            deque.addLast(item);
            assertEquals(1, deque.size());
            assertFalse(deque.isEmpty());
        }
    }


    @ParameterizedTest
    @CsvSource({
            "a, b",
            "1, ",// 1,null
            "4, ''",
            "7, '\t",
            "," // null,null
    })
    void addFirstWithTwo(String item1, String item2) {
        int nullCount = 0;
        if (item1 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
            nullCount++;
        } else {
            deque.addFirst(item1);
        }
        if (item2 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
            nullCount++;
        } else {
            deque.addFirst(item2);
        }

        assertEquals(2 - nullCount, deque.size());
        if (nullCount == 2) {
            assertTrue(deque.isEmpty());
        } else {
            assertFalse(deque.isEmpty());
        }
    }


    @ParameterizedTest
    @CsvSource({
            "a, b",
            "1, ",// 1,null
            "4, ''",
            "7, '\t",
            "," // null,null
    })
    void addFirstAndLast(String item1, String item2) {
        int nullCount = 0;
        if (item1 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
            nullCount++;
        } else {
            deque.addFirst(item1);
        }
        if (item2 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
            nullCount++;
        } else {
            deque.addLast(item2);
        }

        assertEquals(2 - nullCount, deque.size());
        if (nullCount == 2) {
            assertTrue(deque.isEmpty());
        } else {
            assertFalse(deque.isEmpty());
        }
    }

    @Test
    void resizeAddTest() {
        deque.addLast("a");
        assertEquals(1, deque.size());

        deque.addLast("b");
        assertEquals(2, deque.size());

        deque.addLast("c");
        assertEquals(3, deque.size());

        deque.addLast("d");
        assertEquals(4, deque.size());

        deque.addLast("e");
        assertEquals(5, deque.size());

        deque.addLast("f");
        assertEquals(6, deque.size());

        deque.addFirst("g");
        assertEquals(7, deque.size());

        deque.addFirst("h");
        assertEquals(8, deque.size());

        deque.addFirst("i");
        assertEquals(9, deque.size());

        deque.addFirst("j");
        assertEquals(10, deque.size());

        deque.addFirst("k");
        assertEquals(11, deque.size());
    }

    @Test
    void resizeRemoveTest() {
        assertEquals("f", deque.removeLast());
        assertEquals(10, deque.size());

        assertEquals("e", deque.removeLast());
        assertEquals(9, deque.size());

        assertEquals("d", deque.removeLast());
        assertEquals(8, deque.size());

        assertEquals("c", deque.removeLast());
        assertEquals(7, deque.size());

        assertEquals("b", deque.removeLast());
        assertEquals(6, deque.size());

        assertEquals("a", deque.removeLast());
        assertEquals(5, deque.size());

        deque.removeFirst();
        assertEquals(4, deque.size());

        deque.removeFirst();
        assertEquals(3, deque.size());

        deque.removeFirst();
        assertEquals(2, deque.size());

        deque.removeFirst();
        assertEquals(1, deque.size());

        deque.removeFirst();
        assertEquals(0, deque.size());
    }
}

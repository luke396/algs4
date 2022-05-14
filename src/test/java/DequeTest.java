// This is my first test attempts.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class InitiallyTest { // test without actions


    Deque<String> deque = new Deque<>();

    @Test
    @DisplayName("is empty")
    public void isEmpty() {
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("size is 0")
    public void size() {
        assertEquals(0, deque.size());
    }
}

class AddTest { // test with multiple adds

    Deque<String> deque = new Deque<>();


    @NullAndEmptySource
    @ParameterizedTest(name = "add {0}") // run test multiple times, with different arguments
    @ValueSource(strings = {"a", "+", "+1", "Bob", "\t", "\n"})
    public void addFirstWithOne(String item) {
        if (item == null) {
            //assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Cannot add null", addNull.getMessage());
        } else {
            deque.addFirst(item);
            assertEquals(1, deque.size());
            assertFalse(deque.isEmpty());
        }
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "add {0}")
    @ValueSource(strings = {"a", "+", "+1", "Bob", "{", "null"})
    public void addLastWithOne(String item) {
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
    public void addFirstWithTwo(String item1, String item2) {
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
    public void addFirstAndLast(String item1, String item2) {
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
}

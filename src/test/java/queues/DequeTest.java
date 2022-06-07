package queues;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    Deque<String> deque;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Let's start!");
    }

    @BeforeEach
    public void setup() {
        deque = new Deque<>();
    }

    @Test
    void isEmpty() {
        assertTrue(deque.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, deque.size());
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "add first {0}")
    @ValueSource(strings = {"a", "+", "+1", "Bob", "{", "null"})
    void addFirst(String item) {
        if (item == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Can't add null item!", addNull.getMessage());
        } else {
            assertEquals(0, deque.size());
            deque.addFirst(item);
            deque.addFirst(item);
            deque.addFirst(item);
            deque.addFirst(item);
            deque.addFirst(item);

            assertEquals(5, deque.size());
            assertFalse(deque.isEmpty());
        }
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "add last {0}")
    @ValueSource(strings = {"a", "+", "+1", "Bob", "{", "null"})
    void addLast(String item) {
        if (item == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addLast(null));
            assertEquals("Can't add null item!", addNull.getMessage());
        } else {
            assertEquals(0, deque.size());
            deque.addLast(item);
            assertEquals(1, deque.size());
            assertFalse(deque.isEmpty());
        }
    }

    @Test
    @DisplayName("add first and remove first")
    void addFirstRemoveFirst() {
        deque.addFirst("a");
        assertEquals("a", deque.removeFirst());
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("add first but remove last")
    void addFirstRemoveLast() {
        assertEquals(0, deque.size());
        deque.addFirst("a");
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals("a", deque.removeLast());
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("add last and remove last")
    void addLastRemoveLast() {
        deque.addLast("a");
        assertEquals("a", deque.removeLast());
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
    }

    @Test
    @DisplayName("add last but remove first")
    void addLastRemoveFirst() {
        assertEquals(0, deque.size());
        deque.addLast("a");
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals("a", deque.removeFirst());
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "a, b",
            "1, ",// 1,null
            "4, ''",
            "7, '\t",
            "," // null,null
    })
    @DisplayName("add first and add last")
    void addFirstLast(String item1, String item2) {
        int nullCount = 0;
        if (item1 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Can't add null item!", addNull.getMessage());
            nullCount++;
        } else deque.addFirst(item1);

        if (item2 == null) {
            Exception addNull = assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
            assertEquals("Can't add null item!", addNull.getMessage());
            nullCount++;
        } else deque.addLast(item2);
        assertEquals(2 - nullCount, deque.size());
        if (nullCount == 2) assertTrue(deque.isEmpty());
        else assertFalse(deque.isEmpty());
    }

    @Test
    @DisplayName("add and remove multiple")
    void addRemoveMultiple() {
        deque.addFirst("a");
        deque.addLast("b");
        deque.addFirst("c");
        deque.addLast("d");
        deque.addFirst("e");
        deque.addLast("f");
        deque.addFirst("g");
        deque.addLast("h");
        assertFalse(deque.isEmpty());
        assertEquals(8, deque.size());

        assertEquals("h", deque.removeLast());
        assertEquals("g", deque.removeFirst());
        assertEquals("e", deque.removeFirst());
        assertEquals("f", deque.removeLast());
        assertEquals("c", deque.removeFirst());
        assertEquals("a", deque.removeFirst());
        assertEquals("d", deque.removeLast());
        assertEquals("b", deque.removeLast());
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }


    @Test
    void iterator() {
        deque.addFirst("a");
        deque.addLast("b");
        deque.addFirst("c");
        deque.addLast("d");
        assertFalse(deque.isEmpty());
        assertEquals(4, deque.size());

        Iterator<String> iterator = deque.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("d", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("repeat failed tests 1")
    void repeatFailedTest1(){
        deque.addLast("a");
        deque.addFirst("b");
        deque.addFirst("c");
        deque.addFirst("d");
        deque.addFirst("e");
        deque.addLast("f");
        assertEquals(6, deque.size());
    }



    @AfterEach
    void tearDown() {
        deque = null;
        System.out.println("Congratulations, you have passed this test!");
    }
}
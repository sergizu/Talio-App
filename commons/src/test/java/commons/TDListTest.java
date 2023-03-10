package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TDListTest {
    private TDList list;
    private TDList list1;
    private TDList list2;
    private Card card1;

    @BeforeEach
    void setUp() {
        list = new TDList("todo");
        list1 = new TDList("doing");
        list2 = new TDList("todo");
        card1 = new Card("card1");
    }

    @Test
    void testNotEqual() {
        assertNotEquals(list, list1);
    }

    @Test
    void testEqualsSameAddress() {
        assertEquals(list, list);
    }

    @Test
    void testEqualOtherAddress() {
        assertEquals(list, list2);
    }

    @Test
    void testHash() {
        assertEquals(list.hashCode(), list2.hashCode());
    }

    @Test
    void testToString() {
        list.addCard(card1);
        String toString = "TO DO List:\n" + "id: " +
                list.id + "\n" + "title: todo\n" +
                "Cards:\nCard{" +
                "id=" + card1.getId() +
                ", title='card1'}" ;
        assertEquals(list.toString(), toString);
    }

    @Test
    void testAddCard() {
        list.addCard(card1);
        assertEquals(card1, list.list.get(0));
    }
    @Test
    void testRemoveCard() {
        list.addCard(card1);
        list.removeCard(card1.getId());
    }
}

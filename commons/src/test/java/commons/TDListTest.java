package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;

class TDListTest {
    TDList DO;
    TDList DOING;

    TDList DO1;
    TDList DONE;
    Card card2;
    Card card1;



    @BeforeEach
    void setUp() {
         DO = new TDList("TO DO");
         DOING = new TDList("DOING");
         DO1 = new TDList("TO DO");
         DONE = new TDList("TO DO");
         card1 = new Card("test card");
         card2 = new Card("Next Card");

    }

    @Test
    void testEquals() {
        DO.getList().add(card1);
        DO1.getList().add(card2);
        DONE.getList().add(card1);

        assertFalse(DO.equals(DO1));
        assertEquals(DO, DONE);

    }

    @Test
    void TestHashEquals() {
        assertEquals(DO.hashCode(), DONE.hashCode());
    }

    @Test
    void TestHashNotEquals() {
        assertNotEquals(DO.hashCode(), DOING.hashCode());
    }
}



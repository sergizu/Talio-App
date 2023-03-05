package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    public void testConstructor(){
        Board b = new Board("b1");
        assertEquals(b.getTitle(), "b1");
    }
    @Test
    public void testToString(){
        Board b = new Board("b1");
        b.addList("l1");
        b.addList("aa");
        System.out.println(b.toString());
    }
    @Test
    public void testEquals(){
        Board b = new Board("b1");
        Board a = new Board("b1");
        assertEquals(a, b);
        b.addList("aa");
        assertNotEquals(a, b);
    }
}

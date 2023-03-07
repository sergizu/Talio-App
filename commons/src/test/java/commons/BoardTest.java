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
    // needs to be implemented. don't have TDList tostring
    @Test
    public void testToString(){
        Board b = new Board("b1");
        TDList l= new TDList("aa");
        b.addList(l);
        System.out.println(b.toString());
    }
    @Test
    public void testEquals(){
        Board b = new Board("b1");
        Board a = new Board("b1");
        assertEquals(a, b);
        TDList l= new TDList("aa");
        b.addList(l);
        assertNotEquals(a, b);
    }
    @Test
    public void testRemoveList(){
        Board b = new Board("b1");
        Board a = new Board("b1");
        TDList l= new TDList("aa");
        b.addList(l);
        assertNotEquals(b, a);
        b.removeList(l.getId());
        assertEquals(b, a);
    }
}

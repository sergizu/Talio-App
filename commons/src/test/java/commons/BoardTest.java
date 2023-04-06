package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testEmptyConstructor() {
        Board board1 = new Board();
        assertNull(board1.getTitle());
    }

    @Test
    public void testConstructor() {
        Board board1 = new Board("test board");
        assertEquals("test board", board1.getTitle());
    }

    @Test
    public void testSetId() {
        Board board1 = new Board("test board");
        board1.setId(3);
        assertEquals(3, board1.getId());
    }

    @Test
    public void testGetId() {
        Board board1 = new Board("test board");
        assertEquals(board1.id, board1.getId());
    }

    @Test
    public void testGetTitle() {
        Board board1 = new Board("test board");
        assertEquals(board1.title, board1.getTitle());
    }

    @Test
    public void testSetTitle() {
        Board board1 = new Board("test board");
        board1.setTitle("test board 2");
        System.out.println(board1.key);
        assertEquals("test board 2", board1.getTitle());
    }

    @Test
    public void testEquals() {
        Board board1 = new Board("test board");
        Board board2 = new Board("test board");
        assertEquals(board1, board2);
    }

    @Test
    public void testNotEquals() {
        Board board1 = new Board("test board");
        Board board2 = new Board("test board 2");
        assertNotEquals(board1, board2);
    }

    @Test
    public void testNotEqualsNull() {
        Board board1 = new Board("test board");
        assertNotEquals(null, board1);
    }

    @Test
    public void testNotEqualsOtherClass() {
        Board board1 = new Board("test board");
        assertNotEquals(board1, new Object());
    }


    @Test
    public void testToString() {
        Board board1 = new Board("test board");
        String expected = "Board{id=" + board1.getId() + ", title=test board, tdLists=[]}";
        assertEquals(expected, board1.toString());
    }

    @Test
    public void testAddList() {
        Board board1 = new Board("test board");
        TDList list1 = new TDList("test list");
        board1.addList(list1);
        assertEquals(list1, board1.tdLists.get(0));
    }

    @Test
    public void testRemoveListExists() {
        Board board1 = new Board("test board");
        TDList list1 = new TDList("test list");
        board1.addList(list1);
        board1.removeList(list1.getId());
        assertEquals(0, board1.tdLists.size());
    }

    @Test
    public void testRemoveListDoesntExists() {
        Board board1 = new Board("test board");
        TDList list1 = new TDList("test list");
        board1.addList(list1);
        board1.removeList(2);
        assertEquals(1, board1.tdLists.size());
    }


    @Test
    public void testEqualHashCode() {
        Board board1 = new Board("test board");
        Board board2 = new Board("test board");
        assertEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    public void testNotEqualHashCode() {
        Board board1 = new Board("test board");
        Board board2 = new Board("test board 2");
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }
}

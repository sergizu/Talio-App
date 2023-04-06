package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    Card card;
    Card cardDescription;

    Subtask subtask;
    @BeforeEach
    public void setup() {
        card = new Card("test card");
        cardDescription = new Card("title", "descritpion");
        subtask = new Subtask("Hello");
    }

    @Test
    public void testToString() {
        String expected = "Card{id=" + card.getId() + ", title='test card'}";
        assertEquals(expected, card.toString());
    }

    @Test
    public void testEquals() {
        Card card2 = new Card("test card");
        assertEquals(card, card2);
    }

    @Test
    public void testNotEquals() {
        Card card2 = new Card("test card 2");
        assertNotEquals(card, card2);
    }

    @Test
    public void testNotEqualsNull() {
        assertNotEquals(null, card);
    }

    @Test
    public void testGetId() {
        assertEquals(card.id, card.getId());
    }

    @Test
    public void testSetId() {
        card.setId(3);
        assertEquals(3, card.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals(card.title, card.getTitle());
    }

    @Test
    public void testSetTitle() {
        card.setTitle("test card 3");
        assertEquals("test card 3", card.getTitle());
    }

    @Test
    public void testGetList() {
        assertEquals(card.list, card.getList());
    }

    @Test
    public void testSetList() {
        TDList list = new TDList("test list");
        card.setList(list);
        assertEquals(list, card.getList());
    }

    @Test
    public void testSetDescription() {
        cardDescription.setDescription("Desc");
        assertEquals("Desc", cardDescription.description);

    }

    @Test
    public void testHashCode() {
        Card card2 = new Card("test card");
        assertEquals(card.hashCode(), card2.hashCode());
    }

    @Test
    public void testEmptyConstructor() {
        Card card2 = new Card();
        card2.setId(1L);
        assertEquals(1L, card2.getId());
    }

    @Test
    public void testAddSubtask() {
        card.addSubTask(subtask);
        assertEquals(card.getNestedList().get(0), subtask);
    }

    @Test
    public void testRemoveSubtask() {
        card.addSubTask(subtask);
        card.removeSubTask(subtask);
        assertTrue(card.getNestedList().isEmpty());
    }

    //Not sure how to test the getter since the nestedList is not a parameter inside the constructor
    @Test
    public void testSetNestedList() {
        ArrayList<Subtask> newNestedList = new ArrayList<>();
        newNestedList.add(subtask);
        card.setNestedList(newNestedList);
        assertEquals(card.getNestedList(), newNestedList);
    }

    @Test
    public void testNoneSelected() {
        card.addSubTask(subtask);
        assertEquals(0, card.amountSelected());
    }

    @Test
    public void testOneSelected() {
        subtask.setChecked(true);
        Subtask subtask2 = new Subtask("Hello2");
        card.addSubTask(subtask);
        card.addSubTask(subtask2);
        assertEquals(1, card.amountSelected());
    }

    @Test
    public void testCellFactoryOnlyTitle() {
        assertEquals("test card", card.cellFactory());
    }

    @Test
    public void testCellFactoryTitleAndDescription() {
        card.setDescription("description");
        assertEquals("test card\n~", card.cellFactory());
    }

    @Test
    public void testCellFactoryTitleAndSubtask() {
        card.addSubTask(subtask);
        assertEquals("test card\n0/1", card.cellFactory());
    }

    @Test
    public void testCellFactoryTitleAndSubtaskAndDescription() {
        card.addSubTask(subtask);
        card.setDescription("Description");
        assertEquals("test card\n0/1\n~", card.cellFactory());
    }

    @Test
    public void testCellFactoryTitleAndSubtaskAndSelected() {
        card.addSubTask(subtask);
        subtask.setChecked(true);
        assertEquals("test card\n1/1", card.cellFactory());
    }

    @Test
    public void testCellFactoryTitleAndSubtaskAndDescriptionAndSelected() {
        card.addSubTask(subtask);
        card.setDescription("Description");
        subtask.setChecked(true);
        assertEquals("test card\n1/1\n~", card.cellFactory());
    }

    @Test
    public void testCellFactoryDescriptionNull() {
        card.setDescription(null);
        assertEquals("test card", card.cellFactory());
    }

    @Test
    public void testGetDescription() {
        card.setDescription("Description");
        assertEquals("Description", card.getDescription());
    }
}

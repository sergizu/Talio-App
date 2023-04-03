package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    Card card;
    Card cardDescription;
    @BeforeEach
    public void setup() {
        card = new Card("test card");
        cardDescription = new Card("title", "descritpion");

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
}

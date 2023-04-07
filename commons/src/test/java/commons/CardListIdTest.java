package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardListIdTest {
    @Test
    void testEmptyConstructor() {
        CardListId cardListId = new CardListId();
        assertNotNull(cardListId);
    }

    @Test
    void testConstructor() {
        CardListId cardListId = new CardListId(new Card("Card"), 2);
        assertEquals(new Card("Card"), cardListId.card);
        assertEquals(2, cardListId.listId);
    }

    @Test
    void testConstructorAllArgs() {
        CardListId cardListId = new CardListId(new Card("Card"), 2, 3);
        assertEquals(new Card("Card"), cardListId.card);
        assertEquals(2, cardListId.listId);
        assertEquals(3, cardListId.boardId);
    }
}
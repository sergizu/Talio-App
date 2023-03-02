package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {
    Card card;
    @BeforeEach
    public void setup() {
        card = new Card("test card");
    }

    @Test
    public void testToString() {
        String expected = "Card{id=" + card.getId() + ", title='test card'}";
        assertEquals(expected, card.toString());
    }

    @Test
    public void testEquals() {
        Card card2 = new Card("test card");
        assertEquals(card, card);
    }
}
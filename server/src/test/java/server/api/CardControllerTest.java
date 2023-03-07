package server.api;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardControllerTest {
    private TestCardRepository testCardRepository;
    private CardController cardController;

    @BeforeEach
    public void setup() {
        this.testCardRepository = new TestCardRepository();
        this.cardController = new CardController(testCardRepository, null);
    }

    @Test
    public void canAddCard() {
        Card card = new Card("new card 1");
        assertEquals(HttpStatus.OK, cardController.add(card).getStatusCode());
    }

    @Test
    public void newCardIsActuallyAdded() {
        Card card = new Card("new card 1");
        cardController.add(card);
        assertTrue(testCardRepository.existsById(card.getId()));
    }

    @Test
    public void testGetById() {
        Card card1 = new Card("new card 1");
        Card card2 = new Card("new card 2");
        cardController.add(card1);
        cardController.add(card2);
        assertEquals(card1, cardController.getById(card1.getId()).getBody());
    }
}

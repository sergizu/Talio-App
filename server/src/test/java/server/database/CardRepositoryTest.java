package server.database;

import commons.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Test
    public void addCard() {
        Card card = new Card("Card 1");
        cardRepository.save(card);
        assertTrue(cardRepository.existsById(card.id));
    }
}


package server.api;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.CardRepository;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    //Method returns either the card or null if the card doesn't exist
    public Card getById(long Id) {
        if(cardRepository.existsById(Id))
            return cardRepository.getById(Id);
        return null;
    }

    //Method returns either the card that is added or null if null was sent
    public Card addCard(Card card) {
        if(card == null) return null;
        Card saved = cardRepository.save(card);
        return saved;
    }
}

package server.service;

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
    public Card getById(long id) {
        if(cardRepository.existsById(id))
            return cardRepository.getById(id);
        return null;
    }

    //Method returns either the card that is added or null if an error occurs was sent
    public Card addCard(Card card) {
        if(card == null || card.title == null) return null;
        if(cardRepository.existsById(card.id))
            return null;
        return cardRepository.save(card);
    }

    public boolean existsById(long id) {
        return cardRepository.existsById(id);
    }

    //Method that updates the card if it exists, otherwise it will return null
    public Card update(Card card) {
        if(card == null || card.title == null) return null;
        if(!cardRepository.existsById(card.id))
            return null;
        return cardRepository.save(card);
    }

    public boolean delete(long id) {
        if(!cardRepository.existsById(id))
            return false;
        cardRepository.deleteById(id);
        return true;
    }

    public boolean updateName(long id, String name) {
        try {
            Card toUpdate = cardRepository.getById(id);
            toUpdate.setTitle(name);
            cardRepository.save(toUpdate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

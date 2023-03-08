package server.service;

import commons.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.CardRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;
    private CardService cardService;


    @BeforeEach
    void setUp() {
        cardService = new CardService(cardRepository);
    }

    @Test
    void getAll() {
        cardService.getAll();
        verify(cardRepository).findAll();
    }

    @Test
    void getByIdIfExists() {
        long id = 1;
        given(cardRepository.existsById(id)).willReturn(true);
        cardService.getById(id);
        verify(cardRepository).getById(id);
    }

    @Test
    void getByIdIfNotExists() {
        long id = 1;
        given(cardRepository.existsById(id)).willReturn(false);
        cardService.getById(id);
        verify(cardRepository, never()).findById(anyLong());
    }


    @Test
    void addCard() {
        Card toAdd = new Card("Card");
        toAdd.id = 1;
        given(cardRepository.existsById(toAdd.id)).willReturn(false);
        cardService.addCard(toAdd);
        verify(cardRepository).save(toAdd);
    }

    @Test
    void addCardIfExists() {
        Card toAdd = new Card("Card");
        toAdd.id = 1;
        given(cardRepository.existsById(toAdd.id)).willReturn(true);
        cardService.addCard(toAdd);
        verify(cardRepository, never()).save(toAdd);
    }

    @Test
    void existsById() {
        long id = 1;
        cardService.existsById(id);
        verify(cardRepository).existsById(id);
    }

    @Test
    void update() {
        Card card = new Card("Card");
        card.id = 1;
        given(cardRepository.existsById(card.id)).willReturn(true);
        cardService.update(card);
        verify(cardRepository).save(card);
    }

    @Test
    void updateIfNotExists() {
        Card card = new Card("Card");
        card.id = 1;
        given(cardRepository.existsById(card.id)).willReturn(false);
        cardService.update(card);
        verify(cardRepository, never()).save(card);
    }
}
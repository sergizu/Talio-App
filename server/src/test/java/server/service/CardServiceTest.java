package server.service;

import commons.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.BoardController;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private BoardService boardService;

    @Mock
    private ListRepository listRepository;
    private CardService cardService;


    @BeforeEach
    void setUp() {
        cardService = new CardService(cardRepository, boardService, listRepository);
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
        given(cardRepository.findById(id)).willReturn(Optional.of(new Card("test")));
        cardService.getById(id);
        verify(cardRepository).findById(id);
    }

    @Test
    void getByIdIfNotExists() {
        long id = 1;
        when(cardRepository.existsById(id)).thenReturn(false);
        cardService.getById(id);
        verify(cardRepository, never()).findById(anyLong());
    }


    @Test
    void addCard() {
        Card toAdd = new Card("Card");
        toAdd.id = 1;
        when(cardRepository.existsById(toAdd.id)).thenReturn(false);
        cardService.addCard(toAdd);
        verify(cardRepository).save(toAdd);
    }

    @Test
    void addCardIfExists() {
        Card toAdd = new Card("Card");
        toAdd.id = 1;
        when(cardRepository.existsById(toAdd.id)).thenReturn(true);
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
        when(cardRepository.existsById(card.id)).thenReturn(true);
        cardService.update(card);
        verify(cardRepository).save(card);
    }

    @Test
    void updateIfNotExists() {
        Card card = new Card("Card");
        card.id = 1;
        when(cardRepository.existsById(card.id)).thenReturn(false);
        cardService.update(card);
        verify(cardRepository, never()).save(card);
    }
}
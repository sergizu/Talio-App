package server.api;

import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import server.service.CardService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {
    @Mock CardService cardService;
    CardController cardController;

    @BeforeEach
    void setUp() {
        cardController = new CardController(cardService);
    }
    @Test
    void getAll() {
        cardController.getAll();
        verify(cardService).getAll();
    }

    @Test
    void add() {
        Card card = new Card("Card 1");
        when(cardService.addCard(card)).thenReturn(card);
        assertEquals(cardController.add(card), ResponseEntity.ok(card));
        verify(cardService).addCard(card);
    }

    @Test
    void addAlreadyWhenExists() {
        Card card = new Card("Card 1");
        when(cardService.addCard(card)).thenReturn(null);
        assertEquals(cardController.add(card), ResponseEntity.badRequest().build());
        verify(cardService).addCard(card);
    }

    @Test
    void getById() {
        Card card = new Card("Card 1");
        when(cardService.getById(card.id)).thenReturn(card);
        assertEquals(ResponseEntity.ok(card), cardController.getById(card.id));
        verify(cardService).getById(card.id);
    }

    @Test
    void getByIdNotExisting() {
        Card card = new Card("Card 1");
        given(cardService.getById(card.id)).willReturn(null);
        assertEquals(ResponseEntity.badRequest().build(), cardController.getById(card.id));
        verify(cardService).getById(card.id);
    }

    @Test
    void update() {
        Card card = new Card("Card 1");
        when(cardService.update(card)).thenReturn(card);
        assertEquals(ResponseEntity.ok(card), cardController.update(card));
        verify(cardService).update(card);
    }

    @Test
    void updateNonExisting() {
        Card card = new Card("Card 1");
        when(cardService.update(card)).thenReturn(null);
        assertEquals(ResponseEntity.badRequest().build(), cardController.update(card));
        verify(cardService).update(card);
    }

    @Test
    void removeByID() {
        Card card = new Card("Card 1");
        when(cardService.delete(card.id)).thenReturn(true);
        assertEquals(cardController.removeByID(card.id), ResponseEntity.ok().build());
        verify(cardService).delete(card.id);
    }

    @Test
    void RemoveNotExist() {
        Card card = new Card("Card 1");
        when(cardService.delete(card.id)).thenReturn(false);
        assertEquals(cardController.removeByID(card.id), ResponseEntity.badRequest().build());
        verify(cardService).delete(card.id);
    }
    @Test
    void updateNameIfExists(){
        when(cardService.updateName(any(Long.class), any(String.class))).thenReturn(true);
        assertEquals(ResponseEntity.ok().build(), cardController.updateName(1L, "a"));
    }
    @Test
    void updateNameIfNotExists(){
        when(cardService.updateName(any(Long.class), any(String.class))).thenReturn(false);
        assertEquals(ResponseEntity.badRequest().build(), cardController.updateName(1L, "a"));
    }
    @Test
    void updateListIfExists(){
        when(cardService.updateList(any(Long.class), any(long.class))).thenReturn(true);
        assertEquals(ResponseEntity.ok().build(), cardController.updateList(1L, 2L));
    }
    @Test
    void updateListIfNotExists(){
        when(cardService.updateList(any(Long.class), any(long.class))).thenReturn(false);
        assertEquals(ResponseEntity.badRequest().build(), cardController.updateList(1L, 2L));
    }
}
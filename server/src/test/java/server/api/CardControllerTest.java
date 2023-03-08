package server.api;

import commons.Card;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {
    @Mock CardService cardService;
    @Mock SimpMessagingTemplate messagingTemplate;
    CardController cardController;

    @BeforeEach
    void setUp() {
        cardController = new CardController(cardService, messagingTemplate);
    }
    @Test
    void getAll() {
        cardController.getAll();
        verify(cardService).getAll();
    }

    @Test
    void add() {
        Card card = new Card("Card 1");
        given(cardService.addCard(card)).willReturn(card);
        assertEquals(cardController.add(card), ResponseEntity.ok(card));
        verify(cardService).addCard(card);
    }

    @Test
    void addAlreadyWhenExists() {
        Card card = new Card("Card 1");
        given(cardService.addCard(card)).willReturn(null);
        assertEquals(cardController.add(card), ResponseEntity.badRequest().build());
        verify(cardService).addCard(card);
    }

    @Test
    void getById() {
        Card card = new Card("Card 1");
        given(cardService.getById(card.id)).willReturn(card);
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
        given(cardService.update(card)).willReturn(card);
        assertEquals(ResponseEntity.ok(card), cardController.update(card));
        verify(cardService).update(card);
    }

    @Test
    void updateNonExisting() {
        Card card = new Card("Card 1");
        given(cardService.update(card)).willReturn(null);
        assertEquals(ResponseEntity.badRequest().build(), cardController.update(card));
        verify(cardService).update(card);
    }
}
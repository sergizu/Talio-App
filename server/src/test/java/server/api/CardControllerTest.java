package server.api;

import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.CardService;

import java.util.ArrayList;

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
    void updateDescriptionIfExists(){
        when(cardService.updateDescription(any(Long.class), any(String.class))).thenReturn(true);
        assertEquals(ResponseEntity.ok().build(), cardController.updateDescription(1L, "a"));
    }

    @Test
    void updateDescriptionIfNotExists(){
        when(cardService.updateDescription(any(Long.class), any(String.class))).thenReturn(false);
        assertEquals(ResponseEntity.badRequest().build(), cardController.updateDescription(1L, "a"));
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

    @Test
    void testSubscribeForUpdates() {
        given(cardService.subscribeForUpdates()).willReturn(new DeferredResult<>(null, new TDList()));
        assertNotNull(cardController.subscribeForUpdates());
    }

    @Test
    void testUpdateNestedList() {
        given(cardService.updateNestedList(any(Long.class), any())).willReturn(true);
        assertEquals(ResponseEntity.ok().build(), cardController.updateNestedList(1L, new ArrayList<>()));
    }

    @Test
    void testUpdateNestedListIfNotExists() {
        given(cardService.updateNestedList(any(Long.class), any())).willReturn(false);
        assertEquals(ResponseEntity.badRequest().build(), cardController.updateNestedList(1L, new ArrayList<>()));
    }
}
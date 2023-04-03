package server.service;

import commons.Board;
import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.CardRepository;

import java.util.Optional;

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
    private ListService listService;

    private CardService cardService;
    private Card card;

    private Card cardDescription;
    private TDList list;

    private TDList list2;
    private Board board;


    @BeforeEach
    void setUp() {
        cardService = new CardService(cardRepository, boardService, listService);
        card = new Card("Card");
        cardDescription = new Card("title", "Description");
        list = new TDList("List");
        list2 = new TDList("List 2");
        board = new Board("Board");
        list.id = 1;
        card.id = 2;
        cardDescription.id = 4;
        list2.id =5;
        cardDescription.setList(list2);
        list2.setBoard(board);
        board.id = 3;
        card.setList(list);
        list.setBoard(board);
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

    @Test
    public void deleteIfExists() {
        when(cardRepository.existsById(card.getId())).thenReturn(true);
        when(cardRepository.getById(card.getId())).thenReturn(card);
        cardService.delete(card.getId());
        verify(cardRepository).deleteById(card.getId());
    }

    @Test
    public void deleteIfNotExists() {
        when(cardRepository.existsById(card.getId())).thenReturn(false);
        cardService.delete(card.getId());
        verify(cardRepository, never()).deleteById(card.getId());
    }

    @Test
    public void testUpdateName() {
        String newName = "New Name";
        when(cardRepository.existsById(card.getId())).thenReturn(true);
        when(cardRepository.getById(card.getId())).thenReturn(card);
        card.setTitle(newName);
        when(cardRepository.save(card)).thenReturn(card);
        cardService.updateName(card.getId(), newName);
        verify(cardRepository).save(card);
    }

    @Test
    public void testUpdateDescription() {
        String newDescription = "New Description";
        when(cardRepository.existsById(cardDescription.getId())).thenReturn(true);
        when(cardRepository.getById(cardDescription.getId())).thenReturn(cardDescription);
        cardDescription.setDescription(newDescription);
        when(cardRepository.save(cardDescription)).thenReturn(cardDescription);
        cardService.updateDescription(cardDescription.getId(), newDescription);
        verify(cardRepository).save(cardDescription);
    }

    @Test
    public void testUpdateDescriptionIfNotExists() {
        String newDescription = "New Description";
        when(cardRepository.existsById(cardDescription.getId())).thenReturn(false);
        cardService.updateDescription(cardDescription.getId(), newDescription);
        verify(cardRepository, never()).save(card);
    }
    @Test
    public void testUpdateNameIfNotExists() {
        String newName = "New Name";
        when(cardRepository.existsById(card.getId())).thenReturn(false);
        cardService.updateName(card.getId(), newName);
        verify(cardRepository, never()).save(card);
    }

    @Test
    public void testRefuseEmptyName() {
        String newName = "";
        cardService.updateName(card.getId(), newName);
        verify(cardRepository, never()).save(card);
    }

    @Test
    public void testUpdateList() {
        TDList newList = new TDList("New List");
        newList.id = 1;
        newList.setBoard(board);
        when(cardRepository.existsById(card.getId())).thenReturn(true);
        when(cardRepository.getById(card.getId())).thenReturn(card);
        card.setList(newList);
        when(cardRepository.save(card)).thenReturn(card);
        when(listService.getById(1)).thenReturn(newList);
        when(listService.existsById(newList.getId())).thenReturn(true);
        cardService.updateList(card.getId(), newList.getId());
        verify(cardRepository).save(card);
    }

    @Test
    public void testUpdateListIfNotExists() {
        TDList newList = new TDList("New List");
        newList.id = 1;
        newList.setBoard(board);
        when(cardRepository.existsById(card.getId())).thenReturn(false);
        when(listService.existsById(newList.getId())).thenReturn(true);
        cardService.updateList(card.getId(), newList.getId());
        verify(cardRepository, never()).save(card);
    }
}
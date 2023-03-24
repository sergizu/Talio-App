package server.api;

import commons.Board;

import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ListRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;



import server.service.BoardService;



import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @Mock BoardService boardService;
    @Mock ListRepository listRepository;
    @Mock CardRepository cardRepository;
    @Mock
    BoardRepository boardRepository;


    BoardController boardController;


    @BeforeEach

    void setUp() {
        boardController = new BoardController(boardService, listRepository,
                cardRepository, boardRepository);
    }

    @Test
    void getAll() {
        boardController.getAll();
        verify(boardService).getAll();
    }

    @Test
    void add() {
        Board board = new Board("Board 1");
        given(boardService.addBoard(board)).willReturn(board);
        assertEquals(boardController.add(board), ResponseEntity.ok(board));
        verify(boardService).addBoard(board);
    }

    @Test
    void addAlreadyWhenExists() {
        Board board = new Board("Board 1");
        given(boardService.addBoard(board)).willReturn(null);
        assertEquals(boardController.add(board), ResponseEntity.badRequest().build());
        verify(boardService).addBoard(board);
    }

    @Test
    void getById() {
        Board board = new Board("Board 1");
        given(boardService.getById(board.getId())).willReturn(board);
        assertEquals(ResponseEntity.ok(board), boardController.getById(board.getId()));
        verify(boardService).getById(board.getId());
    }

    @Test
    void getByIdNotExisting() {
        Board board = new Board("Board 1");
        given(boardService.getById(board.getId())).willReturn(null);
        assertEquals(ResponseEntity.badRequest().build(), boardController.getById(board.getId()));
        verify(boardService).getById(board.getId());
    }

    @Test
    void update() {
        Board board = new Board("Board 1");
        given(boardService.update(board)).willReturn(board);
        assertEquals(ResponseEntity.ok(board), boardController.update(board));
        verify(boardService).update(board);
    }

    @Test
    void addCardToBoard() {
        Board board = new Board("Board 1");
        Card card = new Card("Card 1");
        TDList list = new TDList("list 1");
        board.addList(list);
        given(boardService.getById(board.getId())).willReturn(board);
        assertEquals(ResponseEntity.ok().build(), boardController.addCardToBoard(board.getId(), card));
    }

    @Test
    void addListToBoard() {
        Board board = new Board("Board 1");
        TDList list = new TDList("list 1");
        given(boardService.getById(board.getId())).willReturn(board);
        assertEquals(ResponseEntity.ok().build(), boardController.addListToBoard(board.getId(), list));
    }

    @Test
    void removeByID() {
        Board board = new Board("Board 1");
        given(boardService.delete(board.id)).willReturn(true);
        assertEquals(boardController.removeByID(board.id), ResponseEntity.ok().build());
        verify(boardService).delete(board.id);
    }

    @Test
    void RemoveNotExist() {
        Board board = new Board("Board 1");
        given(boardService.delete(board.id)).willReturn(false);
        assertEquals(boardController.removeByID(board.id), ResponseEntity.badRequest().build());
        verify(boardService).delete(board.id);
    }
}

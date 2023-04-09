package server.api;

import commons.Board;
import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import server.service.BoardService;
import server.service.ListService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @Mock
    BoardService boardService;

    @Mock
    ListService listService;

    BoardController boardController;


    @BeforeEach

    void setUp() {
        boardController = new BoardController(boardService, listService);
    }

    @Test
    void getAll() {
        boardController.getAll();
        verify(boardService).getAll();
    }

    @Test
    void add() {
        Board board = new Board("Board 1");
        when(boardService.addBoard(board)).thenReturn(board);
        assertEquals(boardController.add(board), ResponseEntity.ok(board));
        verify(boardService).addBoard(board);
    }

    @Test
    void addAlreadyWhenExists() {
        Board board = new Board("Board 1");
        when(boardService.addBoard(board)).thenReturn(null);
        assertEquals(boardController.add(board), ResponseEntity.badRequest().build());
        verify(boardService).addBoard(board);
    }

    @Test
    void getById() {
        Board board = new Board("Board 1");
        when(boardService.getById(board.getId())).thenReturn(board);
        assertEquals(ResponseEntity.ok(board), boardController.getById(board.getId()));
        verify(boardService).getById(board.getId());
    }

    @Test
    void getByIdNotExisting() {
        Board board = new Board("Board 1");
        when(boardService.getById(board.getId())).thenReturn(null);
        assertEquals(ResponseEntity.badRequest().build(), boardController.getById(board.getId()));
        verify(boardService).getById(board.getId());
    }

    @Test
    void updateIfExists() {
        Board board = new Board("Board 1");
        when(boardService.update(board)).thenReturn(board);
        assertEquals(ResponseEntity.ok(board), boardController.update(board));
        verify(boardService).update(board);
    }
    @Test
    void updateIfNotExists(){
        assertEquals(ResponseEntity.badRequest().build(), boardController.update(null));
    }

    @Test
    void addListToBoard() {
        Board board = new Board("Board 1");
        TDList list = new TDList("list 1");
        assertEquals(ResponseEntity.ok().build(), boardController.addListToBoard(board.getId(), list));
    }

    @Test
    void removeByID() {
        Board board = new Board("Board 1");
        when(boardService.delete(board.id)).thenReturn(true);
        assertEquals(boardController.removeByID(board.id), ResponseEntity.ok().build());
        verify(boardService).delete(board.id);
    }

    @Test
    void RemoveNotExist() {
        Board board = new Board("Board 1");
        when(boardService.delete(board.id)).thenReturn(false);
        assertEquals(boardController.removeByID(board.id), ResponseEntity.badRequest().build());
        verify(boardService).delete(board.id);
    }
    @Test
    void subscribeForUpdates(){
        boardController.subscribeForUpdates();
        verify(boardService).subscribeForUpdates();
    }
}
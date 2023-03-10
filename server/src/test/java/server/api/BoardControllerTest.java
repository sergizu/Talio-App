package server.api;

import commons.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.SimpMessagingTemplate;


import server.service.BoardService;



import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @Mock BoardService boardService;

    BoardController boardController;

    @BeforeEach
    void setUp() {
        boardController = new BoardController(boardService);
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



}

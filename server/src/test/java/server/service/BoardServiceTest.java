package server.service;


import commons.Board;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.UpperCase;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.BoardRepository;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;
    private BoardService boardService;


    @BeforeEach
    void setUp() {
        boardService = new BoardService(boardRepository);
    }

    @Test
    void getAll() {
        boardService.getAll();
        verify(boardRepository).findAll();
    }

    @Test
    void getByIdIfExists() {
        long id = 0;
        when(boardRepository.existsById(id)).thenReturn(true);
        Board board = new Board("new Board");
        when(boardRepository.findById(id)).thenReturn(Optional.of(board));
        boardService.getById(id);
        verify(boardRepository).findById(id);
    }


    @Test
    void getByIdIfNotExists() {
        long id = 1;
        when(boardRepository.existsById(id)).thenReturn(false);
        boardService.getById(id);
        verify(boardRepository, never()).findById(anyLong());
    }

    @Test
    void addBoard() {
        Board toAdd = new Board("Board");
        toAdd.setId(1);
        when(boardRepository.existsById(toAdd.getId())).thenReturn(false);
        boardService.addBoard(toAdd);
        verify(boardRepository).save(toAdd);
    }

    @Test
    void addCardIfExists() {

        Board toAdd = new Board("Board");
        toAdd.setId(1);
        when(boardRepository.existsById(toAdd.getId())).thenReturn(true);
        boardService.addBoard(toAdd);
        verify(boardRepository, never()).save(toAdd);
    }

    @Test
    void existsById() {
        long id = 1;
        boardService.existsById(id);
        verify(boardRepository).existsById(id);
    }

    @Test
    void update() {
            Board board = new Board("Board 1");
            board.id = 1;
            when(boardRepository.existsById(board.id)).thenReturn(true);
            boardService.update(board);
            verify(boardRepository).save(board);
    }
}



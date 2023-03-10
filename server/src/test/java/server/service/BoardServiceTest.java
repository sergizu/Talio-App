package server.service;


import commons.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.BoardRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
        given(boardRepository.existsById(id)).willReturn(true);
        Board board = new Board("new Board");
        given(boardRepository.findById(id)).willReturn(Optional.of(board));
        boardService.getById(id);
        verify(boardRepository).findById(id);
    }


    @Test
    void getByIdIfNotExists() {
        long id = 1;
        given(boardRepository.existsById(id)).willReturn(false);
        boardService.getById(id);
        verify(boardRepository, never()).findById(anyLong());
    }

    @Test
    void addBoard() {
        Board toAdd = new Board("Board");
        toAdd.setId(1);
        given(boardRepository.existsById(toAdd.getId())).willReturn(false);
        boardService.addBoard(toAdd);
        verify(boardRepository).save(toAdd);
    }

    @Test
    void addCardIfExists() {

        Board toAdd = new Board("Board");
        toAdd.setId(1);
        given(boardRepository.existsById(toAdd.getId())).willReturn(true);
        boardService.addBoard(toAdd);
        verify(boardRepository, never()).save(toAdd);
    }

    @Test
    void existsById() {
        long id = 1;
        boardService.existsById(id);
        verify(boardRepository).existsById(id);
    }

}


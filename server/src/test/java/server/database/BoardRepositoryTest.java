package server.database;

import commons.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void addBoard() {
        Board board = new Board("Board 1");
        boardRepository.save(board);
        assertTrue(boardRepository.existsById(board.getId()));
    }
}
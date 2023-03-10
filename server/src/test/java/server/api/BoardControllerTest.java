package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import server.database.CardRepository;
import server.database.ListRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardControllerTest {
    private TestBoardRepository testBoardRepository;
    private BoardController boardController;

    @BeforeEach
    public void setup() {
        this.testBoardRepository = new TestBoardRepository();
        this.boardController = new BoardController(testBoardRepository, null, null  );
    }

    @Test
    public void canAddEntity() {
        Board board = new Board("board");
        assertEquals(HttpStatus.OK, boardController.add(board).getStatusCode());
    }

    @Test
    public void newEntityIsActuallyAdded() {
        Board board = new Board("board");
        boardController.add(board);
        assertTrue(testBoardRepository.existsById(board.getId()));
    }

    @Test
    public void testGetById() {
        Board b1 = new Board("board1");
        Board b2 = new Board("board2");
        boardController.add(b1);
        boardController.add(b2);
        assertEquals(b1, boardController.getById(b1.getId()).getBody());
    }
}

package server.service;


import commons.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.BoardRepository;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    void testAddingBoard() {
        assertTrue(boardRepository.findAll().isEmpty());
        Board board = new Board("Board");
        board = boardService.addBoard(board);
        assertNotNull(boardRepository.getById(board.getId()));
        assertFalse(boardRepository.findAll().isEmpty());
        boardRepository.delete(board);
    }

    @Test
    void addBoardNull() {
        Board toAdd = null;
        assertNull(boardService.addBoard(toAdd));
    }

    @Test
    void addBoardNullTitle() {
        Board toAdd = new Board("Board");
        toAdd.setTitle(null);
        assertNull(boardService.addBoard(toAdd));
    }

    @Test
    void existsById() {
        Board board = boardService.addBoard(new Board("b1"));
        boardService.existsById(board.getId());
        boardRepository.delete(board);
    }

    @Test
    void updateIfExists() {
        Board board = boardService.addBoard(new Board("b1"));
        board.setTitle("b2");
        board = boardService.update(board);
        assertEquals("b2", boardRepository.findById(board.getId()).get().getTitle());
        boardRepository.delete(board);
    }
    @Test
    void updateIfNotExists(){
        assertNull(boardService.update(new Board("b1")));
    }

    @Test
    void testUpdateIfNullTitle() {
        Board board = new Board();
        board.setTitle(null);
        assertNull(boardService.update(board));
    }

    @Test
    void deleteIfExists(){
        Board board = boardService.addBoard(new Board("b1"));
        assertTrue(boardService.delete(board.getId()));
    }
    @Test
    void deleteIfNotExists(){
        assertFalse(boardService.delete(1L));
    }
    @Test
    void subscribeForUpdates(){
        DeferredResult<ResponseEntity<Long>> df = boardService.subscribeForUpdates();
        assertNull(df.getResult());
    }
    @Test
    void testSendUpdates() {
        boardService.sendUpdates(1L);
    }

    @Test
    void testUpdateIfNull() {
        assertNull(boardService.update(null));
    }
}
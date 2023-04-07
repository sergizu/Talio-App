package client.scenes;

import client.dependentOnCompontents.TestBoardOptionsService;
import client.utils.ServerUtils;
import commons.Board;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardOptionsCtrlImplTest {

    TestBoardOptionsService optionsService;
    @Mock
    ServerUtils server;
    @Mock
    MainCtrl mainCtrl;
    BoardOptionsCtrlImpl boardOptionsCtrl;
    Board board;
    @BeforeEach
    void setUp(){
        optionsService = new TestBoardOptionsService();
        boardOptionsCtrl = new BoardOptionsCtrlImpl(server,mainCtrl,optionsService);
        board = new Board("board");
    }

    @Test
    void testConstructor() {
        assertNotNull(boardOptionsCtrl);

    }
    @Test
    void init() {

        boardOptionsCtrl.init(board);

        assertEquals(optionsService.calls.get(0),"setName");
        assertEquals(optionsService.calls.get(1),"init");
        assertEquals(board,boardOptionsCtrl.getBoard());
    }

    @Test
    void okSameNameAdmin() {
        boardOptionsCtrl.setBoard(board);
        optionsService.boardName = "board";
        given(mainCtrl.getAdmin()).willReturn(true);

        boardOptionsCtrl.ok();
        verify(server,never()).send("/app/boards/renameBoard",board);
        verify(server,never()).updateBoard(board);
        verify(mainCtrl).showBoardOverview();
        verify(mainCtrl,never()).showJoinedBoards();
    }

    @Test
    void okDifferentNameAdmin() {
        boardOptionsCtrl.setBoard(board);
        optionsService.boardName = "board2";
        given(mainCtrl.getAdmin()).willReturn(true);

        boardOptionsCtrl.ok();
        verify(server).send("/app/boards/renameBoard", board);
        verify(server).updateBoard(board);
        assertEquals("board2", boardOptionsCtrl.getBoard().title);
        verify(mainCtrl).showBoardOverview();
        verify(mainCtrl,never()).showJoinedBoards();
    }

    @Test
    void okSameNameClient() {
        boardOptionsCtrl.setBoard(board);
        optionsService.boardName = "board";
        given(mainCtrl.getAdmin()).willReturn(false);

        boardOptionsCtrl.ok();
        verify(server,never()).send("/app/boards/renameBoard",board);
        verify(server,never()).updateBoard(board);
        verify(mainCtrl,never()).showBoardOverview();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void okDifferentNameClient() {
        boardOptionsCtrl.setBoard(board);
        optionsService.boardName = "board2";
        given(mainCtrl.getAdmin()).willReturn(false);

        boardOptionsCtrl.ok();
        verify(server).send("/app/boards/renameBoard", board);
        verify(server).updateBoard(board);
        assertEquals("board2", boardOptionsCtrl.getBoard().title);
        verify(mainCtrl,never()).showBoardOverview();
        verify(mainCtrl).showJoinedBoards();
    }
    
    @Test 
    void testSetBoard(){
        boardOptionsCtrl.setBoard(board);
        assertEquals(board,boardOptionsCtrl.getBoard());
    }

    @Test
    void cancelAdmin() {
        given(mainCtrl.getAdmin()).willReturn(true);
        boardOptionsCtrl.cancel();
        verify(mainCtrl).showBoardOverview();
        verify(mainCtrl,never()).showJoinedBoards();
    }
    
    @Test 
    void cancelClient() {
        given(mainCtrl.getAdmin()).willReturn(false);
        boardOptionsCtrl.cancel();
        verify(mainCtrl,never()).showBoardOverview();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void delete() {
        board.id = 1L;
        boardOptionsCtrl.setBoard(board);
        boardOptionsCtrl.delete();
        verify(server).deleteBoard(1L);
        verify(server).send("/app/boards/deleteBoard", 1L);

    }

    @Test
    void showNextSceneClient(){
        given(mainCtrl.getAdmin()).willReturn(false);
        boardOptionsCtrl.showNextScene();
        verify(mainCtrl,never()).showBoardOverview();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void showNextSceneAdmin() {
        given(mainCtrl.getAdmin()).willReturn(true);
        boardOptionsCtrl.showNextScene();
        verify(mainCtrl).showBoardOverview();
        verify(mainCtrl,never()).showJoinedBoards();
    }


    @Test
    void keyPressedEnter() {
        boardOptionsCtrl.setBoard(board);
        boardOptionsCtrl.keyPressed(new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        assertEquals(optionsService.calls.get(0),"getName");
    }

    @Test
    void keyPressedEscape() {
        boardOptionsCtrl.keyPressed(new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(mainCtrl).getAdmin();
    }

    @Test
    void keyPressedOther() {
        boardOptionsCtrl.keyPressed(new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        verify(mainCtrl, never()).getAdmin();
    }
}
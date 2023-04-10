package client.scenes;

import client.scenes.implementations.CreateBoardCtrlImpl;
import client.services.interfaces.CreateBoardService;
import client.utils.ServerUtils;
import commons.AppClient;
import commons.Board;
import commons.TDList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBoardCtrlImplTest {
    @Mock
    ServerUtils serverUtils;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    CreateBoardService createBoardService;

    CreateBoardCtrlImpl createBoardCtrl;

    @BeforeEach
    void setUp() {
        createBoardCtrl = new CreateBoardCtrlImpl(serverUtils, mainCtrl, createBoardService);
    }

    @Test
    public void testConstructor() {
        assertNotNull(createBoardCtrl);
    }

    @Test
    void getBoardWithTitle() {
        given(createBoardService.getBoardName()).willReturn("Test");
        Board board = createBoardCtrl.getBoardWithTitle();
        assertEquals("Test", board.getTitle());
        Board boardAct = new Board("Test");
        createBoardCtrl.addDefaultLists(boardAct);
        assertEquals(boardAct, board);
    }

    @Test
    void getBoardWithTitle2() {
        given(createBoardService.getBoardName()).willReturn(null);
        Board board = createBoardCtrl.getBoardWithTitle();
        assertNull(board);
    }

    @Test
    void testCreateBoardNo(){
        given(createBoardService.getBoardName()).willReturn(null);
        createBoardCtrl.createBoard();
        verify(createBoardService).getBoardName();
        verify(createBoardService).setErrorLabel(anyString());
    }

    @Test
    void addDefaultLists() {
        Board board = new Board("Test");
        Board board2 = new Board("Test");
        board2.addList(new TDList("TO DO"));
        board2.addList(new TDList("DOING"));
        board2.addList(new TDList("DONE"));
        createBoardCtrl.addDefaultLists(board);
        assertEquals(board2, board);
    }

    @Test
    void createBoard() {
        Board board = new Board("Test");
        board.addList(new TDList("TO DO"));
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
        AppClient client = new AppClient();
        given(mainCtrl.getClient()).willReturn(client);
        given(mainCtrl.getAdmin()).willReturn(false);
        given(createBoardService.getBoardName()).willReturn("Test");
        given(serverUtils.addBoard(board)).willReturn(board);
        createBoardCtrl.createBoard();
        verify(createBoardService, times(2)).getBoardName();
        verify(createBoardService).setBoardName("");
        verify(serverUtils).addBoard(board);
        verify(mainCtrl).showOverview(0);
        verify(mainCtrl).getClient();
    }

    @Test
    void addBoardToClient() {
        Board board = new Board("Test");
        AppClient client = new AppClient();
        given(mainCtrl.getClient()).willReturn(client);
        createBoardCtrl.addBoardToClient(board);
        assertFalse(client.boards.isEmpty());
        assertEquals(board, client.boards.get(serverUtils.getServer()).get(0));
    }

    @Test
    void escapeJoinedBoardsCtrlParent() {
        given(mainCtrl.getAdmin()).willReturn(false);
        createBoardCtrl.escape();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void escapeBoardOverviewCtrl() {
        given(mainCtrl.getAdmin()).willReturn(true);
        createBoardCtrl.escape();
        verify(mainCtrl).showBoardOverview();
    }

    @Test
    void keyPressedEnter() {
        Board board = new Board("Test");
        board.addList(new TDList("TO DO"));
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
        given(createBoardService.getBoardName()).willReturn("Test");
        given(serverUtils.addBoard(board)).willReturn(board);
        AppClient client = new AppClient();
        given(mainCtrl.getClient()).willReturn(client);
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(mainCtrl).showOverview(0);
    }

    @Test
    void keyPressedEscape() {
        given(mainCtrl.getAdmin()).willReturn(false);
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void keyPressedAnyKey() {
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false));
        verify(mainCtrl, never()).showJoinedBoards();
        verify(mainCtrl, never()).showBoardOverview();
    }
}
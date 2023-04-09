package client.scenes;

import client.services.AddCardService;
import client.services.CreateBoardService;
import client.utils.ServerUtils;
import commons.AppClient;
import commons.Board;
import commons.TDList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateBoardCtrlImplTest {
    @Mock
    ServerUtils serverUtils;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    CreateBoardService createBoardService;

    static CreateBoardCtrlImpl createBoardCtrl;

    @BeforeEach
    void setUp() {
        createBoardCtrl = new CreateBoardCtrlImpl(serverUtils, mainCtrl, createBoardService);
    }

    @Test
    public void testConstructor() {
        assertNotNull(new CreateBoardCtrlImpl(serverUtils, mainCtrl, createBoardService));
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
        createBoardCtrl.setParent(JoinedBoardsCtrl.class);
        given(createBoardService.getBoardName()).willReturn("Test");
        given(serverUtils.addBoard(board)).willReturn(board);
        createBoardCtrl.createBoard();
        verify(createBoardService).getBoardName();
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
        createBoardCtrl.setParent(JoinedBoardsCtrl.class);
        createBoardCtrl.escape();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void escapeBoardOverviewCtrl() {
        createBoardCtrl.setParent(BoardOverviewCtrl.class);
        createBoardCtrl.escape();
        verify(mainCtrl).showBoardOverview();
    }

    @Test
    void setParent() {
        AnchorPane anchorPane = new AnchorPane();
        createBoardCtrl.setParent(anchorPane);
        assertEquals(anchorPane, createBoardCtrl.getParent());
    }

    @Test
    void keyPressedEnter() {
        Board board = new Board("Test");
        board.addList(new TDList("TO DO"));
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
        given(createBoardService.getBoardName()).willReturn("Test");
        given(serverUtils.addBoard(board)).willReturn(board);
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(mainCtrl).showOverview(0);
    }

    @Test
    void keyPressedEscape() {
        createBoardCtrl.setParent(JoinedBoardsCtrl.class);
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    void keyPressedAnyKey() {
        createBoardCtrl.setParent(JoinedBoardsCtrl.class);
        createBoardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false));
        verify(mainCtrl, never()).showJoinedBoards();
        verify(mainCtrl, never()).showBoardOverview();
    }
}
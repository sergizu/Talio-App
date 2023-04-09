package client.scenes;

import client.dependentOnCompontents.TestJoinedBoardsService;
import client.scenes.implementations.JoinedBoardsCtrlImpl;
import client.utils.ServerUtils;
import commons.AppClient;
import commons.Board;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JoinedBoardsCtrlImplTest {

    TestJoinedBoardsService doc;
    @Mock
    ServerUtils server;
    @Mock
    MainCtrl mainCtrl;
    JoinedBoardsCtrlImpl joinedBoardsCtrl;
    Board board;
    AppClient client;

    @BeforeAll
    static void usefulSetup() {
        MockedStatic<ServerUtils> mocked = mockStatic(ServerUtils.class);
        mocked.when(()->ServerUtils.getServer()).thenReturn("server");
    }
    @BeforeEach
    void setUp() {
        doc = new TestJoinedBoardsService();
        joinedBoardsCtrl = new JoinedBoardsCtrlImpl(server, mainCtrl, doc);
        board = new Board("board1");
        board.id = 1;
        board.key = 1;
        client = new AppClient();
        joinedBoardsCtrl.setClient(client);

    }
    @Test
    void testConstructor() {
        assertNotNull(joinedBoardsCtrl);
    }

    @Test
    void registerForBoardRename() {
        joinedBoardsCtrl.registerForBoardRename();
        assertTrue(doc.calls.isEmpty());
        verify(server).registerForMessages(eq("/topic/renameBoard"),eq(Board.class),any());
    }

    @Test
    void registerForBoardDeletion() {
        joinedBoardsCtrl.registerForBoardDeletion();
        assertTrue(doc.calls.isEmpty());
        verify(server).registerForMessages(eq("/topic/boardDeletion"),eq(Long.class),any());
    }

    @Test
    void registerForMessages() {
        joinedBoardsCtrl.registerForMessages();
        assertTrue(doc.calls.isEmpty());
        verify(server).registerForMessages(eq("/topic/renameBoard"),eq(Board.class),any());
        verify(server).registerForMessages(eq("/topic/boardDeletion"),eq(Long.class),any());
    }

    @Test
    void init() {
        given(mainCtrl.getClient()).willReturn(client);
        joinedBoardsCtrl.init();
        assertEquals(joinedBoardsCtrl.getClient(),client);
        assertTrue(doc.calls.contains("setPrompt"));
        assertTrue(doc.calls.contains("showJoinedBoards"));
    }


    @Test
    void addServerKeyIntoMap() {
        joinedBoardsCtrl.addServerKeyIntoMap("server");
        assertTrue(client.boards.containsKey("server"));
        assertNotNull(client.boards.get("server"));
    }

    @Test
    void addServerKeyIntoMap2() {
        client.boards.put("server",null);
        joinedBoardsCtrl.setClient(client);
        joinedBoardsCtrl.addServerKeyIntoMap("server");
        assertNull(client.boards.get("server"));
    }

    @Test
    void addExistentServerKeyIntoMap() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        joinedBoardsCtrl.addServerKeyIntoMap("server");

        assertTrue(client.boards.containsKey("server"));
        assertEquals(client,joinedBoardsCtrl.getClient());
    }

    @Test
    void getBoardsForServerContains() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);
        joinedBoardsCtrl.getBoardsForServer("server");
        assertTrue(doc.calls.contains("showJoinedBoards"));
        assertEquals(doc.boards,boards);
    }

    @Test
    void getBoardsForServerNotContains() {
        ArrayList<Board> boards = new ArrayList<>();
        joinedBoardsCtrl.getBoardsForServer("server");
        assertTrue(doc.calls.contains("showJoinedBoards"));
        assertEquals(doc.boards,boards);
    }

    @Test
    void showCreateBoard() {
        joinedBoardsCtrl.showCreateBoard();
        verify(mainCtrl).showCreateBoard();
    }

    @Test
    void leaveBoard() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        joinedBoardsCtrl.leaveBoard(board);
        assertTrue(doc.calls.contains("showJoinedBoards"));
        assertEquals(boards,client.boards.get("server"));
    }

    @Test
    void showOptions() {
        joinedBoardsCtrl.showOptions(board);
        verify(mainCtrl).showBoardOptions(board);
    }

    @Test
    void enterBoard() {
        joinedBoardsCtrl.enterBoard(board);
        verify(mainCtrl).showOverview(board.id);
    }

    @Test
    void disconnectPressed() {
        joinedBoardsCtrl.disconnectPressed();
        verify(mainCtrl).showSelectServer();
        verify(server).stopSession();
    }

    @Test
    void joinByKeyGoodKey() { ///a board with that key exists
        doc.toReturn = "1";
        Board board2 = new Board("board2");
        ArrayList<Board> allBoards = new ArrayList<>();
        allBoards.add(board);
        allBoards.add(board2);
        when(server.getBoards()).thenReturn(allBoards);

        joinedBoardsCtrl.joinByKey();

        verify(mainCtrl).showOverview(1);
        assertFalse(doc.calls.contains("adjustPrompt"));
        assertTrue(doc.calls.contains("getJoin"));
        assertTrue(doc.calls.contains("clearJoin"));
    }

    @Test
    void joinByKeyValidKey() {
        doc.toReturn = "1";
        when(server.getBoards()).thenReturn(new ArrayList<>());

        joinedBoardsCtrl.joinByKey();

        assertEquals("No board with that key!", doc.promptText);
    }

    @Test
    void joinByKeyInvalidKey() {
        doc.toReturn = "not a number";
        joinedBoardsCtrl.joinByKey();
        assertEquals("Please enter a valid key!", doc.promptText);

    }

    @Test
    void lookForBoardKeyTrue() {
        ArrayList<Board> allBoards = new ArrayList<>();
        allBoards.add(board);
        when(server.getBoards()).thenReturn(allBoards);

        assertTrue(joinedBoardsCtrl.lookForBoardKey(1));
        assertTrue(doc.calls.contains("clearJoin"));
    }

    @Test
    void lookForBoardKeyFalse() {
        ArrayList<Board> allBoards = new ArrayList<>();
        when(server.getBoards()).thenReturn(allBoards);

        assertFalse(joinedBoardsCtrl.lookForBoardKey(1));
        assertFalse(doc.calls.contains("clearJoin"));
    }

    @Test
    void joinBoard() {
        joinedBoardsCtrl.joinBoard(board);
        verify(mainCtrl).showOverview(board.id);
    }

    @Test
    void joinPressedEnter() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        joinedBoardsCtrl.joinPressed(event);
        assertTrue(doc.calls.contains("getJoin"));
    }

    @Test
    void joinPressedOtherKey() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        joinedBoardsCtrl.joinPressed(event);
        assertFalse(doc.calls.contains("getJoin"));
    }

    @Test
    void containsBoardIdNoBoards() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);

        assertFalse(joinedBoardsCtrl.containsBoardId(board));
        assertEquals(joinedBoardsCtrl.getClient().boards.get("server"),boards);
    }

    @Test
    void containsBoardId() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        assertTrue(joinedBoardsCtrl.containsBoardId(board));
        assertEquals(joinedBoardsCtrl.getClient().boards.get("server"),boards);
    }

    @Test
    void updateBoard() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server", boards);
        joinedBoardsCtrl.setClient(client);
        Board board2 = new Board("board2");
        boards.set(0,board2);

        joinedBoardsCtrl.updateBoard(board);

        assertEquals(client.boards.get("server"),boards);
        assertFalse(client.boards.get("server").contains(board));
        assertTrue(doc.calls.contains("showJoinedBoards"));
    }

    @Test
    void updateBoardSameTitle() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server", boards);
        joinedBoardsCtrl.setClient(client);
        Board board2 = new Board("board1");
        board2.id = 1;
        boards.set(0,board2);

        joinedBoardsCtrl.updateBoard(board);

        assertEquals(client.boards.get("server"),boards);
        assertTrue(client.boards.get("server").contains(board));
        assertTrue(doc.calls.contains("showJoinedBoards"));

    }

    @Test
    void removeBoardById() {
        ArrayList<Board> boards = new ArrayList<>();
        board.id = 1L;
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        joinedBoardsCtrl.removeBoardById(1L);

        assertEquals(client.boards.get("server").size(), 0);
    }

    @Test
    void removeBoardById2() {
        ArrayList<Board> boards = new ArrayList<>();
        board.id = 2L;
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        joinedBoardsCtrl.removeBoardById(1L);

        assertEquals(client.boards.get("server").size(), 1);
    }

    @Test
    void removeBoardById3() {
        joinedBoardsCtrl.setClient(null);
        given(mainCtrl.getClient()).willReturn(client);
        ArrayList<Board> boards = new ArrayList<>();
        board.id = 2L;
        boards.add(board);
        client.boards.put("server",boards);

        joinedBoardsCtrl.removeBoardById(1L);

        assertEquals(client.boards.get("server").size(), 1);
    }

    @Test
    void removeBoardById4() {
        joinedBoardsCtrl.setClient(null);
        given(mainCtrl.getClient()).willReturn(client);

        joinedBoardsCtrl.removeBoardById(1L);

        assertNull(client.boards.get("server"));
    }

    @Test
    void setClient() {
        joinedBoardsCtrl.setClient(client);
        assertEquals(joinedBoardsCtrl.getClient(),client);
    }

    @Test
    void getClient() {
        joinedBoardsCtrl.setClient(client);
        AppClient client2 = joinedBoardsCtrl.getClient();
        assertEquals(client, client2);
    }
}
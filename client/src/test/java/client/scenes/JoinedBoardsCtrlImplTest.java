package client.scenes;

import client.dependentOnCompontents.TestJoinedBoardsService;
import client.scenes.implementations.JoinedBoardsCtrlImpl;
import client.utils.ServerUtils;
import commons.AppClient;
import commons.Board;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

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

    @BeforeEach
    void setUp() {
        doc = new TestJoinedBoardsService();
        joinedBoardsCtrl = new JoinedBoardsCtrlImpl(server, mainCtrl, doc);
        board = new Board("board1");
        client = new AppClient();
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
        joinedBoardsCtrl.setClient(client);
        joinedBoardsCtrl.addServerKeyIntoMap("server");
        assertTrue(client.boards.containsKey("server"));
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
        joinedBoardsCtrl.setClient(client);
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
        MockedStatic<ServerUtils> mocked = mockStatic(ServerUtils.class);
        mocked.when(()->ServerUtils.getServer()).thenReturn("server");
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
    void joinByKey() {
    }

    @Test
    void lookForBoardKey() {
    }

    @Test
    void adjustPromptText() {
    }

    @Test
    void joinBoard() {
    }

    @Test
    void joinPressed() {
    }

    @Test
    void containsBoardId() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void removeBoardById() {
    }
}
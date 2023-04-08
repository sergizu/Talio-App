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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
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


    @BeforeEach
    void setUp() {
        doc = new TestJoinedBoardsService();
        joinedBoardsCtrl = new JoinedBoardsCtrlImpl(server, mainCtrl, doc);
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
        AppClient client = new AppClient();
        given(mainCtrl.getClient()).willReturn(client);
        joinedBoardsCtrl.init();
        assertEquals(joinedBoardsCtrl.getClient(),client);
        assertTrue(doc.calls.contains("setPrompt"));
        assertTrue(doc.calls.contains("showJoinedBoards"));
    }


    @Test
    void addServerKeyIntoMap() {
        AppClient client = new AppClient();
        joinedBoardsCtrl.setClient(client);
        joinedBoardsCtrl.addServerKeyIntoMap("server");
        assertTrue(client.boards.containsKey("server"));
    }

    @Test
    void addExistentServerKeyIntoMap() {
        AppClient client = new AppClient();
        board = new Board("board1");
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        client.boards.put("server",boards);
        joinedBoardsCtrl.setClient(client);

        joinedBoardsCtrl.addServerKeyIntoMap("server");

        assertTrue(client.boards.containsKey("server"));
        assertEquals(client,joinedBoardsCtrl.getClient());
    }

    @Test
    void getBoardsForServer() {
        
    }

    @Test
    void showCreateBoard() {
    }

    @Test
    void leaveBoard() {
    }

    @Test
    void showOptions() {
    }

    @Test
    void enterBoard() {
    }

    @Test
    void disconnectPressed() {
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
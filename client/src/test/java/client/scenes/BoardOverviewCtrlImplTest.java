package client.scenes;

import client.scenes.implementations.BoardOverviewCtrlImpl;
import client.scenes.interfaces.MainCtrl;
import client.services.interfaces.BoardOverviewService;
import client.utils.ServerUtils;
import commons.Board;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardOverviewCtrlImplTest {

    @Mock
    ServerUtils server;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    BoardOverviewService service;

    Board board;
    BoardOverviewCtrlImpl sut;

    @BeforeEach
    void setUp() {
        sut = new BoardOverviewCtrlImpl(server, mainCtrl, service);
        board = new Board("board");
        board.key = 1;
        board.id = 1;
    }

    @Test
    void testConstructor() {
        assertNotNull(sut);
    }

    @Test
    void registerForMessages() {
        sut.registerForMessages();
        verify(server).registerForMessages(eq("/topic/renameBoard"), eq(Board.class), any());
        verify(server).registerForMessages(eq("/topic/boardDeletion"), eq(Long.class), any());
        verify(server).registerForMessages(eq("/topic/boardCreation"), eq(Long.class), any());
    }

    @Test
    void registerForBoardRename() {
        sut.registerForBoardRename();
        verify(server).registerForMessages(eq("/topic/renameBoard"), eq(Board.class), any());
    }

    @Test
    void registerForBoardDeletion() {
        sut.registerForBoardDeletion();
        verify(server).registerForMessages(eq("/topic/boardDeletion"), eq(Long.class), any());
    }

    @Test
    void registerForBoardCreation() {
        sut.registerForBoardCreation();
        verify(server).registerForMessages(eq("/topic/boardCreation"), eq(Long.class), any());
    }

    @Test
    void keyPressedEnter() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        sut.keyPressed(event);
        verify(mainCtrl).showCreateBoard();
    }

    @Test
    void keyPressedAnother() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        sut.keyPressed(event);
        verify(mainCtrl, never()).showCreateBoard();
    }

    @Test
    void joinByKeyInvalid() {
        given(service.getJoinByKeyText()).willReturn("aaa");
        sut.joinByKey();
        verify(service).adjustPromptText("Please enter a valid key!");
    }

    @Test
    void joinByKeyValidFound() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        given(server.getBoards()).willReturn(boards);
        given(service.getJoinByKeyText()).willReturn("1");
        sut.joinByKey();
        verify(service).clearJoinByKey();
        verify(mainCtrl).showOverview(1);
    }

    @Test
    void joinByKeyNotFound() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        given(server.getBoards()).willReturn(boards);
        given(service.getJoinByKeyText()).willReturn("2");
        sut.joinByKey();
        verify(service,never()).clearJoinByKey();
        verify(mainCtrl,never()).showOverview(any(Long.class));
    }

    @Test
    void lookForBoardKeyFound() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        given(server.getBoards()).willReturn(boards);
        assertTrue(sut.lookForBoardKey(1));
        verify(mainCtrl).showOverview(1);
        verify(service).clearJoinByKey();
    }

    @Test
    void lookForBoardKeyNotFound() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        given(server.getBoards()).willReturn(boards);
        assertFalse(sut.lookForBoardKey(2));
        verify(mainCtrl,never()).showOverview(any(Long.class));
        verify(service,never()).clearJoinByKey();
    }

    @Test
    void enterBoard() {
        sut.enterBoard(board);
        verify(mainCtrl).showOverview(1);
    }

    @Test
    void joinPressedEnter() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        sut.joinPressed(event);
        verify(service).getJoinByKeyText();
    }

    @Test
    void joinPressedDifferentKey() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        sut.joinPressed(event);
        verify(service,never()).getJoinByKeyText();
    }

    @Test
    void showCreateBoard() {
        sut.showCreateBoard();
        verify(mainCtrl).showCreateBoard();
    }

    @Test
    void disconnectPressed() {
        sut.disconnectPressed();
        verify(mainCtrl).showSelectServer();
        verify(server).stop();
    }

    @Test
    void showBoardOptions() {
        sut.showBoardOptions(board);
        verify(mainCtrl).showBoardOptions(board);
    }

    @Test
    void getBoards() {
        sut.getBoards();
        verify(server).getBoards();
    }

    @Test
    void showAllBoards() {
        sut.showAllBoards();
        verify(service).showAllBoards();
    }
}

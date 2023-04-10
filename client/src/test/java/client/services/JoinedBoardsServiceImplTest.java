package client.services;

import client.scenes.interfaces.JoinedBoardsCtrl;
import client.services.implementations.JoinedBoardsServiceImpl;
import client.services.interfaces.JoinedBoardsService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JoinedBoardsServiceImplTest {

    @Mock
    JoinedBoardsCtrl joinedBoardsCtrl;

    JoinedBoardsService joinedBoardsService;
    @BeforeEach
    void setUp() {
        joinedBoardsService = new JoinedBoardsServiceImpl(joinedBoardsCtrl);
    }


    @Test
    void joinPressed() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        joinedBoardsService.joinPressed(event);
        verify(joinedBoardsCtrl).joinPressed(event);
    }

    @Test
    void disconnectPressed() {
        joinedBoardsService.disconnectPressed();
        verify(joinedBoardsCtrl).disconnectPressed();
    }

    @Test
    void showCreateBoard() {
        joinedBoardsService.showCreateBoard();
        verify(joinedBoardsCtrl).showCreateBoard();
    }

    @Test
    void joinByKey() {
        joinedBoardsService.joinByKey();
        verify(joinedBoardsCtrl).joinByKey();
    }

}
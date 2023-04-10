package client.services;

import client.scenes.interfaces.BoardOverviewCtrl;
import client.services.implementations.BoardOverviewServiceImpl;
import client.services.interfaces.BoardOverviewService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class BoardOverviewServiceImplTest {

    @Mock
    BoardOverviewCtrl boardOverviewCtrl;

    BoardOverviewService boardOverviewService;

    @BeforeEach
    void setUp() {
        boardOverviewService = new BoardOverviewServiceImpl(boardOverviewCtrl);
    }
    @Test
    void testConstructor() {
        assertNotNull(boardOverviewService);
    }

    @Test
    void disconnectPressed() {
        boardOverviewService.disconnectPressed();
        verify(boardOverviewCtrl).disconnectPressed();
    }

    @Test
    void joinByKey() {
        boardOverviewService.joinByKey();
        verify(boardOverviewCtrl).joinByKey();
    }

    @Test
    void joinPressed() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        boardOverviewService.joinPressed(event);
        verify(boardOverviewCtrl).joinPressed(event);
    }

    @Test
    void createBoard() {
        boardOverviewService.createBoard();
        verify(boardOverviewCtrl).showCreateBoard();
    }
}

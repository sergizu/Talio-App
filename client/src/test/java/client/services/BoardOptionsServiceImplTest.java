package client.services;

import client.scenes.interfaces.BoardOptionsCtrl;
import client.services.implementations.BoardOptionsServiceImpl;
import client.services.interfaces.BoardOptionsService;
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
class BoardOptionsServiceImplTest {

    @Mock
    BoardOptionsCtrl boardOptionsCtrl;

    BoardOptionsService boardOptionsService;
    @BeforeEach
    void setUp() {
        boardOptionsService = new BoardOptionsServiceImpl(boardOptionsCtrl);
    }

    @Test
    void testConstructor() {
        assertNotNull(boardOptionsService);
    }
    @Test
    void ok() {
        boardOptionsService.ok();
        verify(boardOptionsCtrl).ok();
    }

    @Test
    void cancel() {
        boardOptionsService.cancel();
        verify(boardOptionsCtrl).cancel();
    }

    @Test
    void keyPressed() {
        KeyEvent event = new KeyEvent(
                KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        boardOptionsService.keyPressed(event);
        verify(boardOptionsCtrl).keyPressed(event);
    }

    @Test
    void delete() {
        boardOptionsService.delete();
        verify(boardOptionsCtrl).delete();
    }
}
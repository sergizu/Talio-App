package client.services;

import client.helperClass.SubtaskWrapper;
import client.scenes.interfaces.EditCardCtrl;

import client.services.implementations.EditCardServiceImpl;
import client.services.interfaces.EditCardService;
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
public class EditCardServiceImplTest {

    @Mock
    private EditCardCtrl editCardCtrl;

    private EditCardService editCardService;

    private SubtaskWrapper subtaskWrapper;

    @BeforeEach
    void setUp() {
        editCardService = new EditCardServiceImpl(editCardCtrl, subtaskWrapper);
    }

    @Test
    void testConstructor() {
        assertNotNull(editCardService);
    }

    @Test
    public void okTest() {
        editCardService.ok();
        verify(editCardCtrl).ok();
    }

    @Test
    public void deleteTest() {
        editCardService.delete();
        verify(editCardCtrl).delete();
    }

    @Test
    public void cancelTest() {
        editCardService.cancel();
        verify(editCardCtrl).cancel();
    }

    @Test
    public void createSubtaskTest() {
        editCardService.createSubtask();
        verify(editCardCtrl).createSubtask();
    }

    @Test
    public void keyPressedTest() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        editCardService.keyPressed(event);
        verify(editCardCtrl).keyPressed(event);
    }
}

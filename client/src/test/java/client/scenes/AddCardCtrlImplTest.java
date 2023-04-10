package client.scenes;

import client.scenes.implementations.AddCardCtrlImpl;
import client.services.interfaces.AddCardService;
import client.utils.ServerUtils;
import commons.Card;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddCardCtrlImplTest {
    @Mock
    ServerUtils serverUtils;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    AddCardService addCardService;

    AddCardCtrlImpl addCardCtrl;

    @BeforeEach
    public void setUp() {
        addCardCtrl = new AddCardCtrlImpl(serverUtils, mainCtrl, addCardService);
    }

    @Test
    public void testConstructor() {
        assertNotNull(new AddCardCtrlImpl(serverUtils, mainCtrl, addCardService));
    }

    @Test
    public void testSetListBoardId() {
        addCardCtrl.setListBoardId(1, 2);
        assertEquals(1, addCardCtrl.getListId());
        assertEquals(2, addCardCtrl.getBoardId());
    }

    @Test
    public void testOk() {
        given(addCardService.getCardName()).willReturn("");
        addCardCtrl.ok();
        verify(addCardService).setMyLabelText("Card name cannot be empty!");
        verify(addCardService, never()).clearFields();
    }

    @Test
    public void testOk2() {
        given(addCardService.getCardName()).willReturn("Card 1");
        addCardCtrl.setListBoardId(1, 2);
        addCardCtrl.ok();

        verify(addCardService, never()).setMyLabelText("Card name cannot be empty!");
        verify(addCardService).clearFields();
        verify(serverUtils).addCardToList(1, new Card("Card 1"));
    }

    @Test
    public void testCancel() {
        addCardCtrl.cancel();
        verify(addCardService).clearFields();
    }


    @Test
    public void testKeyPressedEnter() {
        given(addCardService.getCardName()).willReturn("");
        addCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(addCardService).setMyLabelText("Card name cannot be empty!");
    }

    @Test
    public void testKeyPressedEscape() {
        addCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(addCardService).clearFields();
        verify(mainCtrl).showOverview(anyLong());
    }

    @Test
    public void testKeyPressedOther() {
        addCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        verify(addCardService, never()).setMyLabelText("Card name cannot be empty!");
        verify(addCardService, never()).clearFields();
        verify(mainCtrl, never()).showOverview(anyLong());
    }
}
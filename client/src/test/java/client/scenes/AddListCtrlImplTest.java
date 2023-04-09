package client.scenes;

import client.scenes.implementations.AddListCtrlImpl;
import client.services.interfaces.AddListService;
import client.utils.ServerUtils;
import commons.TDList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddListCtrlImplTest {

    @Mock
    ServerUtils serverUtils;

    @Mock
    MainCtrl mainCtrl;

    @Mock
    AddListService addListService;
    static AddListCtrlImpl addListCtrl;

    @BeforeEach
    void setUp() {
        addListCtrl = new AddListCtrlImpl(serverUtils, mainCtrl, addListService);
    }

    @Test
    void testConstructor() {
        assertNotNull(addListCtrl);
    }

    @Test
    void testGetListEmpty() {
        given(addListService.getListTitle()).willReturn("");
        assertNull(addListCtrl.getList());
    }

    @Test
    void testGetListNotEmpty() {
        given(addListService.getListTitle()).willReturn("test");
        assertNotNull(addListCtrl.getList());
    }

    @Test
    void testCancel() {
        addListCtrl.cancel();
        verify(addListService).clearFields();
        verify(mainCtrl).showOverview(anyLong());
    }

    @Test
    void testOkEmptyName() {
        given(addListService.getListTitle()).willReturn("");
        addListCtrl.ok();
        verify(serverUtils, never()).addListToBoard(anyLong(), any(TDList.class));
    }

    @Test
    void testOkNotEmptyName() {
        given(addListService.getListTitle()).willReturn("test");
        addListCtrl.ok();
        verify(serverUtils).addListToBoard(anyLong(), any(TDList.class));
    }

    @Test
    void testClearFields() {
        addListCtrl.clearFields();
        verify(addListService).clearFields();
    }

    @Test
    void testKeyPressedEnter() {
        given(addListService.getListTitle()).willReturn("");
        addListCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(addListService).setEmptyNameText("List name can not be empty!");
    }

    @Test
    void testKeyPressedEscape() {
        addListCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(addListService).clearFields();
    }

    @Test
    void testKeyPressedOther() {
        addListCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        verify(addListService, never()).getListTitle();
        verify(addListService, never()).clearFields();
    }


    @Test
    void setBoard() {
        addListCtrl.setBoard(1);
        assertEquals(1, addListCtrl.getBoardId());
    }
}
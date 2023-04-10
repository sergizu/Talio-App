package client.scenes;

import client.helperClass.SubtaskWrapper;
import client.scenes.implementations.AddCardCtrlImpl;
import client.scenes.implementations.EditCardCtrlImpl;
import client.scenes.interfaces.EditCardCtrl;
import client.services.interfaces.AddCardService;
import client.services.interfaces.EditCardService;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditCardCtrlImplTest {

    @Mock
    ServerUtils server;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    EditCardService service;

    EditCardCtrlImpl editCardCtrl;

    SubtaskWrapper subtaskWrapper;

    Card card;

    Board board;

    TDList tdList;

    @BeforeEach
    public void setUp() {
        editCardCtrl = new EditCardCtrlImpl(server, mainCtrl, subtaskWrapper, service);
        card = new Card("Card", "Description");
        this.board = new Board("Board");
        this.tdList = new TDList("List");
        tdList.setBoard(board);
        tdList.addCard(card);
        board.addList(tdList);
        card.setList(tdList);
        editCardCtrl.setCard(card);
    }

    @Test
    public void testConstructor() {
        assertNotNull(editCardCtrl);
    }

    @Test
    public void initTest() {
        editCardCtrl.init(card);
        verify(service).setCardName(card.getTitle());
        verify(service).setDescription(card.getDescription());
        verify(service).initTableView(service.initSubtask(card.getNestedList()));
    }

    @Test
    public void initSubtaskTestNewValuesNonEmpty() {
        given(service.getCardName()).willReturn("Card1");
        given(service.getDescription()).willReturn("Description1");
        editCardCtrl.ok();
        verify(service, times(3)).getCardName();
        verify(service, times(3)).getDescription();
        verify(server).updateCardDescription(card.getId(), "Description1");
        verify(server, never()).updateCardDescription(card.getId(), " ");
        verify(mainCtrl).showOverview(board.getId());
    }

    @Test
    public void initSubtaskTestNewValuesEmptyCard() {
        given(service.getCardName()).willReturn("");
        editCardCtrl.ok();
        verify(service, times(2)).getCardName();
        verify(service).setEmptyName("Card name can not be empty!");
        verify(server, never()).updateCardDescription(card.getId(), " ");
        verify(service, never()).getDescription();
        verify(mainCtrl, never()).showOverview(board.getId());
    }

    @Test
    public void initSubtaskTestNewValuesEmptyDescription() {
        given(service.getCardName()).willReturn("Card1");
        given(service.getDescription()).willReturn("");
        editCardCtrl.ok();
        verify(service, times(3)).getCardName();
        verify(service).getDescription();
        verify(server).updateCardDescription(card.getId(), " ");
        verify(mainCtrl).showOverview(board.getId());
    }
    @Test
    public void initSubtaskTestOldValues() {
        given(service.getCardName()).willReturn("Card");
        given(service.getDescription()).willReturn("Description");
        editCardCtrl.ok();
        verify(service).getCardName();
        verify(service, times(2)).getDescription();
        verify(server, never()).updateCardDescription(card.getId(), " ");
        verify(server, never()).updateCardDescription(card.getId(), "Description");
        verify(mainCtrl).showOverview(board.getId());
    }

    @Test
    public void deleteTest() {
        editCardCtrl.delete();
        verify(server).removeCard(card);
        verify(service).setEmptyName("");
        verify(mainCtrl).showOverview(board.getId());
    }

    @Test
    public void keyPressedTestEmpty() {
        given(service.getCardName()).willReturn("");
        editCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(service).setEmptyName("Card name can not be empty!");
    }

    @Test
    public void keyPressedTestEnter() {
        given(service.getCardName()).willReturn("Hello");
        given(service.getDescription()).willReturn("Hello");
        editCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
        verify(server).updateCardName(card.getId(), service.getCardName());
    }
    @Test
    public void keyPressedTestEscape() {
        editCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
        verify(mainCtrl).showOverview(board.getId());
    }

    @Test
    public void keyPressedTestAnyKey() {
        editCardCtrl.keyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        verify(mainCtrl, never()).showOverview(board.getId());
        verify(service, never()).setEmptyName("Card name can not be empty!");
        verify(server, never()).updateCardName(card.getId(), service.getCardName());
    }

    @Test
    public void cancelTest() {
        editCardCtrl.cancel();
        verify(mainCtrl).showOverview(board.getId());
    }

    @Test
    public void createSubtaskTest() {
        editCardCtrl.createSubtask();
        verify(mainCtrl).showAddSubtask(card);
    }

}

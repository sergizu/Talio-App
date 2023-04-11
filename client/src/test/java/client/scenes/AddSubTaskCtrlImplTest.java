package client.scenes;

import client.scenes.implementations.AddSubTaskCtrlImpl;
import client.scenes.interfaces.MainCtrl;
import client.services.interfaces.AddSubTaskService;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddSubTaskCtrlImplTest {
    @Mock
    private ServerUtils server;
    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private AddSubTaskService addSubTaskService;
    private AddSubTaskCtrlImpl addSubTaskCtrl;

    @BeforeEach
    void setUp() {
        addSubTaskCtrl = new AddSubTaskCtrlImpl(server, mainCtrl, addSubTaskService);
    }

    @Test
    void testConstructor() {
        assertNotNull(addSubTaskCtrl);
    }

    @Test
    void testInit() {
        addSubTaskCtrl.init(new Card("test card"));
        assertEquals("test card", addSubTaskCtrl.getCard().getTitle());
    }

    @Test
    void testCreateEmptyName() {
        given(addSubTaskService.getSubtaskName()).willReturn("");
        addSubTaskCtrl.create();
        verify(addSubTaskService).setMyLabelText("Name cannot be empty!");
    }

    @Test
    void testCreateNotEmptyName() {
        given(addSubTaskService.getSubtaskName()).willReturn("test subtask");
        addSubTaskCtrl.init(new Card("test card"));
        addSubTaskCtrl.create();
        assertTrue(addSubTaskCtrl.getCard().nestedList.contains(new Subtask("test subtask")));
        verify(addSubTaskService).setSubtaskName("");
        verify(server).updateNestedList(addSubTaskCtrl.getCard().id, addSubTaskCtrl.getCard().nestedList);
        verify(mainCtrl).showEdit(addSubTaskCtrl.getCard());
    }

    @Test
    void testCancel() {
        Board board = new Board("test board");
        board.id = 1L;
        TDList list = new TDList("test list");
        Card card = new Card("test card");
        card.list = list;
        list.board = board;
        addSubTaskCtrl.init(card);
        addSubTaskCtrl.cancel();
        verify(addSubTaskService).setMyLabelText("");
        verify(mainCtrl).showEdit(card);
    }
}
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

    @BeforeEach
    public void setUp() {
        editCardCtrl = new EditCardCtrlImpl(server, mainCtrl, subtaskWrapper, service);
        card = new Card("Card", "Description");
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
        verify(service).dragAndDrop();
    }
//
//    @Test
//    public void initSubtaskTest() {
//        Board board = new Board("Board");
//        TDList tdList = new TDList("List");
//        tdList.setBoard(board);
//        board.addList(tdList);
//        card.setList(tdList);
//        editCardCtrl.setCard(card);
//        given(service.getCardName()).willReturn("Card1");
//        given(service.getDescription()).willReturn("Description1");
//        verify(mainCtrl, never()).showOverview(card.getList().getBoard().getId());
//        verify(service).setEmptyName("");
//        verify(server).updateCardName(card.getId(), service.getCardName());
//    }
}

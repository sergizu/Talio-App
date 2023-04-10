package client.scenes;

import client.scenes.implementations.ListOverviewCtrlImp;
import client.scenes.interfaces.MainCtrl;
import client.services.interfaces.ListOverviewService;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListOverviewCtrlImplTest {
    @Mock
    ServerUtils serverUtils;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    ListOverviewService listOverviewService;

    static ListOverviewCtrlImp listOverviewCtrl;

    @BeforeEach
    void setUp() {
        listOverviewCtrl = new ListOverviewCtrlImp(serverUtils, mainCtrl, listOverviewService);
    }

    @Test
    public void testConstructor() {
        assertNotNull(listOverviewService);
    }

    @Test
    public void testInit(){
        listOverviewCtrl.init();
        verify(listOverviewService).setScrollPane();
        verify(serverUtils).registerForBoardUpdates(any());
        verify(serverUtils, times(2)).registerForMessages(any(), any(), any());
    }

    @Test
    public void testSetBoard(){
        Board board = new Board("Test");
        given(serverUtils.getBoardById(1)).willReturn(board);
        listOverviewCtrl.setBoard(1);
        verify(serverUtils).getBoardById(1);
        verify(listOverviewService).setBoardTitle(board.title);
        verify(listOverviewService).showLists(board.tdLists);
    }

    @Test
    public void testUpdateList(){
        TDList tdList = new TDList("Test");
        List<Card> lists = new ArrayList<>();
        lists.add(new Card("test"));
        listOverviewCtrl.updateList(tdList, lists);
        verify(serverUtils).updateBoard(any());
    }

    @Test
    public void testGetBoardKey(){
        listOverviewCtrl.init();
        assertEquals(0, listOverviewCtrl.getBoardKey());
    }

    @Test
    public void testAddCardToList(){
        Board board = new Board("Test");
        TDList tdList = new TDList("Test2");
        tdList.cards = new ArrayList<>();
        tdList.cards.add(new Card("TestCard"));
        List<TDList> lists = new ArrayList<>();
        lists.add(tdList);
        board.tdLists = lists;
        given(serverUtils.getBoardById(anyLong())).willReturn(board);
        listOverviewCtrl.setBoard(1);
        listOverviewCtrl.addCardToList(new Card("T2"), 0);
    }

    @Test
    public void testSetBoardNoArg(){
        Board board = new Board(" ");
        listOverviewCtrl.init();
        given(serverUtils.getBoardById(anyLong())).willReturn(board);
        listOverviewCtrl.setBoard();
        verify(serverUtils).getBoardById(anyLong());
        verify(listOverviewService).setBoardTitle(board.title);
        verify(listOverviewService).showLists(board.tdLists);
    }

    @Test
    public void testStopActive(){
        given(serverUtils.isExecutorServiceShutdown()).willReturn(true);
        listOverviewCtrl.stop();
        verify(serverUtils, never()).stop();
    }

    @Test
    public void testNoStop(){
        given(serverUtils.isExecutorServiceShutdown()).willReturn(false);
        listOverviewCtrl.stop();
        verify(serverUtils).stop();
    }

    @Test
    public void testAddList(){
        given(serverUtils.getBoardById(1)).willReturn(new Board("Test"));
        listOverviewCtrl.setBoard(1);
        listOverviewCtrl.addList();
        verify(mainCtrl).showAddList(anyLong());
    }

    @Test
    public void testBackPressed(){
        given(mainCtrl.getAdmin()).willReturn(true);
        listOverviewCtrl.backPressed();
        verify(mainCtrl).showBoardOverview();
    }

    @Test
    public void testBackPressed2(){
        given(mainCtrl.getAdmin()).willReturn(false);
        listOverviewCtrl.backPressed();
        verify(mainCtrl).showJoinedBoards();
    }

    @Test
    public void testShowAddCard(){
        given(serverUtils.getBoardById(1)).willReturn(new Board("Test"));
        listOverviewCtrl.setBoard(1);
        listOverviewCtrl.showAddCard(1);
        verify(mainCtrl).showAddCard(anyLong(), anyLong());
    }

    @Test
    public void testShowEditList(){
        TDList tdList = new TDList("Test");
        listOverviewCtrl.showEditList(tdList);
        verify(mainCtrl).showEditList(tdList);
    }

    @Test
    public void testShowEdit(){
        Card card = new Card("Test");
        listOverviewCtrl.showEdit(card);
        verify(mainCtrl).showEdit(card);
    }

    @Test
    public void testUpdateCardList(){
        TDList tdList = new TDList("Test");
        listOverviewCtrl.updateCardList(1, tdList);
        verify(serverUtils).updateCardList(1, tdList);
    }

    @Test
    public void testRemoveList(){
        listOverviewCtrl.removeList(new TDList("Test"));
        verify(serverUtils).removeList(any());
    }
}

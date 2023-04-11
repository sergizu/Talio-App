package client.scenes;

import client.factory.SceneFactory;
import client.helperClass.SubtaskWrapper;
import client.scenes.implementations.MainCtrlImpl;
import client.scenes.interfaces.*;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainCtrlImplTest {
    @Mock
    private ListOverviewCtrl listOverviewCtrl;
    @Mock
    private EditCardCtrl editCardCtrl;
    @Mock
    private EditListCtrl editListCtrl;
    @Mock
    private BoardOverviewCtrl boardOverviewCtrl;
    @Mock
    private JoinedBoardsCtrl joinedBoardsCtrl;
    @Mock
    private CreateBoardCtrl createBoardCtrl;
    @Mock
    private AddCardCtrl createAddCardCtrl;
    @Mock
    private AddListCtrl createAddListCtrl;
    @Mock
    private AddSubTaskCtrl createAddSubtaskCtrl;
    @Mock
    private BoardOptionsCtrl boardOptionsCtrl;
    @Mock
    private SubtaskWrapper subtaskWrapper;
    @Mock
    private SceneFactory sceneFactory;
    @Mock
    private Stage primaryStage;
    @Mock
    private Scene scene;
    @Mock
    private DataFormat dataFormat;
    private final int WIDTH = 1080;
    private final int HEIGHT = 720;


    private MainCtrlImpl mainCtrl;
    @BeforeEach
    void setUp() {
        given(sceneFactory.createAddListScene()).willReturn(scene);
        given(sceneFactory.createAddCardScene()).willReturn(scene);
        mainCtrl = new MainCtrlImpl(listOverviewCtrl, editCardCtrl, editListCtrl,
                boardOverviewCtrl, joinedBoardsCtrl,
                createBoardCtrl, createAddCardCtrl, createAddListCtrl,
                createAddSubtaskCtrl, boardOptionsCtrl, subtaskWrapper, dataFormat);
        mainCtrl.initialize(primaryStage, sceneFactory);
    }

    @Test
    void initialize() {
        verify(primaryStage).show();
    }

    @Test
    void showSelectServer() {
        verify(primaryStage).setTitle("Server: selecting server");
    }

    @Test
    void showOverview() {
        mainCtrl.showOverview(1L);
        verify(listOverviewCtrl).setBoard(1L);
        verify(primaryStage).setTitle("Lists: Overview");
    }

    @Test
    void showBoardOverview() {
        mainCtrl.showBoardOverview();
        verify(primaryStage).setTitle("Boards: Overview");
        verify(boardOverviewCtrl).showAllBoards();
    }

    @Test
    void showAddCard() {
        mainCtrl.showAddCard(1L, 2L);
        verify(createAddCardCtrl).setListBoardId(1L, 2L);
    }

    @Test
    void showAddList() {
        mainCtrl.showAddList(1L);
        verify(primaryStage).setTitle("Board: Adding List");
        verify(createAddListCtrl).setBoard(1L);
    }

    @Test
    void showEdit() {
        Card card = new Card("test");
        mainCtrl.showEdit(card);
        verify(primaryStage).setTitle("Card: Edit Card");
        verify(editCardCtrl).init(card);
    }

    @Test
    void showEditList() {
        TDList list = new TDList("test");
        mainCtrl.showEditList(list);
        verify(primaryStage).setTitle("List: Rename list");
        verify(editListCtrl).init(list);
    }

    @Test
    void showAddSubtask() {
        Card card = new Card("test");
        mainCtrl.showAddSubtask(card);
        verify(primaryStage).setTitle("Subtask: Create subtask");
        verify(createAddSubtaskCtrl).init(card);
    }

    @Test
    void showJoinedBoards() {
        mainCtrl.showJoinedBoards();
        verify(primaryStage).setTitle("Your boards");
        verify(joinedBoardsCtrl).init();
    }

    @Test
    void showCreateBoard() {
        mainCtrl.showCreateBoard();
        verify(primaryStage).setTitle("Create a new board");
    }

    @Test
    void showBoardOptions() {
        Board board = new Board("test");
        mainCtrl.showBoardOptions(board);
        verify(primaryStage).setTitle("Board options");
        verify(boardOptionsCtrl).init(board);
    }

    @Test
    void testGetAppClient() {
        assertNotNull(mainCtrl.getClient());
    }

    @Test
    void adminSetters() {
        mainCtrl.setAdmin(true);
        assertTrue(mainCtrl.getAdmin());
        mainCtrl.setAdmin(false);
        assertFalse(mainCtrl.getAdmin());
    }

    @Test
    void triggerResize() {
        double size = 10.0;
        given(primaryStage.getHeight()).willReturn(size);
        mainCtrl.triggerSceneResize();
        verify(primaryStage).setHeight(size + 0.5);
    }

    @Test
    void getTitle() {
        String name = "app name";
        given(primaryStage.getTitle()).willReturn(name);
        assertEquals(name, mainCtrl.getPrimaryStageTitle());
    }

    @Test
    void setScene() {
        mainCtrl.setPrimaryStage();
        verify(primaryStage, times(2)).setWidth(WIDTH);
        verify(primaryStage, times(2)).setHeight(HEIGHT);
    }
}
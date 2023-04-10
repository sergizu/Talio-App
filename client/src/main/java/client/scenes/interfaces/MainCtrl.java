package client.scenes.interfaces;

import client.factory.SceneFactory;
import client.scenes.implementations.MainCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.AppClient;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.stage.Stage;

@ImplementedBy(MainCtrlImpl.class)
public interface MainCtrl {
    void initialize(Stage primaryStage, SceneFactory sceneFactory);
    void showSelectServer();
    void showOverview(long boardId);
    void showBoardOverview();
    void showAddCard(long listId, long boardId);
    void showAddList(long boardId);
    void showEdit(Card card);
    void showEditList(TDList list);
    void showAddSubtask(Card card);
    void showJoinedBoards();
    void showCreateBoard();
    void showBoardOptions(Board board);
    AppClient getClient();
    void setAdmin(boolean value);
    boolean getAdmin();
    void triggerSceneResize();
    String getPrimaryStageTitle();
    void setPrimaryStage();
}

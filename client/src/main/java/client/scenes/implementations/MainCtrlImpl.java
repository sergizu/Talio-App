/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes.implementations;

import client.factory.SceneFactory;
import client.helperClass.SubtaskWrapper;
import client.scenes.EditListCtrl;
import client.scenes.interfaces.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.AppClient;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;

@Singleton
public class MainCtrlImpl implements MainCtrl {

    private Stage primaryStage;

    private Scene overview;
    private final ListOverviewCtrl listOverviewCtrl;

    private Scene editCardScene;
    private final EditCardCtrl editCardCtrl;

    private Scene editListScene;
    private final EditListCtrl editListCtrl;

    private Scene selectServer;

    private Scene boardOverviewScene;
    private final BoardOverviewCtrl boardOverviewCtrl;

    private Scene joinedBoardsScene;
    private final JoinedBoardsCtrl joinedBoardsCtrl;

    private Scene createBoardScene;
    private final CreateBoardCtrl createBoardCtrl;

    private Scene createAddCardScene;
    private final AddCardCtrl createAddCardCtrl;

    private Scene createAddListScene;
    private final AddListCtrl createAddListCtrl;

    private Scene createAddSubtaskScene;
    private final AddSubTaskCtrl createAddSubtaskCtrl;

    private final BoardOptionsCtrl boardOptionsCtrl;
    private Scene boardOptionsScene;

    private boolean isAdmin;
    private final int width = 1080;
    private final int heigth = 720;

    private final SubtaskWrapper subtaskWrapper;

    private AppClient client;
    private final DataFormat dataFormat;


    @SuppressWarnings("ParameterNumber")
    @Inject
    public MainCtrlImpl(ListOverviewCtrl listOverviewCtrl, EditCardCtrl editCardCtrl,
                    EditListCtrl editListCtrl,
                    BoardOverviewCtrl boardOverviewCtrl, JoinedBoardsCtrl joinedBoardsCtrl,
                    CreateBoardCtrl createBoardCtrl, AddCardCtrl createAddCardCtrl,
                    AddListCtrl createAddListCtrl, AddSubTaskCtrl createAddSubtaskCtrl,
                    BoardOptionsCtrl boardOptionsCtrl, SubtaskWrapper subtaskWrapper,
                    DataFormat dataFormat) {
        this.listOverviewCtrl = listOverviewCtrl;
        this.editCardCtrl = editCardCtrl;
        this.editListCtrl = editListCtrl;
        this.boardOverviewCtrl = boardOverviewCtrl;
        this.joinedBoardsCtrl = joinedBoardsCtrl;
        this.createBoardCtrl = createBoardCtrl;
        this.createAddCardCtrl = createAddCardCtrl;
        this.createAddListCtrl = createAddListCtrl;
        this.createAddSubtaskCtrl = createAddSubtaskCtrl;
        this.boardOptionsCtrl = boardOptionsCtrl;
        this.subtaskWrapper = subtaskWrapper;
        this.dataFormat = dataFormat;
    }

    public void initialize(Stage primaryStage,
                           SceneFactory sceneFactory) {

        this.primaryStage = primaryStage;

        this.overview = sceneFactory.createListOverviewScene();

        this.editCardScene = sceneFactory.createEditCardScene();

        this.editListScene = sceneFactory.createEditListScene();

        this.selectServer = sceneFactory.createSelectServerScene();

        this.boardOverviewScene = sceneFactory.createBoardOverviewScene();

        this.joinedBoardsScene = sceneFactory.createJoinedBoardsScene();

        this.createBoardScene = sceneFactory.createNewBoardScene();

        this.createAddCardScene = sceneFactory.createAddCardScene();

        this.createAddListScene = sceneFactory.createAddListScene();

        this.createAddSubtaskScene = sceneFactory.createAddSubtaskScene();

        this.boardOptionsScene = sceneFactory.createBoardOptionsScene();
        this.client = new AppClient();

        subtaskWrapper.setSerialization(dataFormat);

        showSelectServer();
        setPrimaryStage();
        primaryStage.show();
    }

    public void showSelectServer() {
        primaryStage.setTitle("Server: selecting server");
        primaryStage.setScene(selectServer);
        triggerSceneResize();
    }

    public void showOverview(long boardId) {
        primaryStage.setTitle("Lists: Overview");
        listOverviewCtrl.setBoard(boardId);
        primaryStage.setScene(overview);
        triggerSceneResize();
    }

    public void showBoardOverview() {
        primaryStage.setTitle("Boards: Overview");
        primaryStage.setScene(boardOverviewScene);
        boardOverviewCtrl.showAllBoards();
        triggerSceneResize();
    }

    public void showAddCard(long listId, long boardId) {
        primaryStage.setTitle("Board: Adding Card");
        createAddCardCtrl.setListBoardId(listId, boardId);
        primaryStage.setScene(createAddCardScene);
        createAddCardScene.setOnKeyPressed(createAddCardCtrl::keyPressed);
        triggerSceneResize();
    }

    public void showAddList(long boardId) {
        primaryStage.setTitle("Board: Adding List");
        primaryStage.setScene(createAddListScene);
        createAddListCtrl.setBoard(boardId);
        createAddListScene.setOnKeyPressed(createAddListCtrl::keyPressed);
        triggerSceneResize();
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        editCardCtrl.init(card);
        primaryStage.setScene(editCardScene);
        triggerSceneResize();
    }

    public void showEditList(TDList list) {
        primaryStage.setTitle("List: Rename list");
        primaryStage.setScene(editListScene);
        editListCtrl.init(list);
        triggerSceneResize();
    }

    public void showAddSubtask(Card card) {
        primaryStage.setTitle("Subtask: Create subtask");
        primaryStage.setScene(createAddSubtaskScene);
        createAddSubtaskCtrl.init(card);
        triggerSceneResize();
    }

    public void showJoinedBoards() {
        primaryStage.setTitle("Your boards");
        primaryStage.setScene(joinedBoardsScene);
        joinedBoardsCtrl.init();
        triggerSceneResize();
    }

    public void showCreateBoard() {
        primaryStage.setTitle("Create a new board");
        primaryStage.setScene(createBoardScene);
        triggerSceneResize();
    }

    public void showBoardOptions(Board board) {
        primaryStage.setTitle("Board options");
        primaryStage.setScene(boardOptionsScene);
        boardOptionsCtrl.init(board);
        triggerSceneResize();
    }

    public AppClient getClient() {
        return client;
    }

    public void setAdmin(boolean value) {
        isAdmin = value;
    }
    public boolean getAdmin() {
        return isAdmin;
    }

    public void triggerSceneResize() {
        primaryStage.setHeight(primaryStage.getHeight() + 0.5);
    }

    public String getPrimaryStageTitle() {
        return primaryStage.getTitle();
    }

    public void setPrimaryStage() {
        this.primaryStage.setWidth(width);
        this.primaryStage.setHeight(heigth);
        this.primaryStage.setMinWidth(537);
        this.primaryStage.setMinHeight(409);

        this.primaryStage.setOnCloseRequest(event -> this.listOverviewCtrl.stop());
    }
}
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
package client.scenes;

import client.factory.SceneFactory;
import client.helperClass.SubtaskWrapper;
import client.scenes.implementations.BoardOptionsCtrlImpl;
import client.scenes.interfaces.*;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;

public class MainCtrl {

    private Stage primaryStage;

    private Scene overview;
    @Inject
    private ListOverviewCtrl listOverviewCtrl;

    private Scene edit;
    @Inject
    private EditCardCtrl editCardCtrl;

    private Scene editListScene;
    @Inject
    private EditListCtrl editListCtrl;

    private Scene selectServer;

    private Scene boardOverviewScene;
    @Inject
    private BoardOverviewCtrl boardOverviewCtrl;

    private Scene joinedBoardsScene;
    @Inject
    private JoinedBoardsCtrl joinedBoardsCtrl;

    private Scene createBoardScene;
    @Inject
    private CreateBoardCtrl createBoardCtrl;

    private Scene createAddCardScene;
    @Inject
    private AddCardCtrl createAddCardCtrl;

    private Scene createAddListScene;
    @Inject
    private AddListCtrl createAddListCtrl;

    private Scene createAddSubtaskScene;
    @Inject
    private AddSubTaskCtrl createAddSubtaskCtrl;

    @Inject
    private BoardOptionsCtrlImpl boardOptionsCtrl;
    private Scene boardOptionsScene;

    boolean isAdmin;
    @Inject
    private SubtaskWrapper subtaskWrapper;

    private AppClient client;

    public void initialize(Stage primaryStage,
                           SceneFactory sceneFactory) {

        this.primaryStage = primaryStage;

        this.overview = new Scene(sceneFactory.createListOverviewScene(), 1080, 720);

        this.edit = new Scene(sceneFactory.createEditCardScene(), 1080, 720);

        this.editListScene = new Scene(sceneFactory.createEditListScene(), 1080, 720);

        this.selectServer = new Scene(sceneFactory.createSelectServerScene(), 1080, 720);

        this.boardOverviewScene = new Scene(sceneFactory.createBoardOverviewScene(), 1080, 720);

        this.joinedBoardsScene = new Scene(sceneFactory.createJoinedBoardsScene(), 1080, 720);

        this.createBoardScene = new Scene(sceneFactory.createNewBoardScene(), 1080, 720);

        this.createAddCardScene = new Scene(sceneFactory.createAddCardScene(), 1080, 720);

        this.createAddListScene = new Scene(sceneFactory.createAddListScene(), 1080, 720);

        this.createAddSubtaskScene = new Scene(sceneFactory.createAddSubtaskScene(), 1080, 720);

        this.boardOptionsScene = new Scene(sceneFactory.createBoardOptionsScene(), 1080, 720);
        this.client = new AppClient();

        subtaskWrapper.setSerialization(new DataFormat("application/x-java-serialized-object"));

        showSelectServer();
        setPrimaryStage();
        primaryStage.show();
    }

    public void showSelectServer() {
        primaryStage.setTitle("Server: selecting server");
        primaryStage.setScene(selectServer);
        setSizeScene();
    }

    public void showOverview(long boardId) {
        primaryStage.setTitle("Lists: Overview");
        listOverviewCtrl.setBoard(boardId);
        primaryStage.setScene(overview);
        setSizeScene();
    }

    public void showBoardOverview() {
        primaryStage.setTitle("Boards: Overview");
        primaryStage.setScene(boardOverviewScene);
        boardOverviewCtrl.showAllBoards();
        setSizeScene();
    }

    public void showAddCard(long listId, long boardId) {
        primaryStage.setTitle("Board: Adding Card");
        createAddCardCtrl.setListBoardId(listId, boardId);
        primaryStage.setScene(createAddCardScene);
        createAddCardScene.setOnKeyPressed(e -> createAddCardCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showAddList(long boardId) {
        primaryStage.setTitle("Board: Adding List");
        primaryStage.setScene(createAddListScene);
        createAddListCtrl.setBoard(boardId);
        createAddListScene.setOnKeyPressed(e -> createAddListCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        editCardCtrl.init(card);
        primaryStage.setScene(edit);
        setSizeScene();
    }

    public void showEditList(TDList list) {
        primaryStage.setTitle("List: Rename list");
        primaryStage.setScene(editListScene);
        editListCtrl.init(list);
        setSizeScene();
    }

    public void showAddSubtask(Card card) {
        primaryStage.setTitle("Subtask: Create subtask");
        primaryStage.setScene(createAddSubtaskScene);
        createAddSubtaskCtrl.init(card);
        setSizeScene();
    }

    public void showJoinedBoards() {
        primaryStage.setTitle("Your boards");
        primaryStage.setScene(joinedBoardsScene);
        joinedBoardsCtrl.init();
        setSizeScene();
    }

    public void showCreateBoard() {
        primaryStage.setTitle("Create a new board");
        primaryStage.setScene(createBoardScene);
        setSizeScene();
    }

    public void showBoardOptions(Board board) {
        primaryStage.setTitle("Board options");
        primaryStage.setScene(boardOptionsScene);
        boardOptionsCtrl.init(board);
        setSizeScene();
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

    public void setSizeScene() {
        primaryStage.setHeight(primaryStage.getHeight() + 0.5);
    }

    public String getPrimaryStageTitle() {
        return this.primaryStage.getTitle();
    }

    public void setPrimaryStage() {
        this.primaryStage.setWidth(1080);
        this.primaryStage.setHeight(720);
        this.primaryStage.setMinWidth(425.0);
        this.primaryStage.setMinHeight(409);

        this.primaryStage.setOnCloseRequest(event -> {
            this.listOverviewCtrl.stop();
        });
    }
}
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
import com.google.inject.Inject;
import commons.AppClient;
import commons.Card;
import commons.TDList;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;

public class MainCtrl {

    private final DataFormat serialization =
            new DataFormat("application/x-java-serialized-object");

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

        this.client = new AppClient();

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

    public void showOverview(long boardId, Object parent) {
        primaryStage.setTitle("Lists: Overview");
        listOverviewCtrl.setBoard(boardId);
        listOverviewCtrl.setParent(parent);
        primaryStage.setScene(overview);
        setSizeScene();
    }

    public void showBoardOverview() {
        primaryStage.setTitle("Boards: Overview");
        primaryStage.setScene(boardOverviewScene);
        boardOverviewCtrl.showAllBoards();
        setSizeScene();
    }

    public void showAddCard(long listId,long boardId) {
        primaryStage.setTitle("Board: Adding Card");
        createAddCardCtrl.setListBoardId(listId,boardId);
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
        joinedBoardsCtrl.init(client);
        setSizeScene();
    }

    public void showCreateBoard(Object parent){
        primaryStage.setTitle("Create a new board");
        primaryStage.setScene(createBoardScene);
        createBoardCtrl.setParent(parent);
        listOverviewCtrl.setParent(parent);
        setSizeScene();
    }

    public AppClient getClient() {
        return client;
    }

    public void setSizeScene() {
        primaryStage.setWidth(primaryStage.getWidth() + 1);
        primaryStage.setHeight(primaryStage.getHeight() + 1);
    }

    public void setPrimaryStage(){
        this.primaryStage.setWidth(1080);
        this.primaryStage.setHeight(720);
        this.primaryStage.setMinWidth(425.0);
        this.primaryStage.setMinHeight(409);

        this.primaryStage.setOnCloseRequest(event -> {
            this.listOverviewCtrl.stop();
        });
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public DataFormat getSerialization() {
        return this.serialization;
    }
}
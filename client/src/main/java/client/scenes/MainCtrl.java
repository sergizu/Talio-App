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
import commons.AppClient;
import commons.Card;
import commons.TDList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private Scene overview;
    private ListOverviewCtrl listOverviewCtrl;

    private Scene edit;
    private EditCardCtrl editCardCtrl;

    private Scene editListScene;
    private EditListCtrl editListCtrl;

    private Scene selectServer;
    private SelectServerCtrl selectServerCtrl;

    private Scene boardOverviewScene;
    private BoardOverviewCtrl boardOverviewCtrl;

    private Scene joinedBoardsScene;
    private JoinedBoardsCtrl joinedBoardsCtrl;

    private Scene createBoardScene;
    private CreateBoardCtrl createBoardCtrl;

    private Scene createAddCardScene;
    private AddCardCtrl createAddCardCtrl;

    private Scene createAddListScene;
    private AddListCtrl createAddListCtrl;

    private Scene createAddSubtaskScene;
    private AddSubTaskCtrl createAddSubtaskCtrl;

    private AppClient client;

    public void initialize(Stage primaryStage,
                           SceneFactory sceneFactory) {

        this.primaryStage = primaryStage;

        Pair<ListOverviewCtrl, Parent> overview = sceneFactory.createListOverviewScene();
        this.listOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue(), 1080, 720);

        Pair<EditCardCtrl, Parent> edit = sceneFactory.createEditCardScene();
        this.editCardCtrl = edit.getKey();
        this.edit = new Scene(edit.getValue(), 1080, 720);

        Pair<EditListCtrl, Parent> editList = sceneFactory.createEditListScene();
        this.editListCtrl = editList.getKey();
        this.editListScene = new Scene(editList.getValue(), 1080, 720);

        Pair<SelectServerCtrl, Parent> selectServer = sceneFactory.createSelectServerScene();
        this.selectServer = new Scene(selectServer.getValue(), 1080, 720);
        this.selectServerCtrl = selectServer.getKey();

        Pair<BoardOverviewCtrl, Parent> boardOverview = sceneFactory.createBoardOverviewScene();
        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverviewScene = new Scene(boardOverview.getValue(), 1080, 720);

        Pair<JoinedBoardsCtrl, Parent> joinedBoards = sceneFactory.createJoinedBoardsScene();
        this.joinedBoardsCtrl = joinedBoards.getKey();
        this.joinedBoardsScene = new Scene(joinedBoards.getValue(), 1080, 720);

        Pair<CreateBoardCtrl, Parent> createBoard = sceneFactory.createNewBoardScene();
        this.createBoardCtrl = createBoard.getKey();
        this.createBoardScene = new Scene(createBoard.getValue(), 1080, 720);

        Pair<AddCardCtrl, Parent> createAddCard = sceneFactory.createAddCardScene();
        this.createAddCardCtrl = createAddCard.getKey();
        this.createAddCardScene = new Scene(createAddCard.getValue(), 1080, 720);

        Pair<AddListCtrl, Parent> createAddList = sceneFactory.createAddListScene();
        this.createAddListCtrl = createAddList.getKey();
        this.createAddListScene = new Scene(createAddList.getValue(), 1080, 720);

        Pair<AddSubTaskCtrl, Parent> createAddSubtask = sceneFactory.createAddSubtaskScene();
        this.createAddSubtaskCtrl = createAddSubtask.getKey();
        this.createAddSubtaskScene = new Scene(createAddSubtask.getValue(), 1080, 720);

        this.client = new AppClient();

        showSelectServer();
        setPrimaryStage();
        primaryStage.show();
    }

    public void showSelectServer() {
        primaryStage.setTitle("Server: selecting server");
        selectServerCtrl.setListOverviewCtrl(listOverviewCtrl);
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
}
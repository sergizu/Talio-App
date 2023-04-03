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
    private EditCardCtrl editCardCtrl;
    private Scene edit;

    private Scene selectServer;
    private SelectServerCtrl selectServerCtrl;

    private Scene addSubtask;

    private AddSubTaskCtrl addSubTaskCtrl;

    private Scene boardOverviewScene;
    private BoardOverviewCtrl boardOverviewCtrl;

    private JoinedBoardsCtrl joinedBoardsCtrl;
    private Scene joinedBoardsScene;

    private AppClient client;

    @SuppressWarnings("checkstyle:ParameterNumber")
    public void initialize(Stage primaryStage, Pair<ListOverviewCtrl, Parent> overview,
                           Pair<EditCardCtrl, Parent> edit,
                           Pair<SelectServerCtrl, Parent> selectServer,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<JoinedBoardsCtrl,Parent> joinedBoards) {

        this.primaryStage = primaryStage;
        this.listOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue(), 1080, 720);

        this.editCardCtrl = edit.getKey();
        this.edit = new Scene(edit.getValue(), 1080, 720);

        this.selectServerCtrl = selectServer.getKey();
        this.selectServer = new Scene(selectServer.getValue(), 1080, 720);

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverviewScene = new Scene(boardOverview.getValue(),1080,720);

        this.joinedBoardsCtrl = joinedBoards.getKey();
        this.joinedBoardsScene = new Scene(joinedBoards.getValue(),1080,720);

        this.client = new AppClient();
        showSelectServer();
        this.primaryStage.setWidth(1080);
        this.primaryStage.setHeight(720);
        this.primaryStage.setMinWidth(425.0);
        this.primaryStage.setMinHeight(409);
        primaryStage.show();
    }

    public void showSelectServer(){
        primaryStage.setTitle("Server: selecting server");
        primaryStage.setScene(selectServer);
        setSizeScene();
    }

    public void showOverview(long boardId) {
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(360);
        listOverviewCtrl.setBoard(boardId);
        listOverviewCtrl.registerForUpdates();
        listOverviewCtrl.setAddCard();
        listOverviewCtrl.setEditList();
        listOverviewCtrl.setAddList();
        primaryStage.setScene(overview);
        setSizeScene();
    }

    public void showBoardOverview() {
        primaryStage.setTitle("Boards: Overview");
        primaryStage.setScene(boardOverviewScene);
        setSizeScene();
        boardOverviewCtrl.showOtherBoards();
    }

    public void showOverviewNoRefresh(){
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setScene(overview);
        setSizeScene();
    }

    public void showAddCard(long listId,long boardId, AddCardCtrl addCardCtrl, Scene sceneAddCard) {
        primaryStage.setTitle("Board: Adding Card");
        addCardCtrl.setListBoardId(listId,boardId);
        primaryStage.setScene(sceneAddCard);
        sceneAddCard.setOnKeyPressed(e -> addCardCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showAddList(long boardId, AddListCtrl addListCtrl, Scene addListScene) {
        primaryStage.setTitle("Board: Adding List");
        primaryStage.setScene(addListScene);
        addListCtrl.setBoard(boardId);
        addListScene.setOnKeyPressed(e -> addListCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        editCardCtrl.init(card);
        primaryStage.setScene(edit);
        setSizeScene();
    }


    public void showEditList(TDList list, EditListCtrl editListCtrl, Scene editListScene) {
        primaryStage.setTitle("List: Rename list");
        primaryStage.setScene(editListScene);
        editListCtrl.init(list);
        setSizeScene();
    }

    public void showAddSubtask(Card card,Scene addSubTaskScene, AddSubTaskCtrl addSubTaskCtrl) {
        primaryStage.setTitle("Subtask: Create subtask");
        primaryStage.setScene(addSubTaskScene);
        addSubTaskCtrl.init(card);
        setSizeScene();
    }

    public void setSizeScene(){
        primaryStage.setWidth(primaryStage.getWidth()+1);
        primaryStage.setHeight(primaryStage.getHeight()+1);
    }

    public void showJoinedBoards(AppClient client) {
        primaryStage.setTitle("Your boards");
        primaryStage.setScene(joinedBoardsScene);
        joinedBoardsCtrl.init(client);
        setSizeScene();
    }

    public void showCreateBoard(Scene createBoardScene){
        primaryStage.setTitle("Create a new board");
        primaryStage.setScene(createBoardScene);
        setSizeScene();

    }
    public AppClient getClient(){
        return client;
    }
}
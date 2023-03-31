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
    private AddCardCtrl addCardCtrl;
    private AddListCtrl addListCtrl;


    private Scene sceneAddCard;
    private Scene sceneAddList;

    private EditCardCtrl editCardCtrl;
    private EditListCtrl editListCtrl;
    private Scene edit;
    private Scene editList;

    private Scene selectServer;
    private SelectServerCtrl selectServerCtrl;

    private Scene addSubtask;

    private AddSubTaskCtrl addSubTaskCtrl;

    private Scene boardOverviewScene;
    private BoardOverviewCtrl boardOverviewCtrl;

    private JoinedBoardsCtrl joinedBoardsCtrl;
    private Scene joinedBoardsScene;

    private CreateBoardCtrl createBoardCtrl;
    private Scene createBoardScene;

    private AppClient client;

    @SuppressWarnings("checkstyle:ParameterNumber")
    public void initialize(Stage primaryStage, Pair<ListOverviewCtrl, Parent> overview,
                           Pair<AddCardCtrl, Parent> addCard, Pair<AddListCtrl, Parent> addList,
                           Pair<EditCardCtrl, Parent> edit, Pair<EditListCtrl, Parent> editList,
                           Pair<SelectServerCtrl, Parent> selectServer,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<AddSubTaskCtrl, Parent> addSubtask,
                           Pair<JoinedBoardsCtrl,Parent> joinedBoards,
                           Pair<CreateBoardCtrl, Parent> createBoard,
                           AppClient client) {

        this.primaryStage = primaryStage;
        this.listOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue(), 1080, 720);

        this.addCardCtrl = addCard.getKey();
        this.addListCtrl = addList.getKey();
        this.sceneAddCard = new Scene(addCard.getValue(), 1080, 720);
        this.sceneAddList = new Scene(addList.getValue(), 1080, 720);

        this.editCardCtrl = edit.getKey();
        this.edit = new Scene(edit.getValue(), 1080, 720);

        this.editListCtrl = editList.getKey();
        this.editList = new Scene(editList.getValue(), 1080, 720);

        this.selectServerCtrl = selectServer.getKey();
        this.selectServer = new Scene(selectServer.getValue(), 1080, 720);

        this.addSubTaskCtrl = addSubtask.getKey();
        this.addSubtask = new Scene(addSubtask.getValue(), 1080, 720);

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverviewScene = new Scene(boardOverview.getValue());

        this.joinedBoardsCtrl = joinedBoards.getKey();
        this.joinedBoardsScene = new Scene(joinedBoards.getValue());

        this.createBoardCtrl = createBoard.getKey();
        this.createBoardScene = new Scene(createBoard.getValue());

        this.client = client;
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
    }

    public void showOverview(long boardId) {
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(360);
        listOverviewCtrl.setBoard(boardId);
        primaryStage.setScene(overview);
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

    public void showAdd(long listId,long boardId) {
        primaryStage.setTitle("Board: Adding Card");
        primaryStage.setScene(sceneAddCard);
        addCardCtrl.setListBoardId(listId,boardId);
        sceneAddCard.setOnKeyPressed(e -> addCardCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showAddList(long boardId) {
        primaryStage.setTitle("Board: Adding List");
        primaryStage.setScene(sceneAddList);
        addListCtrl.setBoard(boardId);
        sceneAddList.setOnKeyPressed(e -> addListCtrl.keyPressed(e));
        setSizeScene();
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        primaryStage.setScene(edit);
        editCardCtrl.init(card);
        setSizeScene();
    }


    public void showEditList(TDList list) {
        primaryStage.setTitle("List: Rename list");
        primaryStage.setScene(editList);
        editListCtrl.init(list);
        setSizeScene();
    }

    public void showAddSubtask(Card card) {
        primaryStage.setTitle("Subtask: Create subtask");
        primaryStage.setScene(addSubtask);
        addSubTaskCtrl.init(card);
        setSizeScene();
    }

    public void setSizeScene(){
        primaryStage.setWidth(primaryStage.getWidth()+1);
        primaryStage.setHeight(primaryStage.getHeight()+1);
    }

    public void showJoinedBoards(AppClient client) {
        primaryStage.setScene(joinedBoardsScene);
        joinedBoardsCtrl.init(client);
        setSizeScene();
    }

    public void showCreateBoard(){
        primaryStage.setScene(createBoardScene);

    }
    public AppClient getClient(){
        return client;
    }
}
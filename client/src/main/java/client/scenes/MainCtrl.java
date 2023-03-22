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

    public void initialize(Stage primaryStage, Pair<ListOverviewCtrl, Parent> overview,
                           Pair<AddCardCtrl, Parent> addCard, Pair<AddListCtrl, Parent> addList,
                           Pair<EditCardCtrl, Parent> edit, Pair<EditListCtrl, Parent> editList,
                           Pair<SelectServerCtrl, Parent> selectServer) {

        this.primaryStage = primaryStage;
        this.listOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCardCtrl = addCard.getKey();
        this.addListCtrl = addList.getKey();
        this.sceneAddCard = new Scene(addCard.getValue());
        this.sceneAddList = new Scene(addList.getValue());

        this.editCardCtrl = edit.getKey();
        this.edit = new Scene(edit.getValue());

        this.editListCtrl = editList.getKey();
        this.editList = new Scene(editList.getValue());

        this.selectServerCtrl = selectServer.getKey();
        this.selectServer = new Scene(selectServer.getValue());
        System.out.println("working");
        showSelectServer();
        primaryStage.show();
    }
    public void showSelectServer(){
        primaryStage.setTitle("Server: selecting server");
        primaryStage.setScene(selectServer);
    }

    public void showOverview() {
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setScene(overview);
        listOverviewCtrl.refresh(1L);
        //temporarily hardcoded boardID(which might even not be the correct ID)
    }

    public void showAdd(long listId) {
        primaryStage.setTitle("Board: Adding Card");
        primaryStage.setScene(sceneAddCard);
        addCardCtrl.setListId(listId);
        sceneAddCard.setOnKeyPressed(e -> addCardCtrl.keyPressed(e));
    }
    public void showAddList(long boardId) {
        primaryStage.setTitle("Board: Adding List");
        primaryStage.setScene(sceneAddList);
        addListCtrl.setBoard(boardId);
        sceneAddList.setOnKeyPressed(e -> addListCtrl.keyPressed(e));
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        primaryStage.setScene(edit);
        editCardCtrl.init(card);
    }

    public void showEditList(TDList list){
        primaryStage.setTitle("List: Rename list");
        primaryStage.setScene(editList);
        editListCtrl.init(list);
    }
}
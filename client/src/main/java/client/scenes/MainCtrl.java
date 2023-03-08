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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class MainCtrl {

    private Stage primaryStage;
    private Scene overview;

    private ListOverviewCtrl list;
    private AddCardCtrl addCardCtrl;

    private Scene add;

    private EditCardCtrl editCardCtrl;
    private Scene edit;

    public void initialize(Stage primaryStage, Pair<ListOverviewCtrl, Parent> overview,
            Pair<AddCardCtrl, Parent> add, Pair<EditCardCtrl, Parent> edit) {
        this.primaryStage = primaryStage;
        this.list = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCardCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.editCardCtrl = edit.getKey();
        this.edit = new Scene(edit.getValue());

        showOverview();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setScene(overview);
        list.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Board: Adding Card");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCardCtrl.keyPressed(e));
    }

    public void showEdit(Card card) {
        primaryStage.setTitle("Card: Edit Card");
        primaryStage.setScene(edit);
        editCardCtrl.init(card);
    }
}
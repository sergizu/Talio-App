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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainYCtrl {

    private Stage primaryStage;

    private ListOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddCardCtrl addCtrl;
    private Scene add;

    public void initialize(Stage primaryStage, Pair<ListOverviewCtrl, Parent> overview,
                           Pair<AddCardCtrl, Parent> add) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        showOverview();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Lists: Overview");
        primaryStage.setScene(overview);
//        overviewCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Cards: Adding Card");
        primaryStage.setScene(add);
//        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }
}
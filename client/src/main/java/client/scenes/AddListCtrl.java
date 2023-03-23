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

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TDList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddListCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private long boardId;

    @FXML
    private TextField listTitle;

    @FXML
    private Label emptyName;

    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    private TDList getList() {
        String title = listTitle.getText();
        if(title.equals("")) {
            emptyName.setText("List name can not be empty!");
            return null;
        }
        return new TDList(title);
    }

    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }

    public void ok() {
        try {
            TDList list = getList();
            if(list == null)
                return;
            server.addListToBoard(boardId, list);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }

    private void clearFields() {
        listTitle.clear();
        emptyName.setText("");
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    public void setBoard(long boardId) {
        this.boardId = boardId;
    }
}
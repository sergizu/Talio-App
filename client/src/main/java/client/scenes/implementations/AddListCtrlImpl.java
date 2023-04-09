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

import client.scenes.MainCtrl;
import client.scenes.interfaces.AddListCtrl;
import client.services.interfaces.AddListService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.TDList;
import javafx.scene.input.KeyEvent;

@Singleton
public class AddListCtrlImpl implements AddListCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private long boardId;

    private final AddListService addListService;

    @Inject
    public AddListCtrlImpl(ServerUtils server, MainCtrl mainCtrl, AddListService addListService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.addListService = addListService;
    }

    public TDList getList() {
        String title = addListService.getListTitle();
        if(title.equals("")) {
            addListService.setEmptyNameText("List name can not be empty!");
            return null;
        }
        return new TDList(title);
    }

    public void cancel() {
        clearFields();
        mainCtrl.showOverview(boardId);
    }

    public void ok() {
        TDList list = getList();
        if(list == null)
            return;
        server.addListToBoard(boardId, list);
        clearFields();
        mainCtrl.showOverview(boardId);
    }

    public void clearFields() {
        addListService.clearFields();
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

    public long getBoardId() {
        return boardId;
    }
}
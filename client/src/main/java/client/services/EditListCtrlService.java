package client.services;

import client.utils.ServerUtils;

import commons.TDList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditListCtrlService {

    public boolean keyEnter(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            return true;
        }
        return false;
    }

    public boolean keyEscape(KeyEvent e) {
        if(e.getCode() == KeyCode.ESCAPE) {
            return true;
        }
        return false;
    }

    public boolean ok(TextField listName, ServerUtils server, Label myLabel, TDList list) {
        if (list.getTitle().equals(list.title)) {
            return false;
        }
        server.updateListName(list.getId(), list.getTitle());
        return true;


    }
 // not sure how to properly check this
    public void delete(TDList list, ServerUtils server) {
        server.removeList(list);
    }
}

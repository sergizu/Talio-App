package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TDList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditListCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private TDList taskList;

    @FXML
    private TextField listName;

    @Inject
    public EditListCtrl (MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(TDList list) {
        this.taskList = list;
        listName.setText(list.title);
    }

    public void ok() {
        if (listName.getText().equals(taskList.title)) {
            mainCtrl.showOverview();
            return;
        }
        taskList.title = listName.getText();
        server.updateList(taskList);
        mainCtrl.showOverview();
    }

    public void enterPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
    }
}

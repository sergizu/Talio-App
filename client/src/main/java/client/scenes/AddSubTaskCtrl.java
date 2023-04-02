package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddSubTaskCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField subtaskName;

    @FXML
    private Label myLabel;

    private Card card;

    @Inject
    public AddSubTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void init(Card card) {
        this.card = card;
    }

    public void create() {
        if(subtaskName.getText().isEmpty()) {
            myLabel.setText("Name cannot be empty!");
        } else {
            String name = subtaskName.getText();
            subtaskName.setText("");
            Subtask subtask = new Subtask(name);
            card.addSubTask(subtask);
            server.updateNestedList(card.id, card.nestedList);
            mainCtrl.showEdit(card);
        }
    }
    public void cancel() {
        myLabel.setText("");
        mainCtrl.showOverview(card.list.board.id);
    }
}

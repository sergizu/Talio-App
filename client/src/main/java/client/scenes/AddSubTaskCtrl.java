package client.scenes;

import client.utils.ServerUtils;
import commons.Card;
import commons.SubTask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddSubTaskCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField SubtaskName;

    @FXML
    private Label myLabel;

    private Card card;

    public AddSubTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void init(Card card) {
        this.card = card;
    }

    public void create() {
        String name = SubtaskName.getText();
        SubTask subTask = new SubTask(name);
        card.addSubTask(subTask);
        mainCtrl.showEdit(card);
    }

}

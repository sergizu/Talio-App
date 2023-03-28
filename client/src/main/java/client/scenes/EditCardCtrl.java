package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card card;

    @FXML
    private TextField cardName;

    @FXML
    private Label emptyName;

    @FXML
    private TableView<Subtask> tableView;

    @FXML
    private TableColumn<Subtask, String> tableColumn;

    @Inject
    public EditCardCtrl (MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Card card) {
        this.card = card;
        System.out.println(card.getNestedList());
        cardName.setText(card.title);
        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        ObservableList<Subtask> data = FXCollections.observableList(card.nestedList);
        tableView.setItems(data);
    }

    public void ok() {
        if (cardName.getText().equals(card.title)) {
            mainCtrl.showOverview();
            return;
        }
        else if(cardName.getText().equals("")){
            emptyName.setText("Card name can not be empty!");
            return;
        }
        emptyName.setText("");
        server.updateCardName(card.getId(), cardName.getText());
        mainCtrl.showOverview();
    }

    public void delete() {
        server.removeCard(card);
        emptyName.setText("");
        mainCtrl.showOverview();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
        if(e.getCode() == KeyCode.ESCAPE) {
            cancel();
        }
    }

    public void cancel() {
        mainCtrl.showOverview();
    }

    public void createSubtask() {
        mainCtrl.showAddSubtask(card);
    }
}

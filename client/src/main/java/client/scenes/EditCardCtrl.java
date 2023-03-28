package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.SubTask;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card card;

    private ObservableList<SubTask> data;

    @FXML
    private TextField cardName;

    @FXML
    private Label emptyName;

    @FXML
    private TableView<SubTask> tableView;

    @Inject
    public EditCardCtrl (MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Card card) {
        this.card = card;
        cardName.setText(card.title);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(SubTask subTask: card.nestedList) {
            data.add(subTask);
        }
        tableView.setItems(data);
    }
}

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Card> data;

    @FXML private TableView<Card> tableView;
    @FXML private TableColumn<Card, String> cardColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        server.registerForMessages("/topic/cards", Card.class, q -> data.add(q));
        cardExpansion();
    }

    public void refresh() {
        var cards = server.getCard();
        data = FXCollections.observableList(cards);
        tableView.setItems(data);
    }

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void addCard() {
        mainCtrl.showAdd();
    }

    public void cardExpansion() {
        tableView.setOnMousePressed(event -> {
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                Card card = tableView.getSelectionModel().getSelectedItem();
                mainCtrl.showEdit(card);
            }
        });
    }
}



package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOverviewTest implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Card> data;

    @FXML
    private ListView<Card> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tableView.setCellFactory(q -> new SimpleStringProperty(q.));
        server.registerForMessages("/topic/cards", Card.class, q -> {
            data.add(q);
        });
    }

    public void refresh() {
        var cards = server.getCard();
        data = FXCollections.observableList(cards);
        tableView.setItems(data);
    }

    @Inject
    public ListOverviewTest(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void addCard() {
        mainCtrl.showCard();
    }
}

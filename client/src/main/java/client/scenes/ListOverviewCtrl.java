package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Change;
import commons.TDList;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private ObservableList<Card> dataLists;
    @FXML private TableView<Card> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForUpdates(cardChange -> {
            if(cardChange.change == Change.Add)
                dataLists.add(cardChange.card); //adding the card to the most left list(TO-DO)
        });
    }

    public void refresh() {
        board = server.tempBoardGetter();
        tableView.setItems(dataLists);
    }

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        dataLists = FXCollections.observableArrayList();
    }

    public void addCard() {
        mainCtrl.showAdd();
    }

    public void removeCard() {
//        Card card = tableView.getSelectionModel().getSelectedItem();
//        data.remove(card);
//        server.removeCard(card);
//        refresh();
    }

    public void stop() {
        server.stop();
    }
}


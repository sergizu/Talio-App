package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Change;
import commons.TDList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private List<ObservableList<Card>> dataLists;
    @FXML private TableView<Card> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForUpdates(cardChange -> {
            if(cardChange.change == Change.Add)
                dataLists.get(0).add(cardChange.card); //adding the card to the most left list(TO-DO)
        });
    }

    public void refresh() {
        board = server.tempBoardGetter();
        for (TDList tdList: board.lists) {
            dataLists.add(FXCollections.observableList(tdList.list));
        }
        tableView.setItems(dataLists.get(0));
    }

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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


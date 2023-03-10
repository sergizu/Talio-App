package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Change;
import commons.TDList;
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
    private Board board;
    private ObservableList<ObservableList<Card>> dataLists;
    @FXML private TableView<Card> tableView;
    @FXML private TableColumn<Card, String> cardColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        server.registerForUpdates(cardChange -> {
            if(cardChange.change == Change.Add)
                dataLists.get(0).add(cardChange.card);
                //adding the card to the most left list(TO-DO)
        });
    }

    //boardID is not yet used
    public void refresh(long boardId) {
        board = server.tempBoardGetter();
        for(TDList tdList : board.lists) {
            dataLists.add(FXCollections.observableList(tdList.list));
        }

        //when we can dynamicly add lists we would need a for loop here
        tableView.setItems(dataLists.get(0));
    }

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        dataLists = FXCollections.observableArrayList();
    }

    public void addCard() {
        mainCtrl.showAdd(board.id);
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


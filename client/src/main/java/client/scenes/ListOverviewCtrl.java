package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<Card> data;

    @FXML private TableView<Card> tableView;
    @FXML private TableColumn<Card, String> cardColumn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
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
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    public void switchToCard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("client\\scenes\\AddCard.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }   catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addCard() {
        mainCtrl.showAdd();
    }



}

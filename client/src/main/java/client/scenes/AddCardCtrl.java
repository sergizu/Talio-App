package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Person;
import commons.Quote;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class AddCardCtrl {
//    private Stage stage;
//    private Scene scene;
    private Parent root;

    @FXML
    private TextField CardName;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

//    public void SwitchToList(ActionEvent event) {
//
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("client\\scenes\\AddCard.fxml"));
//            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

    public void ShowList() {
        mainCtrl.showOverview();
    }

    private Card getCard() {
        String title = CardName.getText();
        System.out.println(title);
        return new Card(title);
    }

    public void ok() {
        try {
            server.addCard(getCard());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        clearFields();
        mainCtrl.showOverview();
    }

    private void clearFields() {
        CardName.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            default:
                break;
        }
    }






}

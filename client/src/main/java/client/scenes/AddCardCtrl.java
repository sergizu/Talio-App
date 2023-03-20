package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddCardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label myLable;

    @FXML
    private TextField cardName;
    private long listId;

    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    private Card getCard() {
        String title = cardName.getText();
        return new Card(title);
    }

    public void ok() {
        if (cardName.getText().isEmpty()) {
            myLable.setText("Cant be empty");
        } else {
            try {
                myLable.setText("");
                Card added = server.addCard(getCard());
                server.addToList(listId, added);
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
    }

    private void clearFields() {
        cardName.clear();
    }

    public void cancel() {
        myLable.setText("");
        clearFields();
        mainCtrl.showOverview();
    }




    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
        else if (e.getCode() == KeyCode.ESCAPE) {
            cancel();
        }
    }
}

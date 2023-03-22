package client.services;

import client.utils.ServerUtils;
import commons.Card;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddCardService {
    public boolean keyEnter(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER) return true;
        return false;
    }
    public boolean keyEscape(KeyEvent e){
        if(e.getCode() == KeyCode.ESCAPE) return true;
        return false;
    }
    public Card createCard(String title){
        return new Card(title);
    }

    public boolean cardNameEmpty(TextField cardName) {
        return cardName.getText().isEmpty();
    }

    public void ok(TextField cardName, Label myLabel, ServerUtils server, long listId, Card card) {
        if (cardNameEmpty(cardName)) {
            myLabel.setText("Cant be empty");
        } else {
            try {
                myLabel.setText("");
                server.addCardToList(listId, card);
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            clearFields(cardName);
        }
    }
    public void clearFields(TextField cardName) {
        cardName.clear();
    }
    public void setLabelToEmpty(Label label){
        label.setText("");
    }
}

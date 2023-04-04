package client.services;

import client.scenes.AddCardCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddCardServiceImpl implements AddCardService {
    @FXML
    private Label myLabel;
    @FXML
    private TextField cardName;

    @FXML
    private TextArea description;

    @Inject
    private AddCardCtrl addCardCtrl;


    public void setMyLabelText(String text) {
        myLabel.setText(text);
    }

    public void clearFields() {
        cardName.clear();
        description.clear();
        setMyLabelText("");
    }

    public String getCardName() {
        return cardName.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public void setCardName(String name) {
        cardName.setText(name);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void ok() {
        addCardCtrl.ok();
    }

    public void cancel() {
        addCardCtrl.cancel();
    }
}

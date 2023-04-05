package client.services;

import client.scenes.AddCardCtrl;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Singleton
public class AddCardServiceImpl implements AddCardService {
    @FXML
    private Label myLabel;
    @FXML
    private TextField cardName;

    @FXML
    private TextArea description;

    private final AddCardCtrl addCardCtrl;

    @Inject
    public AddCardServiceImpl(AddCardCtrl addCardCtrl) {
        this.addCardCtrl = addCardCtrl;
    }


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

    //method that gets called when the ok button is pressed
    public void ok() {
        addCardCtrl.ok();
    }

    //method that gets called when the cancel button is pressed
    public void cancel() {
        addCardCtrl.cancel();
    }
}

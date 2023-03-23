package client.scenes;

import client.services.AddCardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AddCardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final AddCardService service;

    @FXML
    private Label myLabel;
    @FXML
    private TextField cardName;
    private long listId;

    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl, AddCardService service) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.service = service;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    private Card getCard() {
        return service.createCard(cardName.getText());
    }

    public void ok() {
        service.ok(cardName, myLabel, server, listId, getCard());
        mainCtrl.showOverview();
    }

    private void clearFields() {
        service.clearFields(cardName);
    }

    public void cancel() {
        service.setLabelToEmpty(myLabel);
        clearFields();
        mainCtrl.showOverview();
    }

    public void keyPressed(KeyEvent e) {
        if (service.keyEnter(e)) {
            ok();
        }
        else if (service.keyEscape(e)) {
            cancel();
        }
    }
}

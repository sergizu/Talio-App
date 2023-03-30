package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.CardListId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddCardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label myLabel;
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

    public void ok() {
        Card toSend = new Card(cardName.getText());
        server.addCardToList(listId,toSend);
        server.send("/app/tdLists/addCard", new CardListId(toSend,listId));
        mainCtrl.showOverviewNoRefresh();// I don't want to refresh
        // because each client is registered for this change already
    }

    private void clearFields() {
        cardName.clear();
    }

    public void cancel() {
        myLabel.setText("");
        clearFields();
        mainCtrl.showOverview();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }
}

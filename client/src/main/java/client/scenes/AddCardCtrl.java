package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.CardListId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

    @FXML
    private TextArea description;
    private long listId;

    private long boardId;

    @Inject
    public AddCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void setListBoardId(long listId,long boardId) {
        this.listId = listId;
        this.boardId = boardId;
    }

    public void ok() {
        if(cardName.getText().isEmpty()) {
            myLabel.setText("Card name cannot be empty!");
            return;
        }
        myLabel.setText("");
        Card toSend = new Card(cardName.getText(), description.getText());
        server.addCardToList(listId,toSend);
        server.send("/app/tdLists/addCard", new CardListId(toSend,listId));
        mainCtrl.showOverview(boardId);
        clearFields();
        // I don't want to refresh
        // because each client is registered for this change already
    }

    private void clearFields() {
        cardName.clear();
        description.clear();
    }

    public void cancel() {
        myLabel.setText("");
        clearFields();
        mainCtrl.showOverview(boardId);
    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }
}

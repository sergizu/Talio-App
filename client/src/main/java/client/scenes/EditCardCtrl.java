package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card card;

    @FXML
    private TextField cardName;

    @Inject
    public EditCardCtrl (MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Card card) {
        this.card = card;
        cardName.setText(card.title);
    }

    public void ok() {
        if (cardName.getText().equals(card.title)) {
            mainCtrl.showOverview();
            return;
        }
        card.setTitle(cardName.getText());
        server.updateCard(card);
        mainCtrl.showOverview();
    }

    public void delete() {
        server.removeCard(card);
        mainCtrl.showOverview();
    }


}

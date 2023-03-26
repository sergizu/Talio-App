package client.services;


import client.utils.ServerUtils;
import commons.Card;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class EditCardCtrlService {

    public boolean keyEnter(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            return true;
        }
        return false;
    }

    public boolean keyEscape(KeyEvent e) {
        if(e.getCode() == KeyCode.ESCAPE) {
            return true;
        }
        return false;
    }

    public boolean ok(TextField cardName, ServerUtils server, Label myLabel,  Card card) {
        if (card.getTitle().equals(card.title)) {
            return false;
        }
        server.updateCardName(card.getId(), card.getTitle());
        return true;




    }

    public void delete(Card card, ServerUtils server) {
        server.removeCard(card);
    }


}

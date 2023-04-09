package client.scenes;

import com.google.inject.ImplementedBy;
import commons.Card;
import javafx.scene.input.KeyEvent;

@ImplementedBy(AddSubTaskCtrlImpl.class)
public interface AddSubTaskCtrl {
    void init(Card card);
    void create();
    void cancel();
    Card getCard();
    void keyPressed(KeyEvent e);
}

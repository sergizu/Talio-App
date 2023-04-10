package client.scenes.interfaces;

import client.scenes.implementations.EditCardCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Card;
import commons.Subtask;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

@ImplementedBy(EditCardCtrlImpl.class)
public interface EditCardCtrl {

    void init(Card card);
    void ok();

    void delete();

    void keyPressed(KeyEvent e);

    void cancel();

    void createSubtask();

    void registerForUpdates();

    void setCard(Card card);

    Card getCard();

    void updateNestedList(ArrayList<Subtask> nestedList);

    void showEdit();
}

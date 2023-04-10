package client.scenes.interfaces;

import client.helperClass.SubtaskWrapper;
import client.scenes.implementations.EditCardCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Card;
import commons.Subtask;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

@ImplementedBy(EditCardCtrlImpl.class)
public interface EditCardCtrl {

    void init(Card card);
    void ok();

    void delete();

    void keyPressed(KeyEvent e);

    void cancel();

    void createSubtask();

    void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit);

    void registerForUpdates();

    void setCard(Card card);

    void updateNestedList(ArrayList<Subtask> nestedList);
    void showEdit();
}

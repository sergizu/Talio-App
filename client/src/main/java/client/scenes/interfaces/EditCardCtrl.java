package client.scenes.interfaces;

import client.helperClass.SubtaskWrapper;
import client.scenes.implementations.EditCardCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Card;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

import java.util.List;

@ImplementedBy(EditCardCtrlImpl.class)
public interface EditCardCtrl {

    void init(Card card);

    List<SubtaskWrapper> initSubtask();

    void ok();

    void delete();

    void keyPressed(KeyEvent e);

    void cancel();

    void createSubtask();

    void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit);

    void dragAndDrop(TableView<SubtaskWrapper> tableView);

    void registerForUpdates();

}

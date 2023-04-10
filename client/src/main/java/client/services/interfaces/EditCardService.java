package client.services.interfaces;

import client.helperClass.SubtaskWrapper;
import client.services.implementations.EditCardServiceImpl;
import com.google.inject.ImplementedBy;
import commons.Subtask;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
@ImplementedBy(EditCardServiceImpl.class)
public interface EditCardService {

    void setCardName(String title);

    void setDescription(String description);

    String getCardName();

    String getDescription();

    void initTableView(List<SubtaskWrapper> subtaskWrappers);

    void setEmptyName(String s);
    void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit);

    void dragAndDrop(TableView<SubtaskWrapper> tableView);
    List<SubtaskWrapper> initSubtask(ArrayList<Subtask> nestedList);
}

package client.services.implementations;

import client.helperClass.SubtaskWrapper;
import client.scenes.interfaces.EditCardCtrl;
import client.services.interfaces.EditCardService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Subtask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import java.util.List;

@Singleton
public class EditCardServiceImpl implements EditCardService {

    private final EditCardCtrl editCardCtrl;

    @FXML
    private TextField cardName;

    @FXML
    private TextArea description;

    @FXML
    private Label emptyName;

    @FXML
    private TableView<SubtaskWrapper> tableView;

    @FXML
    private TableColumn<SubtaskWrapper, String> tableColumnSubtask;

    @FXML
    private TableColumn<SubtaskWrapper, CheckBox> tableColumnCheckbox;

    @FXML
    private TableColumn<SubtaskWrapper, Button> tableColumnButton;

    @Inject
    public EditCardServiceImpl(EditCardCtrl editCardCtrl) {
        this.editCardCtrl = editCardCtrl;
    }

    public void setCardName(String title) {
        this.cardName.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public String getCardName() {
        return cardName.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public void setEmptyName(String s) {
        this.emptyName.setText(s);
    }

    public void initTableView(List<SubtaskWrapper> subtaskWrappers) {
        tableColumnSubtask.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getSubtask().getName()));
        tableColumnCheckbox.setCellValueFactory(
                new PropertyValueFactory<SubtaskWrapper, CheckBox>("checkBox"));
        tableColumnButton.setCellValueFactory(
                new PropertyValueFactory<SubtaskWrapper, Button>("button"));
        ObservableList<SubtaskWrapper> data = FXCollections.observableList(subtaskWrappers);
        tableView.setItems(data);
        tableView.setEditable(true);
        tableColumnSubtask.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void editSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit) {
        SubtaskWrapper subtaskWrapper = tableView.getSelectionModel().getSelectedItem();
        Subtask subtask = subtaskWrapper.getSubtask();
        subtask.setName(edit.getNewValue());
    }

    public void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit) {
        editCardCtrl.changeSubtask(edit);
    }

    public void dragAndDrop() {
        editCardCtrl.dragAndDrop(tableView);
    }

    public void ok() {
        editCardCtrl.ok();
    }

    public void delete() {
        editCardCtrl.delete();
    }

    public void cancel() {
        editCardCtrl.cancel();
    }

    public void createSubtask() {
        editCardCtrl.createSubtask();
    }

    public void keyPressed(KeyEvent e) {
        editCardCtrl.keyPressed(e);
    }
}

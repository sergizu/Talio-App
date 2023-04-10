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

import java.util.ArrayList;
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

    public List<SubtaskWrapper> initSubtask(ArrayList<Subtask> nestedList) {
        List<SubtaskWrapper> subtaskWrappers = new ArrayList<>();
        for (Subtask subtask : nestedList) {
            CheckBox checkBox = new CheckBox();
            if (subtask.checked) {
                checkBox.setSelected(true);
            }
            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    subtask.setChecked(true);
                    editCardCtrl.updateNestedList(nestedList);
                }
                if (!checkBox.isSelected()) {
                    subtask.setChecked(false);
                    editCardCtrl.updateNestedList(nestedList);
                }
            });
            Button button = new Button("   ");
            button.setOnAction(event -> {
                nestedList.remove(subtask);
                editCardCtrl.updateNestedList(nestedList);
                editCardCtrl.showEdit();
            });
            subtaskWrappers.add(new SubtaskWrapper(subtask, checkBox, button));
        }
        return subtaskWrappers;
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

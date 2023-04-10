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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EditCardServiceImpl implements EditCardService {

    private final EditCardCtrl editCardCtrl;

    private final SubtaskWrapper subtaskWrapper;

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
    public EditCardServiceImpl(EditCardCtrl editCardCtrl, SubtaskWrapper subtaskWrapper) {
        this.editCardCtrl = editCardCtrl;
        this.subtaskWrapper = subtaskWrapper;
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
        dragAndDrop(tableView);
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

    public void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit) {
        SubtaskWrapper subtaskWrapper = tableView.getSelectionModel().getSelectedItem();
        Subtask subtask = subtaskWrapper.getSubtask();
        subtask.setName(edit.getNewValue());
        ObservableList<SubtaskWrapper> items = tableView.getItems();
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for(SubtaskWrapper item : items) {
            subtasks.add(item.getSubtask());
        }
        editCardCtrl.updateNestedList(subtasks);
    }

    public void dragAndDrop(TableView<SubtaskWrapper> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<SubtaskWrapper> row = new TableRow<>();
            row.setOnDragDetected(e -> {
                if (!row.isEmpty()) {
                    int i = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(subtaskWrapper.getSerialization(), i);

                    db.setContent(cc);
                    e.consume();
                }
            });
            row.setOnDragOver(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(subtaskWrapper.getSerialization())) {

                    e.acceptTransferModes(TransferMode.MOVE);
                    e.consume();
                }
            });
            row.setOnDragDropped(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(subtaskWrapper.getSerialization())) {
                    int draggedIndex = (int) db.getContent(subtaskWrapper.getSerialization());

                    SubtaskWrapper subtaskWrapper = tableView.getItems().remove(draggedIndex);
                    int dropIndex;
                    if (row.isEmpty())
                        dropIndex = tableView.getItems().size();
                    else
                        dropIndex = row.getIndex();
                    tableView.getItems().add(dropIndex, subtaskWrapper);
                    ObservableList<SubtaskWrapper> items = tableView.getItems();
                    ArrayList<Subtask> subtasks = new ArrayList<>();
                    for (SubtaskWrapper item : items) {
                        subtasks.add(item.getSubtask());
                    }
                    editCardCtrl.updateNestedList(subtasks);
                    e.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    e.consume();
                }
            });
            return row;
        });
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

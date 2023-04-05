package client.scenes;

import client.helperClass.SubtaskWrapper;
import client.services.AddSubTaskService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static client.helperClass.SubtaskWrapper.serialization;

public class EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card card;
    private Scene addSubTaskScene;
    private final AddSubTaskCtrl addSubTaskCtrl;
    private final AddSubTaskService addSubTaskService;
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
    public EditCardCtrl (MainCtrl mainCtrl, ServerUtils server, AddSubTaskCtrl addSubTaskCtrl,
                         AddSubTaskService addSubTaskService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.addSubTaskCtrl = addSubTaskCtrl;
        this.addSubTaskService = addSubTaskService;
    }

    public void init(Card card) {
        this.card = card;
        cardName.setText(card.title);
        setAddSubtask();
        description.setText(card.description);
        tableColumnSubtask.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getSubtask().getName()));
        tableColumnCheckbox.setCellValueFactory(
                new PropertyValueFactory<SubtaskWrapper, CheckBox>("checkBox"));
        tableColumnButton.setCellValueFactory(
                new PropertyValueFactory<SubtaskWrapper, Button>("button"));
        List<SubtaskWrapper> subtaskWrappers = new ArrayList<>();
        for(Subtask subtask : card.getNestedList()) {
            CheckBox checkBox = new CheckBox();
            if(subtask.checked) {
                checkBox.setSelected(true);
            }
            checkBox.setOnMouseClicked(event -> {
                if(checkBox.isSelected()) {
                    subtask.setChecked(true);
                    server.updateNestedList(card.id, card.getNestedList());
                }
                if(!checkBox.isSelected()) {
                    subtask.setChecked(false);
                    server.updateNestedList(card.id, card.getNestedList());
                }
            });
            Button button = new Button("X");
            button.setOnAction(event -> {
                card.getNestedList().remove(subtask);
                server.updateNestedList(card.id, card.getNestedList());
                mainCtrl.showEdit(card);
            });
            subtaskWrappers.add(new SubtaskWrapper(subtask, checkBox, button));
        }
        ObservableList<SubtaskWrapper> data = FXCollections.observableList(subtaskWrappers);
        tableView.setItems(data);
        tableView.setEditable(true);
        tableColumnSubtask.setCellFactory(TextFieldTableCell.forTableColumn());
        dragAndDrop(tableView);
    }

    public void setAddSubtask(){
        FXMLLoader addSubTaskLoader = new FXMLLoader(getClass().
                getResource("/client/scenes/AddSubtask.fxml"));
        addSubTaskLoader.setController(addSubTaskService);
        try {
            addSubTaskScene = new Scene(addSubTaskLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ok() {
        if(cardName.getText().equals(card.title )&&
                (description.getText() == null || description.getText().equals(card.description))){
            mainCtrl.showOverview(card.getList().getBoard().getId());
            return;
        }
        else if(cardName.getText().equals("")){
            emptyName.setText("Card name can not be empty!");
            return;
        }
        emptyName.setText("");
        server.updateCardName(card.getId(), cardName.getText());
        if(description.getText().isEmpty() || description.getText() == null) {
            server.updateCardDescription(card.getId(), " ");
        } else {
            server.updateCardDescription(card.getId(), description.getText());
        }
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void delete() {
        long boardId = card.getList().getBoard().getId();
        server.removeCard(card);
        emptyName.setText("");
        mainCtrl.showOverview(boardId);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
        if(e.getCode() == KeyCode.ESCAPE) {
            cancel();
        }
    }

    public void cancel() {
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void createSubtask() {
        mainCtrl.showAddSubtask(card,addSubTaskScene,addSubTaskCtrl);
    }

    public void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit) {
        SubtaskWrapper subtaskWrapper = tableView.getSelectionModel().getSelectedItem();
        Subtask subtask = subtaskWrapper.getSubtask();
        subtask.setName(edit.getNewValue());
        server.updateNestedList(card.id, card.getNestedList());
    }
    public void dragAndDrop(TableView<SubtaskWrapper> tableView){
        tableView.setRowFactory(tv -> {
            TableRow<SubtaskWrapper> row = new TableRow<>();
            row.setOnDragDetected(e -> {
                if (!row.isEmpty()) {
                    int i = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    //shows a snapshot of the row when moving it
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(serialization, i);
                    db.setContent(cc);
                    e.consume();
                }
            });
            row.setOnDragOver(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(serialization)) {
                    e.acceptTransferModes(TransferMode.MOVE);
                    e.consume();
                }
            });
            row.setOnDragDropped(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(serialization)) {
                    int draggedIndex = (int) db.getContent(serialization);
                    SubtaskWrapper subtaskWrapper = tableView.getItems().remove(draggedIndex);
                    int dropIndex;
                    if (row.isEmpty())
                        dropIndex = tableView.getItems().size();
                    else
                        dropIndex = row.getIndex();
                    tableView.getItems().add(dropIndex, subtaskWrapper);
                    ObservableList<SubtaskWrapper> items = tableView.getItems();
                    ArrayList<Subtask> subtasks = new ArrayList<>();
                    for(SubtaskWrapper item : items) {
                        subtasks.add(item.getSubtask());
                    }
                    server.updateNestedList(card.id, subtasks);
                    e.setDropCompleted(true); //marks the end of the drag event
                    tableView.getSelectionModel().select(dropIndex);
                    e.consume();
                }
            });
            return row;
        });
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        server.registerForCardUpdates(updatedCardID -> {
//                Platform.runLater(() -> {
//                    if(card.getId() == updatedCardID) {
//                        TDList tdList = card.getList();
//                    Card boardReference = card;
//                    try {
//                        card = server.getCardById(card.id);
//                        System.out.println(card.getList());
//                        card.setList(tdList);
//                    } catch (Exception e) {
//                        System.out.println("Hello World!");
//                        mainCtrl.showOverview(boardReference.getList().getBoard().getId());
//                    }
//                        if (mainCtrl.getPrimaryStage().getTitle().equals("Card: Edit Card")) {
//                            mainCtrl.showEdit(card);
//                        }
//                    }
//                });
//        });
//    }
}

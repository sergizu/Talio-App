package client.scenes.implementations;

import client.helperClass.SubtaskWrapper;
import client.scenes.MainCtrl;
import client.scenes.interfaces.EditCardCtrl;
import client.services.interfaces.EditCardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EditCardCtrlImpl implements EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final SubtaskWrapper subtaskWrapper;

    private final EditCardService service;

    private Card card;

    @Inject
    public EditCardCtrlImpl(ServerUtils server, MainCtrl mainCtrl, SubtaskWrapper subtaskWrapper, EditCardService service) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.subtaskWrapper = subtaskWrapper;
        this.service = service;
    }

    public void init(Card card) {
        this.card = card;
        service.setCardName(card.getTitle());
        service.setDescription(card.getDescription());
        service.dragAndDrop();
        List<SubtaskWrapper> subtaskWrappers = initSubtask();
        service.initTableView(subtaskWrappers);
    }

    public List<SubtaskWrapper> initSubtask() {
        List<SubtaskWrapper> subtaskWrappers = new ArrayList<>();
        for (Subtask subtask : card.getNestedList()) {
            CheckBox checkBox = new CheckBox();
            if (subtask.checked) {
                checkBox.setSelected(true);
            }
            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    subtask.setChecked(true);
                    server.updateNestedList(card.id, card.getNestedList());
                }
                if (!checkBox.isSelected()) {
                    subtask.setChecked(false);
                    server.updateNestedList(card.id, card.getNestedList());
                }
            });
            Button button = new Button("   ");
            button.setOnAction(event -> {
                card.getNestedList().remove(subtask);
                server.updateNestedList(card.id, card.getNestedList());
                mainCtrl.showEdit(card);
            });
            subtaskWrappers.add(new SubtaskWrapper(subtask, checkBox, button));
        }
        return subtaskWrappers;
    }

    public void ok() {
        if (service.getCardName().equals(card.title) &&
                (service.getDescription() == null || service.getDescription().equals(card.description))) {
            mainCtrl.showOverview(card.getList().getBoard().getId());
            return;
        } else if (service.getCardName().equals("")) {
            service.setEmptyName("Card name can not be empty!");
            return;
        }
        service.setEmptyName("");
        server.updateCardName(card.getId(), service.getCardName());
        if (service.getDescription().isEmpty() || service.getDescription() == null) {
            server.updateCardDescription(card.getId(), " ");
        } else {
            server.updateCardDescription(card.getId(), service.getDescription());
        }
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void delete() {
        long boardId = card.getList().getBoard().getId();
        server.removeCard(card);
        service.setEmptyName("");
        mainCtrl.showOverview(boardId);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
        if (e.getCode() == KeyCode.ESCAPE) {
            cancel();
        }
    }

    public void cancel() {
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void createSubtask() {
        mainCtrl.showAddSubtask(card);
    }

    public void changeSubtask(TableColumn.CellEditEvent<SubtaskWrapper, String> edit) {
        service.changeSubtask(edit);
        server.updateNestedList(card.id, card.getNestedList());
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
                    server.updateNestedList(card.id, subtasks);
                    e.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    e.consume();
                }
            });
            return row;
        });
    }

    public void registerForUpdates() {
        server.registerForCardUpdates(updatedCardID -> Platform.runLater(() -> {
            if (card!=null && card.getId() == updatedCardID) {
                TDList tdList = card.getList();
                Card boardReference = card;
                try {
                    card = server.getCardById(card.id);
                    card.setList(tdList);
                } catch (Exception e) {
                    mainCtrl.showOverview(boardReference.getList().getBoard().getId());
                }

                if (mainCtrl.getPrimaryStageTitle().equals("Card: Edit Card")) {

                    mainCtrl.showEdit(card);
                }
            }
        }));
    }

}

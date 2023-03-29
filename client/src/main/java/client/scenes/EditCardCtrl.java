package client.scenes;

import client.helperClass.SubtaskWrapper;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card card;

    @FXML
    private TextField cardName;

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
    public EditCardCtrl (MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Card card) {
        this.card = card;
        cardName.setText(card.title);
        tableColumnSubtask.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getSubtask().getName()));
        tableColumnCheckbox.setCellValueFactory(new PropertyValueFactory<SubtaskWrapper, CheckBox>("checkBox"));
        tableColumnButton.setCellValueFactory(new PropertyValueFactory<SubtaskWrapper, Button>("button"));
        List<SubtaskWrapper> subtaskWrappers = new ArrayList<>();
        for(Subtask subtask : card.nestedList) {
            CheckBox checkBox = new CheckBox();
            Button button = new Button("X");
            button.setOnAction(event -> {
                card.nestedList.remove(subtask);
                mainCtrl.showEdit(card);
            });
            subtaskWrappers.add(new SubtaskWrapper(subtask, checkBox, button));
        }
        ObservableList<SubtaskWrapper> data = FXCollections.observableList(subtaskWrappers);
        tableView.setItems(data);
    }

    public void ok() {
        if (cardName.getText().equals(card.title)) {
            mainCtrl.showOverview();
            return;
        }
        else if(cardName.getText().equals("")){
            emptyName.setText("Card name can not be empty!");
            return;
        }
        emptyName.setText("");
        server.updateCardName(card.getId(), cardName.getText());
        mainCtrl.showOverview();
    }

    public void delete() {
        server.removeCard(card);
        emptyName.setText("");
        mainCtrl.showOverview();
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
        mainCtrl.showOverview();
    }

    public void createSubtask() {
        mainCtrl.showAddSubtask(card);
    }
}

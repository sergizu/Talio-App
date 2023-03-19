package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Change;
import commons.TDList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private ObservableList<ObservableList<Card>> dataLists;

    private static final DataFormat serialization =
            new DataFormat("application/x-java-serialized-object");

    @FXML
    private ScrollPane scrollPane;

    private TableView<Card> selection;

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        dataLists = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //cardColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        server.registerForUpdates(cardChange -> {
            if (cardChange.change == Change.Add)
                dataLists.get(0).add(cardChange.card);
            //adding the card to the most left list(TO-DO)
        });
        setScrollPane();
    }

    public void showLists() {
        //System.out.println(dataLists);
        scrollPane.setContent(createFlowPane());
    }

    public Button createButton(long id) {
        Button button = new Button("+");
        button.setOnAction(e -> {
            addCard(id);
        });
        return button;
    }

    public FlowPane createFlowPane() {
        FlowPane flowPane = new FlowPane();
        setFlowPane(flowPane);
        var lists = board.lists;
        for (var tdList : lists) {
            Button button = createButton(tdList.id);
            TableView<Card> tv = createTable(tdList);
            cardExpansion(tv);
            dragAndDrop(tv);
            setSelection(tv);
            dragOtherLists(tv);
            flowPane.getChildren().addAll(createVBox(tv, button));
        }
        return flowPane;
    }

    public void setFlowPane(FlowPane flowPane) {
        flowPane.setAlignment(Pos.BASELINE_CENTER);
        flowPane.setHgap(50);
        flowPane.setVgap(5);
    }

    public void setScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    public TableView<Card> createTable(TDList tdList) {
        TableView<Card> tv = new TableView<>();
        tv.setPrefSize(157, 270);
        TableColumn<Card, String> tableColumn = new TableColumn<>();
        tableColumn.setText(tdList.title);
        tableColumn.setPrefWidth(tv.getPrefWidth());
        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title));
        tv.getColumns().add(tableColumn);
        ObservableList<Card> dataCards = FXCollections.observableList(tdList.cards);
        tv.setItems(dataCards);
        return tv;
    }

    public VBox createVBox(TableView<Card> cards, Button button) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(button, cards);
        vBox.setAlignment(Pos.TOP_RIGHT);
        vBox.setSpacing(3);
        return vBox;
    }

    //boardID is not yet used
    public void refresh(long boardID) {
        board = server.tempBoardGetter();
        showLists();
        for (TDList tdList : board.lists) {
            dataLists.add(FXCollections.observableList(tdList.cards));
        }

        //when we can dynamically add lists we would need a for loop here
        //tableView.setItems(dataLists.get(0));
    }

    public void addCard(long listId) {
        mainCtrl.showAdd(listId);
    }

    public void stop() {
        server.stop();
    }

    public void addList() {
        mainCtrl.showAddList(board.id);
    }

    //Method that will pop up a window to change the card name whenever you double-click on a card
    public void cardExpansion(TableView<Card> tableView) {
        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null
                    && event.getClickCount() == 2) {
                Card card = tableView.getSelectionModel().getSelectedItem();
                mainCtrl.showEdit(card);
            }
        });
    }

    public void editListName() {
        TDList list = board.lists.get(0);
        mainCtrl.showEditList(list);
    }

    public void dragAndDrop(TableView<Card> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<Card> row = new TableRow<>();
            row.setOnDragDetected(e -> { //Method gets called whenever a mouse drags a row
                if (!row.isEmpty()) {
                    int i = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    //shows a snapshot of the row when moving it
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(serialization, i);
                    db.setContent(cc);
                    //makes it so that you can find the row index in the dragboard
                    e.consume(); //Marks the end of the event
                }
            });
            row.setOnDragOver(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(serialization)) {
                    //Checks whether the data format has any information, which it should have as
                    //it has been associated with the row Index in the setOnRowDetected method
                    e.acceptTransferModes(TransferMode.MOVE); //accepts the drag event
                    e.consume();
                }
            });
            row.setOnDragDropped(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasContent(serialization) && selection == tableView) {
                    int draggedIndex = (int) db.getContent(serialization);
                    Card card = tableView.getItems().remove(draggedIndex);
                    //gets the rowIndex and removes the Card at it's position
                    int dropIndex;
                    if (row.isEmpty()) dropIndex = tableView.getItems().size();
                    else dropIndex = row.getIndex();
                    tableView.getItems().add(dropIndex, card);
                    ArrayList<Card> items = new ArrayList<>();
                    items.addAll(tableView.getItems());
                    TDList tdList = card.list;
                    updateList(tdList, items);
                    e.setDropCompleted(true); //marks the end of the drag event
                    tableView.getSelectionModel().select(dropIndex);
                    //Selects the dropped card, otherwise the first card in the tableview
                    //would be selected, which would not make sense
                    e.consume();
                }
            });
            return row;
        });
    }

    public void updateList(TDList tdList, ArrayList<Card> items ) {
        tdList.cards.clear();
        tdList.cards.addAll(items);
        for(Card item : items) {
            server.addCardToList(tdList.id, item);
        }
        System.out.println(tdList);
        server.updateList(tdList);
        server.updateBoard(board);
//        refresh(board.id);
    }

    public void dragOtherLists(TableView<Card> tableView) {
        tableView.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(serialization)) {
                e.acceptTransferModes(TransferMode.MOVE);
                e.consume();
            }
        });
        tableView.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            int draggedIndex = (int) db.getContent(serialization);
            Card card = selection.getItems().remove(draggedIndex);
            tableView.getItems().add(card);
            e.consume();
        });
    }

    public void setSelection(TableView<Card> tableView) {
        tableView.setOnMousePressed(e ->  {
            selection = tableView;
            System.out.println(selection.getItems());
        });
    }
}



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

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        dataLists = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForUpdates(cardChange -> {
            if(cardChange.change == Change.Add)
                dataLists.get(0).add(cardChange.card);
                //adding the card to the most left list(TO-DO)
        });
        setScrollPane();
    }

    public void showLists(){
        scrollPane.setContent(createFlowPane());
    }

    public Button createButton(long id){
        Button button = new Button("+");
        button.setOnAction(e ->{
            addCard();
        });
        return button;
    }

    public FlowPane createFlowPane(){
        FlowPane flowPane = new FlowPane();
        setFlowPane(flowPane);
        var lists = board.lists;
        for(var tdList: lists){
            Button button = createButton(tdList.id);
            TableView<Card> tv = createTable(tdList);
            cardExpansion(tv);
            dragAndDrop(tv);
            flowPane.getChildren().addAll(createVBox(tv, button));
        }
        return flowPane;
    }

    public void setFlowPane(FlowPane flowPane){
        flowPane.setAlignment(Pos.BASELINE_CENTER);
        flowPane.setHgap(50);
        flowPane.setVgap(5);
    }

    public void setScrollPane(){
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    public TableView<Card> createTable(TDList tdList){
        TableView<Card> tv = new TableView<>();
        tv.setPrefSize(157, 270);
        TableColumn<Card, String> tableColumn = new TableColumn<>();
        tableColumn.setText(tdList.title);
        tableColumn.setPrefWidth(tv.getPrefWidth());
        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title));
        tv.getColumns().add(tableColumn);
        ObservableList<Card> dataCards = FXCollections.observableList(tdList.list);
        tv.setItems(dataCards);
        return tv;
    }

    public VBox createVBox(TableView<Card> cards, Button button){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(button, cards);
        vBox.setAlignment(Pos.TOP_RIGHT);
        vBox.setSpacing(3);
        return vBox;
    }

    //boardID is not yet used
    public void refresh(long boardId) {
        board = server.tempBoardGetter();
        showLists();
        for(TDList tdList : board.lists) {
            dataLists.add(FXCollections.observableList(tdList.list));
        }
    }

    public void addCard() {
        mainCtrl.showAdd(board.id);
    }

    public void stop() {
        server.stop();
    }

    public void addList(){
        mainCtrl.showAddList(board.id);
    }

    //Method that will pop up a window to change the card name whenever you double-click on a card
    public void cardExpansion(TableView<Card> tableView) {
        tableView.setOnMousePressed(event -> {
            if(tableView.getSelectionModel().getSelectedItem() != null
                    && event.getClickCount() == 2) {
                Card card = tableView.getSelectionModel().getSelectedItem();
                mainCtrl.showEdit(card);
                refresh(board.id);
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
                if (db.hasContent(serialization)) {
                    int draggedIndex = (int) db.getContent(serialization);
                    Card card = tableView.getItems().remove(draggedIndex);
                    //gets the rowIndex and removes the Card at it's position
                    int dropIndex;
                    if (row.isEmpty()) dropIndex = tableView.getItems().size();
                    else dropIndex = row.getIndex();
                    tableView.getItems().add(dropIndex, card);
                    //If the row we drop the card on is empty, the card will be appended to
                    //the end of the tableview, otherwise it's dropped at the row index and
                    //added to the tableview items list

                    e.setDropCompleted(true); //marks the end of the drag event
                    tableView.getSelectionModel().select(dropIndex);
                    //Selects the dropped card, otherwise the first card in the tableview
                    //would be selected, which would not make sense
                    e.consume();
                }
            });
            return row ;
        });
    }
}



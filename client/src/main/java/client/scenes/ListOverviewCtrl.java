package client.scenes;

import client.services.ListOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private static final DataFormat serialization =
            new DataFormat("application/x-java-serialized-object");

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label boardTitle;
    @FXML
    private Label boardKey;
    @FXML
    private Button copyButton;
    private TableView<Card> selection;
    private ListOverviewService service;
    private double height = 700;
    private double width = 700;

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl, ListOverviewService service) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service.setScrollPane(scrollPane);
        server.registerForUpdates(updatedBoardID -> {
            if(board.getId() == updatedBoardID)
                board = server.tempBoardGetter();
            Platform.runLater(() -> {
                boardTitle.setText(board.title);
                boardKey.setText("key: " + board.key);
                showLists();
            });
        });
    }
    public void setAnchorPaneHeightWidth(){
        anchorPane.setPrefHeight(height);
        anchorPane.setPrefWidth(width);
    }
    public void saveAnchorPaneHeightWidth(){
        height = anchorPane.getHeight();
        width = anchorPane.getWidth();
    }

    private void setScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
    public void showLists() {
        scrollPane.setContent(createFlowPane());
    }

    public void setFlowPane(FlowPane flowPane) {
        flowPane.setAlignment(Pos.BASELINE_CENTER);
        flowPane.setHgap(50);
        flowPane.setVgap(5);
    }

    public FlowPane createFlowPane() {
        FlowPane flowPane = new FlowPane();
        setFlowPane(flowPane);
        var lists = board.tdLists;
        for (var tdList : lists) {
            Button buttonAddCard = createAddCardButton(tdList.id);
            Button buttonEditList = createEditListButton(tdList);
            TableView<Card> tv = service.createTable(tdList);
            service.cardExpansion(tv,mainCtrl);
            dragAndDrop(tv);
            dragOtherLists(tv, tdList);
            flowPane.getChildren().addAll(service.createVBox(tv,
                    service.createHBox(buttonAddCard, buttonEditList)));
        }
        return flowPane;
    }


    public Button createAddCardButton(long id) {
        return service.createAddCardButton(id, mainCtrl);
    }

    public Button createEditListButton(TDList list) {
        return service.createEditListButton(list, mainCtrl);
    }

//    public FlowPane createFlowPane() {
//        return service.createFlowPane(board, mainCtrl,
//                serialization, selection, server);
//    }

//    public void setFlowPane(FlowPane flowPane) {
//        flowPane.setAlignment(Pos.BASELINE_CENTER);
//        flowPane.setHgap(50);
//        flowPane.setVgap(5);
//    }



//    public TableView<Card> createTable(TDList tdList) {
//        TableView<Card> tv = new TableView<>();
//        tv.setPrefSize(157, 270);
//        tv.setId(Long.toString(tdList.getId()));
//        TableColumn<Card, String> tableColumn = new TableColumn<>();
//        tableColumn.setText(tdList.title);
//        tableColumn.setPrefWidth(tv.getPrefWidth());
//        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title));
//        tableColumn.setSortable(false);
//        tv.getColumns().add(tableColumn);
//        ObservableList<Card> dataCards = FXCollections.observableList(tdList.cards);
//        tv.setItems(dataCards);
//        return tv;
//    }

//    public TableView<Card> createTable(TDList tdList) {
//        TableView<Card> tv = new TableView<>();
//        tv.setPrefSize(157, 270);
//        TableColumn<Card, String> tableColumn = new TableColumn<>();
//        tableColumn.setText(tdList.title);
//        tableColumn.setPrefWidth(tv.getPrefWidth());
//        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title));
//        tv.getColumns().add(tableColumn);
//        ObservableList<Card> dataCards = FXCollections.observableList(tdList.cards);
//        tv.setItems(dataCards);
//        return tv;
//    }


//    public HBox createHBox(Button button1, Button button2) {
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(button2, button1);
//        //hBox.setAlignment(Pos.BASELINE_CENTER);
//        hBox.setSpacing(100);
//        return hBox;
//    }

//    public VBox createVBox(TableView<Card> cards, HBox hBox) {
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(hBox, cards);
//        vBox.setAlignment(Pos.TOP_RIGHT);
//        vBox.setSpacing(3);
//        return vBox;
//    }

    //boardID is not yet used
    public void refresh(long boardID) {
        board = server.tempBoardGetter();
        boardTitle.setText(board.title);
        boardKey.setText("key: " + board.key);
        showLists();

        //when we can dynamically add tdLists we would need a for loop here
        //tableView.setItems(dataLists.get(0));
    }
    public void stop() {
        server.stop();
    }

    public void addList() {
        mainCtrl.showAddList(board.id);
    }

//    public void dragAndDrop(TableView<Card> tableView){
//        service.dragAndDrop(tableView,serialization,selection,server,board);
//    }

    //Method that will pop up a window to change the card name whenever you double-click on a card
//    public void cardExpansion(TableView<Card> tableView) {
//        tableView.setOnMouseClicked(event -> {
//            if (tableView.getSelectionModel().getSelectedItem() != null
//                    && event.getClickCount() == 2) {
//                Card card = tableView.getSelectionModel().getSelectedItem();
//                mainCtrl.showEdit(card);
//            }
//        });
//    }

    public void dragAndDrop(TableView<Card> tableView){
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
                    int dropIndex;
                    if (row.isEmpty())
                        dropIndex = tableView.getItems().size();
                    else
                        dropIndex = row.getIndex();
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
        var ids = tdList.cards.stream().map(Card::getId).sorted().collect(Collectors.toList());
        for(int i = 0;i<ids.size();i++){
            tdList.cards.get(i).setId(ids.get(i)); // changing the ids of the cards to store
            // them in a different order in the database
        }
        server.updateBoard(board);
    }

    public void dragOtherLists(TableView<Card> tableView, TDList tdList) {
        tableView.setOnMousePressed(e ->  {
            selection = tableView;
        });
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
            server.updateCardList(card.getId(), tdList);
            refresh(board.id);
            server.updateBoard(board);
            e.consume();
        });
    }


    public void copyKey(){
        copyToClipboard();
        animateCopyButton();
    }

    public void copyToClipboard(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(String.valueOf(board.key));
        clipboard.setContents(selection,null);
    }

    public void afterCopyButton(){
        copyButton.setFont(new Font(9));
        copyButton.setText("Copied!");
        copyButton.setStyle("-fx-background-color: #34eb67;");
    }

    public void restoreCopyButton(){
        copyButton.setStyle("-fx-background-color: #2596be;");
        copyButton.setFont(new Font(12));
        copyButton.setText("Copy!");
    }

    public void animateCopyButton(){
        Platform.runLater(()->
        {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        afterCopyButton();
                    }),
                    new KeyFrame(Duration.seconds(2), event -> {
                        restoreCopyButton();
                    })
            );
            timeline.play();
        });
    }
//    public void dragOtherLists(TableView<Card> tableView) {
//        tableView.setOnDragOver(e -> {
//            Dragboard db = e.getDragboard();
//            if (db.hasContent(serialization)) {
//                e.acceptTransferModes(TransferMode.MOVE);
//                e.consume();
//            }
//        });
//        tableView.setOnDragDropped(e -> {
//            Dragboard db = e.getDragboard();
//            int draggedIndex = (int) db.getContent(serialization);
//            Card card = selection.getItems().remove(draggedIndex);
//            tableView.getItems().add(card);
//            ArrayList<Card> items = new ArrayList<>();
//            items.addAll(tableView.getItems());
//            updateList(card.list, items);
//            //I have to find a way to get a reference to the TDList that it's dropped on
//            //Don't know how to do that yet
//            e.consume();
//        });
//    }

    //Sets whatever tableview the drag starts with to be the selection tableview
    //This allows dragOtherLists to work because otherwise there would be no
    //reference to the tableview from which the card is removed
//    public void setSelection(TableView<Card> tableView) {
//        tableView.setOnMousePressed(e ->  {
//            selection = tableView;
//            System.out.println(selection.getItems());
//        });
//    }
}



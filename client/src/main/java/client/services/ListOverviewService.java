package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.TDList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

public class ListOverviewService {
//    public void initialize(ServerUtils server, Board board, TextField boardTitle,
//    Label boardKey, ScrollPane scrollPane){
//        server.registerForUpdates(updatedBoardID -> {
//            if(board.getId() == updatedBoardID)
//                board = server.tempBoardGetter();
//            Platform.runLater(() -> {
//                boardTitle.setText(board.title);
//                boardKey.setText("key: " + board.key);
//                showLists();
//            });
//        });
//    }

//    public void showLists() {
//        scrollPane.setContent(createFlowPane());
//    }

    public void setScrollPane(ScrollPane scrollPane) {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
    public Button createAddCardButton(long id, MainCtrl mainCtrl){
        Button button = new Button("+");
        button.setOnAction(e -> {
            mainCtrl.showAdd(id);
        });
        return button;
    }
    public Button createEditListButton(TDList list, MainCtrl mainCtrl){
        Button button = new Button("Edit");
        button.setOnAction(e -> {
            mainCtrl.showEditList(list);
        });
        return button;
    }
    public FlowPane createFlowPane(Board board, MainCtrl mainCtrl, DataFormat serialization,
                                   TableView<Card> selection, ServerUtils server){
        FlowPane flowPane = new FlowPane();
        setFlowPane(flowPane);
        var lists = board.tdLists;
        for (var tdList : lists) {
            Button buttonAddCard = createAddCardButton(tdList.id, mainCtrl);
            Button buttonEditList = createEditListButton(tdList, mainCtrl);
            TableView<Card> tv = createTable(tdList);
            cardExpansion(tv, mainCtrl);
            dragAndDrop(tv, serialization, selection, server, board);
            setSelection(tv, selection);
            dragOtherLists(tv, serialization, selection, server, board);
            flowPane.getChildren().addAll(createVBox(tv,
                    createHBox(buttonAddCard, buttonEditList)));
        }
        return flowPane;
    }
    public void setFlowPane(FlowPane flowPane) {
        flowPane.setAlignment(Pos.BASELINE_CENTER);
        flowPane.setHgap(50);
        flowPane.setVgap(5);
    }
    public TableView<Card> createTable(TDList tdList) {
        TableView<Card> tv = new TableView<>();
        tv.setPrefSize(157, 270);
        TableColumn<Card, String> tableColumn = new TableColumn<>();
        tableColumn.setText(tdList.title);
        tableColumn.setPrefWidth(tv.getPrefWidth());
        tableColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().cellFactory()));
        tv.getColumns().add(tableColumn);
        ObservableList<Card> dataCards = FXCollections.observableList(tdList.cards);
        tv.setItems(dataCards);
        return tv;
    }
    public void cardExpansion(TableView<Card> tableView, MainCtrl mainCtrl) {
        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null
                    && event.getClickCount() == 2) {
                Card card = tableView.getSelectionModel().getSelectedItem();
                mainCtrl.showEdit(card);
            } else if (tableView.getSelectionModel().getSelectedItem() == null
                    && tableView.getItems().get(0) != null && event.getClickCount() == 2) {
                mainCtrl.showEditList(tableView.getItems().get(0).list);
                //This will make it so that when u double-click on a tableview without having
                //anything selected, you will then be able to change the title of said tableview
            }
        });
    }
    public void dragAndDrop(TableView<Card> tableView, DataFormat serialization,
                            TableView<Card> selection,
                            ServerUtils server, Board board) {
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
                    updateList(tdList, items, server, board);
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
    public void updateList(TDList tdList, ArrayList<Card> items, ServerUtils server, Board board) {
        tdList.cards.clear();
        tdList.cards.addAll(items);
        for(Card item : items) {
            server.addCardToList(tdList.id, item);
        }
        server.updateList(tdList);
        server.updateBoard(board);
//        refresh(board.id);
    }

    public void addList(MainCtrl mainCtrl,Long boardId){
        mainCtrl.showAddList(boardId);
    }
    public void dragOtherLists(TableView<Card> tableView, DataFormat serialization,
                               TableView<Card> selection, ServerUtils server,
                               Board board) {
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
            ArrayList<Card> items = new ArrayList<>();
            items.addAll(tableView.getItems());
            updateList(card.list, items, server, board);
            //I have to find a way to get a reference to the TDList that it's dropped on
            //Don't know how to do that yet
            e.consume();
        });
    }
    public VBox createVBox(TableView<Card> cards, HBox hBox) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, cards);
        vBox.setAlignment(Pos.TOP_RIGHT);
        vBox.setSpacing(3);
        return vBox;
    }
    public HBox createHBox(Button button1, Button button2) {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(button2, button1);
        //hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(100);
        return hBox;
    }
    public void setSelection(TableView<Card> tableView, TableView<Card> selection) {
//        TableView<Card> arr[] = new TableView[2];
//        arr[0] = selection;
//        tableView.setOnMousePressed((MouseEvent e) ->  {
//            arr[0] = tableView;
//            //System.out.println(selection.getItems());
//        });
//        selection = arr[0];
    }

    public void copyKey(long boardKey, Button copyButton){
        copyToClipboard(boardKey);
        animateCopyButton(copyButton);
    }
    public void copyToClipboard(long boardKey){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(String.valueOf(boardKey));
        clipboard.setContents(selection,null);
    }

    public void afterCopyButton(Button copyButton){
        copyButton.setFont(new javafx.scene.text.Font(9));
        copyButton.setText("Copied!");
        copyButton.setStyle("-fx-background-color: #34eb67;");
    }

    public void restoreCopyButton(Button copyButton){
        copyButton.setStyle("-fx-background-color: #2596be;");
        copyButton.setFont(new Font(12));
        copyButton.setText("Copy!");
    }

    public void animateCopyButton(Button copyButton){
        Platform.runLater(()->
        {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        afterCopyButton(copyButton);
                    }),
                    new KeyFrame(Duration.seconds(2), event -> {
                        restoreCopyButton(copyButton);
                    })
            );
            timeline.play();
        });
    }
}

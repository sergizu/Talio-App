package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JoinedBoardsCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private AppClient client;

    @FXML
    private TextField boardTitle;

    @FXML
    private Label boardOverviewTitle;
    @FXML
    private HBox topHBox;
    @FXML
    private VBox boardsList;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button createBoardButton;
    @FXML
    private Button browseButton;


    @Inject
    public JoinedBoardsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
        disconnectButton.setStyle("-fx-background-color: red;");
        createBoardButton.setStyle("-fx-background-color: #2596be");
    }

    public void init(AppClient client) {
        this.client = client;
        String serverString = ServerUtils.getServer();
        addServerKeyIntoMap(serverString);
        getBoardsForServer(serverString);
    }

    public void addServerKeyIntoMap(String serverString) {
        if(!client.boards.containsKey(serverString)){
            ArrayList<Board> boards = new ArrayList<>();
            client.boards.put(serverString,boards);
        }
    }

    public void getBoardsForServer(String serverString) {
        ArrayList<Board> boards = new ArrayList<>();
        if(client.boards.containsKey(serverString)){
            boards = client.boards.get(serverString);
        }
        showJoinedBoards(boards);
    }

    private Board getBoard() {
        String title = boardTitle.getText();
        return new Board(title);
    }

    public void showCreateBoard() {
        mainCtrl.showCreateBoard();
    }

    public void createBoard() {
        Board board = getBoard();
        try {
            board = server.addBoard(board);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        mainCtrl.showOverview(board.getId());
    }

    public void showJoinedBoards(ArrayList<Board> boards) {
        if(boards.isEmpty()) {
            clearBoardList();
            Label noBoards = new Label("You have not joined any boards yet!");
            noBoards.setFont(Font.font(25.0));
            noBoards.setPadding(new Insets(30,30,30,30));
            boardsList.getChildren().add(noBoards);
        }
        else {
            displayBoards(boards);
        }
    }

    public void displayBoards(ArrayList<Board> boards) {
        clearBoardList();
        boardsList.setSpacing(3);
        for(Board board:boards)
            boardsList.getChildren().add(createHBox(board));
    }

    public HBox createHBox(Board board) {
        HBox tableLine = createTableLine();
        tableLine.setOnMouseClicked(event -> {
            enterBoard(board);
        });
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createLeaveButton(board));
        return tableLine;
    }

    public void clearBoardList() {
        while(!boardsList.getChildren().isEmpty())
            boardsList.getChildren().remove(0);///removeAll did not work!?
    }

    public Label createLabel(String title){
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10,50,10,100));
        return boardTitle;
    }

    public HBox createTableLine(){
        HBox tableLine = new HBox();
        tableLine.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null , null)));//will change color, was added for testing
        tableLine.setPrefHeight(50);
        return tableLine;
    }

    public Button createLeaveButton(Board board) {
        Button leaveButton = new Button("leave");
        leaveButton.setStyle("-fx-background-color: red;");
        leaveButton.setOnMouseClicked(event -> {
            leaveBoard(board);
        });
        return leaveButton;
    }

    public void leaveBoard(Board board) {
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        boards.remove(board);
        client.boards.put(ServerUtils.getServer(),boards);
        showJoinedBoards(boards);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createBoard();
                break;
            default:
                break;
        }
    }

    public void browsePressed() {
        mainCtrl.showBoardOverview();
    }
    public void enterBoard(Board board) {
        mainCtrl.showOverview(board.id);
    }
    public void disconnectPressed(){
        mainCtrl.showSelectServer();
    }


}

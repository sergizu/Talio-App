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
import java.util.List;
import java.util.ResourceBundle;

public class BoardOverviewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField boardTitle;

    @FXML
    private Label boardOverviewTitle;
    @FXML
    private HBox topHBox;
    @FXML
    private VBox boardsList;

    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
    }

    private Board getBoard() {
        String title = boardTitle.getText();
        return new Board(title);
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

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createBoard();
                break;
            default:
                break;
        }
    }

    public void showOtherBoards() {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        if(allBoards.isEmpty())
            displayNoBoardsMessage();
        else displayBoards(allBoards);
    }
    public void displayNoBoardsMessage(){
        clearBoardList();
        Label noBoards = new Label("There are no other boards");
        noBoards.setFont(Font.font(25.0));
        noBoards.setPadding(new Insets(30,30,30,30));
        boardsList.getChildren().add(noBoards);
    }

    public void displayBoards(List<Board> allBoards){
        ArrayList<Board> boards = (ArrayList<Board>) getLeftBoards(allBoards);
        if(boards.size() == 0)
            displayNoBoardsMessage();
        else {
            clearBoardList();
            for (Board board : boards)
                boardsList.getChildren().add(createHBox(board));
        }
    }

    public List<Board> getLeftBoards(List<Board> allBoards){
        ArrayList<Board> boards = new ArrayList<>();
        AppClient client = mainCtrl.getClient();
        ArrayList<Board> clientBoards = client.boards.get(ServerUtils.getServer());
        for(Board board:allBoards)
            if(!containsId(clientBoards,board.id) && !containsId(boards,board.id))
                boards.add(board);
        return boards;
    }

    boolean containsId(ArrayList<Board> boards, long boardId){
        for(Board board : boards)
            if(board.id == boardId)
                return true;
        return false;
    }

    public HBox createHBox(Board board) {
        HBox tableLine = createTableLine();
//        tableLine.setOnMouseClicked(event -> {
//            enterBoard(board);
//        });
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createJoinButton(board));
        return tableLine;
    }

    public HBox createTableLine(){
        HBox tableLine = new HBox();
        tableLine.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null , null)));//will change color, was added for testing
        tableLine.setPrefHeight(50);
        return tableLine;
    }
    public Label createLabel(String title){
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10,50,10,100));
        return boardTitle;
    }

    public Button createJoinButton(Board board) {
        Button leaveButton = new Button("Join");
        leaveButton.setStyle("-fx-background-color: #34eb67;");
        leaveButton.setOnMouseClicked(event -> {
            joinBoard(board);
        });
        return leaveButton;
    }

    public void clearBoardList() {
        while(!boardsList.getChildren().isEmpty())
            boardsList.getChildren().remove(0);///removeAll did not work!?
    }

    public void backToMyBoards() {
        mainCtrl.showJoinedBoards(mainCtrl.getClient());
    }

    public void joinBoard(Board board){
        AppClient client = mainCtrl.getClient();
        ArrayList<Board> joinedBoards = client.boards.get(ServerUtils.getServer());
        if(!joinedBoards.contains(board))
            joinedBoards.add(board);
        client.boards.put(ServerUtils.getServer(),joinedBoards);
        mainCtrl.showOverview(board.id);
    }


}

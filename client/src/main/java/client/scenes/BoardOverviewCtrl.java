package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BoardOverviewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label boardOverviewTitle;
    @FXML
    private VBox boardsList;

    private Scene createBoardScene;
    private CreateBoardCtrl createBoardCtrl;

    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
        FXMLLoader createBoardLoader = new FXMLLoader((getClass().
                getResource("/client/scenes/CreateBoard.fxml")));
        createBoardLoader.setControllerFactory(c ->
                createBoardCtrl = new CreateBoardCtrl(server, mainCtrl));
        try {
            createBoardScene = new Scene(createBoardLoader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBoard() {
        mainCtrl.showCreateBoard(createBoardScene, BoardOverviewCtrl.class, createBoardCtrl);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            createBoard();
        }
    }

    public void showAllBoards() {
        List<Board> allBoards = server.getBoards();
        boardsList.getChildren().clear();
        if (allBoards.isEmpty())
            displayNoBoardsMessage();
        else {
            for (Board board : allBoards)
                boardsList.getChildren().add(createHBox(board));
        }
    }

    public void displayNoBoardsMessage() {
        boardsList.getChildren().clear();
        Label noBoards = new Label("There are no other boards");
        noBoards.setFont(Font.font(25.0));
        noBoards.setPadding(new Insets(30, 30, 30, 30));
        boardsList.getChildren().add(noBoards);
    }

    public HBox createHBox(Board board) {
        HBox tableLine = createTableLine();
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createJoinButton(board));
        return tableLine;
    }

    public HBox createTableLine() {
        HBox tableLine = new HBox();
        tableLine.setAlignment(Pos.CENTER);
        tableLine.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null, null)));//will change color, was added for testing
        tableLine.setPrefHeight(50);
        return tableLine;
    }

    public Label createLabel(String title) {
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10, 50, 10, 100));
        return boardTitle;
    }

    public Button createJoinButton(Board board) {
        Button joinButton = new Button("Join");
        joinButton.setStyle("-fx-background-color: #34eb67;");
        joinButton.setOnMouseClicked(event -> {
            mainCtrl.showOverview(board.id, BoardOverviewCtrl.class);
        });
        return joinButton;
    }

    public void disconnect() {
        mainCtrl.showSelectServer();
    }
}

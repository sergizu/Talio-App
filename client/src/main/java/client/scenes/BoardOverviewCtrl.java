package client.scenes;

import client.scenes.interfaces.CreateBoardCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardOverviewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField joinByKey;
    @FXML
    private Label boardOverviewTitle;
    @FXML
    private VBox boardsList;
    @FXML
    private Button createBoardButton;
    @FXML
    private Button disconnectButton;
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
        disconnectButton.setStyle("-fx-background-color: red;");
        createBoardButton.setStyle("-fx-background-color: #2596be");
    }

    public void registerForMessages() {
        registerForBoardRename();
        registerForBoardDeletion();
        registerForBoardCreation();
    }

    public void registerForBoardRename() {
        server.registerForMessages("/topic/renameBoard", Board.class, renamedBoard -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    showAllBoards();
            });
        });
    }

    public void registerForBoardDeletion() {
        server.registerForMessages("/topic/boardDeletion", Long.class, deletedBoardId -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    showAllBoards();
            });
        });
    }

    public void registerForBoardCreation() {
        server.registerForMessages("/topic/boardCreation", Long.class, newBoardId -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    showAllBoards();
            });
        });
    }



    public void createBoard() {
        mainCtrl.showCreateBoard(BoardOverviewCtrl.class);
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
        tableLine.setOnMouseClicked(event -> {
            enterBoard(board);
        });
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createOptionsButton(board));
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

    public Button createOptionsButton(Board board) {
        Button settingsButton = new Button("Options");
        settingsButton.setStyle("-fx-background-color: #2596be;");
        settingsButton.setPadding(new Insets(10, 5, 10, 5));
        settingsButton.setOnMouseClicked(event -> {
            mainCtrl.showBoardOptions(board);
        });
        return settingsButton;
    }

    public Label createLabel(String title) {
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10, 50, 10, 100));
        return boardTitle;
    }

    public void joinByKey() {
        long key = -1;
        try {
            key = Long.parseLong(joinByKey.getText());
        } catch (NumberFormatException e) {
            adjustPromptText("Please enter a valid key!");
            return;
        }
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                enterBoard(board);
                joinByKey.clear();
                return;
            }
        if (!lookForBoardKey(key))
            adjustPromptText("No board with that key!");
    }

    public boolean lookForBoardKey(long key) {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                enterBoard(board);
                joinByKey.clear();
                return true;
            }
        return false;
    }

    public void adjustPromptText(String information) {
        Platform.runLater(() ->
        {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        joinByKey.clear();
                        joinByKey.setPromptText(information);
                    }),
                    new KeyFrame(Duration.seconds(5), event -> {
                        joinByKey.setPromptText("Join by key");
                    })
            );
            timeline.play();
        });
    }

    public void enterBoard(Board board) {
        mainCtrl.showOverview(board.id);
    }

    public void joinPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            joinByKey();
    }
    public void showCreateBoard() {
        mainCtrl.showCreateBoard(BoardOverviewCtrl.class);
    }
    public void disconnectPressed() {
        mainCtrl.showSelectServer();
        server.stopSession();
    }
}

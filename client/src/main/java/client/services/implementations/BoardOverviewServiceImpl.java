package client.services.implementations;

import client.scenes.interfaces.BoardOverviewCtrl;
import client.services.interfaces.BoardOverviewService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class BoardOverviewServiceImpl implements BoardOverviewService {

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

    private final BoardOverviewCtrl boardOverviewCtrl;

    @Inject
    public BoardOverviewServiceImpl(BoardOverviewCtrl boardOverviewCtrl) {
        this.boardOverviewCtrl = boardOverviewCtrl;
    }

    public void initialize(URL location, ResourceBundle resources) {
        boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
        disconnectButton.setStyle("-fx-background-color: red;");
        createBoardButton.setStyle("-fx-background-color: #2596be");
    }

    public void showAllBoards() {
        List<Board> allBoards = boardOverviewCtrl.getBoards();
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
            boardOverviewCtrl.enterBoard(board);
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
            boardOverviewCtrl.showBoardOptions(board);
        });
        return settingsButton;
    }

    public Label createLabel(String title) {
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10, 50, 10, 100));
        return boardTitle;
    }

    public void adjustPromptText(String information) {
        Platform.runLater(() ->
        {
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    clearJoinByKey();
                    setJoinByKeyPrompt(information);
                }),
                new KeyFrame(Duration.seconds(5), event -> {
                    setJoinByKeyPrompt("Join by key");
                })
            );
            timeline.play();
        });
    }

    public void disconnectPressed() {
        boardOverviewCtrl.disconnectPressed();
    }
    public void joinByKey() {
        boardOverviewCtrl.joinByKey();
    }

    public String getJoinByKeyText() {
        return joinByKey.getText();
    }
    public void clearJoinByKey(){
        joinByKey.clear();
    }

    public void setJoinByKeyPrompt(String text) {
        joinByKey.setPromptText(text);
    }

    public void joinPressed(KeyEvent event) {
        boardOverviewCtrl.joinPressed(event);
    }

    public void createBoard() {
        boardOverviewCtrl.showCreateBoard();
    }
}
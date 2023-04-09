package client.services.implementations;

import client.scenes.interfaces.JoinedBoardsCtrl;
import client.services.interfaces.JoinedBoardsService;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

@Singleton
public class JoinedBoardsServiceImpl implements JoinedBoardsService {

    private final JoinedBoardsCtrl joinedBoardsCtrl;
    @FXML
    private TextField boardTitle;
    @FXML
    private TextField joinByKey;
    @FXML
    private Label boardOverviewTitle;
    @FXML
    private VBox boardsList;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button createBoardButton;

    @Inject
    public JoinedBoardsServiceImpl(JoinedBoardsCtrl joinedBoardsCtrl) {
        this.joinedBoardsCtrl = joinedBoardsCtrl;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
        disconnectButton.setStyle("-fx-background-color: red;");
        createBoardButton.setStyle("-fx-background-color: #2596be");
    }

    public void setJoinByKeyPrompt(String text) {
        joinByKey.setPromptText(text);
    }
    
    public String getJoinByKeyText() {
        return joinByKey.getText();
    }

    public void clearJoinByKey() {
        joinByKey.clear();
    }

    public void clearBoardList() {
        while (!boardsList.getChildren().isEmpty())
            boardsList.getChildren().remove(0);///removeAll did not work!?
    }

    public void displayBoards(ArrayList<Board> boards) {
        clearBoardList();
        boardsList.setSpacing(3);
        for (Board board : boards)
            boardsList.getChildren().add(createHBox(board));
    }

    public HBox createHBox(Board board) {
        HBox tableLine = createTableLine();
        tableLine.setOnMouseClicked(event -> {
            joinedBoardsCtrl.enterBoard(board);
        });
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createLeaveButton(board));
        tableLine.getChildren().add(createOptionsButton(board));
        return tableLine;
    }

    public Label createLabel(String title) {
        Label boardTitle = new Label(title);
        boardTitle.setFont(Font.font(20));
        boardTitle.setPadding(new Insets(10, 50, 10, 100));
        return boardTitle;
    }

    public HBox createTableLine() {
        HBox tableLine = new HBox();
        tableLine.setAlignment(Pos.CENTER);
        tableLine.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null, null)));//will change color, was added for testing
        tableLine.setPrefHeight(50);
        tableLine.setSpacing(20);
        return tableLine;
    }

    public Button createLeaveButton(Board board) {
        Button leaveButton = new Button("Leave");
        leaveButton.setStyle("-fx-background-color: red;");
        leaveButton.setPadding(new Insets(10, 12, 10, 12));
        leaveButton.setOnMouseClicked(event -> {
            joinedBoardsCtrl.leaveBoard(board);
        });
        return leaveButton;
    }

    public Button createOptionsButton(Board board) {
        Button settingsButton = new Button("Options");
        settingsButton.setStyle("-fx-background-color: #2596be;");
        settingsButton.setPadding(new Insets(10, 5, 10, 5));
        //        String imageFilePath = "../../resources/Images/SettingsButton.png";
//        String settingsImagePath2 = "file:/C:/Users/stanc/oopp-team-10/" +
//                "client/src/main/resources/Images/Settings.png";//only the absoulte path works
//        settingsButton.setStyle("-fx-background-image: url('" + imageFilePath + "');" +
//                "-fx-background-size: cover; " +
//                "-fx-background-repeat: no-repeat; " +
//                "-fx-background-position: center; " +
//                "-fx-padding: 10;");
        settingsButton.setOnMouseClicked(event -> {
            joinedBoardsCtrl.showOptions(board);
        });
        return settingsButton;
    }

    public void showJoinedBoards(ArrayList<Board> boards) {
        if (boards == null || boards.isEmpty()) {
            clearBoardList();
            Label noBoards = new Label("You have not joined any boards yet!");
            noBoards.setFont(Font.font(25.0));
            noBoards.setPadding(new Insets(30, 30, 30, 30));
            boardsList.getChildren().add(noBoards);
        } else {
            displayBoards(boards);
        }
    }

    public void joinPressed(KeyEvent e) {
        joinedBoardsCtrl.joinPressed(e);
    }

    public void disconnectPressed() {
        joinedBoardsCtrl.disconnectPressed();
    }
    
    public void showCreateBoard() {
        joinedBoardsCtrl.showCreateBoard();
    }
    
    public void joinByKey() {
        joinedBoardsCtrl.joinByKey();
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
}

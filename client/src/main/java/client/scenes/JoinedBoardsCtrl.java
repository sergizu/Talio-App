package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JoinedBoardsCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private AppClient client;
    private Scene createBoardScene;
    private CreateBoardCtrl createBoardCtrl;

    @FXML
    private TextField boardTitle;
    @FXML
    private TextField joinByKey;
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
        joinByKey.setPromptText("Join by key");
        String serverString = ServerUtils.getServer();
        addServerKeyIntoMap(serverString);
        getBoardsForServer(serverString);

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

    public void addServerKeyIntoMap(String serverString) {
        if (!client.boards.containsKey(serverString)) {
            ArrayList<Board> boards = new ArrayList<>();
            client.boards.put(serverString, boards);
        }
    }

    public void getBoardsForServer(String serverString) {
        ArrayList<Board> boards = new ArrayList<>();
        if (client.boards.containsKey(serverString)) {
            boards = client.boards.get(serverString);
        }
        showJoinedBoards(boards);
    }

    public void showCreateBoard() {
        mainCtrl.showCreateBoard(createBoardScene, JoinedBoardsCtrl.class, createBoardCtrl);
    }

    public void showJoinedBoards(ArrayList<Board> boards) {
        if (boards.isEmpty()) {
            clearBoardList();
            Label noBoards = new Label("You have not joined any boards yet!");
            noBoards.setFont(Font.font(25.0));
            noBoards.setPadding(new Insets(30, 30, 30, 30));
            boardsList.getChildren().add(noBoards);
        } else {
            displayBoards(boards);
        }
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
            enterBoard(board);
        });
        tableLine.getChildren().add(createLabel(board.title));
        tableLine.getChildren().add(createLeaveButton(board));
        tableLine.getChildren().add(createSettingsButoon(board));
        return tableLine;
    }

    public void clearBoardList() {
        while (!boardsList.getChildren().isEmpty())
            boardsList.getChildren().remove(0);///removeAll did not work!?
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
            leaveBoard(board);
        });
        return leaveButton;
    }

    public Button createSettingsButoon(Board board) {
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
            showOptions(board);
        });
        return settingsButton;
    }

    public void leaveBoard(Board board) {
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        boards.remove(board);
        client.boards.put(ServerUtils.getServer(), boards);
        showJoinedBoards(boards);
    }

    public void showOptions(Board board) {
        mainCtrl.showBoardOptions(board);
    }

    public void enterBoard(Board board) {
        mainCtrl.showOverview(board.id, JoinedBoardsCtrl.class);
    }

    public void disconnectPressed() {
        mainCtrl.showSelectServer();
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
        for(Board board : allBoards)
            if(board.key == key) {
                joinBoard(board);
                joinByKey.clear();
                return;
            }
        if(!lookForBoardKey(key))
            adjustPromptText("No board with that key!");
    }

    public boolean lookForBoardKey(long key) {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for(Board board : allBoards)
            if(board.key == key) {
                joinBoard(board);
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

    public void joinBoard(Board board) {
        containsBoardId(board);
        enterBoard(board);
    }

    public void joinPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            joinByKey();
    }
    public boolean containsBoardId(Board newBoard) {
        ArrayList<Board> clientBoards = client.boards.get(ServerUtils.getServer());
        for(Board board : clientBoards)
            if(board.id == newBoard.id)
                return true;
        clientBoards.add(newBoard);
        client.boards.put(ServerUtils.getServer(),clientBoards);
        return false;
    }

}

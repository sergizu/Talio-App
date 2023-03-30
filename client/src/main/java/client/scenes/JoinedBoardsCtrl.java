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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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



    @Inject
    public JoinedBoardsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
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
            Label noBoards = new Label("You have not joined any boards yet!");
            noBoards.setFont(Font.font(25.0));
            //noBoards.setAlignment(Pos.CENTER);
            //noBoards.set
            noBoards.setPadding(new Insets(30,30,30,30));
            boardsList.getChildren().add(noBoards);
//            Label noBoards2 = new Label("You have not joined any boards yet!");
//            boardsList.getChildren().add(noBoards2);
        }
        else {

        }
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

    public void disconnectPressed(){

    }


}

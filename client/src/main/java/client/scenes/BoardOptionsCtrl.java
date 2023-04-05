package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BoardOptionsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Board board;
    @FXML
    private TextField boardName;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label emptyName;


    @Inject
    public BoardOptionsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Board board) {
        this.board = board;
        boardName.setText(board.title);
        deleteButton.setStyle("-fx-background-color: red;");
        saveButton.setStyle("-fx-background-color: #2596be");

    }


    public void ok() {
        if(!board.title.equals(boardName.getText())) {
            board.title = boardName.getText();
            server.send("/app/boards/renameBoard", board);
            server.updateBoard(board);
        }
        showNextScene();
    }

    public void cancel() {
        showNextScene();
    }

    public void delete() {
        server.deleteBoard(board.id);
        server.send("/app/boards/deleteBoard",board.id);
        showNextScene();
    }

    public void showNextScene() {
        if(mainCtrl.getAdmin())
            mainCtrl.showBoardOverview();
        else mainCtrl.showJoinedBoards(mainCtrl.getClient());
    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }


}

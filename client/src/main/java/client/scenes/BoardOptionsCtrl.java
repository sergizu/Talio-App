package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
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
    private Label emptyName;


    @Inject
    public BoardOptionsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void init(Board board) {
        this.board = board;
        boardName.setText(board.title);
    }


    public void ok() {
        if(!board.title.equals(boardName.getText())) {
            board.title = boardName.getText();
            server.send("/app/boards/renameBoard", board);
            server.updateBoard(board);
        }
        mainCtrl.showJoinedBoards(mainCtrl.getClient());
    }

    public void cancel() {

    }

    public void delete() {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }


}

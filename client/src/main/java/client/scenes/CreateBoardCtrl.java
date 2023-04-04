package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import commons.TDList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.ArrayList;

public class CreateBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Object parent;

    private AppClient client;

    @FXML
    TextField boardTitle;

    @Inject
    public CreateBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Board getBoardWithTitle() {
        Board board = new Board(boardTitle.getText());
        addDefaultLists(board);
        return board;
    }

    public void addDefaultLists(Board board) {
        board.addList(new TDList("TO DO"));
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
    }

    public void createBoard() {
        client = mainCtrl.getClient();
        Board board = getBoardWithTitle();
        boardTitle.setText("");
        try {
            board = server.addBoard(board);
            if (parent == JoinedBoardsCtrl.class)
                addBoardToClient(board);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        mainCtrl.showOverview(board.getId());
    }

    public void addBoardToClient(Board board) {
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        if (boards == null)
            boards = new ArrayList<>();
        boards.add(board);
        client.boards.put(ServerUtils.getServer(), boards);
    }

    public void escape() {
        if (parent == JoinedBoardsCtrl.class)
            mainCtrl.showJoinedBoards(mainCtrl.getClient());
        else if (parent == BoardOverviewCtrl.class) {
            mainCtrl.showBoardOverview();
        }
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            createBoard();
        } else if (e.getCode() == KeyCode.ESCAPE) {
            escape();
        }
    }
}

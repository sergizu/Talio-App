package client.scenes;

import client.services.CreateBoardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.AppClient;
import commons.Board;
import commons.TDList;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.ArrayList;

@Singleton
public class CreateBoardCtrlImpl implements CreateBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final CreateBoardService createBoardService;
    private Object parent;

    @Inject
    public CreateBoardCtrlImpl(ServerUtils server, MainCtrl mainCtrl, CreateBoardService createBoardService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.createBoardService = createBoardService;
    }

    public Board getBoardWithTitle() {
        Board board = new Board(createBoardService.getBoardName());
        addDefaultLists(board);
        return board;
    }

    public void addDefaultLists(Board board) {
        board.addList(new TDList("TO DO"));
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
    }

    public void createBoard() {
        Board board = getBoardWithTitle();
        createBoardService.setBoardName("");
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
        AppClient client = mainCtrl.getClient();
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        if (boards == null)
            boards = new ArrayList<>();
        boards.add(board);
        client.boards.put(ServerUtils.getServer(), boards);
    }

    public void escape() {
        if (parent == JoinedBoardsCtrl.class)
            mainCtrl.showJoinedBoards();
        else {
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

    public Object getParent() {
        return parent;
    }
}

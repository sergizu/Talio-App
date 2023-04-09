package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.CreateBoardCtrl;
import client.services.interfaces.CreateBoardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.AppClient;
import commons.Board;
import commons.TDList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

@Singleton
public class CreateBoardCtrlImpl implements CreateBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final CreateBoardService createBoardService;
    private Object parent;

    @Inject
    public CreateBoardCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                               CreateBoardService createBoardService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.createBoardService = createBoardService;
    }

    public Board getBoardWithTitle() {
        if(createBoardService.getBoardName().isEmpty()) {
            return null;
        }
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
        if(board == null) {
            createBoardService.setErrorLabel("Board name cannot be empty!");
            return;
        }
        createBoardService.setBoardName("");
        board = server.addBoard(board);
        server.send("/app/boards/createBoard",board.id);
        if (!mainCtrl.getAdmin())
            addBoardToClient(board);
        mainCtrl.showOverview(board.getId());
    }

    public void addBoardToClient(Board board) {
        AppClient client = mainCtrl.getClient();
        ArrayList<Board> boards = client.boards.get(server.getServer());
        if (boards == null)
            boards = new ArrayList<>();
        boards.add(board);
        client.boards.put(server.getServer(), boards);
    }

    public void escape() {
        if (!mainCtrl.getAdmin())
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
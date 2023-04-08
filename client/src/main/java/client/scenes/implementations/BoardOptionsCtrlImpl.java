package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.BoardOptionsCtrl;
import client.services.interfaces.BoardOptionsService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BoardOptionsCtrlImpl implements BoardOptionsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final BoardOptionsService boardOptionsService;
    private Board board;


    @Inject
    public BoardOptionsCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                                BoardOptionsService boardOptionsService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.boardOptionsService = boardOptionsService;
    }

    public void init(Board board) {
        this.board = board;
        boardOptionsService.setBoardName(board.title);
        boardOptionsService.initButtons();
    }


    public void ok() {
        if(!board.title.equals(boardOptionsService.getBoardName())) {
            board.title = boardOptionsService.getBoardName();
            server.updateBoard(board);
            server.send("/app/boards/renameBoard", board);
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
        else mainCtrl.showJoinedBoards();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }

    public Board getBoard() {
        return board;
    }
    
    public void setBoard(Board board){
        this.board = board;
    }


}

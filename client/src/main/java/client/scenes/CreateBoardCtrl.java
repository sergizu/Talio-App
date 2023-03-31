package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import commons.Card;
import commons.TDList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.ArrayList;

public class CreateBoardCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private AppClient client;

    @FXML
    TextField boardTitle;
    @Inject
    public CreateBoardCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Board getBoardWithTitle() {
        Board board = new Board(boardTitle.getText());
        addDefaultLists(board);
        return board;
    }

    public void addDefaultLists(Board board) {
        TDList list1 = new TDList("TO DO");
        list1.addCard(new Card("Card"));
        board.addList(list1);
        board.addList(new TDList("DOING"));
        board.addList(new TDList("DONE"));
    }
    public void createBoard() {
        client = mainCtrl.getClient();
        Board board = getBoardWithTitle();
        boardTitle.setText("");
        try {
            board = server.addBoard(board);
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

    public void addBoardToClient(Board board){
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        if(boards == null)
            boards = new ArrayList<>();
        boards.add(board);
        client.boards.put(ServerUtils.getServer(),boards);
    }
    public void escape(){
        mainCtrl.showJoinedBoards(mainCtrl.getClient());
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createBoard();
                break;
            case ESCAPE:
                escape();
                break;
            default:
                break;
        }
    }
}

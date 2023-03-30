package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardOverviewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField boardTitle;

    @FXML
    private Label boardOverviewTitle;
    @FXML
    private HBox topHBox;

    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardOverviewTitle.setMaxWidth(3000.0);
        HBox.setHgrow(boardOverviewTitle, Priority.ALWAYS);
    }

    private Board getBoard() {
        String title = boardTitle.getText();
        return new Board(title);
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

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createBoard();
                break;
            default:
                break;
        }
    }

    public void showOtherBoards(AppClient client) {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        if(allBoards.isEmpty())
            displayNoBoardsMessage();
    }
    public void displayNoBoardsMessage(){

    }


}

package client.services.interfaces;

import client.services.implementations.JoinedBoardsServiceImpl;
import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

@ImplementedBy(JoinedBoardsServiceImpl.class)
public interface JoinedBoardsService extends Initializable {

    void showJoinedBoards(ArrayList<Board> boards);
    void displayBoards(ArrayList<Board> boards);
    void setJoinByKeyPrompt(String text);
    String getJoinByKeyText();
    void clearJoinByKey();
    void joinPressed(KeyEvent e);
    void disconnectPressed();
    void showCreateBoard();
    void joinByKey();
    void adjustPromptText(String information);

}

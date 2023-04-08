package client.scenes.interfaces;

import client.scenes.implementations.JoinedBoardsCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.scene.input.KeyEvent;

@ImplementedBy(JoinedBoardsCtrlImpl.class)
public interface JoinedBoardsCtrl{

    void enterBoard(Board board);
    void leaveBoard(Board board);
    void showOptions(Board board);
    void disconnectPressed();
    void joinPressed(KeyEvent e);
    void showCreateBoard();
    void joinByKey();
    void init();
    void registerForMessages();

}

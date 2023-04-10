package client.scenes.interfaces;

import client.scenes.implementations.CreateBoardCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.scene.input.KeyEvent;

@ImplementedBy(CreateBoardCtrlImpl.class)
public interface CreateBoardCtrl {
    Board getBoardWithTitle();
    void addDefaultLists(Board board);
    void createBoard();
    void addBoardToClient(Board board);
    void escape();
    void keyPressed(KeyEvent e);
}

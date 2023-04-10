package client.scenes.interfaces;

import client.scenes.implementations.BoardOverviewCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.scene.input.KeyEvent;

import java.util.List;

@ImplementedBy(BoardOverviewCtrlImpl.class)
public interface BoardOverviewCtrl {

    void showCreateBoard();
    void showBoardOptions(Board board);
    void enterBoard(Board board);
    void joinByKey();
    void joinPressed(KeyEvent event);
    List<Board> getBoards();
    void disconnectPressed();
}

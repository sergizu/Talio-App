package client.scenes.interfaces;

import client.scenes.implementations.BoardOptionsCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.scene.input.KeyEvent;

@ImplementedBy(BoardOptionsCtrlImpl.class)
public interface BoardOptionsCtrl {

    void init(Board board);
    void ok();
    void cancel();
    void delete();
    void showNextScene();
    void keyPressed(KeyEvent event);
}

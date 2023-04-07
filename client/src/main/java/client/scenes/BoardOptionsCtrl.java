package client.scenes;

import com.google.inject.ImplementedBy;
import commons.Board;
import javafx.scene.input.KeyEvent;

@ImplementedBy(BoardOptionsCtrlImpl.class)
public interface BoardOptionsCtrl {

    public void init(Board board);
    public void ok();
    public void cancel();
    public void delete();
    public void showNextScene();
    public void keyPressed(KeyEvent event);
}

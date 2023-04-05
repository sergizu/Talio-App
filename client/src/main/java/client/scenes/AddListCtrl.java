package client.scenes;

import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(AddListCtrlImpl.class)
public interface AddListCtrl {
    void ok();
    void cancel();
    void keyPressed(KeyEvent e);
    long getBoardId();
    void setBoard(long boardId);

}

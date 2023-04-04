package client.scenes;

import javafx.scene.input.KeyEvent;

public interface AddCardCtrl {
    void setListBoardId(long listId,long boardId);
    void ok();
    void cancel();
    void keyPressed(KeyEvent e);
}

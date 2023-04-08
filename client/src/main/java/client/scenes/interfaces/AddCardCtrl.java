package client.scenes.interfaces;

import client.scenes.implementations.AddCardCtrlImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(AddCardCtrlImpl.class)
public interface AddCardCtrl {
    void setListBoardId(long listId,long boardId);
    void ok();
    void cancel();
    void keyPressed(KeyEvent e);
    long getListId();
    long getBoardId();

}

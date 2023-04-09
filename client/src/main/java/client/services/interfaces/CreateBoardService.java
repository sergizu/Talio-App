package client.services.interfaces;

import client.services.implementations.CreateBoardServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(CreateBoardServiceImpl.class)
public interface CreateBoardService {
    void setBoardName(String s);
    String getBoardName();
    void createBoard();
    void escape();
    void setErrorLabel(String error);
    void enter();
    void keyPressed(KeyEvent e);
}

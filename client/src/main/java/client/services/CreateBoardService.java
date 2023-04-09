package client.services;

import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(CreateBoardServiceImp.class)
public interface CreateBoardService {
    void setBoardName(String s);
    String getBoardName();
    void createBoard();
    void escape();
    void setErrorLabel(String error);
    void enter();
    void keyPressed(KeyEvent e);
}

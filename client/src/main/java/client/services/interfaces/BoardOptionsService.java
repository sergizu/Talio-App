package client.services.interfaces;

import client.services.implementations.BoardOptionsServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(BoardOptionsServiceImpl.class)
public interface BoardOptionsService {

    public void initButtons();
    public void setBoardName(String text);
    public String getBoardName();
    public void ok();
    public void delete();
    public void cancel();
    public void keyPressed(KeyEvent e);
}

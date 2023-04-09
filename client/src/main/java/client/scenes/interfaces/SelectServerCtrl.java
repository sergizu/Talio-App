package client.scenes;

import client.scenes.implementations.SelectServerCtrlImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;



@ImplementedBy(SelectServerCtrlImpl.class)
public interface SelectServerCtrl {

    public boolean checkPass();

    public void ok();

    public void keyPressed(KeyEvent e);
    public void adminLogIn();
    public void startSession();
}
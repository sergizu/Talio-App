package client.services.interfaces;

import client.services.implementations.SelectServerServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;


@ImplementedBy(SelectServerServiceImpl.class)
public interface SelectServerService {
    String getAdminPassText();
    String getServerNameText();
    void setMyLabel(String s);
    void setAdminPassText(String s);
    void setBoxVisible(Boolean b);
    void adminLogIn();void ok();
    void keyPressed(KeyEvent e);
    boolean getVisible();
    void setChoiceButton(String s);
}

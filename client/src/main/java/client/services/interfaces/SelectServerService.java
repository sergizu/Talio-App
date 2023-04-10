package client.services.interfaces;

import client.services.implementations.SelectServerServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;


@ImplementedBy(SelectServerServiceImpl.class)
public interface SelectServerService {
    public String getAdminPassText();
    public String getServerNameText();
    public void setMyLabel(String s);
    public void setAdminPassText(String s);
    public void setBoxVisible(Boolean b);
    public void adminLogIn();
    public void ok();

    void keyPressed(KeyEvent e);
}

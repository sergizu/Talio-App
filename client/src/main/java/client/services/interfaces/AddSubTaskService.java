package client.services.interfaces;

import client.services.implementations.AddSubTaskServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.scene.input.KeyEvent;

@ImplementedBy(AddSubTaskServiceImpl.class)
public interface AddSubTaskService{
    String getSubtaskName();
    void setSubtaskName(String text);
    void setMyLabelText(String text);
    void create();
    void cancel();
    void keyPressed(KeyEvent e);
}

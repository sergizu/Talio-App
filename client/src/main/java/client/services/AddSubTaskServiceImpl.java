package client.services;

import client.scenes.AddSubTaskCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddSubTaskServiceImpl implements AddSubTaskService{
    @FXML
    private TextField subtaskName;

    @FXML
    private Label myLabel;

    @Inject
    private AddSubTaskCtrl addSubTaskCtrl;

    public String getSubtaskName() {
        return subtaskName.getText();
    }

    public void setSubtaskName(String text) {
        subtaskName.setText(text);
    }

    public void setMyLabelText(String text) {
        myLabel.setText(text);
    }

    public void create() {
        addSubTaskCtrl.create();
    }

    public void cancel() {
        addSubTaskCtrl.cancel();
    }
}

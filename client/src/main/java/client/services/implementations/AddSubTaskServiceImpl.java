package client.services.implementations;

import client.scenes.interfaces.AddSubTaskCtrl;
import client.services.interfaces.AddSubTaskService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Singleton
public class AddSubTaskServiceImpl implements AddSubTaskService {
    @FXML
    private TextField subtaskName;

    @FXML
    private Label myLabel;

    private final AddSubTaskCtrl addSubTaskCtrl;

    @Inject
    public AddSubTaskServiceImpl(AddSubTaskCtrl addSubTaskCtrl) {
        this.addSubTaskCtrl = addSubTaskCtrl;
    }

    public String getSubtaskName() {
        return subtaskName.getText();
    }

    public void setSubtaskName(String text) {
        subtaskName.setText(text);
    }

    public void setMyLabelText(String text) {
        myLabel.setText(text);
    }

    //method that gets called when the create button is pressed
    public void create() {
        addSubTaskCtrl.create();
    }

    //method that gets called when the cancel button is pressed
    public void cancel() {
        addSubTaskCtrl.cancel();
    }
}

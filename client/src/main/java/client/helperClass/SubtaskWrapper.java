package client.helperClass;

import com.google.inject.Inject;
import commons.Subtask;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.DataFormat;

public class SubtaskWrapper {

    private DataFormat serialization;

    private Subtask subtask;

    private CheckBox checkBox;

    private Button button;

    @Inject
    public SubtaskWrapper(Subtask subtask, CheckBox checkBox, Button button) {
        this.subtask = subtask;
        this.checkBox = checkBox;
        this.button = button;
    }

    public Subtask getSubtask() {
        return subtask;
    }

    public void setSubtask(Subtask subtask) {
        this.subtask = subtask;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public DataFormat getSerialization() {
        return serialization;
    }

    public void setSerialization(DataFormat serialization) {
        this.serialization = serialization;
    }
}


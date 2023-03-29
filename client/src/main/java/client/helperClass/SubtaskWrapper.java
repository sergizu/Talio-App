package client.helperClass;

import commons.Subtask;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class SubtaskWrapper {

    private Subtask subtask;

    private CheckBox checkBox;

    private Button button;

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
}

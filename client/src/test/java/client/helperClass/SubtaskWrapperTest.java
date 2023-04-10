package client.helperClass;

import com.google.inject.Inject;
import commons.Subtask;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.DataFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubtaskWrapperTest {

    @Inject
    Subtask subtask;
    @Inject
    private CheckBox checkBox;
    @Inject
    private Button button;

    SubtaskWrapper subtaskWrapper;

    @BeforeEach
    void setUp() {
        subtaskWrapper = new SubtaskWrapper(subtask, checkBox, button);
    }

    @Test
    void getSubtask() {
        assertEquals(subtask, subtaskWrapper.getSubtask());
    }

    @Test
    void setSubtask() {
        Subtask subtask = new Subtask("Test1");
        subtaskWrapper.setSubtask(subtask);
        assertEquals(subtask, subtaskWrapper.getSubtask());
    }

    @Test
    void getCheckBox() {
        assertEquals(checkBox, subtaskWrapper.getCheckBox());
    }

    @Test
    void setCheckBox() {
        subtaskWrapper.setCheckBox(checkBox);
        assertEquals(checkBox, subtaskWrapper.getCheckBox());
    }

    @Test
    void getButton() {
        assertEquals(button, subtaskWrapper.getButton());
    }

    @Test
    void setButton() {
        subtaskWrapper.setButton(button);
        assertEquals(button, subtaskWrapper.getButton());
    }

    @Test
    void getSerialization() {
        assertNull(subtaskWrapper.getSerialization());
    }

    @Test
    void setSerialization() {
        subtaskWrapper.setSerialization(new DataFormat());
        assertEquals(new DataFormat(), subtaskWrapper.getSerialization());
    }
}
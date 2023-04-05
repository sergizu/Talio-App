package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BoardOptionsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField boardName;


    @Inject
    public BoardOptionsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void ok() {

    }

    public void cancel() {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }
}

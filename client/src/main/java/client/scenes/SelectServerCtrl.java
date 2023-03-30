package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.AppClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class SelectServerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final AppClient client;

    @FXML
    private TextField serverName;

    @FXML
    private Label myLabel;

    @Inject
    public SelectServerCtrl(ServerUtils server, MainCtrl mainCtrl, AppClient client) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.client = client;
    }

    public void ok() {
        String s = serverName.getText();
        if (serverName.getText().isEmpty()) {
            myLabel.setText("Can not be empty!");
            return;
        }
        myLabel.setText("");
        server.changeServer(s);
        try {
            server.getLists();
            mainCtrl.showJoinedBoards(client);
        } catch (Exception e) {
            myLabel.setText("Couldn't find the server!");
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
    }
}

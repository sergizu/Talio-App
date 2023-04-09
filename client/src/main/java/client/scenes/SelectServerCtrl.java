package client.scenes;

import client.scenes.interfaces.JoinedBoardsCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.concurrent.Executors;


public class SelectServerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ListOverviewCtrl listOverviewCtrl;
    private JoinedBoardsCtrl joinedBoardsCtrl;

    private final BoardOverviewCtrl boardOverviewCtrl;

    private final EditCardCtrl editCardCtrl;


    @FXML
    private TextField serverName;

    @FXML
    private Label myLabel;

    @FXML
    private TextField adminPass;

    @FXML
    private HBox hbox;

    @Inject
    public SelectServerCtrl(ServerUtils server, MainCtrl mainCtrl,
                            ListOverviewCtrl listOverviewCtrl,
                            JoinedBoardsCtrl joinedBoardsCtrl,
                            BoardOverviewCtrl boardOverviewCtrl,
                            EditCardCtrl editCardCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.listOverviewCtrl = listOverviewCtrl;
        this.boardOverviewCtrl = boardOverviewCtrl;
        this.editCardCtrl = editCardCtrl;
        this.joinedBoardsCtrl = joinedBoardsCtrl;

    }

    public boolean checkPass() {
        String password = adminPass.getText();
        return password.equals("1010");
    }

    public void ok() {
        String s = serverName.getText();
        if (serverName.getText().isEmpty()) {
            myLabel.setText("Can not be empty!");
            return;
        }
        myLabel.setText("");
        server.changeServer(s);
        if(server.serverRunning()){
            startSession();
            if (checkPass()) {
                mainCtrl.setAdmin(true);
                mainCtrl.showBoardOverview();
                adminPass.setText("");
                hbox.setVisible(false);
            } else {
                mainCtrl.showJoinedBoards();
                mainCtrl.setAdmin(false);
            }
        }
        else{
            myLabel.setText("Couldn't find the server!");
        }
    }

    public void adminLogIn() {
        hbox.setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
    }
    public void startSession(){
        server.setExecutorService(Executors.newCachedThreadPool());
        server.initSession();
        boardOverviewCtrl.registerForMessages();
        joinedBoardsCtrl.registerForMessages();
        listOverviewCtrl.init();
        editCardCtrl.registerForUpdates();
    }
}
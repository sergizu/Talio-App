package client.scenes.implementations;


import client.scenes.interfaces.*;
import client.services.interfaces.SelectServerService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.concurrent.Executors;

@Singleton
public class SelectServerCtrlImpl implements SelectServerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ListOverviewCtrl listOverviewCtrl;
    private final JoinedBoardsCtrl joinedBoardsCtrl;

    private final BoardOverviewCtrl boardOverviewCtrl;

    private final EditCardCtrl editCardCtrl;
    private final SelectServerService selectServerService;

    @Inject
    public SelectServerCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                                ListOverviewCtrl listOverviewCtrl,
                                JoinedBoardsCtrl joinedBoardsCtrl,
                                BoardOverviewCtrl boardOverviewCtrl,
                                EditCardCtrl editCardCtrl,
                                SelectServerService selectServerService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.listOverviewCtrl = listOverviewCtrl;
        this.boardOverviewCtrl = boardOverviewCtrl;
        this.editCardCtrl = editCardCtrl;
        this.joinedBoardsCtrl = joinedBoardsCtrl;

        this.selectServerService = selectServerService;
    }

    public boolean checkPass() {
        String password = selectServerService.getAdminPassText();
        return password.equals("1010");
    }

    public void ok() {
        String s = selectServerService.getServerNameText();
        if (s.isEmpty()) {
            selectServerService.setMyLabel("Can not be empty!");
            return;
        }
        selectServerService.setMyLabel("");
        server.changeServer(s);
        if(server.serverRunning()){
            startSession();
            if(!selectServerService.getVisible()) {
                mainCtrl.showJoinedBoards();
            } else if (checkPass()) {
                mainCtrl.setAdmin(true);
                mainCtrl.showBoardOverview();
                selectServerService.setAdminPassText("");
                selectServerService.setBoxVisible(false);
            } else {
                mainCtrl.setAdmin(false);
                selectServerService.setMyLabel("Password is incorrect!");
            }
        } else {
            selectServerService.setMyLabel("Couldn't find the server!");
        }
    }

    public void adminLogIn() {
        selectServerService.setBoxVisible(!selectServerService.getVisible());
        if(!selectServerService.getVisible()) {
            selectServerService.setChoiceButton("Admin log in");
        } else {
            selectServerService.setChoiceButton("User log in");
        }
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

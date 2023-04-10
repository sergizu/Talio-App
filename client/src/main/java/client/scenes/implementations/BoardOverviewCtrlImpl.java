package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.BoardOverviewCtrl;
import client.services.interfaces.BoardOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Board;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class BoardOverviewCtrlImpl implements BoardOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final BoardOverviewService boardOverviewService;

    @Inject
    public BoardOverviewCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                                 BoardOverviewService boardOverviewService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.boardOverviewService = boardOverviewService;
    }

    public void registerForMessages() {
        registerForBoardRename();
        registerForBoardDeletion();
        registerForBoardCreation();
    }

    public void registerForBoardRename() {
        server.registerForMessages("/topic/renameBoard", Board.class, renamedBoard -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    boardOverviewService.showAllBoards();
            });
        });
    }

    public void registerForBoardDeletion() {
        server.registerForMessages("/topic/boardDeletion", Long.class, deletedBoardId -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    boardOverviewService.showAllBoards();
            });
        });
    }

    public void registerForBoardCreation() {
        server.registerForMessages("/topic/boardCreation", Long.class, newBoardId -> {
            Platform.runLater(() -> {
                if(mainCtrl.getPrimaryStageTitle().equals("Boards: Overview"))
                    boardOverviewService.showAllBoards();
            });
        });
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            showCreateBoard();
        }
    }

    public void joinByKey() {
        long key = -1;
        try {
            key = Long.parseLong(boardOverviewService.getJoinByKeyText());
        } catch (NumberFormatException e) {
            boardOverviewService.adjustPromptText("Please enter a valid key!");
            return;
        }
        if (!lookForBoardKey(key))
            boardOverviewService.adjustPromptText("No board with that key!");
    }

    public boolean lookForBoardKey(long key) {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                enterBoard(board);
                boardOverviewService.clearJoinByKey();
                return true;
            }
        return false;
    }

    public void enterBoard(Board board) {
        mainCtrl.showOverview(board.id);
    }

    public void joinPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            joinByKey();
    }
    public void showCreateBoard() {
        mainCtrl.showCreateBoard();
    }
    public void disconnectPressed() {
        mainCtrl.showSelectServer();
        server.stop();
    }

    public void showBoardOptions(Board board) {
        mainCtrl.showBoardOptions(board);
    }

    public List<Board> getBoards() {
        return server.getBoards();
    }

    public void showAllBoards() {
        boardOverviewService.showAllBoards();
    }
}
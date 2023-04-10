package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.BoardOverviewCtrl;
import client.services.interfaces.BoardOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class BoardOverviewCtrlImpl implements BoardOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final BoardOverviewService boardOverviewService;

    @Inject
    public BoardOverviewCtrlImpl(ServerUtils server, MainCtrl mainCtrl, BoardOverviewService boardOverviewService) {
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
            adjustPromptText("Please enter a valid key!");
            return;
        }
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                enterBoard(board);
                boardOverviewService.clearJoinByKey();
                return;
            }
        if (!lookForBoardKey(key))
            adjustPromptText("No board with that key!");
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

    public void adjustPromptText(String information) {
        Platform.runLater(() ->
        {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        boardOverviewService.clearJoinByKey();
                        boardOverviewService.setJoinByKeyPrompt(information);
                    }),
                    new KeyFrame(Duration.seconds(5), event -> {
                        boardOverviewService.setJoinByKeyPrompt("Join by key");
                    })
            );
            timeline.play();
        });
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

    }

    public List<Board> getBoards() {
        return server.getBoards();
    }

    public void showAllBoards() {
        boardOverviewService.showAllBoards();
    }
}

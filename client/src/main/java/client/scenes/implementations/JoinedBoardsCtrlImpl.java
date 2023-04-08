package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.JoinedBoardsCtrl;
import client.services.interfaces.JoinedBoardsService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.AppClient;
import commons.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;

@Singleton
public class JoinedBoardsCtrlImpl implements JoinedBoardsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final JoinedBoardsService service;

    private AppClient client;

    @Inject
    public JoinedBoardsCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                                JoinedBoardsService service) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.service = service;
    }

    public void registerForBoardRename() {
        server.registerForMessages("/topic/renameBoard", Board.class, renamedBoard -> {
            Platform.runLater(() -> {
                if (mainCtrl.getPrimaryStageTitle().equals("Your boards"))
                    updateBoard(renamedBoard);
            });
        });
    }

    public void registerForBoardDeletion() {
        server.registerForMessages("/topic/boardDeletion", Long.class, deletedBoardId -> {
            Platform.runLater(() -> {
                removeBoardById(deletedBoardId);
                if (mainCtrl.getPrimaryStageTitle().equals("Your boards"))
                    service.showJoinedBoards(client.boards.get(ServerUtils.getServer()));
            });
        });
    }

    public void registerForMessages() {
        registerForBoardRename();
        registerForBoardDeletion();
    }
    public void init() {
        this.client = mainCtrl.getClient();
        service.setJoinByKeyPrompt("Join by key");
        String serverString = ServerUtils.getServer();
        addServerKeyIntoMap(serverString);
        getBoardsForServer(serverString);
    }

    public void addServerKeyIntoMap(String serverString) {
        if (!client.boards.containsKey(serverString)) {
            ArrayList<Board> boards = new ArrayList<>();
            client.boards.put(serverString, boards);
        }
    }

    public void getBoardsForServer(String serverString) {
        ArrayList<Board> boards = new ArrayList<>();
        if (client.boards.containsKey(serverString)) {
            boards = client.boards.get(serverString);
        }
        service.showJoinedBoards(boards);
    }

    public void showCreateBoard() {
        mainCtrl.showCreateBoard();
    }

    public void leaveBoard(Board board) {
        ArrayList<Board> boards = client.boards.get(ServerUtils.getServer());
        boards.remove(board);
        client.boards.put(ServerUtils.getServer(), boards);
        service.showJoinedBoards(boards);
    }

    public void showOptions(Board board) {
        mainCtrl.showBoardOptions(board);
    }

    public void enterBoard(Board board) {
        mainCtrl.showOverview(board.id);
    }

    public void disconnectPressed() {
        mainCtrl.showSelectServer();
        server.stopSession();
    }

    public void joinByKey() {
        long key = -1;
        try {
            key = Long.parseLong(service.getJoinByKeyText());
        } catch (NumberFormatException e) {
            adjustPromptText("Please enter a valid key!");
            return;
        }
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                joinBoard(board);
                service.clearJoinByKey();
                return;
            }
        if (!lookForBoardKey(key))
            adjustPromptText("No board with that key!");
    }

    public boolean lookForBoardKey(long key) {
        ArrayList<Board> allBoards = (ArrayList<Board>) server.getBoards();
        for (Board board : allBoards)
            if (board.key == key) {
                joinBoard(board);
                service.clearJoinByKey();
                return true;
            }
        return false;
    }

    public void adjustPromptText(String information) {
        Platform.runLater(() ->
        {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        service.clearJoinByKey();
                        service.setJoinByKeyPrompt(information);
                    }),
                    new KeyFrame(Duration.seconds(5), event -> {
                        service.setJoinByKeyPrompt("Join by key");
                    })
            );
            timeline.play();
        });
    }

    public void joinBoard(Board board) {
        containsBoardId(board);
        enterBoard(board);
    }

    public void joinPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            joinByKey();
    }

    public boolean containsBoardId(Board newBoard) {
        ArrayList<Board> clientBoards = client.boards.get(ServerUtils.getServer());
        if(clientBoards == null)
            clientBoards = new ArrayList<>();
        for (Board board : clientBoards)
            if (board.id == newBoard.id)
                return true;
        clientBoards.add(newBoard);
        client.boards.put(ServerUtils.getServer(), clientBoards);
        return false;
    }

    public void updateBoard(Board board) {
        ArrayList<Board> allBoards = client.boards.get(ServerUtils.getServer());
        for (int i = 0; i < allBoards.size(); i++)
            if (allBoards.get(i).id == board.id) {
                allBoards.get(i).title = board.title;
                break;
            }
        client.boards.put(ServerUtils.getServer(), allBoards);
        service.showJoinedBoards(allBoards);
    }

    public void removeBoardById(long boardId) {
        if (client == null)
            client = mainCtrl.getClient();
        ArrayList<Board> allBoards = client.boards.get(ServerUtils.getServer());
        if (allBoards != null)
            for (int i = 0; i < allBoards.size(); i++)
                if (allBoards.get(i).id == boardId) {
                    allBoards.remove(i);
                    break;
                }
        client.boards.put(ServerUtils.getServer(), allBoards);
    }
}

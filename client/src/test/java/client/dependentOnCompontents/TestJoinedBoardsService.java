package client.dependentOnCompontents;

import client.services.interfaces.JoinedBoardsService;
import commons.Board;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestJoinedBoardsService implements JoinedBoardsService {

    public ArrayList<String> calls;

    public String toReturn;

    public TestJoinedBoardsService() {
        calls = new ArrayList<>();
    }
    @Override
    public void showJoinedBoards(ArrayList<Board> boards) {
        calls.add("showJoinedBoards");
    }

    @Override
    public void displayBoards(ArrayList<Board> boards) {
        calls.add("displayBoards");
    }

    @Override
    public void setJoinByKeyPrompt(String text) {
        calls.add("setPrompt");
    }

    @Override
    public String getJoinByKeyText() {
        calls.add("getJoin");
        return toReturn;
    }

    @Override
    public void clearJoinByKey() {
        calls.add("clearJoin");
    }

    @Override
    public void joinPressed(KeyEvent e) {
        calls.add("joinPressed");
    }

    @Override
    public void disconnectPressed() {
        calls.add("disconnectPressed");
    }

    @Override
    public void showCreateBoard() {
        calls.add("showCreateBoard");
    }

    @Override
    public void joinByKey() {
        calls.add("joinByKey");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calls.add("initialize");
    }
}

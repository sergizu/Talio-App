package client.dependentOnCompontents;

import client.services.interfaces.BoardOptionsService;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class TestBoardOptionsService implements BoardOptionsService {

    public ArrayList<String> calls;
    public String boardName;

    public TestBoardOptionsService() {
        calls = new ArrayList<>();
    }

    @Override
    public void initButtons() {
        calls.add("init");
    }

    @Override
    public void setBoardName(String text) {
        calls.add("setName");
    }

    @Override
    public String getBoardName() {
        calls.add("getName");
        return boardName;
    }

    public void ok() {
        calls.add("ok");
    }
    public void delete() {
        calls.add("delete");
    }
    public void cancel() {
        calls.add("cancel");
    }
    public void keyPressed(KeyEvent e) {
        calls.add("keyPressed");
    }
}

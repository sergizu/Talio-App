package client.dependentOnCompontents;

import client.services.BoardOptionsService;

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
}

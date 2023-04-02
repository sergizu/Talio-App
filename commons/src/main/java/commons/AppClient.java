package commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AppClient {

    public int id;

    public HashMap<String, ArrayList<Board>> boards;
    public AppClient() {
        Random randomGenerator = new Random();
        id = Math.abs(randomGenerator.nextInt());
        boards = new HashMap<>();
    }
}

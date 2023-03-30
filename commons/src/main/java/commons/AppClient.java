package commons;

import java.util.Random;

public class AppClient {

    public int id;
    public AppClient() {
        Random randomGenerator = new Random();
        id = Math.abs(randomGenerator.nextInt());
    }
}

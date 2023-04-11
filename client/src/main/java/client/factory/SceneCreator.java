package client.factory;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneCreator {
    private final int width = 1080;
    private final int heigth = 720;
    public Scene createScene(Parent parent) {
        return new Scene(parent, width, heigth);
    }
}

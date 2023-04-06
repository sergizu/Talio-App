package client.factory;

import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class SceneFactoryTest {
    private final SceneFactory sceneFactory = new SceneFactory();

    @Test
    void getInjector() {
        assertNotNull(sceneFactory.getInjector());
    }

    @Test
    void createAddSubtaskScene() {
        assertNotNull(sceneFactory.createAddSubtaskScene());
    }

    @Test
    void createAddCardScene() {
        assertNotNull(sceneFactory.createAddCardScene());
    }

    @Test
    void createAddListScene() {
        assertNotNull(sceneFactory.createAddListScene());
    }

}
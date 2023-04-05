package client.factory;

import client.scenes.ListOverviewCtrl;
import javafx.scene.Parent;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class SceneFactoryTest {
    private final SceneFactory sceneFactory = new SceneFactory();

    @Test
    void getInjector() {
        assertNotNull(sceneFactory.getInjector());
    }

    @Test
    public void testCreateListOverviewScene() {
        Pair<ListOverviewCtrl, Parent> pair = sceneFactory.createListOverviewScene();
        assertNotNull(pair);
        assertNotNull(pair.getKey()) ;
        assertNotNull(pair.getValue());
    }

    @Test
    void createEditCardScene() {
        assertNotNull(sceneFactory.createEditCardScene());
    }

    @Test
    void createSelectServerScene() {
        assertNotNull(sceneFactory.createSelectServerScene());
    }

    @Test
    void createBoardOverviewScene() {
        assertNotNull(sceneFactory.createBoardOverviewScene());
    }

    @Test
    void createJoinedBoardsScene() {
        assertNotNull(sceneFactory.createJoinedBoardsScene());
    }

    @Test
    void createNewBoardScene() {
        assertNotNull(sceneFactory.createNewBoardScene());
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

    @Test
    void createEditListScene() {
        assertNotNull(sceneFactory.createEditListScene());
    }
}
package client.factory;

import client.MyFXML;
import com.google.inject.Injector;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SceneFactoryTest {
    @Mock
    Injector injector;
    @Mock
    MyFXML fxml;

    @Test
    public void testConstructor() {
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertEquals(injector, sceneFactory.getInjector());
    }

    @Test
    public void testCreateListOverviewScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createListOverviewScene());
    }

    @Test
    public void testCreateEditCardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createEditCardScene());
    }

    @Test
    public void testCreateSelectServerScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createSelectServerScene());
    }

    @Test
    public void testCreateBoardOverviewScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createBoardOverviewScene());
    }

    @Test
    public void testCreateJoinedBoardsScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createJoinedBoardsScene());
    }

    @Test
    public void testCreateNewBoardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createNewBoardScene());
    }

    @Test
    public void testCreateAddSubtaskScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createAddSubtaskScene());
    }

    @Test
    public void testCreateAddCardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createAddCardScene());
    }

    @Test
    public void testCreateAddListScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createAddListScene());
    }

    @Test
    public void testCreateEditListScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createEditListScene());
    }

    @Test
    public void testCreateBoardOptionsScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, null));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml);
        assertNull(sceneFactory.createBoardOptionsScene());
    }
}
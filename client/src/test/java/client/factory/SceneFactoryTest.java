package client.factory;

import client.MyFXML;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SceneFactoryTest {
    @Mock
    Injector injector;
    @Mock
    MyFXML fxml;
    @Mock
    Parent parent;
    @Mock
    SceneCreator sceneCreator;

    @Test
    public void testConstructor() {
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        assertEquals(injector, sceneFactory.getInjector());
    }

    @Test
    public void testCreateListOverviewScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createListOverviewScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateEditCardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createEditCardScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateSelectServerScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createSelectServerScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateBoardOverviewScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createBoardOverviewScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateJoinedBoardsScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createJoinedBoardsScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateNewBoardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createNewBoardScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateAddSubtaskScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createAddSubtaskScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateAddCardScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createAddCardScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateAddListScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createAddListScene();
        verify(sceneCreator).createScene(parent);
    }

    @Test
    public void testCreateEditListScene() {
        given(fxml.load(any(), any(), any(), any())).willReturn(new Pair<>(null, parent));
        SceneFactory sceneFactory = new SceneFactory(injector, fxml, sceneCreator);
        sceneFactory.createEditListScene();
        verify(sceneCreator).createScene(parent);
    }
}
package client.factory;

import client.MyFXML;
import client.scenes.EditListCtrl;
import client.services.interfaces.*;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneFactory {
    private final Injector injector;
    private final MyFXML fxml;

    private final SceneCreator sceneCreator;

    private final int width = 1080;
    private final int heigth = 720;

    public SceneFactory(Injector injector, MyFXML fxml, SceneCreator sceneCreator) {
        this.injector = injector;
        this.fxml = fxml;
        this.sceneCreator = sceneCreator;
    }


    public Injector getInjector(){
        return injector;
    }

    public Scene createListOverviewScene(){
        Parent parent = fxml.load(ListOverviewService.class,
                "client", "scenes", "ListOverview.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createEditCardScene(){
        Parent parent = fxml.load(EditCardService.class, "client",
                "scenes", "EditCard.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createSelectServerScene(){
        Parent parent = fxml.load(SelectServerService.class,"client","scenes",
                "SelectServer.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createBoardOverviewScene(){
        Parent parent = fxml.load(BoardOverviewService.class,
                "client", "scenes", "BoardOverview.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createJoinedBoardsScene(){
        Parent parent = fxml.load(JoinedBoardsService.class,
                "client", "scenes", "JoinedBoards.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createNewBoardScene(){
        Parent parent =  fxml.load(CreateBoardService.class,
                "client", "scenes", "CreateBoard.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createAddSubtaskScene(){
        Parent parent = fxml.load(AddSubTaskService.class, "client",
                "scenes", "AddSubtask.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createAddCardScene(){
        Parent parent = fxml.load(AddCardService.class, "client",
                "scenes", "AddCard.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createAddListScene(){
        Parent parent = fxml.load(AddListService.class, "client",
                "scenes", "AddList.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createEditListScene(){
        Parent parent = fxml.load(EditListCtrl.class, "client",
                "scenes", "RenameList.fxml").getValue();
        return sceneCreator.createScene(parent);
    }

    public Scene createBoardOptionsScene() {
        Parent parent =  fxml.load(BoardOptionsService.class, "client",
                "scenes", "BoardOptions.fxml").getValue();
        return sceneCreator.createScene(parent);
    }
}

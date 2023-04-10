package client.factory;

import client.MyFXML;
import client.scenes.EditListCtrl;
import client.scenes.interfaces.BoardOverviewCtrl;
import client.services.interfaces.*;
import com.google.inject.Injector;
import javafx.scene.Parent;

public class SceneFactory {
    private final Injector injector;
    private final MyFXML fxml;

    public SceneFactory(Injector injector, MyFXML fxml) {
        this.injector = injector;
        this.fxml = fxml;
    }


    public Injector getInjector(){
        return injector;
    }

    public Parent createListOverviewScene(){
        return fxml.load(ListOverviewService.class,
                "client", "scenes", "ListOverview.fxml").getValue();
    }

    public Parent createEditCardScene(){
        return fxml.load(EditCardService.class, "client", "scenes", "EditCard.fxml").getValue();
    }

    public Parent createSelectServerScene(){
        return fxml.load(SelectServerService.class,
                "client","scenes", "SelectServer.fxml").getValue();
    }

    public Parent createBoardOverviewScene(){
        return fxml.load(BoardOverviewCtrl.class,
                "client", "scenes", "BoardOverview.fxml").getValue();
    }

    public Parent createJoinedBoardsScene(){
        return fxml.load(JoinedBoardsService.class,
                "client", "scenes", "JoinedBoards.fxml").getValue();
    }

    public Parent createNewBoardScene(){
        return fxml.load(CreateBoardService.class,
                "client", "scenes", "CreateBoard.fxml").getValue();
    }

    public Parent createAddSubtaskScene(){
        return fxml.load(AddSubTaskService.class, "client", "scenes", "AddSubtask.fxml").getValue();
    }

    public Parent createAddCardScene(){
        return fxml.load(AddCardService.class, "client", "scenes", "AddCard.fxml").getValue();
    }

    public Parent createAddListScene(){
        return fxml.load(AddListService.class, "client", "scenes", "AddList.fxml").getValue();
    }

    public Parent createEditListScene(){
        return fxml.load(EditListCtrl.class, "client", "scenes", "RenameList.fxml").getValue();
    }

    public Parent createBoardOptionsScene() {
        return fxml.load(BoardOptionsService.class, "client",
                "scenes", "BoardOptions.fxml").getValue();
    }
}

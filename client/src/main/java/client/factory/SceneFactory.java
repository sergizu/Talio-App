package client.factory;

import client.MyFXML;
import client.scenes.*;
import client.scenes.implementations.BoardOptionsCtrlImpl;
import client.services.interfaces.*;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneFactory {
    private final Injector injector;
    private final MyFXML fxml;

    private final int WIDTH = 1080;
    private final int HEIGHT = 720;

    public SceneFactory(Injector injector, MyFXML fxml) {
        this.injector = injector;
        this.fxml = fxml;
    }


    public Injector getInjector(){
        return injector;
    }

    public Scene createListOverviewScene(){
        Parent parent = fxml.load(ListOverviewCtrl.class,
                "client", "scenes", "ListOverview.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createEditCardScene(){
        Parent parent = fxml.load(EditCardCtrl.class, "client", "scenes", "EditCard.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createSelectServerScene(){
        Parent parent = fxml.load(SelectServerCtrl.class,"client","scenes", "SelectServer.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createBoardOverviewScene(){
        Parent parent = fxml.load(BoardOverviewCtrl.class,
                "client", "scenes", "BoardOverview.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createJoinedBoardsScene(){
        Parent parent = fxml.load(JoinedBoardsService.class,
                "client", "scenes", "JoinedBoards.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createNewBoardScene(){
        Parent parent =  fxml.load(CreateBoardService.class,
                "client", "scenes", "CreateBoard.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createAddSubtaskScene(){
        Parent parent = fxml.load(AddSubTaskService.class, "client", "scenes", "AddSubtask.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createAddCardScene(){
        Parent parent = fxml.load(AddCardService.class, "client", "scenes", "AddCard.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createAddListScene(){
        Parent parent = fxml.load(AddListService.class, "client", "scenes", "AddList.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createEditListScene(){
        Parent parent = fxml.load(EditListCtrl.class, "client", "scenes", "RenameList.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }

    public Scene createBoardOptionsScene() {
        Parent parent =  fxml.load(BoardOptionsCtrlImpl.class, "client",
                "scenes", "BoardOptions.fxml").getValue();
        return new Scene(parent, WIDTH, HEIGHT);
    }
}

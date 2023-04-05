package client.factory;

import client.MyFXML;
import client.MyModule;
import client.scenes.*;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.util.Pair;

import static com.google.inject.Guice.createInjector;

public class SceneFactory {
    private final Injector injector = createInjector(new MyModule());
    private final MyFXML fxml = new MyFXML(injector);

    public Injector getInjector(){
        return injector;
    }

    public Pair<ListOverviewCtrl, Parent> createListOverviewScene(){
        return fxml.load(ListOverviewCtrl.class, "client", "scenes", "ListOverview.fxml");
    }

    public Pair<EditCardCtrl, Parent> createEditCardScene(){
        return fxml.load(EditCardCtrl.class, "client", "scenes", "EditCard.fxml");
    }

    public Pair<SelectServerCtrl, Parent> createSelectServerScene(){
        return fxml.load(SelectServerCtrl.class,"client","scenes", "SelectServer.fxml");
    }

    public Pair<BoardOverviewCtrl, Parent> createBoardOverviewScene(){
        return fxml.load(BoardOverviewCtrl.class,"client", "scenes", "BoardOverview.fxml");
    }

    public Pair<JoinedBoardsCtrl, Parent> createJoinedBoardsScene(){
        return fxml.load(JoinedBoardsCtrl.class, "client", "scenes", "JoinedBoards.fxml");
    }

    public Pair<CreateBoardCtrl, Parent> createNewBoardScene(){
        return fxml.load(CreateBoardCtrl.class, "client", "scenes", "CreateBoard.fxml");
    }

    public Pair<AddSubTaskCtrl, Parent> createAddSubtaskScene(){
        return fxml.load(AddSubTaskCtrl.class, "client", "scenes", "AddSubtask.fxml");
    }

    public Pair<AddCardCtrl, Parent> createAddCardScene(){
        return fxml.load(AddCardCtrl.class, "client", "scenes", "AddCard.fxml");
    }

    public Pair<AddListCtrl, Parent> createAddListScene(){
        return fxml.load(AddListCtrl.class, "client", "scenes", "AddList.fxml");
    }

    public Pair<EditListCtrl, Parent> createEditListScene(){
        return fxml.load(EditListCtrl.class, "client", "scenes", "RenameList.fxml");
    }
}

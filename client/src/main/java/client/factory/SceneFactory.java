package client.factory;

import client.MyFXML;
import client.MyModule;
import client.scenes.*;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.util.Pair;

import static com.google.inject.Guice.createInjector;

public class SceneFactory {
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public Injector getInjector(){
        return INJECTOR;
    }

    public Pair<ListOverviewCtrl, Parent> createListOverviewScene(){
        return FXML.load(ListOverviewCtrl.class, "client", "scenes", "ListOverview.fxml");
    }

    public Pair<EditCardCtrl, Parent> createEditCardScene(){
        return FXML.load(EditCardCtrl.class, "client", "scenes", "EditCard.fxml");
    }

    public Pair<SelectServerCtrl, Parent> createSelectServerScene(){
        return FXML.load(SelectServerCtrl.class,"client","scenes", "SelectServer.fxml");
    }

    public Pair<BoardOverviewCtrl, Parent> createBoardOverviewScene(){
        return FXML.load(BoardOverviewCtrl.class,"client", "scenes", "BoardOverview.fxml");
    }

    public Pair<JoinedBoardsCtrl, Parent> createJoinedBoardsScene(){
        return FXML.load(JoinedBoardsCtrl.class, "client", "scenes", "JoinedBoards.fxml");
    }

    public Pair<CreateBoardCtrl, Parent> createNewBoardScene(){
        return FXML.load(CreateBoardCtrl.class, "client", "scenes", "CreateBoard.fxml");
    }

    public Pair<AddSubTaskCtrl, Parent> createAddSubtaskScene(){
        return FXML.load(AddSubTaskCtrl.class, "client", "scenes", "AddSubtask.fxml");
    }

    public Pair<AddCardCtrl, Parent> createAddCardScene(){
        return FXML.load(AddCardCtrl.class, "client", "scenes", "AddCard.fxml");
    }

    public Pair<AddListCtrl, Parent> createAddListScene(){
        return FXML.load(AddListCtrl.class, "client", "scenes", "AddList.fxml");
    }

    public Pair<EditListCtrl, Parent> createEditListScene(){
        return FXML.load(EditListCtrl.class, "client", "scenes", "RenameList.fxml");
    }
}

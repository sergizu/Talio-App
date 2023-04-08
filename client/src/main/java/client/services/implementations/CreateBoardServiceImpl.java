package client.services.implementations;

import client.scenes.interfaces.CreateBoardCtrl;
import client.services.interfaces.CreateBoardService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Singleton
public class CreateBoardServiceImpl implements CreateBoardService {
    @FXML
    TextField boardTitle;

    private final CreateBoardCtrl createBoardCtrl;

    @Inject
    public CreateBoardServiceImpl(CreateBoardCtrl createBoardCtrl) {
        this.createBoardCtrl = createBoardCtrl;
    }

    @Override
    public void setBoardName(String s) {
        boardTitle.setText(s);
    }

    @Override
    public String getBoardName() {
        return boardTitle.getText();
    }


    @Override
    public void createBoard() {
        createBoardCtrl.createBoard();
    }

    @Override
    public void escape() {
        createBoardCtrl.escape();
    }
}

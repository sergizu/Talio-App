package client.services;

import client.scenes.BoardOptionsCtrl;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

@Singleton
public class BoardOptionsServiceImpl implements BoardOptionsService{

    @FXML
    private TextField boardName;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label emptyName;

    private final BoardOptionsCtrl boardOptionsCtrl;

    @Inject
    public BoardOptionsServiceImpl(BoardOptionsCtrl boardOptionsCtrl) {
        this.boardOptionsCtrl = boardOptionsCtrl;
    }

    @Override
    public void setBoardName(String text) {
        boardName.setText(text);
    }

    public String getBoardName() {
        return boardName.getText();
    }

    public void initButtons(){
        deleteButton.setStyle("-fx-background-color: red;");
        saveButton.setStyle("-fx-background-color: #2596be");
    }

    public void ok() {
        boardOptionsCtrl.ok();
    }
    public void cancel() {
        boardOptionsCtrl.cancel();
    }
    public void keyPressed(KeyEvent e) {
        boardOptionsCtrl.keyPressed(e);
    }
    public void delete() {
        boardOptionsCtrl.delete();
    }

}

package client.services.interfaces;

import client.services.implementations.BoardOverviewServiceImpl;
import com.google.inject.ImplementedBy;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

@ImplementedBy(BoardOverviewServiceImpl.class)
public interface BoardOverviewService extends Initializable {

    void createBoard();
    void joinPressed(KeyEvent event);
    void disconnectPressed();
    void joinByKey();
    void showAllBoards();
    String getJoinByKeyText();
    void clearJoinByKey();
    void setJoinByKeyPrompt(String text);
    void adjustPromptText(String text);
}
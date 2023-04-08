package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.AddCardCtrl;
import client.services.interfaces.AddCardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Card;
import commons.CardListId;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


@Singleton
public class AddCardCtrlImpl implements AddCardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final AddCardService addCardService;
    private long listId;

    private long boardId;

    @Inject
    public AddCardCtrlImpl(ServerUtils server, MainCtrl mainCtrl, AddCardService addCardService) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.addCardService = addCardService;
    }

    public void setListBoardId(long listId,long boardId) {
        this.listId = listId;
        this.boardId = boardId;
    }

    public void ok() {
        if(addCardService.getCardName().isEmpty()) {
            addCardService.setMyLabelText("Card name cannot be empty!");
            return;
        }
        Card toSend = new Card(addCardService.getCardName(), addCardService.getDescription());
        server.addCardToList(listId,toSend);
        server.send("/app/tdLists/addCard", new CardListId(toSend,listId));
        mainCtrl.showOverview(boardId);
        clearFields();
    }

    private void clearFields() {
        addCardService.clearFields();
    }

    public void cancel() {
        addCardService.clearFields();
        mainCtrl.showOverview(boardId);
    }

    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER)
            ok();
        else if(e.getCode() == KeyCode.ESCAPE)
            cancel();
    }

    public long getListId() {
        return listId;
    }

    public long getBoardId() {
        return boardId;
    }
}

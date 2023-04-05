package client.scenes;

import client.services.AddSubTaskService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Card;
import commons.Subtask;

@Singleton
public class AddSubTaskCtrlImpl implements AddSubTaskCtrl{

    private final ServerUtils server;

    private final MainCtrl mainCtrl;
    private final AddSubTaskService addSubTaskService;

    private Card card;

    @Inject
    public AddSubTaskCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                              AddSubTaskService addSubTaskService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.addSubTaskService = addSubTaskService;
    }

    public void init(Card card) {
        this.card = card;
    }

    public void create() {
        if(addSubTaskService.getSubtaskName().isEmpty()) {
            addSubTaskService.setMyLabelText("Name cannot be empty!");
        } else {
            String name = addSubTaskService.getSubtaskName();
            addSubTaskService.setSubtaskName("");
            Subtask subtask = new Subtask(name);
            card.addSubTask(subtask);
            server.updateNestedList(card.id, card.nestedList);
            mainCtrl.showEdit(card);
        }
    }

    public void cancel() {
        addSubTaskService.setMyLabelText("");
        mainCtrl.showOverview(card.list.board.id);
    }

    public Card getCard() {
        return card;
    }
}
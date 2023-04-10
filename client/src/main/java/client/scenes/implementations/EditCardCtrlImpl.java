package client.scenes.implementations;

import client.helperClass.SubtaskWrapper;
import client.scenes.MainCtrl;
import client.scenes.interfaces.EditCardCtrl;
import client.services.interfaces.EditCardService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EditCardCtrlImpl implements EditCardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final SubtaskWrapper subtaskWrapper;

    private final EditCardService service;

    private Card card;

    @Inject
    public EditCardCtrlImpl(ServerUtils server, MainCtrl mainCtrl,
                            SubtaskWrapper subtaskWrapper, EditCardService service) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.subtaskWrapper = subtaskWrapper;
        this.service = service;
    }

    public void init(Card card) {
        this.card = card;
        service.setCardName(card.getTitle());
        service.setDescription(card.getDescription());
        service.initTableView(service.initSubtask(card.getNestedList()));
    }

    public void ok() {
        if (service.getCardName().equals(card.title) && (service.getDescription() == null
                || service.getDescription().equals(card.description))) {
            mainCtrl.showOverview(card.getList().getBoard().getId());
            return;
        } else if (service.getCardName().equals("")) {
            service.setEmptyName("Card name can not be empty!");
            return;
        }
        service.setEmptyName(" ");
        server.updateCardName(card.getId(), service.getCardName());
        if (service.getDescription().isEmpty() || service.getDescription() == null) {
            server.updateCardDescription(card.getId(), " ");
        } else {
            server.updateCardDescription(card.getId(), service.getDescription());
        }
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void delete() {
        long boardId = card.getList().getBoard().getId();
        server.removeCard(card);
        service.setEmptyName("");
        mainCtrl.showOverview(boardId);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ok();
        }
        if (e.getCode() == KeyCode.ESCAPE) {
            cancel();
        }
    }

    public void cancel() {
        mainCtrl.showOverview(card.getList().getBoard().getId());
    }

    public void createSubtask() {
        mainCtrl.showAddSubtask(card);
    }

    public void registerForUpdates() {
        server.registerForCardUpdates(updatedCardID -> Platform.runLater(() -> {
            if (card!=null && card.getId() == updatedCardID) {
                TDList tdList = card.getList();
                Card boardReference = card;
                try {
                    card = server.getCardById(card.id);
                    card.setList(tdList);
                } catch (Exception e) {
                    mainCtrl.showOverview(boardReference.getList().getBoard().getId());
                }

                if (mainCtrl.getPrimaryStageTitle().equals("Card: Edit Card")) {

                    mainCtrl.showEdit(card);
                }
            }
        }));
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void updateNestedList(ArrayList<Subtask> nestedList) {
        server.updateNestedList(card.getId(), nestedList);
    }

    public void showEdit() {
        mainCtrl.showEdit(card);
    }
}

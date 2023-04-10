package client.scenes.implementations;

import client.scenes.MainCtrl;
import client.scenes.interfaces.ListOverviewCtrl;
import client.services.interfaces.ListOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Board;
import commons.Card;
import commons.CardListId;
import commons.TDList;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Singleton
public class ListOverviewCtrlImp implements ListOverviewCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ListOverviewService listOverviewService;

    private Board board;

    @Inject
    public ListOverviewCtrlImp(ServerUtils server, MainCtrl mainCtrl, ListOverviewService listOverviewService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.listOverviewService = listOverviewService;
    }

    public void init(){
        listOverviewService.setScrollPane();
        board = new Board("");
        ///added this because I was getting a NullPtrException in register for updates
        registerForUpdates();
    }

    public void refresh(long boardId){
        board = server.getBoardById(boardId);
        listOverviewService.setBoardTitle(board.title);
        listOverviewService.showLists(board.tdLists);
    }

    public void setBoard(long boardId) {
        board = server.getBoardById(boardId);
        refresh(boardId);
    }

    public void updateList(TDList tdList, ArrayList<Card> items) {
        tdList.cards.clear();
        tdList.cards.addAll(items);
        var ids = tdList.cards.stream().map(Card::getId).sorted().collect(Collectors.toList());
        for (int i = 0; i < ids.size(); i++) {
            tdList.cards.get(i).setId(ids.get(i)); // changing the ids of the cards to store
            // them in a different order in the database
        }
        server.updateBoard(board);
    }

    public long getBoardKey(){
        return board.key;
    }
    public long getBoardId(){
        return board.id;
    }

    public void registerForUpdates() {
        server.registerForBoardUpdates(updatedBoardID -> {
            Platform.runLater(() -> {
                if (board.getId() == updatedBoardID) {
                    setBoard(updatedBoardID);
                }
            });
        });
        server.registerForMessages("/topic/addCard", CardListId.class, c -> {
            Platform.runLater(() -> {
                addCardToList(c.card, c.listId);
                setBoard(c.boardId);
            });
        });
        server.registerForMessages("/topic/boardDeletion", Long.class, deletedBoardId -> {
            Platform.runLater(() -> {
                if (deletedBoardId == board.id &&
                        mainCtrl.getPrimaryStageTitle().equals("Lists: Overview")) {
                    if(mainCtrl.getAdmin())
                        mainCtrl.showBoardOverview();
                    else mainCtrl.showJoinedBoards();
                }
            });
        });
    }

    public void addCardToList(Card card, long listId) {
        for (int i = 0; i < board.tdLists.size(); i++)
            if (board.tdLists.get(i).id == listId)
                board.tdLists.get(i).addCard(card);
    }

    public void stop() {
        if(!server.isExecutorServiceShutdown())
            server.stop();
    }

    public void addList() {
        mainCtrl.showAddList(board.id);
    }

    public void backPressed() {
        if (!mainCtrl.getAdmin())
            mainCtrl.showJoinedBoards();
        else mainCtrl.showBoardOverview();
    }

    public void showAddCard(long id){
        mainCtrl.showAddCard(id, board.id);
    }

    public void showEditList(TDList list){
        mainCtrl.showEditList(list);
    }

    public void showEdit(Card card){
        mainCtrl.showEdit(card);
    }

    public void updateCardList(long id, TDList tdList){
        server.updateCardList(id, tdList);
    }
}

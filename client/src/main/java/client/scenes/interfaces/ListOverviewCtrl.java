package client.scenes.interfaces;

import client.scenes.implementations.ListOverviewCtrlImp;
import com.google.inject.ImplementedBy;
import commons.Card;
import commons.TDList;
import java.util.List;

@ImplementedBy(ListOverviewCtrlImp.class)
public interface ListOverviewCtrl {
    void init();
    void registerForUpdates();
    void addCardToList(Card card, long listId);
    void showAddCard(long id);
    void showEditList(TDList list);
    void showEdit(Card card);
    long getBoardKey();
    void stop();
    void addList();
    void updateList(TDList tdList, List<Card> items);
    void setBoard(long boardId);
    void setBoard();
    void backPressed();
    void updateCardList(long id, TDList tdList);
    void removeList(TDList tdList);
    void removeCard(Card card);
}

package client.scenes.interfaces;

import client.scenes.implementations.ListOverviewCtrlImp;
import com.google.inject.ImplementedBy;
import commons.Card;
import commons.TDList;

import java.util.ArrayList;

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
    void updateList(TDList tdList, ArrayList<Card> items);
    void setBoard(long boardId);
    void refresh(long boardId);
    void backPressed();
    void updateCardList(long id, TDList tdList);
    long getBoardId();
}

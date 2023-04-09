package client.scenes.interfaces;

import client.scenes.implementations.AddSubTaskCtrlImpl;
import com.google.inject.ImplementedBy;
import commons.Card;

@ImplementedBy(AddSubTaskCtrlImpl.class)
public interface AddSubTaskCtrl {
    void init(Card card);
    void create();
    void cancel();
    Card getCard();
}

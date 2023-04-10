package client.services.interfaces;

import client.services.implementations.ListOverviewServiceImpl;
import com.google.inject.ImplementedBy;
import commons.Card;
import commons.TDList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

@ImplementedBy(ListOverviewServiceImpl.class)
public interface ListOverviewService {
    void setScrollPane();
    void showLists(List<TDList> lists);
    FlowPane createFlowPane(List<TDList> lists);
    void setBoardTitle(String title);
    void setFlowPane(FlowPane flowPane);
    Button createAddCardButton(long id);
    Button createEditListButton(TDList list);
    TableView<Card> createTable(TDList tdList);
    HBox createHBox(Button button1, Button button2);
    VBox createVBox(TableView<Card> cards, HBox hBox);
    void cardExpansion(TableView<Card> tableView);
    void dragAndDrop(TableView<Card> tableView);
    void dragOtherLists(TableView<Card> tableView, TDList tdList);
    void copyKey();
    void copyToClipboard(long boardKey);
    void animateCopyButton(Button copyButton);
    void afterCopyButton(Button copyButton);
    void restoreCopyButton(Button copyButton);
    void backPressed();
    void addList();
}

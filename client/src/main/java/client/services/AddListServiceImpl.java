package client.services;

import client.scenes.AddListCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddListServiceImpl implements AddListService {
    @FXML
    private TextField listTitle;

    @FXML
    private Label emptyName;

    @Inject
    private AddListCtrl addListCtrl;

    public void setEmptyNameText(String text) {
        emptyName.setText(text);
    }

    public String getListTitle() {
        return listTitle.getText();
    }

    public void clearFields() {
        listTitle.clear();
        emptyName.setText("");
    }

    public void ok() {
        addListCtrl.ok();
    }

    public void cancel() {
        addListCtrl.cancel();
    }
}

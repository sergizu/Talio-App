package client.services;
import client.utils.ServerUtils;
import commons.TDList;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;


public class AddListCtrlService {
    public boolean keyEnter(KeyEvent e) {
        if(e.getCode() == KeyCode.ENTER) {
            return true;
        }
        return false;
    }

    public boolean keyEscape(KeyEvent e) {
        if(e.getCode() == KeyCode.ESCAPE) {
            return true;
        }
        return false;
    }

    public TDList getList(String title) {
        TDList list = new TDList(title);
        return list;
    }

    public boolean listNameEmpty(TextField listName) {
        return listName.getText().isEmpty();
    }

    public void ok(TextField listName, ServerUtils
            server, Label myLabel, long boardId, TDList list) {
        if (listNameEmpty(listName)) {
            myLabel.setText("Cant be empty");
        } else {
            try {
                myLabel.setText("");
                server.addListToBoard(boardId, list);
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            clearFields(listName);
        }
    }
    public void clearFields(TextField listName) {
        listName.clear();
    }

    public void setLabelToEmpty(Label label){
        label.setText("");
    }






}

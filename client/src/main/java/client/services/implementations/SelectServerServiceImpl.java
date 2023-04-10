package client.services.implementations;

import client.scenes.interfaces.SelectServerCtrl;
import client.services.interfaces.SelectServerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import javax.inject.Inject;

public class SelectServerServiceImpl implements SelectServerService {
    @FXML
    private TextField serverName;

    @FXML
    private Label myLabel;

    @FXML
    private TextField adminPass;
    @FXML
    private HBox hbox;
    private final SelectServerCtrl selectServerCtrl;
    @Inject
    public SelectServerServiceImpl(SelectServerCtrl selectServerCtrl) {
        this.selectServerCtrl = selectServerCtrl;
    }
    public String getAdminPassText(){
        return adminPass.getText();
    }
    public String getServerNameText(){
        return serverName.getText();
    }
    public void setMyLabel(String s){
        myLabel.setText(s);
    }
    public void setAdminPassText(String s){
        adminPass.setText(s);
    }
    public void setBoxVisible(Boolean b){
        hbox.setVisible(b);
    }
    public void keyPressed(KeyEvent e){selectServerCtrl.keyPressed(e);}
    public void ok(){selectServerCtrl.ok();}
    public void adminLogIn(){selectServerCtrl.adminLogIn();}
}

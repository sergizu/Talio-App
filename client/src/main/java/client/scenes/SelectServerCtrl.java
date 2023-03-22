package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SelectServerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField serverName;
    @FXML
    private Label myLabel;
    @Inject
    public SelectServerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    public void ok(){
        String s = serverName.getText();
        if(serverName.getText().isEmpty()) {
            myLabel.setText("Can not be empty!");
            return;
        }
        ServerUtils.changeSERVER(s);
        try{
            server.getLists();
            mainCtrl.showOverview();
        }
        catch(Exception e){
            myLabel.setText("Couldn't find the server!");
        }
    }

}

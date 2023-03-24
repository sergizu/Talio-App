package client.services;

import client.utils.ServerUtils;
import java.awt.*;

public class SelectServerCtrlService {

    public  void ok(ServerUtils server, TextField serverName, Label myLabel) {
        if(serverName.getText().isEmpty()) {
            myLabel.setText("Can not be empty!");
            return;
        }
        server.changeServer(serverName.getText());
        try{
            server.getLists();

        }
        catch(Exception e){
            myLabel.setText("Couldn't find the server!");
        }


    }

}

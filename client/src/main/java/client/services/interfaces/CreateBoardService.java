package client.services.interfaces;

import client.services.implementations.CreateBoardServiceImp;
import com.google.inject.ImplementedBy;

@ImplementedBy(CreateBoardServiceImp.class)
public interface CreateBoardService {
    void setBoardName(String s);
    String getBoardName();
    void createBoard();
    void escape();
}

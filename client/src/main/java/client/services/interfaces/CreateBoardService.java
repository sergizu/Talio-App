package client.services.interfaces;

import client.services.implementations.CreateBoardServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(CreateBoardServiceImpl.class)
public interface CreateBoardService {
    void setBoardName(String s);
    String getBoardName();
    void createBoard();
    void escape();
}

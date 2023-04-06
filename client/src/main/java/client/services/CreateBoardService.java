package client.services;

import com.google.inject.ImplementedBy;

@ImplementedBy(CreateBoardServiceImp.class)
public interface CreateBoardService {
    void setBoardName(String s);
    String getBoardName();
    void createBoard();
    void escape();
}

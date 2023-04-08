package client.services.interfaces;

import client.services.implementations.BoardOptionsServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(BoardOptionsServiceImpl.class)
public interface BoardOptionsService {

    public void initButtons();
    public void setBoardName(String text);
    public String getBoardName();

}

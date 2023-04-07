package client.services;

import com.google.inject.ImplementedBy;

@ImplementedBy(BoardOptionsServiceImpl.class)
public interface BoardOptionsService {

    public void initButtons();
    public void setBoardName(String text);
    public String getBoardName();

}

package client.services;

import com.google.inject.ImplementedBy;

@ImplementedBy(AddSubTaskServiceImpl.class)
public interface AddSubTaskService{
    String getSubtaskName();
    void setSubtaskName(String text);
    void setMyLabelText(String text);
    void create();
    void cancel();
}

package client.services;

public interface AddSubTaskService{
    String getSubtaskName();
    void setSubtaskName(String text);
    void setMyLabelText(String text);
    void create();
    void cancel();
}

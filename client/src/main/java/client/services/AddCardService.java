package client.services;

public interface AddCardService {
    void setMyLabelText(String text);
    void clearFields();
    String getCardName();
    String getDescription();
    void setCardName(String name);
    void setDescription(String description);
    void ok();
    void cancel();
}

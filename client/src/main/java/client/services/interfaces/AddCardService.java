package client.services.interfaces;

import client.services.implementations.AddCardServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(AddCardServiceImpl.class)
public interface AddCardService {
    void setMyLabelText(String text);
    void clearFields();
    String getCardName();
    String getDescription();
    void ok();
    void cancel();
}

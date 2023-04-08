package client.services.interfaces;

import client.services.implementations.AddListServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(AddListServiceImpl.class)
public interface AddListService {
    void setEmptyNameText(String text);
    String getListTitle();
    void clearFields();
}

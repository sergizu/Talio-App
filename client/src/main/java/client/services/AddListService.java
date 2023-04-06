package client.services;

import com.google.inject.ImplementedBy;

@ImplementedBy(AddListServiceImpl.class)
public interface AddListService {
    void setEmptyNameText(String text);
    String getListTitle();
    void clearFields();
}

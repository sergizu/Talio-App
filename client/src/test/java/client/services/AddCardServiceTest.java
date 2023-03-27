package client.services;

import client.utils.ServerUtils;
import commons.Card;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class AddCardServiceTest {
    @Test
    public void createCardTest(){
        Card card = new Card("card1");
        AddCardService service = new AddCardService();
        assertEquals(card, service.createCard("card1"));
    }
    @Test
    public void okTest(){
        ServerUtils server = Mockito.mock(ServerUtils.class);
        AddCardService service = new AddCardService();
        Card card = new Card("card1");
        service.ok(server, 1L, card);
        verify(server).addCardToList(1L, card);
    }
}

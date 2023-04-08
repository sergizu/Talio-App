package client.services;

import client.scenes.AddCardCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddCardServiceImplTest {
    @Mock
    private AddCardCtrl addCardCtrl;
    private AddCardService addCardService;

    @BeforeEach
    void setUp() {
        addCardService = new AddCardServiceImpl(addCardCtrl);
    }

    @Test
    void testConstructor() {
        assertNotNull(addCardService);
    }

    @Test
    void testOk() {
        addCardService.ok();
        verify(addCardCtrl).ok();
    }

    @Test
    void testCancel() {
        addCardService.cancel();
        verify(addCardCtrl).cancel();
    }
}
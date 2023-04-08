package client.services;

import client.scenes.AddListCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddListServiceImplTest {
    @Mock
    private AddListCtrl addListCtrl;
    private AddListService addListService;

    @BeforeEach
    void setUp() {
        addListService = new AddListServiceImpl(addListCtrl);
    }

    @Test
    void testConstructor() {
        assertNotNull(addListService);
    }

    @Test
    void testOk() {
        addListService.ok();
        verify(addListCtrl).ok();
    }

    @Test
    void testCancel() {
        addListService.cancel();
        verify(addListCtrl).cancel();
    }
}
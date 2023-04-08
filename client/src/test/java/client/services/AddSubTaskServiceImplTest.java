package client.services;

import client.scenes.interfaces.AddSubTaskCtrl;
import client.services.implementations.AddSubTaskServiceImpl;
import client.services.interfaces.AddSubTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddSubTaskServiceImplTest {

    @Mock
    private AddSubTaskCtrl addSubTaskCtrl;
    private AddSubTaskService addSubTaskService;

    @BeforeEach
    void setUp() {
        addSubTaskService = new AddSubTaskServiceImpl(addSubTaskCtrl);
    }

    @Test
    void testConstructor() {
        assertNotNull(addSubTaskService);
    }

    @Test
    void testCreate() {
        addSubTaskService.create();
        verify(addSubTaskCtrl).create();
    }

    @Test
    void testCancel() {
        addSubTaskService.cancel();
        verify(addSubTaskCtrl).cancel();
    }


}
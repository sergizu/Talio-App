package client.services;


import client.scenes.SelectServerCtrl;
import client.services.implementations.SelectServerServiceImpl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SelectServerServiceImplTest {
    @Mock
    SelectServerCtrl selectServerCtrl;
    SelectServerServiceImpl selectServerServiceImpl;
    @BeforeEach
    void setUp(){
        selectServerServiceImpl = new SelectServerServiceImpl(selectServerCtrl);
    }
    @Test
    void constractorTest(){
        assertNotNull(selectServerServiceImpl);
    }
    @Test
    void okTest(){
        selectServerServiceImpl.ok();
        verify(selectServerCtrl).ok();
    }
    @Test
    void keyPressedTest(){
        KeyEvent e = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        selectServerServiceImpl.keyPressed(e);
        verify(selectServerCtrl).keyPressed(e);
    }
    @Test
    void adminLogInTest(){
        selectServerServiceImpl.adminLogIn();
        verify(selectServerCtrl).adminLogIn();
    }
}

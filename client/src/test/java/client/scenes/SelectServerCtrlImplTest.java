package client.scenes;

import client.scenes.implementations.SelectServerCtrlImpl;
import client.scenes.interfaces.BoardOverviewCtrl;
import client.scenes.interfaces.EditCardCtrl;
import client.scenes.interfaces.JoinedBoardsCtrl;
import client.scenes.interfaces.ListOverviewCtrl;
import client.scenes.interfaces.MainCtrl;
import client.services.interfaces.SelectServerService;
import client.utils.ServerUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class SelectServerCtrlImplTest {
    SelectServerCtrlImpl selectServerCtrlImpl;
    @Mock
    ServerUtils server;
    @Mock
    MainCtrl mainCtrl;
    @Mock
    ListOverviewCtrl listOverviewCtrl;
    @Mock
    JoinedBoardsCtrl joinedBoardsCtrl;
    @Mock
    BoardOverviewCtrl boardOverviewCtrl;
    @Mock
    EditCardCtrl editCardCtrl;
    @Mock
    SelectServerService selectServerService;
    @BeforeEach
    void setUp(){
        selectServerCtrlImpl = new SelectServerCtrlImpl(server, mainCtrl,
                listOverviewCtrl, joinedBoardsCtrl, boardOverviewCtrl,
                editCardCtrl, selectServerService);
    }
    @Test
    void constructorTest(){
        assertNotNull(selectServerCtrlImpl);
    }
    @Test
    void checkPassTest(){
        given(selectServerService.getAdminPassText()).willReturn("1010");
        assertTrue(selectServerCtrlImpl.checkPass());
        given(selectServerService.getAdminPassText()).willReturn("nothing");
        assertFalse(selectServerCtrlImpl.checkPass());
    }
    @Test
    void startSessionTest(){
        selectServerCtrlImpl.startSession();
        verify(server).setExecutorService(any(ThreadPoolExecutor.class));
        verify(server).initSession();
        verify(boardOverviewCtrl).registerForMessages();
        verify(joinedBoardsCtrl).registerForMessages();
        verify(listOverviewCtrl).init();
        verify(editCardCtrl).registerForUpdates();
    }
    @Test
    void okTestServerNameEmpty(){
        when(selectServerService.getServerNameText()).thenReturn("");
        selectServerCtrlImpl.ok();
        verify(selectServerService).setMyLabel("Can not be empty!");
    }
    @Test
    void okTestServerNotRunning(){
        when(selectServerService.getServerNameText()).thenReturn("something");
        when(server.serverRunning()).thenReturn(false);
        selectServerCtrlImpl.ok();
        verify(selectServerService).setMyLabel("Couldn't find the server!");
    }
    @Test
    void OkTestCheckPassFalse(){
        given(selectServerService.getVisible()).willReturn(true);
        given(selectServerService.getAdminPassText()).willReturn("something");
        when(selectServerService.getServerNameText()).thenReturn("localhost:8080");
        when(server.serverRunning()).thenReturn(true);
        selectServerCtrlImpl.ok();
        verify(selectServerService).setMyLabel("Password is incorrect!");
        verify(mainCtrl).setAdmin(false);
    }
    @Test
    void OkTest(){
        given(selectServerService.getVisible()).willReturn(true);
        given(selectServerService.getAdminPassText()).willReturn("1010");
        given(selectServerService.getServerNameText()).willReturn("localhost:8080");
        given(server.serverRunning()).willReturn(true);
        selectServerCtrlImpl.ok();
        verify(mainCtrl).setAdmin(true);
        verify(mainCtrl).showBoardOverview();
        verify(selectServerService).setAdminPassText("");
        verify(selectServerService).setBoxVisible(false);
    }
    @Test
    void adminLogInTest(){
        selectServerCtrlImpl.adminLogIn();
        verify(selectServerService).setBoxVisible(true);
    }
    @Test
    void keyPressedTest(){
        when(selectServerService.getServerNameText()).thenReturn("");
        KeyEvent e = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
                false, false, false, false);
        selectServerCtrlImpl.keyPressed(e);
        verify(selectServerService).setMyLabel("Can not be empty!");
    }
}

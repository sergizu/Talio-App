package client.utils;

import commons.Board;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServerUtilsTest {
    @Mock
    private RestClient restClient;
    @Mock
    private ExecutorService executorService;
    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;
    @Mock
    private StompSession stompSession;
    private ServerUtils serverUtils;

    @BeforeEach
    void setUp() {
        serverUtils = new ServerUtils(restClient);
    }

    @Test
    void isExecutorServiceShutdownFalse() {
        serverUtils.setExecutorService(executorService);
        given(serverUtils.isExecutorServiceShutdown()).willReturn(false);
        assertFalse(serverUtils.isExecutorServiceShutdown());
    }

    @Test
    void isExecutorServiceShutdownTrue() {
        serverUtils.setExecutorService(executorService);
        given(serverUtils.isExecutorServiceShutdown()).willReturn(true);
        assertTrue(serverUtils.isExecutorServiceShutdown());
    }

    @Test
    void changeServer() {
        serverUtils.changeServer("localhost:8080");
        assertEquals("localhost:8080", serverUtils.getServer());
    }

    @Test
    void getServer() {
        serverUtils.changeServer("localhost:8080");
        assertEquals("localhost:8080", serverUtils.getServer());
    }

    @Test
    void removeList() {
        TDList list = new TDList();
        list.id = 1;
        serverUtils.removeList(list);
        verify(restClient).executeRequest(RequestType.DELETE, null, null,"api/tdLists/1");
    }

    @Test
    void removeCard() {
        Card card = new Card();
        card.id = 1;
        serverUtils.removeCard(card);
        verify(restClient).executeRequest(RequestType.DELETE, null, null, "api/cards/1");
    }

    @Test
    void addBoard() {
        Board board = new Board();
        board.id = 1;
        serverUtils.addBoard(board);
        verify(restClient).executeRequest(RequestType.POST, board, new GenericType<Board>(){}, "api/boards");
    }

    @Test
    void getBoardById() {
        serverUtils.getBoardById(1);
        verify(restClient).executeRequest(RequestType.GET, null, new GenericType<Board>(){}, "/api/boards/1");
    }

    @Test
    void getCardById() {
        serverUtils.getCardById(1);
        verify(restClient).executeRequest(RequestType.GET, null, new GenericType<Card>(){}, "/api/cards/1");
    }

    @Test
    void addCardToList() {
        Card card = new Card();
        card.id = 1;
        serverUtils.addCardToList(1, card);
        verify(restClient).executeRequest(RequestType.PUT, card, null, "/api/tdLists/1/addCard");
    }

    @Test
    void addListToBoard() {
        TDList tdList = new TDList();
        tdList.id = 1;
        serverUtils.addListToBoard(1, tdList);
        verify(restClient).executeRequest(RequestType.PUT, tdList, null, "/api/boards/1/addList");
    }

    @Test
    void updateListName() {
        TDList tdList = new TDList();
        tdList.id = 1;
        tdList.setTitle("test");
        serverUtils.updateListName(tdList.getId(), tdList.getTitle());
        verify(restClient).executeRequest(RequestType.PUT, tdList.getTitle(), null, "api/tdLists/updateName/1");
    }

    @Test
    void updateBoard() {
        Board board = new Board();
        board.id = 1;
        board.setTitle("test");
        serverUtils.updateBoard(board);
        verify(restClient).executeRequest(RequestType.PUT, board, new GenericType<Board>(){}, "api/boards/update");
    }

    @Test
    void updateCardName() {
        Card card = new Card();
        card.id = 1;
        card.setTitle("test");
        serverUtils.updateCardName(card.getId(), card.getTitle());
        verify(restClient).executeRequest(RequestType.PUT, card.getTitle(), null, "api/cards/updateName/1");
    }

    @Test
    void updateCardDescription() {
        Card card = new Card();
        card.id = 1;
        card.setDescription("test");
        serverUtils.updateCardDescription(card.getId(), card.getDescription());
        verify(restClient).executeRequest(RequestType.PUT, card.getDescription(), null, "api/cards/updateDescription/1");
    }

    @Test
    void updateCardList() {
        TDList tdList = new TDList();
        tdList.id = 1;
        serverUtils.updateCardList(2L, tdList);
        verify(restClient).executeRequest(RequestType.PUT, tdList.getId(), null, "api/cards/updateList/2");
    }

    @Test
    void getBoards() {
        serverUtils.getBoards();
        verify(restClient).executeRequest(RequestType.GET, null, new GenericType<List<Board>>(){}, "api/boards");
    }

    @Test
    void deleteBoards() {
        serverUtils.deleteBoard(1);
        verify(restClient).executeRequest(RequestType.DELETE, null, null, "api/boards/1");
    }

    @Test
    void updateNestedList() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        serverUtils.updateNestedList(1, subtasks);
        verify(restClient).executeRequest(RequestType.PUT, subtasks, null, "api/cards/updateNestedList/1");
    }

    @Test
    void testIsServerRunning() {
        serverUtils.serverRunning();
        verify(restClient).isServerRunning();
    }

    @Test
    void registerBoardNoUpdates() {
        Consumer<Long> consumer = mock(Consumer.class);
        serverUtils.setExecutorService(executorService);
        Future future = mock(Future.class);
        Response response = mock(Response.class);
        given(response.getStatus()).willReturn(Response.Status.NO_CONTENT.getStatusCode());
        given(executorService.submit(any(Runnable.class))).willReturn(future);
        given(restClient.longPolling("/api/boards/updates")).willAnswer(new Answer<Response>() {
            int counter = 0;
            @Override
            public Response answer(InvocationOnMock invocation) {
                if (counter >= 5) {
                    Thread.currentThread().interrupt();
                }
                counter++;
                return response;
            }
        });

        serverUtils.registerForBoardUpdates(consumer);
        verify(executorService).submit(runnableCaptor.capture());
        runnableCaptor.getValue().run();
        verify(consumer, never()).accept(anyLong());
    }

    @Test
    void registerBoardUpdates() {
        Consumer<Long> consumer = mock(Consumer.class);
        serverUtils.setExecutorService(executorService);
        Future future = mock(Future.class);
        Response response = mock(Response.class);
        given(response.getStatus()).willReturn(Response.Status.OK.getStatusCode());
        given(response.readEntity(Long.class)).willReturn(1L);
        given(executorService.submit(any(Runnable.class))).willReturn(future);
        given(restClient.longPolling("/api/boards/updates")).willAnswer(new Answer<Response>() {
            @Override
            public Response answer(InvocationOnMock invocation) {
                Thread.currentThread().interrupt();
                return response;
            }
        });

        serverUtils.registerForBoardUpdates(consumer);
        verify(executorService).submit(runnableCaptor.capture());
        runnableCaptor.getValue().run();
        verify(consumer).accept(anyLong());
    }

    @Test
    void registerCardNoUpdates() {
        Consumer<Long> consumer = mock(Consumer.class);
        serverUtils.setExecutorService(executorService);
        Future future = mock(Future.class);
        Response response = mock(Response.class);
        given(response.getStatus()).willReturn(Response.Status.NO_CONTENT.getStatusCode());
        given(executorService.submit(any(Runnable.class))).willReturn(future);
        given(restClient.longPolling("api/cards/updates")).willAnswer(new Answer<Response>() {
            int counter = 0;
            @Override
            public Response answer(InvocationOnMock invocation) {
                if (counter >= 5) {
                    Thread.currentThread().interrupt();
                }
                counter++;
                return response;
            }
        });

        serverUtils.registerForCardUpdates(consumer);
        verify(executorService).submit(runnableCaptor.capture());
        runnableCaptor.getValue().run();
        verify(consumer, never()).accept(anyLong());
    }

    @Test
    void registerCardUpdates() {
        Consumer<Long> consumer = mock(Consumer.class);
        serverUtils.setExecutorService(executorService);
        Future future = mock(Future.class);
        Response response = mock(Response.class);
        given(response.getStatus()).willReturn(Response.Status.OK.getStatusCode());
        given(response.readEntity(Long.class)).willReturn(1L);
        given(executorService.submit(any(Runnable.class))).willReturn(future);
        given(restClient.longPolling("api/cards/updates")).willAnswer(new Answer<Response>() {
            @Override
            public Response answer(InvocationOnMock invocation) {
                Thread.currentThread().interrupt();
                return response;
            }
        });

        serverUtils.registerForCardUpdates(consumer);
        verify(executorService).submit(runnableCaptor.capture());
        runnableCaptor.getValue().run();
        verify(consumer).accept(anyLong());
    }

    @Test
    void setStompSession() {
        serverUtils.setSession(stompSession);
        assertEquals(stompSession, serverUtils.getSession());
    }

    @Test
    void initSession() {
        assertThrows(RuntimeException.class, () -> serverUtils.initSession());
    }

    @Test
    void send() {
        serverUtils.setSession(stompSession);
        serverUtils.send("test1", "test2");
        verify(stompSession).send("test1", "test2");
    }

    @Test
    void stop() {
        serverUtils.setExecutorService(executorService);
        serverUtils.setSession(stompSession);
        serverUtils.stop();
        verify(executorService).shutdownNow();
        verify(stompSession).disconnect();
    }

    @Test
    void registerForMessages() {
        serverUtils.setSession(stompSession);
        Consumer<Board> consumer = mock(Consumer.class);
        serverUtils.registerForMessages("destination", Board.class, consumer);
        verify(stompSession).subscribe(anyString(), any(StompFrameHandler.class));
    }
}
package client.utils;

import commons.Board;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import jakarta.ws.rs.core.GenericType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServerUtilsTest {
    @Mock
    private RestClient restClient;
    private ServerUtils serverUtils;

    @BeforeEach
    void setUp() {
        serverUtils = new ServerUtils(restClient);
    }

    @Test
    void isExecutorServiceShutdown() {
        assertFalse(serverUtils.isExecutorServiceShutdown());
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
}
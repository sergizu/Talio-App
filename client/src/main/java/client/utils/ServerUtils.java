/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Subtask;
import commons.TDList;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private String server;
    private ExecutorService executorService;
    private final RestClient restClient;

    @Inject
    public ServerUtils(RestClient restClient) {
        this.executorService = Executors.newCachedThreadPool();
        this.restClient = restClient;
    }

    //setter only for testing
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public boolean isExecutorServiceShutdown() {
        return executorService.isShutdown();
    }


    public void changeServer(String serverAddress) {
        server = serverAddress;
        restClient.changeServer(serverAddress);
    }

    public String getServer() {
        return server;
    }


    public void removeList(TDList list) {
        restClient.executeRequest(RequestType.DELETE, null, null,"api/tdLists/" + list.id);
    }

    public void removeCard(Card card) {
        restClient.executeRequest(RequestType.DELETE, null, null, "api/cards/" + card.id);
    }


    public Board addBoard(Board board) {
        return restClient.executeRequest(RequestType.POST, board, new GenericType<Board>(){}, "api/boards");
    }

    public Board getBoardById(long boardId) {
        return restClient.executeRequest(RequestType.GET, null, new GenericType<Board>(){}, "/api/boards/" + boardId);
    }

    public Card getCardById(long cardId) {
        return restClient.executeRequest(RequestType.GET, null, new GenericType<Card>(){}, "/api/cards/" + cardId);
    }

    public void addCardToList(long listId, Card card) {
        restClient.executeRequest(RequestType.PUT, card, null, "/api/tdLists/" + listId + "/addCard");
    }

    public void addListToBoard(long boardId, TDList tdList) {
        restClient.executeRequest(RequestType.PUT, tdList, null, "/api/boards/" + boardId + "/addList");
    }


    public void registerForBoardUpdates(Consumer<Long> consumer) {
        executorService.submit(() -> {
            while (!Thread.interrupted()) {
                Response result = restClient.longPolling("/api/boards/updates");
                if (result.getStatus() == HttpStatus.NO_CONTENT.value())
                    continue;
                consumer.accept(result.readEntity(Long.class));
            }
        });
    }

    public void registerForCardUpdates(Consumer<Long> consumer) {
        executorService.submit(() -> {
            while (!Thread.interrupted()) {
                Response result = restClient.longPolling("api/cards/updates");
                if (result.getStatus() == HttpStatus.NO_CONTENT.value())
                    continue;
                consumer.accept(result.readEntity(Long.class));
            }
        });
    }

    public void updateListName(long listId, String newName) {
        restClient.executeRequest(RequestType.PUT, newName, null, "api/tdLists/updateName/" + listId);
    }

    public Board updateBoard(Board board) {
        return restClient.executeRequest(RequestType.PUT, board, new GenericType<Board>(){}, "api/boards/update");
    }

    public void updateCardName(long id, String name) {
        restClient.executeRequest(RequestType.PUT, name, null, "api/cards/updateName/" + id);
    }

    public void updateCardDescription(long id, String name) {
        restClient.executeRequest(RequestType.PUT, name, null, "api/cards/updateDescription/" + id);
    }


    public void updateCardList(long id, TDList list) {
        restClient.executeRequest(RequestType.PUT, list.getId(), null, "api/cards/updateList/" + id);
    }

    public List<Board> getBoards() {
        return restClient.executeRequest(RequestType.GET, null, new GenericType<List<Board>>(){}, "api/boards");
    }

    public void deleteBoard(long boardId) {
        restClient.executeRequest(RequestType.DELETE, null, null, "api/boards/" + boardId);
    }

    public boolean serverRunning() {
        return restClient.isServerRunning();
    }

    public void updateNestedList(long id, ArrayList<Subtask> nestedList) {
        restClient.executeRequest(RequestType.PUT, nestedList, null, "api/cards/updateNestedList/" + id);
    }


    private StompSession session;

    public void initSession(){
        session = connect("ws://localhost:8080/websocket");
    }

    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public <T> void registerForMessages(String dest, Class<T> type,  Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    public void send(String dest, Object o) {
        session.send(dest, o);
    }

    public void stop() {
        executorService.shutdownNow();
        session.disconnect();
    }
}
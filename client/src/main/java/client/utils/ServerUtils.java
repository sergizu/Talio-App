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

import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
    }

    public List<Card> getCard() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Card>>() {});
    }

    public List<TDList> getLists() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/lists") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<TDList>>() {});
    }

    public TDList addList(TDList list) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/lists") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(list, APPLICATION_JSON), TDList.class);
    }

    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    public Card addCard(Card card) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(card, APPLICATION_JSON), Card.class);
    }

    public void removeCard(Card card) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + card.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete();
    }

    public Board tempBoardGetter() {
        Response result = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/boards/tempGetter")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get();
        if(result.getStatus() == HttpStatus.OK.value()) {
            try {
                Board board = result.readEntity(Board.class);
                return board;
            } catch (Exception e) {
                System.out.println("problems: couldnt parse the incoming board");
            }
        }
        return null;
    }

    public void addCardToList(long listId, Card card) {
        Response result = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/lists/" + listId + "/addCard")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(card, APPLICATION_JSON));
    }

    public void addToList(long boardId, Card card) {
        Response result = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/boards/" + boardId + "/addCard")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(card, APPLICATION_JSON));
    }

    public void addListToBoard(long boardId, TDList tdList) {
        Response result = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/boards/" + boardId + "/addList")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(tdList, APPLICATION_JSON));
    }


    public void registerForUpdates(Consumer<CardChange> consumer) {
        EXECUTOR_SERVICE.submit(() -> {
            while (!Thread.interrupted()) {
                Response result = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("/api/cards/updates")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);
                if(result.getStatus() == HttpStatus.NO_CONTENT.value())
                    continue;
                System.out.println(result);
                CardChange cardChange = null;
                try {
                    cardChange = result.readEntity(CardChange.class);
                } catch (Exception e) {
                    System.out.println("problems");
                }

                System.out.println(cardChange);
                consumer.accept(cardChange);
            }
        });
    }

    public void updateCard(Card card) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/update") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON)
                .put(Entity.entity(card, APPLICATION_JSON));//
    }

    public void updateList(TDList list){
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/update") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON)
                .put(Entity.entity(list, APPLICATION_JSON));//
    }

    public void updateBoard(Board board) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/update") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON)
                .put(Entity.entity(board, APPLICATION_JSON), Board.class);//
    }


    public void stop() {
        EXECUTOR_SERVICE.shutdownNow();
    }


}
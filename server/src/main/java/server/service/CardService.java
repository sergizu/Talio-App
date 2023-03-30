package server.service;

import commons.Card;
import commons.Subtask;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoardService boardService;
    private final ListRepository listRepository;

    private final Map<Object, Consumer<Long>> listeners = new HashMap<>();

    @Autowired
    public CardService(CardRepository cardRepository, BoardService boardService,
                       ListRepository listRepository) {
        this.cardRepository = cardRepository;
        this.boardService = boardService;
        this.listRepository = listRepository;
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    //Method returns either the card or null if the card doesn't exist
    public Card getById(long id) {
        if(cardRepository.existsById(id))
            return cardRepository.findById(id).get();
        return null;
    }

    //Method returns either the card that is added or null if an error occurs was sent
    public Card addCard(Card card) {
        if(card == null || card.title == null) return null;
        if(cardRepository.existsById(card.id))
            return null;
        return cardRepository.save(card);
    }

    public boolean existsById(long id) {
        return cardRepository.existsById(id);
    }

    //Method that updates the card if it exists, otherwise it will return null
    public Card update(Card card) {
        if(card == null || card.title == null) return null;
        if(!cardRepository.existsById(card.id))
            return null;
        return cardRepository.save(card);
    }

    public boolean delete(long id) {
        if(!cardRepository.existsById(id))
            return false;
        Card toDelete = cardRepository.getById(id);
        cardRepository.deleteById(id);
        boardService.sendUpdates(toDelete.getList().getBoard().getId());
        return true;
    }

    public boolean updateName(long cardID, String name) {
        try {
            Card toUpdate = cardRepository.getById(cardID); //only get a proxy/reference
            toUpdate.setTitle(name);
            toUpdate = cardRepository.save(toUpdate);
            boardService.sendUpdates(toUpdate.getList().getBoard().getId());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateList(long id, TDList list) {
        try {
            Card toUpdate = cardRepository.getById(id);
            toUpdate.setList(list);
            toUpdate = cardRepository.save(toUpdate);
            boardService.sendUpdates(toUpdate.getList().getBoard().getId());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateNestedList(long id, ArrayList<Subtask> nestedList) {
        try {
            Card toUpdate = cardRepository.getById(id);
            toUpdate.setNestedList(nestedList);
            toUpdate = cardRepository.save(toUpdate);
            boardService.sendUpdates(toUpdate.getList().getBoard().getId());
//            sendUpdates(toUpdate.getId());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public DeferredResult<ResponseEntity<Long>> subscribeForUpdates() {
//        ResponseEntity<Long> noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        org.springframework.web.context.request.async.DeferredResult<ResponseEntity<Long>>
//        result = new DeferredResult<>(10000L, noContent);
//
//        Object key = new Object(); //trick to uniquely identify every key
//        listeners.put(key, id -> {
//            result.setResult(ResponseEntity.ok(id));
//        });
//        result.onCompletion(() -> {
//            listeners.remove(key);
//        });
//        return result;
//    }
//
//    public void sendUpdates(long id) {
//        try {
//            listeners.forEach((key, listener) -> listener.accept(id));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}

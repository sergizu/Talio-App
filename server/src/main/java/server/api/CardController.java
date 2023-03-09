package server.api;

import commons.Card;
import commons.CardChange;
import commons.Change;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.CardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final Map<Object, Consumer<CardChange>> listeners = new HashMap<>();

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path = { "", "/" })
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        Card retrievedCard = cardService.getById(id);
        if(retrievedCard == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(retrievedCard);
    }

    //this method adds a card to the database and generates an ID for the card
    //if the id is already set, and there already exists a card with that id in the database
    //an error will be thrown, if you want to update the card send a put request
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> add(@RequestBody Card card) {
        Card response = cardService.addCard(card);
        if(response == null)
            return ResponseEntity.badRequest().build();
        listeners.forEach((key, listener) -> listener.accept(new CardChange(response, Change.Add)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeByID(@PathVariable("id") long id) {
        boolean result = cardService.delete(id);
        if(!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = { "", "/" })
    public ResponseEntity<Card> update(@RequestBody Card card) {
        Card response = cardService.update(card);
        if(response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<CardChange>> getUpdates() {
        ResponseEntity<CardChange> noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<CardChange>> result = new DeferredResult<>(1000L, noContent);

        Object key = new Object(); //trick to uniquely identify every key

        listeners.put(key, cardChange -> {
           result.setResult(ResponseEntity.ok(cardChange));
        });
        result.onCompletion(() -> {
            listeners.remove(key);
        });

        return result;
    }
}

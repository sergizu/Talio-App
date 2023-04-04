package server.api;

import commons.Card;
import commons.Subtask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.CardService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path = {"", "/"})
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        Card retrievedCard = cardService.getById(id);
        if (retrievedCard == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(retrievedCard);
    }

    //this method adds a card to the database and generates an ID for the card
    //if the id is already set, and there already exists a card with that id in the database
    //an error will be thrown, if you want to update the card send a put request
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Card> add(@RequestBody Card card) {
        Card response = cardService.addCard(card);
        if (response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeByID(@PathVariable("id") long id) {
        boolean result = cardService.delete(id);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Card> update(@RequestBody Card card) {
        Card response = cardService.update(card);
        if (response == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/updateName/{cardID}")
    public ResponseEntity updateName(@PathVariable("cardID") long cardID,
                                     @RequestBody String newName) {
        if (cardService.updateName(cardID, newName))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateDescription/{cardID}")
    public ResponseEntity updateDescription(@PathVariable("cardID") long cardID,
                                     @RequestBody String newName) {
        if(cardService.updateDescription(cardID, newName))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateList/{id}")
    public ResponseEntity updateList(@PathVariable("id") long id, @RequestBody long listId) {
        if (cardService.updateList(id, listId))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateNestedList/{id}")
    public ResponseEntity updateNestedList(@PathVariable("id") long id,
                                           @RequestBody ArrayList<Subtask> nestedList) {
        if (cardService.updateNestedList(id, nestedList))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

//    @GetMapping("/updates")
//    public DeferredResult<ResponseEntity<Long>> subscribeForUpdates() {
//        return cardService.subscribeForUpdates();
//    }
}

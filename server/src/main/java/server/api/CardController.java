package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardRepository cardRepository;
    private SimpMessagingTemplate msgs;

    //using a constructor to allow for dependency injection
    public CardController(CardRepository cardRepository, SimpMessagingTemplate msgs) {
        this.cardRepository = cardRepository;
        this.msgs = msgs;
    }

    @GetMapping(path = { "", "/" })
    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        if(!cardRepository.existsById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(cardRepository.findById(id).get());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Card> add(@RequestBody Card card) {
        if(card == null || isNullOrEmpty(card.title))
            return ResponseEntity.badRequest().build();
        if(msgs != null)
            msgs.convertAndSend("/topic/cards", card);
        Card saved = cardRepository.save(card);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeByID(@PathVariable("id") long id) {
        if(!cardRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        cardRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}

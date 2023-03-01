package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardRepository cardRepository;

    //using a constructor to allow for dependency injection
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        if(!cardRepository.existsById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(cardRepository.findById(id).get());
    }

    @GetMapping()
    public ResponseEntity<List<Card>> getAll() {
        return ResponseEntity.ok(cardRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Card> add(@RequestBody Card card) {
        if(card == null || cardRepository.existsById(card.getId()))
            return ResponseEntity.badRequest().build();
        cardRepository.save(card);
        return ResponseEntity.ok(card);
    }
}

package server.api;

import commons.Board;
import commons.Card;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ListRepository;
import server.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository, ListRepository listRepository, CardRepository cardRepository) {
        this.boardRepository = boardRepository;
        this.listRepository = listRepository;
        this.cardRepository = cardRepository;
    }

    //This mapper is only temporary to make it possible to already work with a board even though we dont id this
    //board yet or give the possibilities to add more boards
    @GetMapping("/tempGetter")
    public ResponseEntity<Board> tempGetter() {
        Board board = new Board("Default board");
        Card card = new Card("Default card");
        card = cardRepository.save(card);
        TDList tdList = new TDList("Default list");
        tdList.addCard(card);
        tdList = listRepository.save(tdList);
        board.addList(tdList);
        board = boardRepository.save(board);

        return ResponseEntity.ok(board);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {
        if(!boardRepository.existsById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(boardRepository.findById(id).get());
    }

    @GetMapping()
    public ResponseEntity<List<Board>> getAll() {
        return ResponseEntity.ok(boardRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Board> add(@RequestBody Board board) {
        if(board == null || boardRepository.existsById(board.getId()))
            return ResponseEntity.badRequest().build();
        boardRepository.save(board);
        return ResponseEntity.ok(board);
    }
}

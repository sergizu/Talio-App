package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //This mapper is only temporary to make it possible to already work with a board even though we dont id this
    //board yet or give the possibilities to add more boards
    @GetMapping("/tempGetter")
    public ResponseEntity<Board> tempGetter() {
        if(!boardRepository.existsById(1L)) {
            Board board = new Board("Default board");
            board.id = 1L;
            boardRepository.save(board);
        }
        return ResponseEntity.ok(boardRepository.getById(1L));
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

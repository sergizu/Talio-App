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

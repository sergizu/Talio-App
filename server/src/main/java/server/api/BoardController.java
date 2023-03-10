package server.api;

import commons.Board;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import server.service.BoardService;


import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;


    @Autowired
    public BoardController(BoardService boardService,
                          SimpMessagingTemplate MsgTemp) {
        this.boardService = boardService;

    }
    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {

        Board retrieveBoard = boardService.getById(id);
        if(retrieveBoard == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(retrieveBoard);
    }

    @GetMapping()
    public List<Board> getAll() {
        return  boardService.getAll();
    }

    @PostMapping()
    public ResponseEntity<Board> add(@RequestBody Board board) {
        Board response = boardService.addBoard(board);
        if(response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}

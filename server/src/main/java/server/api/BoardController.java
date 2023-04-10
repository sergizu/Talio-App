package server.api;

import commons.Board;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.BoardService;
import server.service.ListService;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    private final ListService listService;

    private Long defaultBoardID; //temporary default board to return to all requests

    @Autowired
    public BoardController(BoardService boardService,
                           ListService listService) {
        this.boardService = boardService;
        this.listService = listService;
        this.defaultBoardID = -1L; //setting it to undefined
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {

        Board retrieveBoard = boardService.getById(id);
        if(retrieveBoard == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(retrieveBoard);
    }

    @PutMapping("/{id}/addList")
    public ResponseEntity addListToBoard(@PathVariable("id") long id, @RequestBody TDList tdList) {
        if (!boardService.existsById(id) || tdList == null)
            ResponseEntity.badRequest().build();
        boardService.addListToBoard(tdList, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = { "", "/" })
    public List<Board> getAll() {
        return  boardService.getAll();
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> add(@RequestBody Board board) {
        Board response = boardService.addBoard(board);
        if(response == null) {
            return ResponseEntity.badRequest().build();
        }
        boardService.sendUpdates(board.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Board> update(@RequestBody Board board) {
        Board response = boardService.update(board);
        if(response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeByID(@PathVariable("id") long id) {
        boolean result = boardService.delete(id);
        if(!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<Long>> subscribeForUpdates() {
        return boardService.subscribeForUpdates();
    }
    @MessageMapping("/boards/renameBoard")
    @SendTo("/topic/renameBoard")
    public Board sendBoardRename(Board board) {
        return board;
    }

    @MessageMapping("/boards/deleteBoard")
    @SendTo("/topic/boardDeletion")
    public Long sendDeletedBoardId(long boardId) {
        return boardId;
    }

    @MessageMapping("/boards/createBoard")
    @SendTo("/topic/boardCreation")
    public Long sendCreatedBoardId(long boardId) {
        return boardId;
    }
    public long getDefaultId(){return defaultBoardID;}
}
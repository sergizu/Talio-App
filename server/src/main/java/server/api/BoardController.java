package server.api;

import commons.Board;
import commons.Card;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.BoardService;
import server.service.ListService;

import java.util.List;
import java.util.Random;

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

    //This mapper is only temporary to make it possible to already
    //work with a board even though we dont id this
    //board yet or give the possibilities to add more boards
    @GetMapping("/tempGetter")
    public ResponseEntity<Board> tempGetter() {
        if (defaultBoardID != -1L) {
            boolean existsById = boardService.existsById(defaultBoardID);
            Board board = null;
            if(existsById)
                board = boardService.getById(defaultBoardID);
            return ResponseEntity.ok(board);
        }
        Board board = new Board("Default board");
        Random randomGenerator = new Random();
        board.key = Math.abs(randomGenerator.nextLong());
        Card card = new Card("Default card");
        TDList tdList = new TDList("TO DO");
        TDList tdList1 = new TDList("DOING");
        TDList tdList2 = new TDList("DONE");
        card.list = tdList;
        //card = cardRepository.save(card);
        tdList.addCard(card);
        tdList.board = board;
        //tdList = listRepository.save(tdList);
        board.addList(tdList);
        board.addList(tdList1);
        tdList1.board =board;
        board.addList(tdList2);
        tdList2.board =board;
        board = boardService.addBoard(board);
        defaultBoardID = board.id;
        return ResponseEntity.ok(board);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {

        Board retrieveBoard = boardService.getById(id);
        if(retrieveBoard == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(retrieveBoard);
    }

    @PutMapping("/{id}/addCard")
    public ResponseEntity addCardToBoard(@PathVariable("id") long id, @RequestBody Card card) {
        if (!boardService.existsById(id))
             ResponseEntity.badRequest().build();
        Board board = boardService.getById(id);
        TDList list = board.tdLists.get(0);
        list.addCard(card);
        list = listService.update(list);
        board.tdLists.set(0, list);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/addList")
    public ResponseEntity addListToBoard(@PathVariable("id") long id, @RequestBody TDList tdList) {
        if (!boardService.existsById(id))
            ResponseEntity.badRequest().build();
        Board board = boardService.getById(id);
        tdList.board = board;
        board.tdLists.add(tdList);
        boardService.update(board);
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

    @DeleteMapping("/remove/{id}")
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
    public long getDefaultId(){return defaultBoardID;}
}

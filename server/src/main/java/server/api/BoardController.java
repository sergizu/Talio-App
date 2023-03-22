package server.api;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardRepository;
import server.database.ListRepository;
import server.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private Long defaultBoardID; //temporary default board to return to all requests


    @Autowired
    public BoardController(BoardService boardService,
                           ListRepository listRepository, CardRepository cardRepository) {
        this.boardService = boardService;
        this.listRepository = listRepository;
        this.cardRepository = cardRepository;
        this.defaultBoardID = -1L; //setting it to undefined
    }

    //This mapper is only temporary to make it possible to already
    //work with a board even though we dont id this
    //board yet or give the possibilities to add more boards
    @GetMapping("/tempGetter")
    public ResponseEntity<Board> tempGetter() {
        if (defaultBoardID != -1L) {
            boolean existsById = boardService.existsById(defaultBoardID);
            //System.out.println(existsById);
            Board board = null;
            try {
                board = boardService.getById(defaultBoardID);
            } catch (Exception e) {
                System.out.println("problems");
            }
            return ResponseEntity.ok(board);
        }
        Board board = new Board("Default board");
        Card card = new Card("Default card");
        TDList tdList = new TDList("Default list");
        card.list = tdList;
        //card = cardRepository.save(card);
        tdList.addCard(card);
        tdList.board = board;
        //tdList = listRepository.save(tdList);
        board.addList(tdList);
        board = boardService.addBoard(board);
        //System.out.println(board);
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
        //System.out.println(board.lists);
        TDList list = board.lists.get(0);
        list.addCard(card);
        list = listRepository.save(list);
        board.lists.set(0, list);
        board = boardService.update(board);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/addList")
    public ResponseEntity addListToBoard(@PathVariable("id") long id, @RequestBody TDList tdList) {
        if (!boardService.existsById(id))
            ResponseEntity.badRequest().build();
        Board board = boardService.getById(id);
        tdList.board = board;
        board.lists.add(tdList);
        boardService.update(board);
        return ResponseEntity.ok().build();
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
    public DeferredResult<ResponseEntity<Board>> getUpdates() {
        return boardService.subscribeForUpdates();
    }
}

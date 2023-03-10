package server.api;

import commons.Board;
import commons.Card;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
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
            System.out.println(existsById);
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
        card = cardRepository.save(card);
        TDList tdList = new TDList("Default list");
        tdList.addCard(card);
        tdList = listRepository.save(tdList);
        board.addList(tdList);
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
        TDList list = board.lists.get(0);
        list.addCard(card);
        list = listRepository.save(list);
        board.lists.set(0, list);
        board = boardService.update(board);
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
}

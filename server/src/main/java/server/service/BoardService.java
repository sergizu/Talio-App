package server.service;
import commons.Board;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.BoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    private final Map<Object, Consumer<Long>> listeners = new HashMap<>();

    public Board getById(long id) {
        if(boardRepository.existsById(id)) {
            return boardRepository.findById(id).get();
        }
        return null;
    }

    public Board addBoard(Board board) {
        if( board == null || board.getTitle() == null) return null;
        if(boardRepository.existsById(board.getId()))
            return null;
        generateKey(board);
        return boardRepository.save(board);
    }

    public boolean existsById(long id) {
        return boardRepository.existsById(id);
    }

    //Method that updates the board if it exists, otherwise it will return null
    public Board update(Board board) {
        if(board == null || board.title == null) return null;
        if(!boardRepository.existsById(board.id))
            return null;
        Board toUpdate = boardRepository.save(board);
        sendUpdates(toUpdate.getId());
        return toUpdate;
    }

    public boolean delete(long id) {
        if(!boardRepository.existsById(id))
            return false;
        boardRepository.deleteById(id);
        return true;
    }

    public DeferredResult<ResponseEntity<Long>> subscribeForUpdates() {
        ResponseEntity<Long> noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<Long>> result = new DeferredResult<>(1000L, noContent);

        Object key = new Object(); //trick to uniquely identify every key

        listeners.put(key, id -> {
            result.setResult(ResponseEntity.ok(id));
        });
        result.onCompletion(() -> {
            listeners.remove(key);
        });
        return result;
    }

    public void sendUpdates(long id) {
        listeners.forEach((key, listener) -> listener.accept(id));
    }

    public void generateKey(Board board){
        Random randomGenerator = new Random();
        board.key = Math.abs(randomGenerator.nextLong());
    }


    public void addListToBoard(TDList toAdd, long boardId) {
        Board board = getById(boardId);
        toAdd.board = board;
        board.tdLists.add(toAdd);
        update(board);
    }
}
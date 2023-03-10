package server.service;



import commons.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import java.util.List;

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
        return boardRepository.save(board);
    }
}





package server.service;

import commons.Card;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ListRepository;

import java.util.List;

@Service
public class ListService {
    private final ListRepository listRepository;

    private final BoardService boardService;

    @Autowired
    public ListService(ListRepository listRepository, BoardService boardService) {
        this.listRepository = listRepository;
        this.boardService = boardService;
    }

    public List<TDList> getAll() {
        return listRepository.findAll();
    }

    public TDList getById(long id) {
        if(listRepository.existsById(id))
            return listRepository.findById(id).get();
        return null;
    }

    public TDList addList(TDList l) {
        if(l == null || l.title == null || l.cards == null) return null;
        if(listRepository.existsById(l.id))
            return null;
        return listRepository.save(l);
    }

    public boolean existsById(long id) {
        return listRepository.existsById(id);
    }

    public TDList update(TDList l) {
        if(l == null || l.getTitle() == null) return null;
        if(!listRepository.existsById(l.getId()))
            return null;
        return listRepository.save(l);
    }

    public boolean delete(long id) {
        if(!listRepository.existsById(id))
            return false;
        TDList toDelete = listRepository.getById(id);
        listRepository.deleteById(id);
        boardService.sendUpdates(toDelete.getBoard().getId());
        return true;
    }

    public boolean updateName(long id,String newName){
        if(!listRepository.existsById(id))
            return false;
        TDList toUpdate = listRepository.getById(id);
        toUpdate.setTitle(newName);
        TDList updated = listRepository.save(toUpdate);
        boardService.sendUpdates(updated.getBoard().getId());
        return true;
    }

    public boolean addCardToList(Long listId, Card cardToAdd) {
        if(!listRepository.existsById(listId) || cardToAdd == null)
            return false;
        TDList tdlist = listRepository.getById(listId);
        cardToAdd.list = tdlist;
        tdlist.addCard(cardToAdd);
        TDList update = listRepository.save(tdlist);
        return true;
    }
    public boolean addCardToList(Long listId, Card cardToAdd) {
        if(!listRepository.existsById(listId) || cardToAdd == null)
            return false;
        TDList tdlist = listRepository.getById(listId);
        cardToAdd.list = tdlist;
        tdlist.addCard(cardToAdd);
        TDList update = listRepository.save(tdlist);
        return true;
    }
}

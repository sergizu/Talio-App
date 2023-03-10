package server.service;

import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ListRepository;

import java.util.List;

@Service
public class ListService {
    private final ListRepository listRepository;

    @Autowired
    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public List<TDList> getAll() {
        return listRepository.findAll();
    }

    public TDList getById(long id) {
        if(listRepository.existsById(id))
            return listRepository.getById(id);
        return null;
    }

    public TDList addList(TDList l) {
        if(l == null || l.title == null || l.list == null) return null;
        if(listRepository.existsById(l.id))
            return null;
        return listRepository.save(l);
    }

    public boolean existsById(long id) {
        return listRepository.existsById(id);
    }

    public TDList update(TDList l) {
        if(l == null || l.title == null) return null;
        if(!listRepository.existsById(l.id))
            return null;
        return listRepository.save(l);
    }

    public boolean delete(long id) {
        if(!listRepository.existsById(id))
            return false;
        listRepository.deleteById(id);
        return true;
    }
}

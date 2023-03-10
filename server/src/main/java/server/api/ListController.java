package server.api;

import commons.TDList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ListRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    private final ListRepository listRepository;

    public ListController(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TDList> getById(@PathVariable("id") long id) {
        if(!listRepository.existsById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(listRepository.findById(id).get());
    }

    @GetMapping()
    public ResponseEntity<List<TDList>> getAll() {
        return ResponseEntity.ok(listRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<TDList> add(@RequestBody TDList tdList) {
        if(tdList == null || listRepository.existsById(tdList.id)) {
            return ResponseEntity.badRequest().build();
        }
        TDList saved = listRepository.save(tdList);
        return ResponseEntity.ok(saved);
    }
}

package server.api;

import commons.Card;
import commons.TDList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.ListService;

import java.util.List;

@RestController
@RequestMapping("/api/tdLists")
public class ListController {

    private final ListService listService;


    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TDList> getById(@PathVariable("id") long id) {
        TDList retrievedList = listService.getById(id);
        if(retrievedList == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(retrievedList);
    }

    @GetMapping(path = { "", "/" })
    public List<TDList> getAll() {
        return listService.getAll();
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<TDList> add(@RequestBody TDList list) {
        TDList response = listService.addList(list);
        if(response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeByID(@PathVariable("id") long id) {
        boolean result = listService.delete(id);
        if(!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<TDList> update(@RequestBody TDList list) {
        TDList response = listService.update(list);
        if(response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{listId}/addCard")
    public ResponseEntity addCardToList(@PathVariable("listId") long listId, @RequestBody Card card) {
        if (!listService.addCardToList(listId, card))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateName/{listId}")
    public ResponseEntity updateListName(@PathVariable("listId")long id,
                                         @RequestBody String newName) {
        if(listService.updateName(id,newName))
            return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();

    }

}

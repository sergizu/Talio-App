package server.api;

import commons.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.ClientService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @GetMapping("/{id}/boards")
    public ResponseEntity<List<Board>> getBoardsForClient(@PathVariable("id") int clientId) {
        //ArrayList<Board> boards = clientService.getBoardsForClient(clie)
        return ResponseEntity.ok(new ArrayList<Board>());
    }

}

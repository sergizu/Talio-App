package server.api;

import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.ListRepository;
import server.service.BoardService;
import server.service.ListService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ListControllerTest {
        @Mock ListService listService;
        @Mock SimpMessagingTemplate messagingTemplate;

        @Mock
        BoardService boardService;

        @Mock
        ListRepository listRepository;
        ListController listController;

        @BeforeEach
        void setUp() {
                listController = new ListController(listService, boardService, listRepository);
        }
        @Test
        void getAll() {
                listController.getAll();
                verify(listService).getAll();
        }

        @Test
        void add() {
                TDList list = new TDList("list1");
                given(listService.addList(list)).willReturn(list);
                assertEquals(listController.add(list), ResponseEntity.ok(list));
                verify(listService).addList(list);
        }

        @Test
        void addAlreadyWhenExists() {
                TDList list = new TDList("list1");
                given(listService.addList(list)).willReturn(null);
                assertEquals(listController.add(list), ResponseEntity.badRequest().build());
                verify(listService).addList(list);
        }

        @Test
        void getById() {
                TDList list = new TDList("list1");
                given(listService.getById(list.id)).willReturn(list);
                assertEquals(ResponseEntity.ok(list), listController.getById(list.id));
                verify(listService).getById(list.id);
        }

        @Test
        void getByIdNotExisting() {
                TDList list = new TDList("list1");
                given(listService.getById(list.id)).willReturn(null);
                assertEquals(ResponseEntity.badRequest().build(), listController.getById(list.id));
                verify(listService).getById(list.id);
        }

        @Test
        void update() {
                TDList list = new TDList("list1");
                given(listService.update(list)).willReturn(list);
                assertEquals(ResponseEntity.ok(list), listController.update(list));
                verify(listService).update(list);
        }

        @Test
        void updateNonExisting() {
                TDList list = new TDList("list1");
                given(listService.update(list)).willReturn(null);
                assertEquals(ResponseEntity.badRequest().build(), listController.update(list));
                verify(listService).update(list);
        }

        @Test
        void removeByID() {
                TDList list = new TDList("lIST 1");
                given(listService.delete(list.id)).willReturn(true);
                assertEquals(listController.removeByID(list.id), ResponseEntity.ok().build());
                verify(listService).delete(list.id);
        }

        @Test
        void RemoveNotExist() {
                TDList list = new TDList("lIST 1");
                given(listService.delete(list.id)).willReturn(false);
                assertEquals(listController.removeByID(list.id), ResponseEntity.badRequest().build());
                verify(listService).delete(list.id);
        }
}
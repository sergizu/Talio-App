package server.api;

import commons.Card;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import server.service.ListService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListControllerTest {
        @Mock ListService listService;

        ListController listController;

        @BeforeEach
        void setUp() {
                listController = new ListController(listService);
        }
        @Test
        void getAll() {
                listController.getAll();
                verify(listService).getAll();
        }

        @Test
        void add() {
                TDList list = new TDList("list1");
                when(listService.addList(list)).thenReturn(list);
                assertEquals(listController.add(list), ResponseEntity.ok(list));
                verify(listService).addList(list);
        }

        @Test
        void addAlreadyWhenExists() {
                TDList list = new TDList("list1");
                when(listService.addList(list)).thenReturn(null);
                assertEquals(listController.add(list), ResponseEntity.badRequest().build());
                verify(listService).addList(list);
        }

        @Test
        void getById() {
                TDList list = new TDList("list1");
                when(listService.getById(list.id)).thenReturn(list);
                assertEquals(ResponseEntity.ok(list), listController.getById(list.id));
                verify(listService).getById(list.id);
        }

        @Test
        void getByIdNotExisting() {
                TDList list = new TDList("list1");
                when(listService.getById(list.id)).thenReturn(null);
                assertEquals(ResponseEntity.badRequest().build(), listController.getById(list.id));
                verify(listService).getById(list.id);
        }

        @Test
        void update() {
                TDList list = new TDList("list1");
                when(listService.update(list)).thenReturn(list);
                assertEquals(ResponseEntity.ok(list), listController.update(list));
                verify(listService).update(list);
        }

        @Test
        void updateNonExisting() {
                TDList list = new TDList("list1");
                when(listService.update(list)).thenReturn(null);
                assertEquals(ResponseEntity.badRequest().build(), listController.update(list));
                verify(listService).update(list);
        }

        @Test
        void removeByID() {
                TDList list = new TDList("lIST 1");
                when(listService.delete(list.id)).thenReturn(true);
                assertEquals(listController.removeByID(list.id), ResponseEntity.ok().build());
                verify(listService).delete(list.id);
        }

        @Test
        void RemoveNotExist() {
                TDList list = new TDList("lIST 1");
                when(listService.delete(list.id)).thenReturn(false);
                assertEquals(listController.removeByID(list.id), ResponseEntity.badRequest().build());
                verify(listService).delete(list.id);
        }
        @Test
        void addCardToListIfNotExists(){
                when(listService.addCardToList(any(Long.class), any(Card.class))).thenReturn(false);
                assertEquals(ResponseEntity.badRequest().build(),
                        listController.addCardToList(1L, new Card("c1")));
        }

        @Test
        void addCardToListIfExists(){
                when(listService.addCardToList(any(Long.class), any(Card.class))).thenReturn(true);
                assertEquals(ResponseEntity.ok().build(),
                        listController.addCardToList(1L, new Card("c1")));
        }

        @Test
        void updateListNameIfExists(){
                when(listService.updateName(any(Long.class), any(String.class))).thenReturn(true);
                assertEquals(ResponseEntity.ok().build(),
                        listController.updateListName(1L, "a"));
        }
        @Test
        void updateListNameIfNotExists(){
                when(listService.updateName(any(Long.class), any(String.class))).thenReturn(false);
                assertEquals(ResponseEntity.badRequest().build(),
                        listController.updateListName(1L, "a"));
        }
}
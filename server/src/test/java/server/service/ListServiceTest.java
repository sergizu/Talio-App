package server.service;

import commons.Board;
import commons.TDList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.ListRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListServiceTest {

    @Mock
    private ListRepository listRepository;
    private ListService listService;
    @Mock
    private BoardService boardService;


    @BeforeEach
    void setUp() {
        listService = new ListService(listRepository, boardService);
    }

    @Test
    void getAll() {
        listService.getAll();
        verify(listRepository).findAll();
    }

    @Test
    void getByIdIfExists() {
        long id = 1;
        when(listRepository.existsById(id)).thenReturn(true);
        TDList list = new TDList("test list");
        list.id = id;
        given(listRepository.findById(id)).willReturn(Optional.of(list));
        assertEquals(list, listService.getById(id));
    }

    @Test
    void getByIdIfNotExists() {
        long id = 1;
        when(listRepository.existsById(id)).thenReturn(false);
        listService.getById(id);
        verify(listRepository, never()).findById(anyLong());
    }


    @Test
    void addList() {
        TDList toAdd = new TDList("list1");
        toAdd.id = 1;
        when(listRepository.existsById(toAdd.id)).thenReturn(false);
        when(listRepository.save(toAdd)).thenReturn(toAdd);
        listService.addList(toAdd);
        verify(listRepository).save(toAdd);
    }

    @Test
    void addListIfExists() {
        TDList toAdd = new TDList("list1");
        toAdd.id = 1;
        when(listRepository.existsById(toAdd.id)).thenReturn(true);
        listService.addList(toAdd);
        verify(listRepository, never()).save(toAdd);
    }

    @Test
    void existsById() {
        long id = 1;
        listService.existsById(id);
        verify(listRepository).existsById(id);
    }

    @Test
    void update() {
        TDList list = new TDList("list1");
        list.id = 1;
        when(listRepository.existsById(list.id)).thenReturn(true);
        listService.update(list);
        verify(listRepository).save(list);
    }

    @Test
    void updateIfNotExists() {
        TDList list = new TDList("list1");
        list.id = 1;
        when(listRepository.existsById(list.id)).thenReturn(false);
        listService.update(list);
        verify(listRepository, never()).save(list);
    }

    @Test
    public void testDelete() {
        TDList list = new TDList("list1");
        Board board = new Board("board1");
        list.id = 1;
        list.setBoard(board);
        when(listRepository.existsById(list.getId())).thenReturn(true);
        when(listRepository.getById(list.getId())).thenReturn(list);
        listService.delete(list.getId());
        verify(listRepository).deleteById(list.getId());
    }

    @Test
    public void testDeleteNotExists() {
        TDList list = new TDList("list1");
        list.id = 1;
        when(listRepository.existsById(list.getId())).thenReturn(false);
        listService.delete(list.getId());
        verify(listRepository, never()).deleteById(list.getId());
    }

    @Test
    public void testUpdateNameExists() {
        TDList list = new TDList("list1");
        Board board = new Board("board1");
        list.id = 1;
        list.setBoard(board);
        when(listRepository.existsById(list.id)).thenReturn(true);
        when(listRepository.getById(list.id)).thenReturn(list);
        list.setTitle("new name");
        when(listRepository.save(list)).thenReturn(list);
        listService.updateName(list.id, "new name");
        verify(listRepository).save(list);
    }

    @Test
    public void testUpdateNameNotExists() {
        TDList list = new TDList("list1");
        list.id = 1;
        when(listRepository.existsById(list.id)).thenReturn(false);
        listService.updateName(list.id, "new name");
        verify(listRepository, never()).save(list);
    }

}
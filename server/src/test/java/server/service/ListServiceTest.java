package server.service;

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
}
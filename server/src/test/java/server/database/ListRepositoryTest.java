package server.database;

import commons.TDList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ListRepositoryTest {

    @Autowired
    ListRepository listRepository;

    @Test
    public void addListTest() {
        TDList list = new TDList("list1");
        listRepository.save(list);
        assertTrue(listRepository.existsById(list.id));
    }
}


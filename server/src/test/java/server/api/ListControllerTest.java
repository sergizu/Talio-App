package server.api;

import org.junit.jupiter.api.Test;
import server.database.ListRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;





    class ListControllerTest {
        private ListRepository listRepository;

        @BeforeEach
        public void setup() {
            listRepository = new TestListRepositry();
        }

        @Test
        void getById() {
        }

        @Test
        void getAll() {
        }

        @Test
        void add() {
        }
    }

package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testEmptyConstructor() {
        Main main = new Main();
        assertNotNull(main);
    }

}
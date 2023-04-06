package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppClientTest {
    @Test
    void testEmptyConstructor() {
        AppClient appClient = new AppClient();
        assertNotNull(appClient);
    }
}
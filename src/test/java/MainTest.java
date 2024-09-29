import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void RESP_1_test_1(){
        Game game = new Game();
        game.initialize();

        assertEquals(100, game.adventureDeckSize());

    }
    @Test
    void RESP_1_test_2() {
        Game game = new Game();
        game.initialize();

        assertEquals(17, game.eventDeckSize());
    }
}

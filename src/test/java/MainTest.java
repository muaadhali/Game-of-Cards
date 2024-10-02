import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
//------------------------- RESP-1 ------------------------------------------//

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

//------------------------- RESP-2 ------------------------------------------//
    
    @Test
    void RESP_2_test_1() {
        Game game = new Game();
        game.initialize();

        assertEquals(12, game.getPlayer1HandSize());
        assertEquals(12, game.getPlayer2HandSize());
        assertEquals(12, game.getPlayer3HandSize());
        assertEquals(12, game.getPlayer4HandSize());


        assertEquals(12*4, 100 - game.adventureDeckSize());
    }
}

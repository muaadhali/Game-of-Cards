import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
//------------------------- RESP-1 ------------------------------------------//

    @Test
    void RESP_1_test_1(){
        Game game = new Game(4);
        game.initialize();

        assertEquals(100, game.getAdventureDeckSize());

    }
    @Test
    void RESP_1_test_2() {
        Game game = new Game(4);
        game.initialize();
        game.initializeHands();

        assertEquals(17, game.getEventDeckSize());
    }

//------------------------- RESP-2 ------------------------------------------//

    @Test
    void RESP_2_test_1() {
        Game game = new Game(4);
        game.initialize();
        game.initializeHands();

        assertEquals(12, game.getPlayer1HandSize());
        assertEquals(12, game.getPlayer2HandSize());
        assertEquals(12, game.getPlayer3HandSize());
        assertEquals(12, game.getPlayer4HandSize());


        assertEquals(12*4, 100 - game.getAdventureDeckSize());
    }

//------------------------- RESP-3 ------------------------------------------//

    @Test
    void RESP_3_test_1() {
        Game game = new Game(4);
        game.initialize();
        game.initializeHands();

        game.playTurn(game.players.getFirst());

        assertEquals(game.players.getFirst(), game.currPlayer);
        assertEquals(16, game.getEventDeckSize());
        assertEquals(1, game.getEventDiscardSize());
    }

//------------------------- RESP-4 ------------------------------------------//

    @Test
    void RESP_4_test_1() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.players.getFirst().shields = 7;
        game.players.getLast().shields = 7;

        ByteArrayInputStream in = new ByteArrayInputStream("\n\n".getBytes());
        System.setIn(in);
        game.playTurn(game.players.getFirst());

        assertFalse(game.winners.isEmpty());
        assertEquals(game.winners.getFirst(), (game.players.getFirst()));
    }

    @Test
    void RESP_4_test_2() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        ByteArrayInputStream in = new ByteArrayInputStream("\n\n".getBytes());
        System.setIn(in);
        game.playTurn(game.players.getFirst());

        assertTrue(game.winners.isEmpty());
    }
}

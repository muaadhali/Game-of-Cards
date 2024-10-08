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

        ByteArrayInputStream in = new ByteArrayInputStream("\n\n".getBytes());
        System.setIn(in);

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

//------------------------- RESP-5 ------------------------------------------//

    @Test
    void RESP_5_test_1() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        Card plague = new EventCard("Plague", "The player who draws this card immediately loses 2 shields.");

        game.currCard = plague;
        game.players.getFirst().shields = 4;
        game.currPlayer = game.players.getFirst();

        game.resolveEvent();

        assertEquals(2, game.players.getFirst().shields);
    }

    @Test
    void RESP_5_test_2() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        Card queen = new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards.");

        game.currCard = queen;
        game.currPlayer = game.players.getFirst();

        game.resolveEvent();

        assertEquals(14, game.players.getFirst().getHandSize());
    }

    @Test
    void RESP_5_test_3() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        Card prosper = new EventCard("Prosperity", "All players immediately draw 2 adventure cards.");

        game.currCard = prosper;
        game.currPlayer = game.players.getFirst();

        game.resolveEvent();

        for (int i = 0; i < game.players.size(); i++) {
            assertEquals(14, game.players.get(i).getHandSize());
        }
    }

//------------------------- RESP-6 ------------------------------------------//

    @Test
    void RESP_6_test_1() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.eventDeck.clear();
        game.eventDeck.add(new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards."));

        ByteArrayInputStream in = new ByteArrayInputStream("\n\n".getBytes());
        System.setIn(in);

        game.playTurn(game.players.getFirst());

        assertEquals(3, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }

    @Test
    void RESP_6_test_2() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.eventDeck.clear();
        game.eventDeck.add(new EventCard("Plague", "The player who draws this card immediately loses 2 shields."));

        ByteArrayInputStream in = new ByteArrayInputStream("\n\n".getBytes());
        System.setIn(in);

        game.playTurn(game.players.getFirst());

        assertEquals(0, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }
}

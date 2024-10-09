import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
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

        game.drawEvent(game.players.getFirst());

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

        game.endTurn();

        assertFalse(game.winners.isEmpty());
        assertEquals(game.winners.getFirst(), (game.players.getFirst()));
        assertTrue(game.winners.contains(game.players.getFirst()));
        assertTrue(game.winners.contains(game.players.getLast()));
    }

    @Test
    void RESP_4_test_2() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.drawEvent(game.players.getFirst());
        game.endTurn();

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

        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("2").thenReturn("55").thenReturn("hj").thenReturn("4");

        game.initialize();
        game.initializeHands();

        game.currCard = new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards.");
        game.currPlayer = game.players.getFirst();
        game.resolveEvent();

        game.trim(scanner);

        assertEquals(2, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }

    @Test
    void RESP_6_test_2() {
        Game game = new Game(4);

        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("2").thenReturn("55").thenReturn("hj").thenReturn("4");

        game.initialize();
        game.initializeHands();

        game.currCard = new EventCard("Plague", "The player who draws this card immediately loses 2 shields.");
        game.currPlayer = game.players.getFirst();
        game.resolveEvent();

        game.trim(scanner);

        assertEquals(0, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }

//------------------------- RESP-7 ------------------------------------------//

    @Test
    void RESP_7_test_1() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);

        game.players.getFirst().hand.add(new AdventureCard("Foe", 5));
        game.players.getFirst().hand.add(new AdventureCard("Foe", 10));

        game.players.getLast().hand.add(new AdventureCard("Foe", 5));
        game.players.getLast().hand.add(new AdventureCard("Foe", 10));

        for (int i = 0; i < game.players.get(1).hand.size(); i++) {
            if (game.players.get(1).hand.get(i).getName().equals("Foe")) {
                game.players.get(1).hand.remove(i);
                i--;
            }
        }

        for (int i = 0; i < game.players.get(2).hand.size(); i++) {
            if (game.players.get(2).hand.get(i).getName().equals("Foe")) {
                game.players.get(2).hand.remove(i);
                i--;
            }
        }

        ArrayList<Player> eligibleSponsors = game.checkSponsorEligibility();

        assertTrue(eligibleSponsors.contains(game.players.getFirst()));
        assertTrue(eligibleSponsors.contains(game.players.getLast()));
        assertEquals(2, eligibleSponsors.size());

    }
}

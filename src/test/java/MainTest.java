import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
//------------------------- RESP-1 ------------------------------------------//

    @Test
    @DisplayName("Check initialized adventure deck size")
    void RESP_1_test_1(){
        Game game = new Game(4);
        game.initialize();

        assertEquals(100, game.getAdventureDeckSize());

    }
    @Test
    @DisplayName("Check event deck size")
    void RESP_1_test_2() {
        Game game = new Game(4);
        game.initialize();
        game.initializeHands();

        assertEquals(17, game.getEventDeckSize());
    }

//------------------------- RESP-2 ------------------------------------------//

    @Test
    @DisplayName("Check player hand size and adventure deck size after initializing hands")
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
    @DisplayName("Check event deck and discard pile size after drawing event card")
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
    @DisplayName("Check winners after players reach max shields")
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
    @DisplayName("Check no winners when players are not max shields")
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
    @DisplayName("Check player shields after Plague event")
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
    @DisplayName("Check player hand size after Queen's Favor event")
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
    @DisplayName("Check players' hand sizes after Prosperity event")
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
    @DisplayName("Check player hand size after trimming")
    void RESP_6_test_1() {
        Game game = new Game(4);

        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("2").thenReturn("55").thenReturn("hj").thenReturn("4");

        game.initialize();
        game.initializeHands();

        game.currCard = new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards.");
        game.currPlayer = game.players.getFirst();
        game.resolveEvent();

        game.trim(game.currPlayer, scanner);

        assertEquals(2, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }

    @Test
    @DisplayName("Check player hands after trimming when hand size is normal")
    void RESP_6_test_2() {
        Game game = new Game(4);

        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("2").thenReturn("55").thenReturn("hj").thenReturn("4");

        game.initialize();
        game.initializeHands();

        game.currCard = new EventCard("Plague", "The player who draws this card immediately loses 2 shields.");
        game.currPlayer = game.players.getFirst();
        game.resolveEvent();

        game.trim(game.currPlayer, scanner);

        assertEquals(0, game.getAdventureDiscardSize());
        assertEquals(12, game.players.getFirst().getHandSize());
    }

//------------------------- RESP-7 ------------------------------------------//

    @Test
    @DisplayName("Check number of eligible sponsors for quest")
    void RESP_7_test_1() {
        Game game = new Game(4);

        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

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

//------------------------- RESP-8 ------------------------------------------//

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_1() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);

        assertTrue(game.isCardValid(stage, foe1));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_2() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);

        stage.add(foe2);

        assertFalse(game.isCardValid(stage, foe1));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_3() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);

        stage.add(foe1);

        assertTrue(game.isCardValid(stage, weapon1));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_4() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Dagger", 5);

        stage.add(foe1);
        stage.add(weapon1);

        assertFalse(game.isCardValid(stage, weapon2));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_5() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);

        stage.add(foe1);
        stage.add(weapon1);

        assertTrue(game.isCardValid(stage, weapon2));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_6() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Card> stage2 = new ArrayList<>();
        ArrayList<ArrayList<Card>> stages = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);

        stage.add(foe1);
        stages.add(stage);

        stage2.add(foe2);
        stage2.add(weapon1);


        assertTrue(game.isStageValid(stages, stage2, true));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_7() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Card> stage2 = new ArrayList<>();
        ArrayList<ArrayList<Card>> stages = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);

        stage.add(foe2);
        stage.add(weapon1);
        stages.add(stage);

        stage2.add(foe1);


        assertFalse(game.isStageValid(stages, stage2, true));
    }

    @Test
    @DisplayName("Check validity of card being added to a stage")
    void RESP_8_test_8() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Card> stage2 = new ArrayList<>();
        ArrayList<ArrayList<Card>> stages = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);

        stage.add(foe1);
        stage.add(weapon1);
        stages.add(stage);

        stage2.add(foe2);

        assertFalse(game.isStageValid(stages, stage2, true));
    }

//------------------------- RESP-9 ------------------------------------------//

    @Test
    @DisplayName("Check player eligibility for tackling a stage")
    void RESP_9_test_1() {
        Game game = new Game(4);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Card> stage2 = new ArrayList<>();
        ArrayList<ArrayList<Card>> stages = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        for (Player i : game.players) {
            i.hand.clear();
        }


        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);
        Card weapon3 = new AdventureCard("Excalibur", 30);

        game.players.getFirst().hand.add(weapon1);
        game.players.getFirst().hand.add(weapon2);
        game.players.getFirst().hand.add(weapon3);
        game.players.getLast().hand.add(weapon3);

        stage.add(foe1);
        stage.add(weapon1);
        stages.add(stage);

        stage2.add(foe2);
        stage2.add(weapon2);
        stages.add(stage2);

        assertTrue(game.isEligibleForStage(stages.getFirst(), game.players.getFirst()));
        assertFalse(game.isEligibleForStage(stages.getFirst(), game.players.get(1)));
        assertFalse(game.isEligibleForStage(stages.getFirst(), game.players.get(2)));
        assertTrue(game.isEligibleForStage(stages.getFirst(), game.players.getLast()));
    }

//------------------------- RESP-10 ------------------------------------------//

    @Test
    @DisplayName("Check player draws same number of cards used in setting up quest")
    void RESP_10_test_1() {
        Game game = new Game(4);
        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("yes").thenReturn("1").thenReturn("1").thenReturn("quit").thenReturn("n").thenReturn("n").thenReturn("n");

        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 1, 1);
        game.currPlayer = game.players.getFirst();

        game.players.getFirst().hand.clear();


        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);

        game.players.getFirst().hand.add(weapon1);
        game.players.getFirst().hand.add(foe1);

        game.resolveQuest(scanner);

        assertEquals(2, game.players.getFirst().getHandSize());
    }

//------------------------- RESP-11 ------------------------------------------//

    @Test
    @DisplayName("Check a player successfully sets up his attack and the cards are no longer in his hand")
    void RESP_11_test_1() {
        Game game = new Game(4);
        ArrayList<Card> stage = new ArrayList<>();
        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("1").thenReturn("1").thenReturn("quit");


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 1, 1);
        game.currPlayer = game.players.getFirst();

        game.players.getFirst().hand.clear();


        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);
        Card weapon3 = new AdventureCard("Excalibur", 30);

        game.players.getFirst().hand.add(weapon1);
        game.players.getFirst().hand.add(weapon2);
        game.players.getFirst().hand.add(weapon3);

        stage.add(foe1);
        stage.add(weapon1);
        game.quest.add(stage);

        game.setupAttack(game.players.getFirst(), stage, scanner);

        assertEquals(1, game.players.getFirst().getHandSize());
        assertEquals(weapon1, game.attack.get(game.players.getFirst().getId()).getFirst());
        assertEquals(weapon2, game.attack.get(game.players.getFirst().getId()).getLast());
    }

//------------------------- RESP-12 ------------------------------------------//

    @Test
    @DisplayName("Check that players are removed from the quest when they fail the stage or are kept when they pass")
    void RESP_12_test_1() {
        Game game = new Game(2);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Player> removePlayers = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        for (Player i : game.players) {
            i.hand.clear();
        }


        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);
        Card weapon3 = new AdventureCard("Excalibur", 30);

        game.attack.get(1).add(weapon1);
        game.attack.get(2).add(weapon3);

        stage.add(foe1);
        stage.add(weapon1);
        game.quest.add(stage);

        game.resolveAttack(removePlayers, game.quest.getFirst());

        assertEquals(1, removePlayers.size());
        assertEquals(1, removePlayers.getFirst().getId());

    }

    @Test
    @DisplayName("Check that players attack is cleared after resolving")
    void RESP_12_test_2() {
        Game game = new Game(2);

        ArrayList<Card> stage = new ArrayList<>();
        ArrayList<Player> removePlayers = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 2, 2);
        game.currPlayer = game.players.getFirst();

        for (Player i : game.players) {
            i.hand.clear();
        }


        Card foe1 = new AdventureCard("Foe", 5);
        Card foe2 = new AdventureCard("Foe", 10);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);
        Card weapon3 = new AdventureCard("Excalibur", 30);

        game.attack.get(1).add(weapon1);
        game.attack.get(2).add(weapon3);

        stage.add(foe1);
        stage.add(weapon1);
        game.quest.add(stage);

        game.resolveAttack(removePlayers, game.quest.getFirst());

        assertTrue(game.attack.get(1).isEmpty());
        assertTrue(game.attack.get(2).isEmpty());
    }
}

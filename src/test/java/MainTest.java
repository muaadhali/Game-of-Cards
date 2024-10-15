import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import java.util.ArrayList;
import java.util.HashMap;
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
    @DisplayName("Check player draws same number of cards used in setting up quest plush the number of quest stages")
    void RESP_10_test_1() {
        Game game = new Game(4);
        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("yes").thenReturn("1").thenReturn("1").thenReturn("quit").thenReturn("n").thenReturn("n").thenReturn("n");

        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 1, 1);
        game.currPlayer = game.players.getFirst();
        game.currSponsor = game.players.getFirst();

        game.players.getFirst().hand.clear();


        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);

        game.players.getFirst().hand.add(weapon1);
        game.players.getFirst().hand.add(foe1);

        game.resolveQuest(scanner);

        assertEquals(3, game.players.getFirst().getHandSize());
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

//------------------------- RESP-12 ------------------------------------------//

    @Test
    @DisplayName("Check that players are awarded correctly after quest finishes")
    void RESP_13_test_1() {
        Game game = new Game(3);
        Scanner scanner = Mockito.mock(Scanner.class);
        Mockito.when(scanner.nextLine()).thenReturn("y").thenReturn("1").thenReturn("2").thenReturn("quit").thenReturn("y").thenReturn("1").thenReturn("quit");

        ArrayList<Card> stage = new ArrayList<>();


        game.initialize();
        game.initializeHands();

        game.currCard = new QuestCard("Quest", 1, 1);
        game.currPlayer = game.players.get(1);
        game.currSponsor = game.players.get(1);

        for (Player i : game.players) {
            i.hand.clear();
        }


        Card foe1 = new AdventureCard("Foe", 5);
        Card weapon1 = new AdventureCard("Dagger", 5);
        Card weapon2 = new AdventureCard("Sword", 10);
        Card weapon3 = new AdventureCard("Excalibur", 30);

        game.players.getFirst().hand.add(weapon1);
        game.players.getFirst().hand.add(weapon2);
        game.players.getFirst().hand.add(weapon3);
        game.players.getLast().hand.add(weapon1);
        game.players.getLast().hand.add(weapon2);
        game.players.getLast().hand.add(weapon3);

        stage.add(foe1);
        stage.add(weapon1);
        game.quest.add(stage);

        game.playQuest(scanner);

        assertEquals(1, game.players.getFirst().shields);
        assertEquals(0, game.players.getLast().shields);
    }

//------------------------- Mandatory A-TEST ------------------------------------------//

    @Test
    @DisplayName("A-TEST JP-Scenario")
    void A_TEST_JP_SCENARIO() {
        Game game = new Game(4);
        Scanner scanner1 = Mockito.mock(Scanner.class);
        Scanner scanner2 = Mockito.mock(Scanner.class);
        Scanner scanner3 = Mockito.mock(Scanner.class);
        Scanner scanner4 = Mockito.mock(Scanner.class);
        Scanner scanner5 = Mockito.mock(Scanner.class);

        Mockito.when(scanner1.nextLine()).thenReturn("1").thenReturn("7").thenReturn("quit").thenReturn("2").thenReturn("5").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("4").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("quit");
        Mockito.when(scanner2.nextLine()).thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("4").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("5").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("5").thenReturn("7").thenReturn("quit");
        Mockito.when(scanner3.nextLine()).thenReturn("yes").thenReturn("5").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("8").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("6").thenReturn("6").thenReturn("quit");
        Mockito.when(scanner4.nextLine()).thenReturn("yes").thenReturn("7").thenReturn("6").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("8").thenReturn("5").thenReturn("5").thenReturn("quit");
        Mockito.when(scanner5.nextLine()).thenReturn("yes").thenReturn("7").thenReturn("4").thenReturn("5").thenReturn("quit").thenReturn("yes").thenReturn("4").thenReturn("7").thenReturn("5").thenReturn("4").thenReturn("quit");
        game.initialize();
        game.initializeHands();

        for (int i = 0; i < 12; i++) {
            game.adventureDeck.add(game.players.get(0).hand.removeFirst());
            game.adventureDeck.add(game.players.get(1).hand.removeFirst());
            game.adventureDeck.add(game.players.get(2).hand.removeFirst());
            game.adventureDeck.add(game.players.get(3).hand.removeFirst());
        }

        addCard("Foe", 5, 2, game.players.getFirst(), game);
        addCard("Foe", 15, 2, game.players.getFirst(), game);
        addCard("Dagger", 5, 1, game.players.getFirst(), game);
        addCard("Sword", 10, 2, game.players.getFirst(), game);
        addCard("Horse", 10, 2, game.players.getFirst(), game);
        addCard("Battle-Axe", 15, 2, game.players.getFirst(), game);
        addCard("Lance", 20, 1, game.players.getFirst(), game);
        System.out.println(game.players.getFirst().printableHand());

        addCard("Foe", 5, 2, game.players.get(1), game);
        addCard("Foe", 15, 2, game.players.get(1), game);
        addCard("Foe", 40, 1, game.players.get(1), game);
        addCard("Dagger", 5, 1, game.players.get(1), game);
        addCard("Sword", 10, 1, game.players.get(1), game);
        addCard("Horse", 10, 2, game.players.get(1), game);
        addCard("Battle-Axe", 15, 2, game.players.get(1), game);
        addCard("Excalibur", 30, 1, game.players.get(1), game);
        System.out.println(game.players.get(1).printableHand());

        addCard("Foe", 5, 3, game.players.get(2), game);
        addCard("Foe", 15, 1, game.players.get(2), game);
        addCard("Dagger", 5, 1, game.players.get(2), game);
        addCard("Sword", 10, 3, game.players.get(2), game);
        addCard("Horse", 10, 2, game.players.get(2), game);
        addCard("Battle-Axe", 15, 1, game.players.get(2), game);
        addCard("Lance", 20, 1, game.players.get(2), game);
        System.out.println(game.players.get(2).printableHand());

        addCard("Foe", 5, 1, game.players.getLast(), game);
        addCard("Foe", 15, 2, game.players.getLast(), game);
        addCard("Foe", 40, 1, game.players.getLast(), game);
        addCard("Dagger", 5, 2, game.players.getLast(), game);
        addCard("Sword", 10, 1, game.players.getLast(), game);
        addCard("Horse", 10, 2, game.players.getLast(), game);
        addCard("Battle-Axe", 15, 1, game.players.getLast(), game);
        addCard("Lance", 20, 1, game.players.getLast(), game);
        addCard("Excalibur", 30, 1, game.players.getLast(), game);
        System.out.println(game.players.getLast().printableHand());

        game.drawEvent(game.players.getFirst());

        game.currCard = new QuestCard("Quest",4, 4);
        game.currSponsor = game.players.get(1);


        game.setupQuest(game.currSponsor, scanner1);

        ArrayList<Player> eligiblePlayers = new ArrayList<>();

        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i) != game.currSponsor) {
                eligiblePlayers.add(game.players.get(i));
            }
        }

        addCard("Foe", 30, 1, game.players.getFirst(), game);
        addCard("Sword", 10, 1, game.players.get(2), game);
        addCard("Battle-Axe", 15, 1, game.players.getLast(), game);

        game.playStage(game.quest.getFirst(), eligiblePlayers, scanner2);

        addCard("Foe", 10, 1, game.players.getFirst(), game);
        addCard("Lance", 20, 1, game.players.get(2), game);
        addCard("Lance", 20, 1, game.players.getLast(), game);

        game.playStage(game.quest.get(1), eligiblePlayers, scanner3);

        for (int i = 0; i < game.players.size(); i++) {
            game.players.get(i).hand.removeLast();
        }

        assertEquals(0, game.players.getFirst().shields);
        ArrayList<Card> dummy = new ArrayList<>();
        dummy.add(new AdventureCard("Foe", 5));
        dummy.add(new AdventureCard("Foe", 10));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 30));
        dummy.add(new AdventureCard("Horse", 10));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Lance", 20));

//        for (Card c : dummy) {
//            assertTrue(game.players.getFirst().hand.contains(c));
//        }

        addCard("Battle-Axe", 15, 1, game.players.get(2), game);
        addCard("Sword", 10, 1, game.players.getLast(), game);

        game.playStage(game.quest.get(2), eligiblePlayers, scanner4);

        game.players.getLast().hand.removeLast();
        game.players.get(2).hand.removeLast();

        addCard("Foe", 30, 1, game.players.get(2), game);
        addCard("Lance", 20, 1, game.players.getLast(), game);

        game.playStage(game.quest.get(3), eligiblePlayers, scanner5);
        game.players.getLast().hand.removeLast();
        game.players.get(2).hand.removeLast();

        game.rewardPlayers(eligiblePlayers);

        assertEquals(0, game.players.get(2).shields);
        assertEquals(4, game.players.getLast().shields);

    }

    void addCard(String cardName, int value, int num, Player player, Game game) {
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < game.adventureDeck.size(); j++) {
                if (cardName.equalsIgnoreCase("Foe") && game.adventureDeck.get(j).getName().equalsIgnoreCase("Foe")) {
                    if (value == ((AdventureCard) game.adventureDeck.get(j)).value) {
                        player.hand.add(game.adventureDeck.get(j));
                        game.adventureDiscard.add(game.adventureDeck.remove(j));
                        break;
                    }
                } else if (game.adventureDeck.get(j).getName().equalsIgnoreCase(cardName)){
                    player.hand.add(game.adventureDeck.get(j));
                    game.adventureDiscard.add(game.adventureDeck.remove(j));
                    break;
                }
            }
        }
    }

}

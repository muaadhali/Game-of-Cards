import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSteps {
    private Game game;
    ArrayList<Player> eligiblePlayers = new ArrayList<>();
    ArrayList<Player> removePlayers = new ArrayList<>();
    ArrayList<Card> dummy = new ArrayList<>();

    @Given("a new game with rigged hands from assignment one")
    public void a_new_game_for_a1_compulsory_test(){
        game = new Game(4);

        game.initialize();
        game.initializeHands();

        for (int i = 0; i < 12; i++) {
            game.adventureDeck.add(game.players.get(0).hand.removeFirst());
            game.adventureDeck.add(game.players.get(1).hand.removeFirst());
            game.adventureDeck.add(game.players.get(2).hand.removeFirst());
            game.adventureDeck.add(game.players.get(3).hand.removeFirst());
        }

        game.addCard("Foe", 5, 2, game.players.getFirst(), game);
        game.addCard("Foe", 15, 2, game.players.getFirst(), game);
        game.addCard("Dagger", 5, 1, game.players.getFirst(), game);
        game.addCard("Sword", 10, 2, game.players.getFirst(), game);
        game.addCard("Horse", 10, 2, game.players.getFirst(), game);
        game.addCard("Battle-Axe", 15, 2, game.players.getFirst(), game);
        game.addCard("Lance", 20, 1, game.players.getFirst(), game);
        System.out.println(game.players.getFirst().printableHand());

        game.addCard("Foe", 5, 2, game.players.get(1), game);
        game.addCard("Foe", 15, 2, game.players.get(1), game);
        game.addCard("Foe", 40, 1, game.players.get(1), game);
        game.addCard("Dagger", 5, 1, game.players.get(1), game);
        game.addCard("Sword", 10, 1, game.players.get(1), game);
        game.addCard("Horse", 10, 2, game.players.get(1), game);
        game.addCard("Battle-Axe", 15, 2, game.players.get(1), game);
        game.addCard("Excalibur", 30, 1, game.players.get(1), game);
        System.out.println(game.players.get(1).printableHand());

        game.addCard("Foe", 5, 3, game.players.get(2), game);
        game.addCard("Foe", 15, 1, game.players.get(2), game);
        game.addCard("Dagger", 5, 1, game.players.get(2), game);
        game.addCard("Sword", 10, 3, game.players.get(2), game);
        game.addCard("Horse", 10, 2, game.players.get(2), game);
        game.addCard("Battle-Axe", 15, 1, game.players.get(2), game);
        game.addCard("Lance", 20, 1, game.players.get(2), game);
        System.out.println(game.players.get(2).printableHand());

        game.addCard("Foe", 5, 1, game.players.getLast(), game);
        game.addCard("Foe", 15, 2, game.players.getLast(), game);
        game.addCard("Foe", 40, 1, game.players.getLast(), game);
        game.addCard("Dagger", 5, 2, game.players.getLast(), game);
        game.addCard("Sword", 10, 1, game.players.getLast(), game);
        game.addCard("Horse", 10, 2, game.players.getLast(), game);
        game.addCard("Battle-Axe", 15, 1, game.players.getLast(), game);
        game.addCard("Lance", 20, 1, game.players.getLast(), game);
        game.addCard("Excalibur", 30, 1, game.players.getLast(), game);
        System.out.println(game.players.getLast().printableHand());
    }

    @When("player1 draws a four stage quest")
    public void player_1_draws_quest_with_4_stages_a1_compulsory_test() {
        game.drawEvent(game.players.getFirst());
        game.currCard = new QuestCard("Quest",4, 4);
    }

    @When("player2 sponsors and sets up the quest")
    public void player_2_sponsors_quest_for_a1_compulsory_test() {
        Scanner scanner1 = Mockito.mock(Scanner.class);
        Mockito.when(scanner1.nextLine()).thenReturn("1").thenReturn("7").thenReturn("quit").thenReturn("2").thenReturn("5").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("4").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("quit");

        game.currSponsor = game.players.get(1);
        game.setupQuest(game.currSponsor, scanner1);

        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i) != game.currSponsor) {
                eligiblePlayers.add(game.players.get(i));
            }
        }
    }

    @When("players draw the first time")
    public void players_draw_the_first_time_a1_compulsory_test() {
        game.addCard("Foe", 30, 1, game.players.getFirst(), game);
        game.addCard("Sword", 10, 1, game.players.get(2), game);
        game.addCard("Battle-Axe", 15, 1, game.players.getLast(), game);

        game.sortHand(0);
        game.sortHand(2);
        game.sortHand(3);
    }

    @When("players play the first stage")
    public void players_play_first_stage_a1_compulsory_test() {
        Scanner scanner2 = Mockito.mock(Scanner.class);
        Mockito.when(scanner2.nextLine()).thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("5").thenReturn("5").thenReturn("quit").thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("5").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("1").thenReturn("13").thenReturn("5").thenReturn("7").thenReturn("quit");

        game.playStage(game.quest.getFirst(), eligiblePlayers, removePlayers, scanner2);

    }

    @When("players draw the second time")
    public void players_draw_the_second_time_a1_compulsory_test() {
        game.addCard("Foe", 10, 1, game.players.getFirst(), game);
        game.addCard("Lance", 20, 1, game.players.get(2), game);
        game.addCard("Lance", 20, 1, game.players.getLast(), game);

        game.sortHand(0);
        game.sortHand(2);
        game.sortHand(3);
    }

    @When("players play the second stage")
    public void players_play_second_stage_a1_compulsory_test() {
        Scanner scanner3 = Mockito.mock(Scanner.class);
        Mockito.when(scanner3.nextLine()).thenReturn("yes").thenReturn("7").thenReturn("6").thenReturn("quit").thenReturn("yes").thenReturn("9").thenReturn("6").thenReturn("quit").thenReturn("yes").thenReturn("6").thenReturn("6").thenReturn("quit");

        game.playStage(game.quest.get(1), eligiblePlayers, removePlayers, scanner3);

        for (int i = 0; i < game.players.size(); i++) {
            if (i != 1) {
                game.adventureDeck.add(game.players.get(i).hand.removeLast());
            }
        }
    }

    @Then("check player1's hand is correct and has {int} shields")
    public void check_player1_hand_and_shields_a1_compulsory_test(int numShields) {
        assertEquals(numShields, game.players.getFirst().shields);

        dummy.add(new AdventureCard("Foe", 5));
        dummy.add(new AdventureCard("Foe", 10));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 30));
        dummy.add(new AdventureCard("Horse", 10));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Lance", 20));

        System.out.println("Player 1: " + game.players.getFirst());
        for (int i = 0; i < dummy.size(); i++) {
            assertTrue(dummy.get(i).getName().equalsIgnoreCase(game.players.getFirst().hand.get(i).getName()));
            assertEquals(((AdventureCard) dummy.get(i)).value, ((AdventureCard) game.players.getFirst().hand.get(i)).value);
        }
    }

    @When("players draw the third time")
    public void players_draw_third_time_a1_compulsory_test() {
        game.addCard("Battle-Axe", 15, 1, game.players.get(2), game);
        game.addCard("Sword", 10, 1, game.players.getLast(), game);

        game.sortHand(2);
        game.sortHand(3);
    }

    @When("players play the third stage")
    public void players_play_third_stage_a1_compulsory_test() {
        Scanner scanner4 = Mockito.mock(Scanner.class);
        Mockito.when(scanner4.nextLine()).thenReturn("yes").thenReturn("10").thenReturn("7").thenReturn("4").thenReturn("quit").thenReturn("yes").thenReturn("7").thenReturn("5").thenReturn("7").thenReturn("quit");

        game.playStage(game.quest.get(2), eligiblePlayers, removePlayers, scanner4);

        game.adventureDeck.add(game.players.getLast().hand.removeLast());
        game.adventureDeck.add(game.players.get(2).hand.removeLast());
    }

    @When("players draw the fourth time")
    public void players_draw_fourth_time_a1_compulsory_test() {
        game.addCard("Foe", 30, 1, game.players.get(2), game);
        game.addCard("Lance", 20, 1, game.players.getLast(), game);

        game.sortHand(2);
        game.sortHand(3);
    }

    @When("players play the fourth stage and draw")
    public void players_play_fourth_stage_a1_compulsory_test() {
        Scanner scanner5 = Mockito.mock(Scanner.class);
        Mockito.when(scanner5.nextLine()).thenReturn("yes").thenReturn("7").thenReturn("6").thenReturn("6").thenReturn("quit").thenReturn("yes").thenReturn("4").thenReturn("4").thenReturn("5").thenReturn("5").thenReturn("quit");

        game.playStage(game.quest.getLast(), eligiblePlayers, removePlayers, scanner5);

        game.adventureDeck.add(game.players.getLast().hand.removeLast());
        game.adventureDeck.add(game.players.get(2).hand.removeLast());

        game.sortHand(2);
        game.sortHand(3);
    }

    @Then("players are rewarded and have correct number of shields")
    public void players_are_rewarded_and_have_correct_number_of_shields_a1_compulsory_test() {
        Scanner scanner6 = Mockito.mock(Scanner.class);
        Mockito.when(scanner6.nextLine()).thenReturn("1").thenReturn("1").thenReturn("1").thenReturn("1");

        game.rewardPlayers(eligiblePlayers);

        dummy.clear();
        dummy.add(new AdventureCard("Foe", 5));
        dummy.add(new AdventureCard("Foe", 5));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 30));
        dummy.add(new AdventureCard("Sword", 10));

        System.out.println("Player 3: " + game.players.get(2));
        for (int i = 0; i < dummy.size(); i++) {
            assertTrue(dummy.get(i).getName().equalsIgnoreCase(game.players.get(2).hand.get(i).getName()));
            assertEquals(((AdventureCard) dummy.get(i)).value, ((AdventureCard) game.players.get(2).hand.get(i)).value);
        }

        assertEquals(0, game.players.get(2).shields);

        dummy.clear();
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 40));
        dummy.add(new AdventureCard("Lance", 20));

        System.out.println("Player 4: " + game.players.getLast());
        for (int i = 0; i < dummy.size(); i++) {
            assertTrue(dummy.get(i).getName().equalsIgnoreCase(game.players.getLast().hand.get(i).getName()));
            assertEquals(((AdventureCard) dummy.get(i)).value, ((AdventureCard) game.players.getLast().hand.get(i)).value);
        }

        assertEquals(4, game.players.getLast().shields);

        game.drawAdventure(1, game.players.get(1).draw + ((QuestCard) game.currCard).stages);

        game.trim(game.players.get(1), scanner6);

        assertEquals(12, game.players.get(1).getHandSize());
    }


}

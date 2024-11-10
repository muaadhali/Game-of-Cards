import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSteps {
    private Game game;
    private ArrayList<Player> eligiblePlayers = new ArrayList<>();
    private ArrayList<Player> eligibleSponsors = new ArrayList<>();
    private ArrayList<Player> removePlayers = new ArrayList<>();
    private ArrayList<Card> dummy = new ArrayList<>();
    private ArrayList<Card> tempCards = new ArrayList<>();
    private ArrayList<String> playerInput = new ArrayList<>();
    private int currentPlayer = -1;

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

        game.addCard("Foe", 5, 2, game.players.getFirst());
        game.addCard("Foe", 15, 2, game.players.getFirst());
        game.addCard("Dagger", 5, 1, game.players.getFirst());
        game.addCard("Sword", 10, 2, game.players.getFirst());
        game.addCard("Horse", 10, 2, game.players.getFirst());
        game.addCard("Battle-Axe", 15, 2, game.players.getFirst());
        game.addCard("Lance", 20, 1, game.players.getFirst());
        System.out.println(game.players.getFirst().printableHand());

        game.addCard("Foe", 5, 2, game.players.get(1));
        game.addCard("Foe", 15, 2, game.players.get(1));
        game.addCard("Foe", 40, 1, game.players.get(1));
        game.addCard("Dagger", 5, 1, game.players.get(1));
        game.addCard("Sword", 10, 1, game.players.get(1));
        game.addCard("Horse", 10, 2, game.players.get(1));
        game.addCard("Battle-Axe", 15, 2, game.players.get(1));
        game.addCard("Excalibur", 30, 1, game.players.get(1));
        System.out.println(game.players.get(1).printableHand());

        game.addCard("Foe", 5, 3, game.players.get(2));
        game.addCard("Foe", 15, 1, game.players.get(2));
        game.addCard("Dagger", 5, 1, game.players.get(2));
        game.addCard("Sword", 10, 3, game.players.get(2));
        game.addCard("Horse", 10, 2, game.players.get(2));
        game.addCard("Battle-Axe", 15, 1, game.players.get(2));
        game.addCard("Lance", 20, 1, game.players.get(2));
        System.out.println(game.players.get(2).printableHand());

        game.addCard("Foe", 5, 1, game.players.getLast());
        game.addCard("Foe", 15, 2, game.players.getLast());
        game.addCard("Foe", 40, 1, game.players.getLast());
        game.addCard("Dagger", 5, 2, game.players.getLast());
        game.addCard("Sword", 10, 1, game.players.getLast());
        game.addCard("Horse", 10, 2, game.players.getLast());
        game.addCard("Battle-Axe", 15, 1, game.players.getLast());
        game.addCard("Lance", 20, 1, game.players.getLast());
        game.addCard("Excalibur", 30, 1, game.players.getLast());
        System.out.println(game.players.getLast().printableHand());
    }

    @When("player {int} draws a {int} stage quest and player {int} decides to sponsor it")
    public void player_1_draws_quest_with_4_stages_a1_compulsory_test(int drawP, int stages, int sponsor) {
        game.drawEvent(game.players.get(drawP - 1));
        game.currCard = new QuestCard("Quest", stages, stages);
        game.currSponsor = game.players.get(sponsor - 1);
    }

    @When("player2 sets up the quest")
    public void player_2_sponsors_quest_for_a1_compulsory_test() {
        Scanner scanner1 = Mockito.mock(Scanner.class);
        Mockito.when(scanner1.nextLine()).thenReturn("1").thenReturn("7").thenReturn("quit").thenReturn("2").thenReturn("5").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("4").thenReturn("quit").thenReturn("2").thenReturn("3").thenReturn("quit");

        game.setupQuest(game.currSponsor, scanner1);

        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i) != game.currSponsor) {
                eligiblePlayers.add(game.players.get(i));
            }
        }
    }

    @When("players draw the first time")
    public void players_draw_the_first_time_a1_compulsory_test() {
        game.addCard("Foe", 30, 1, game.players.getFirst());
        game.addCard("Sword", 10, 1, game.players.get(2));
        game.addCard("Battle-Axe", 15, 1, game.players.getLast());

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
        game.addCard("Foe", 10, 1, game.players.getFirst());
        game.addCard("Lance", 20, 1, game.players.get(2));
        game.addCard("Lance", 20, 1, game.players.getLast());

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

    @When("check player1's hand is correct and has {int} shields")
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
        game.addCard("Battle-Axe", 15, 1, game.players.get(2));
        game.addCard("Sword", 10, 1, game.players.getLast());

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
        game.addCard("Foe", 30, 1, game.players.get(2));
        game.addCard("Lance", 20, 1, game.players.getLast());

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

    @Given("a new game with rigged hands and decks for A2 scenarios")
    public void new_rigged_game_a2_scenario_2() {
        game = new Game(4);

        game.initialize();
        game.initializeHands();

        for (int i = 0; i < 12; i++) {
            game.adventureDeck.add(game.players.get(0).hand.removeFirst());
            game.adventureDeck.add(game.players.get(1).hand.removeFirst());
            game.adventureDeck.add(game.players.get(2).hand.removeFirst());
            game.adventureDeck.add(game.players.get(3).hand.removeFirst());
        }

        game.addCard("Foe", 10, 2, game.players.getFirst());
        game.addCard("Foe", 15, 2, game.players.getFirst());
        game.addCard("Foe", 20, 2, game.players.getFirst());
        game.addCard("Foe", 25, 2, game.players.getFirst());
        game.addCard("Horse", 10, 2, game.players.getFirst());
        game.addCard("Battle-Axe", 15, 2, game.players.getFirst());

        game.addCard("Horse", 10, 4, game.players.get(1));
        game.addCard("Battle-Axe", 15, 3, game.players.get(1));
        game.addCard("Lance", 20, 2, game.players.get(1));
        game.addCard("Sword", 10, 3, game.players.get(1));

        game.addCard("Dagger", 5, 6, game.players.get(2));
        game.addCard("Sword", 10, 3, game.players.get(2));
        game.addCard("Foe", 15, 1, game.players.get(2));
        game.addCard("Foe", 10, 1, game.players.get(2));
        game.addCard("Foe", 20, 1, game.players.get(2));

        game.addCard("Horse", 10, 3, game.players.getLast());
        game.addCard("Sword", 10, 3, game.players.getLast());
        game.addCard("Lance", 20, 3, game.players.getLast());
        game.addCard("Battle-Axe", 15, 3, game.players.getLast());

        game.sortHand(0);
        game.sortHand(1);
        game.sortHand(2);
        game.sortHand(3);

        for (Player p : game.players) {
            System.out.println("Player" + p.getId() + ": " + p.printableHand());
        }
    }

    @When("player {int} draws a {int} stage quest")
    public void player_draws_quest(int player, int stages) {
        game.drawEvent(game.players.get(player - 1));
        game.currCard = new QuestCard("Quest", stages, stages);
        eligibleSponsors = game.checkSponsorEligibility();
    }

    @When("player {int} adds a {string} card with value {int}")
    public void player_input_append_string(int player, String cardName, int cardValue) {
        if (game.currSponsor.getId() != player) {
            currentPlayer = player - 1;
            System.out.println(game.players.get(currentPlayer).printableHand());
        }
        for (int j = 0; j < game.players.get(player - 1).hand.size(); j++) {
            if (cardName.equalsIgnoreCase("Foe") && game.players.get(player - 1).hand.get(j).getName().equalsIgnoreCase("Foe")) {
                if (cardValue == ((AdventureCard) game.players.get(player - 1).hand.get(j)).value) {
                    playerInput.add(String.valueOf(j + 1) + "\n");
                    tempCards.add(game.players.get(player - 1).hand.remove(j));
                    break;
                }
            } else if (game.players.get(player - 1).hand.get(j).getName().equalsIgnoreCase(cardName)){
                playerInput.add(String.valueOf(j + 1) + "\n");
                tempCards.add(game.players.get(player - 1).hand.remove(j));
                break;
            }
        }
    }

    @When("player refuses")
    public void append_no_to_player_input() {
        playerInput.add("no\n");
    }

    @When("player accepts")
    public void append_yes_to_player_input() {
        playerInput.add("yes\n");
    }

    @When("player trims card {int}")
    public void add_trimmed_index_to_player_input(int card) {
        playerInput.add(String.valueOf(card) + "\n");
    }

    @When("player finishes making choices")
    public void append_quit_to_player_input() {
        playerInput.add("quit\n");
        if (currentPlayer > -1) {
            game.players.get(currentPlayer).hand.addAll(tempCards);
            game.sortHand(currentPlayer);
            tempCards.clear();
            currentPlayer = -1;
        }
    }

    @When("current sponsor is set")
    public void set_current_sponsor() {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

        playerInput.clear();

        game.setCurrSponsor(eligibleSponsors, false, scanner1);
    }

    @When("quest building resolves")
    public void player_sets_up_stage() {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

//        System.out.println("playerInput: " + playerInput);
        playerInput.clear();
//        System.out.println("playerInput as string: " + input);
//        System.out.println("tempCards: " + tempCards);
//        System.out.println("player1 hand: " + game.players.getFirst().printableHand());
//        System.out.println("sponsor hand: " + game.currSponsor.printableHand());

//        System.out.println("player2 hand: " + game.players.get(1).printableHand());

        game.currSponsor.hand.addAll(tempCards);
        tempCards.clear();
        game.sortHand(game.currSponsor.getId() - 1);

        game.setupQuest(game.currSponsor, scanner1);

        game.drawAdventure(game.currSponsor.getId() - 1, game.currSponsor.draw);

        System.out.println("currSponsor hand after draw: " + game.currSponsor.printableHand());

        eligiblePlayers.clear();
        removePlayers.clear();
        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i) != game.currSponsor) {
                eligiblePlayers.add(game.players.get(i));
            }
        }

        System.out.println("Quest: " + game.quest);
    }

    @When("quest resolves")
    public void current_quest_resolves() {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

        game.playQuest(scanner1);
    }


    @When("stage {int} attacks resolve")
    public void players_play_the_stage(int stage) {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

//        System.out.println("playerInput: " + playerInput);
        playerInput.clear();
//        System.out.println("playerInput as string: " + input);
//        System.out.println("tempCards: " + tempCards);
//        System.out.println("player2 hand: " + game.players.get(1).printableHand());
//        System.out.println("sponsor hand: " + game.currSponsor.printableHand());


        game.playStage(game.quest.get(stage - 1), eligiblePlayers, removePlayers, scanner1);

        for (Player p : game.players) {
            game.sortHand(p.getId() - 1);
        }

    }

    @When("winners are rewarded")
    public void quest_winners_rewarded() {
        game.rewardPlayers(eligiblePlayers);
        game.quest.clear();
    }

    @When("player {int} has {int} shields")
    public void assert_shields(int player, int shields) {
        assertEquals(shields, game.players.get(player - 1).shields);
    }

    @Then("players {string} are winners")
    public void check_winners(String players) {
        String[] playersArr = players.split(",");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(outStream);
        PrintStream oldPrint = System.out;

        System.setOut(print);

        game.endTurn();

        System.setOut(oldPrint);

        System.out.println(outStream.toString());

        for (int i = 0; i < playersArr.length; i++) {
            String thisPlayer = "Player: " + playersArr[i].replaceAll("\\s", "");
            assertTrue(outStream.toString().contains(thisPlayer));
        }

    }

}

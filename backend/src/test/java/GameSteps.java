import com.game.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String, String> events = new HashMap<>(){{
        put("Plague", "The player who draws this card immediately loses 2 shields.");
        put("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards.");
        put("Prosperity", "All players immediately draw 2 adventure cards.");
    }};

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

    @When("player {int} draws a {string} card with value {int}")
    public void players_draw_the_first_time_a1_compulsory_test(int player, String cardName, int cardValue) {
        game.addCard(cardName, cardValue, 1, game.players.get(player - 1));
        game.players.get(player - 1).draw -= 1;
        game.sortHand(player - 1);
    }


    @When("check player1's hand is correct")
    public void check_player1_hand_and_shields_a1_compulsory_test() {

        dummy.add(new AdventureCard("Foe", 5));
        dummy.add(new AdventureCard("Foe", 10));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 15));
        dummy.add(new AdventureCard("Foe", 30));
        dummy.add(new AdventureCard("Horse", 10));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Battle-Axe", 15));
        dummy.add(new AdventureCard("Lance", 20));

        System.out.println(game.players.getFirst());
        for (int i = 0; i < dummy.size(); i++) {
            assertTrue(dummy.get(i).getName().equalsIgnoreCase(game.players.getFirst().hand.get(i).getName()));
            assertEquals(((AdventureCard) dummy.get(i)).value, ((AdventureCard) game.players.getFirst().hand.get(i)).value);
        }
    }


    @Then("player3 has the correct hand")
    public void check_player3_hand_a1_compulsory_scenario() {
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
    }

    @Then("player4 has the correct hand")
    public void check_player4_hand_a1_compulsory_scenario() {
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
    }

    @Then("player {int} drawing and trimming resolves and ends with {int} cards")
    public void player_draws_and_trims_to_12_cards(int player, int cards) {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

        playerInput.clear();
        game.players.get(player-1).hand.addAll(tempCards);
        game.sortHand(player-1);
        tempCards.clear();

        game.drawAdventure(player-1, game.players.get(player-1).draw);
        game.trim(game.players.get(player-1), scanner1);

        assertEquals(12, game.players.get(player-1).getHandSize());
    }

    @Then("player {int} drawing resolves")
    public void player_draws(int player) {
        game.drawAdventure(player-1, game.players.get(player-1).draw);
    }

    @Then("player {int} trimming resolves")
    public void player_trims_to_12_cards(int player) {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

        playerInput.clear();
        game.players.get(player-1).hand.addAll(tempCards);
        game.sortHand(player-1);
        tempCards.clear();

        game.trim(game.players.get(player-1), scanner1);
    }


    @Given("a new game with rigged hands and decks for 2winner_game_2winner_quest scenario")
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

        game.addCard("Horse", 10, 2, game.players.get(1));
        game.addCard("Battle-Axe", 15, 3, game.players.get(1));
        game.addCard("Lance", 20, 2, game.players.get(1));
        game.addCard("Sword", 10, 2, game.players.get(1));
        game.addCard("Foe", 10, 1, game.players.get(1));
        game.addCard("Foe", 15, 1, game.players.get(1));
        game.addCard("Foe", 20, 1, game.players.get(1));

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

    @When("player {int} refuses")
    public void append_no_to_player_input(int player) {
        playerInput.add("no\n");
    }

    @When("player {int} accepts")
    public void append_yes_to_player_input(int player) {
        if (game.currSponsor != null && player != game.currSponsor.getId()) {
            currentPlayer = player - 1;
            game.sortHand(currentPlayer);
        } else {
            currentPlayer = -1;
        }
        playerInput.add("yes\n");
    }

    @When("player {int} trims card {int}")
    public void add_trimmed_index_to_player_input(int player, int card) {
        if ((player -1) != currentPlayer && player != game.currSponsor.getId()) {
            currentPlayer = player -1;
        }
        if (card <= game.players.get(player-1).getHandSize()) {
//            System.out.println("in player trims player" + player + " hand: " + game.players.get(player-1).printableHand());
            tempCards.add(game.players.get(player-1).hand.remove(card - 1));
        }
        playerInput.add(String.valueOf(card) + "\n");
    }

    @When("player finishes making choices")
    public void append_quit_to_player_input() {
        playerInput.add("quit\n");
        if (currentPlayer > -1) {
            game.players.get(currentPlayer).hand.addAll(tempCards);
            game.sortHand(currentPlayer);
            tempCards.clear();
//            System.out.println("in player finishes making choices player" + currentPlayer + " hand: " + game.players.get(currentPlayer).printableHand());
//            System.out.println("tempCards " + tempCards);
            currentPlayer = -1;
        }
    }

    @When("current sponsor is set")
    public void set_current_sponsor() {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

        playerInput.clear();

        game.setCurrSponsor(eligibleSponsors, scanner1);
//        System.out.println("Current Sponsor: " + game.currSponsor);
        currentPlayer = -1;
    }

    @When("quest building resolves")
    public void player_sets_up_quest() {
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

        eligiblePlayers.clear();
        removePlayers.clear();
        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i) != game.currSponsor) {
                eligiblePlayers.add(game.players.get(i));
            }
        }

        System.out.println("Quest: " + game.quest);
    }

//    @When("quest resolves")
//    public void current_quest_resolves() {
//        String input = String.join("", playerInput);
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        Scanner scanner1 = new Scanner(System.in);
//
//        game.playQuest(scanner1);
//    }


    @When("stage {int} attacks resolve")
    public void players_play_the_stage(int stage) {
        String input = String.join("", playerInput);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner1 = new Scanner(System.in);

//        System.out.println("in Stage resolve, input:\n" + input);

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

    @Given("a new rigged game for 1winner_game_with_events scenario")
    public void rigged_new_game_for_scenario_3() {
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

        game.addCard("Dagger", 5, 3, game.players.get(2));
        game.addCard("Sword", 10, 5, game.players.get(2));
        game.addCard("Excalibur", 30, 2, game.players.get(2));
        game.addCard("Horse", 10, 2, game.players.get(2));

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

    @When("player {int} draws a {string} event card")
    public void player_draws_event_card(int player, String event) {
        game.drawEvent(game.players.get(player - 1));
        game.currCard = new EventCard(event, events.get(event));
    }

    @When("the event resolves")
    public void event_resolves() {
        game.resolveEvent();
    }

    @When("player {int} has {int} cards")
    public void check_player_hand_size(int player, int cards) {
        assertEquals(cards, game.players.get(player - 1).getHandSize());
    }

}

import java.lang.reflect.Array;
import java.security.KeyPair;
import java.util.*;

public class Game {
    private int playerNum;
    public Player currPlayer;
    public Card currCard;
    private Player currSponsor;

    private ArrayList<Card> adventureDeck = new ArrayList<>();
    public ArrayList<Card> eventDeck = new ArrayList<>();
    private ArrayList<Card> adventureDiscard = new ArrayList<>();
    public ArrayList<Card> eventDiscard = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Player> winners = new ArrayList<>();
    public ArrayList<ArrayList<Card>> quest = new ArrayList<>();
    public HashMap<Integer, ArrayList<Card>> attack = new HashMap<>();

    public Game(int playerNum) {
        this.playerNum = playerNum;
    }

    public void initialize(){
        initializeDecks();
    }

    public int getAdventureDeckSize(){
        return adventureDeck.size();
    }
    public int getEventDeckSize() { return eventDeck.size(); }
    public int getAdventureDiscardSize() { return adventureDiscard.size(); }
    public int getEventDiscardSize() { return eventDiscard.size(); }
    public int getPlayer1HandSize() { return players.get(0).getHandSize(); }
    public int getPlayer2HandSize() { return players.get(1).getHandSize(); }
    public int getPlayer3HandSize() { return players.get(2).getHandSize(); }
    public int getPlayer4HandSize() { return players.get(3).getHandSize(); }

    public void initializeHands() {
        for (int i = 0; i < playerNum; i++) {
            Player player = new Player(i+1);
            players.add(player);
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).draw = 12;
            drawAdventure(i, players.get(i).draw);
        }
    }

    public void playTurn(Player currentPlayer) {
        Scanner playerInput = new Scanner(System.in);

        if (eventDeck.isEmpty()) {
            refillDeck(eventDeck, eventDiscard);
        }

        drawEvent(currentPlayer);

        printPlayer(currentPlayer);
        System.out.println("Card Drawn: " + currCard);

        if (currCard instanceof QuestCard) {
            resolveQuest(playerInput);
        } else {
            resolveEvent();
        }
        trim(playerInput);

        System.out.println("Press <Return> to end turn.");
        playerInput.nextLine();

        endTurn();

    }

    public void drawEvent(Player currentPlayer) {
        Random rand = new Random();

        int nextEvent = rand.nextInt(eventDeck.size());

        currCard = eventDeck.get(nextEvent);
        eventDiscard.add(eventDeck.remove(nextEvent));
        currPlayer = currentPlayer;
    }

    public void endTurn() {
        if (checkWinner()) {
            System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
            System.out.println("WINNERS");
            for (Player winner : winners) {
                System.out.println(winner);
            }
        }
    }

    public void resolveEvent() {
        switch (currCard.getName()) {
            case "Plague":
                currPlayer.shields = (currPlayer.shields > 2) ? currPlayer.shields - 2 : 0;
                break;
            case "Queen's Favor":
                currPlayer.draw += 2;
                drawAdventure(currPlayer.getId()-1, 2);
                break;
            case "Prosperity":
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).draw += 2;
                    drawAdventure(players.get(i).getId()-1, 2);
                }
                break;
        }
    }

    public void resolveQuest(Scanner playerInput) {
        ArrayList<Player> eligibleSponsors = checkSponsorEligibility();
        boolean sponsored = false;
        int sponsor = -1;

        if (eligibleSponsors.isEmpty()) {
            System.out.println("\nNo one is eligible to sponsor the quest.\n");
            return;
        }

        System.out.println("# of Players Eligible To Sponsor Quest " + currCard + ": " + eligibleSponsors.size() + "\n");

        int currPIndex = players.indexOf(currPlayer);
        for (int i = currPIndex; i < 4; i++) {
            printPlayer(players.get(i));

            if (eligibleSponsors.contains(players.get(i))) {
                System.out.println("Would you like to sponsor this quest? (Y/N)\n");
            } else {
                System.out.println("You are not eligible to sponsor this quest.\nDouble-press <Return> to continue.\n");
                playerInput.nextLine();
                continue;
            }

            if (playerQuestResponse(playerInput)) {
                sponsored = true;
                sponsor = i;
                break;
            }
        }

        if (!sponsored) {
            for (int i = 0; i < currPIndex; i++) {
                if (players.contains(players.get(i))) {
                    System.out.println("Would you like to sponsor this quest? (Y/N)\n");
                } else {
                    System.out.println("You are not eligible to sponsor this quest.\nDouble-press <Return> to continue.\n");
                    playerInput.nextLine();
                    continue;
                }

                if (playerQuestResponse(playerInput)) {
                    sponsor = i;
                    break;
                }
            }
        }
        currSponsor = players.get(sponsor);
        setupQuest(players.get(sponsor), playerInput);
        playQuest(playerInput);

        for (Player p : players) {
            System.out.println(p);
        }
        for (Player p : players) {
            drawAdventure(p.getId() -1, p.draw);
        }

        for (Player p : players) {
            System.out.println(p);
        }

        currSponsor = null;

    }

    public void playQuest(Scanner scanner) {
        HashMap<Player,Integer> eligiblePlayers = new HashMap<>();
        boolean questComplete = false;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != currSponsor) {
                eligiblePlayers.put(players.get(i), i+1);
            }
        }

        for (int i = 0; i < quest.size(); i++) {
            System.out.println("Playing Stage #" + (i+1) + "...");
            if (!playStage(quest.get(i), eligiblePlayers, scanner)) {
                break;
            }
            questComplete = (i == quest.size()-1);
        }

        if (questComplete) {
            System.out.println("Quest finished successfully!");
        } else {
            System.out.println("Quest failed!");
        }

        quest.clear();
    }

    public void setupAttack(Player attacker, ArrayList<Card> stage, Scanner scanner) {

    }

    public boolean playStage(ArrayList<Card> stage, HashMap<Player, Integer> eligiblePlayers, Scanner scanner) {
        ArrayList<Player> removePlayers = new ArrayList<>();
        String playerInput = "";
        populateEligiblePlayers(stage, eligiblePlayers);

        if (eligiblePlayers.isEmpty()) {
            System.out.println("There are no eligible players.");
            return false;
        }

        System.out.println("Eligible Players:");

        for (Player p : eligiblePlayers.keySet()) {
            System.out.println("Player " + p.getId());
        }

        for (Player p : eligiblePlayers.keySet()) {
            printPlayer(p);
            System.out.println("Would you like to tackle this stage? (Y/N)");

            while (true) {
                playerInput = scanner.nextLine().replaceAll("\\s", "");

                if (playerInput.equalsIgnoreCase("yes") || playerInput.equalsIgnoreCase("y")) {
                    System.out.println("Good luck setting up your attack.");
                    break;
                } else if (playerInput.equalsIgnoreCase("no") || playerInput.equalsIgnoreCase("n")) {
                    System.out.println("Coward!!");
                    removePlayers.add(p);
                    break;
                } else {
                    System.out.println("Invalid input, try again.");
                }
            }
        }

        for (Player p : removePlayers) {
            eligiblePlayers.remove(p);
        }
        return true;
    }

    public void populateEligiblePlayers(ArrayList<Card> stage, HashMap<Player, Integer> playerPool) {
        ArrayList<Player> removePlayers = new ArrayList<>();

        for (Player p : playerPool.keySet()) {
            if (!isEligibleForStage(stage, p)) {
                removePlayers.add(p);
            }
        }

        for (Player p : removePlayers) {
            playerPool.remove(p);
        }
    }

    public boolean isEligibleForStage(ArrayList<Card> stage, Player player) {
        int stageValue = 0, playerValue = 0;
        ArrayList<String> playerHand = new ArrayList<>();

        for (Card c : stage) {
            stageValue += ((AdventureCard) c).value;
        }

        for (Card c : player.hand) {
            if (!c.getName().equals("Foe") && !playerHand.contains(c.getName())) {
                playerHand.add(c.getName());
                playerValue += ((AdventureCard) c).value;
            }
        }

        return playerValue >= stageValue;
    }

    public boolean isCardValid(ArrayList<Card> stage, Card card) {
        for (Card c : stage) {
            if (card.getName().equals(c.getName())) {
                System.out.println("You cannot repeat cards in the stage.");
                return false;
            }
        }

        return true;
    }

    public boolean isStageValid(ArrayList<ArrayList<Card>> stages, ArrayList<Card> currStage, boolean foe) {
        int previous = 0, current = 0;

        for (Card i : currStage) {
            current += ((AdventureCard) i).value;
        }

        if (!stages.isEmpty()) {
            for (Card i : stages.getLast()) {
                previous += ((AdventureCard) i).value;
            }
        }

        if (!foe) {
            System.out.println("ERROR: The stage must contain a foe.");
        }

        if (currStage.isEmpty()) {
            System.out.println("ERROR: A stage cannot be empty.");
        }

        if (previous >= current) {
            System.out.println("ERROR: Insufficient value for this stage.");
            System.out.println("Previous Stage: " + previous + " Current Stage: " + current + "\n");
        }

        return (current > previous) && foe;
    }

    public ArrayList<Player> checkSponsorEligibility() {
        ArrayList<Player> eligibleSponsors = new ArrayList<>();
        QuestCard quest = (QuestCard) currCard;
        for (Player player : players) {
            int count = 0;
            for (Card j : player.hand) {
                if (j.getName().equals("Foe")) {
                    count++;
                }
            }
            if (count >= quest.stages) {
                eligibleSponsors.add(player);
            }
        }

        return eligibleSponsors;
    }

    public void trim(Scanner playerInput) {

        if (currPlayer.getHandSize() == 12) {
            return;
        }

        System.out.println("You have " + currPlayer.getHandSize() + " cards and must trim " + (currPlayer.getHandSize() - 12) + " cards.\n");

        String input;

        for (int i = currPlayer.getHandSize(); i > 12; i--) {
            System.out.println("Current hand: " + currPlayer.printableHand());
            System.out.println("Pick a card to trim by entering a number between 1-" + currPlayer.getHandSize() + ": ");
            input = playerInput.nextLine().replaceAll("\\s+","");
            if (checkValid(input)) {
                int discNum = Integer.valueOf(input) - 1;
                System.out.println("\nYou chose " + (discNum + 1) + ". " + currPlayer.hand.get(discNum) + "\n");
                adventureDiscard.add(currPlayer.hand.remove(discNum));
            } else {
                i++;
            }
        }
    }

    public void setupQuest(Player player, Scanner scanner) {
        String input = "";

        printPlayer(player);
        for (int j = 0; j < ((QuestCard) currCard).stages; j++) {
            ArrayList<Card> stage = new ArrayList<>();
            boolean foeAdded = false;

            System.out.println("\n**Building Stage " + (j+1) + "...");

            while (!player.hand.isEmpty() && !input.equalsIgnoreCase("quit")) {
                System.out.println("Pick a card from 1-" + player.getHandSize() + ".");
                System.out.println("Hand: " + player.printableHand());

                input = scanner.nextLine().replaceAll("\\s", "");

                while (!input.equalsIgnoreCase("quit") && !checkValid(input)) {
                    input = scanner.nextLine().replaceAll("\\s", "");
                }

                if (input.equalsIgnoreCase("quit")) {
                    ArrayList<ArrayList<Card>> temp = quest;
                    if (isStageValid(temp, stage, foeAdded)) {
                        System.out.println("Stage is Valid.\n");
                        quest.add(stage);
                        break;
                    } else {
                        input = "";
                        continue;
                    }
                }

                int sponsorChoice = Integer.parseInt(input) - 1;
                Card tempCard = player.hand.get(sponsorChoice);
                if (isCardValid(stage, tempCard)) {
                    foeAdded = tempCard.getName().equals("Foe") || foeAdded;
                    stage.add(tempCard);
                    player.draw++;
                    adventureDiscard.add(player.hand.remove(sponsorChoice));
                }

                if (player.hand.isEmpty()) {
                    ArrayList<ArrayList<Card>> temp = quest;
                    if (isStageValid(temp, stage, foeAdded)) {
                        System.out.println("Stage is Valid.\n");
                        quest.add(stage);
                        break;
                    } else {
                        input = "";
                        continue;
                    }
                }

                System.out.print("\nStage so far: \nStage #" + (j+1) + ": ");
                int value = 0;
                for (Card c : stage) {
                    value += ((AdventureCard) c).value;
                }
                System.out.println(" Value: " + value + " Cards: " + stage);
            }

            input = "";

            System.out.println("\nQuest:");
            int stageNum = 1;
            for (ArrayList<Card> i : quest) {
                int value = 0;
                for (Card c : i) {
                    value += ((AdventureCard) c).value;
                }
                System.out.println("Stage #" + stageNum + " Value: " + value + " Cards: " + i);
                stageNum++;
            }

            if (player.hand.isEmpty()) {
                System.out.println("Player ran out of cards.");
                break;
            }

            System.out.println("\nFinished Stage " + (j+1) + ". " + (((QuestCard) currCard).stages - j - 1) + " stage(s) left.\n\n");
        }

        System.out.println((quest.size() == ((QuestCard) currCard).stages) ? "Valid Quest.\n" : "Invalid Quest.\n");

        System.out.println("\nQuest:");
        int stageNum = 1;
        for (ArrayList<Card> i : quest) {
            int value = 0;
            for (Card c : i) {
                value += ((AdventureCard) c).value;
            }
            System.out.println("Stage #" + stageNum + " Value: " + value + " Cards: " + i);
            stageNum++;
        }

    }

    private boolean playerQuestResponse(Scanner playerInput) {
        String inp;

        while (true) {
            inp = playerInput.nextLine().replaceAll("\\s+","").toLowerCase();

            if (inp.equals("y") || inp.equals("yes")) {
                System.out.println("Okay...\n\n");
                return true;
            } else if (inp.equals("n") || inp.equals("no")) {
                System.out.println("Okay.");
                return false;
            } else {
                System.out.println("Invalid Input. Try Again: \n");
            }
        }
    }

    private void printPlayer(Player thisPlayer) {
        System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
        System.out.println("CURRENT PLAYER\n" + thisPlayer + "\n");
    }

    private boolean checkValid(String input) {
        try {
            if (Integer.valueOf(input) < 1 || Integer.valueOf(input) > currPlayer.getHandSize()) {
                System.out.println("\nInvalid Input.\n");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("\nInvalid Input.\n");
            return false;
        }
    }

    private void drawAdventure(int index, int num) {
        Random rand = new Random();

        System.out.println("in draw");

        for (int j = 0; j < num; j++) {
            if (getAdventureDiscardSize() == 0) {
                refillDeck(adventureDeck, adventureDiscard);
            }
            int nextCard = rand.nextInt(getAdventureDeckSize());
            players.get(index).hand.add(adventureDeck.remove(nextCard));
            players.get(index).draw--;
        }
    }

    private void refillDeck(ArrayList<Card> deck, ArrayList<Card> discard) {
        Random rand = new Random();

        int totalAdvCards = discard.size();

        for (int i = 0; i < totalAdvCards; i++) {
            int nextCard = rand.nextInt(discard.size());
            deck.add(discard.remove(nextCard));
        }
    }

    private boolean checkWinner() {
        for (Player player : players) {
            if (player.shields >= 7) {
                winners.add(player);
            }
        }
        return !winners.isEmpty();
    }

    private void initializeDecks() {
        for (int i = 0; i < 100; i++) {
            AdventureCard temp;
            Card tempE;

            if (i < 3) {
                tempE = new QuestCard("Quest", 2, 2);
                eventDiscard.add(tempE);
            } else if (i >= 3 && i < 7) {
                tempE = new QuestCard("Quest", 3, 3);
                eventDiscard.add(tempE);
            } else if (i >= 7 && i < 10) {
                tempE = new QuestCard("Quest", 4, 4);
                eventDiscard.add(tempE);
            } else if (i >= 10 && i < 12) {
                tempE = new QuestCard("Quest", 5, 5);
                eventDiscard.add(tempE);
            } else if (i == 12) {
                tempE = new EventCard("Plague", "The player who draws this card immediately loses 2 shields.");
                eventDiscard.add(tempE);
            } else if (i > 12 && i < 15) {
                tempE = new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards.");
                eventDiscard.add(tempE);
            } else if (i >= 15 && i < 17) {
                tempE = new EventCard("Prosperity", "All players immediately draw 2 adventure cards.");
                eventDiscard.add(tempE);
            }

            if (i < 8) {
                temp = new AdventureCard("Foe", 5);
                adventureDiscard.add(temp);
            } else if (i >= 8 && i < 15) {
                temp = new AdventureCard("Foe", 10);
                adventureDiscard.add(temp);
            } else if (i >= 15 && i < 23) {
                temp = new AdventureCard("Foe", 15);
                adventureDiscard.add(temp);
            } else if (i >= 23 && i < 30) {
                temp = new AdventureCard("Foe", 20);
                adventureDiscard.add(temp);
            } else if (i >= 30 && i < 37) {
                temp = new AdventureCard("Foe", 25);
                adventureDiscard.add(temp);
            } else if (i >= 37 && i < 41) {
                temp = new AdventureCard("Foe", 30);
                adventureDiscard.add(temp);
            } else if (i >= 41 && i < 45) {
                temp = new AdventureCard("Foe", 35);
                adventureDiscard.add(temp);
            } else if (i >= 45 && i < 47) {
                temp = new AdventureCard("Foe", 40);
                adventureDiscard.add(temp);
            } else if (i >= 47 && i < 49) {
                temp = new AdventureCard("Foe", 50);
                adventureDiscard.add(temp);
            } else if (i == 49) {
                temp = new AdventureCard("Foe", 70);
                adventureDiscard.add(temp);
            } else if (i >= 50 && i < 56) {
                temp = new AdventureCard("Dagger", 5);
                adventureDiscard.add(temp);
            } else if (i >= 56 && i < 68) {
                temp = new AdventureCard("Horse", 10);
                adventureDiscard.add(temp);
            } else if (i >= 68 && i < 84) {
                temp = new AdventureCard("Sword", 10);
                adventureDiscard.add(temp);
            } else if (i >= 84 && i < 92) {
                temp = new AdventureCard("Battle-Axe", 15);
                adventureDiscard.add(temp);
            } else if (i >= 92 && i < 98) {
                temp = new AdventureCard("Lance", 20);
                adventureDiscard.add(temp);
            } else {
                temp = new AdventureCard("Excalibur", 30);
                adventureDiscard.add(temp);
            }
        }

        Random rand = new Random();
        while (!adventureDiscard.isEmpty()) {
            int randcard = rand.nextInt(adventureDiscard.size());
            adventureDeck.add(adventureDiscard.remove(randcard));
        }

        while (!eventDiscard.isEmpty()) {
            int randcard = rand.nextInt(eventDiscard.size());
            eventDeck.add(eventDiscard.remove(randcard));
        }

    }

    public static void main(String[] args) {
        Game game = new Game(4);
        game.initialize();
        game.initializeHands();

        Scanner scanner = new Scanner(System.in);

        game.currPlayer = game.players.getFirst();
        game.currCard = new QuestCard("Quest", 2, 2);

        game.resolveQuest(scanner);
    }
}

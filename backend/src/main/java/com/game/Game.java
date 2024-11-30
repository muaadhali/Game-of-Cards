package com.game;

import java.util.*;

public class Game {
    private int playerNum;
    public Player currPlayer;
    public Card currCard;
    public Player currSponsor;

    public ArrayList<Card> adventureDeck = new ArrayList<>();
    public ArrayList<Card> eventDeck = new ArrayList<>();
    public ArrayList<Card> adventureDiscard = new ArrayList<>();
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
            attack.put(player.getId(), new ArrayList<>());
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).draw = 12;
            drawAdventure(i, players.get(i).draw);
            System.out.println(players.get(i));
            sortHand(i);
            System.out.println(players.get(i));
        }
    }

    public void play() {
        int round = 0;

        while(true) {
            playTurn(players.get(round % players.size()));
            round++;
            if (endTurn()) {
                break;
            }
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

        for (Player player : players) {
            trim(player, playerInput);
            sortHand(player.getId()-1);
        }

        System.out.println("Press <Return> to end turn.");
        playerInput.nextLine();

    }

    public void drawEvent(Player currentPlayer) {
        currCard = eventDeck.getLast();
        eventDiscard.add(eventDeck.removeLast());
        currPlayer = currentPlayer;
    }

    public boolean endTurn() {
        if (checkWinner()) {
            System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
            System.out.println("WINNERS");
            for (Player winner : winners) {
                System.out.println(winner);
            }
            return true;
        }
        return false;
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

        if (eligibleSponsors.isEmpty()) {
            System.out.println("\nNo one is eligible to sponsor the quest.\n");
            return;
        }

        sponsored = setCurrSponsor(eligibleSponsors, playerInput);

        if (sponsored) {
            setupQuest(currSponsor, playerInput);
            playQuest(playerInput);

            drawAdventure(players.indexOf(currSponsor), currSponsor.draw);
            trim(currSponsor, playerInput);
            currSponsor = null;
        }

    }

    public boolean setCurrSponsor(ArrayList<Player> eligibleSponsors, Scanner playerInput) {
        int sponsor = -1;
        boolean sponsored = false;
        int currPIndex = players.indexOf(currPlayer);
        for (int i = currPIndex; i < players.size(); i++) {
            printPlayer(players.get(i));

            if (eligibleSponsors.contains(players.get(i))) {
                System.out.println("Would you like to sponsor this quest? (Y/N)\n" + currCard + "\n");
            } else {
                System.out.println("You are not eligible to sponsor this quest.\nDouble-press <Return> to continue.\n");
                playerInput.nextLine();
                continue;
            }

            if (playerQuestResponse(playerInput)) {
                System.out.println("In player quest response");
                sponsored = true;
                sponsor = i;
                break;
            }
        }

        if (!sponsored) {
            for (int i = 0; i < currPIndex; i++) {
                printPlayer(players.get(i));

                if (players.contains(players.get(i))) {
                    System.out.println("Would you like to sponsor this quest? (Y/N)\n" + currCard + "\n");
                } else {
                    System.out.println("You are not eligible to sponsor this quest.\nDouble-press <Return> to continue.\n");
                    playerInput.nextLine();
                    continue;
                }

                if (playerQuestResponse(playerInput)) {
                    System.out.println("In player quest response");
                    sponsored = true;
                    sponsor = i;
                    break;
                }
            }
        }
        if (sponsored) {
            System.out.println("Sponsored is true");
            currSponsor = players.get(sponsor);
            currSponsor.draw += ((QuestCard) currCard).shields;
        }

        return sponsored;
    }

    public void playQuest(Scanner scanner) {
        ArrayList<Player> eligiblePlayers = new ArrayList<>();
        ArrayList<Player> removePlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != currSponsor) {
                eligiblePlayers.add(players.get(i));
            }
        }

        int currStage = 1;
        while (!quest.isEmpty()) {
            System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
            System.out.println("Playing Stage #" + currStage + "...\n");
            if (!playStage(quest.getFirst(), eligiblePlayers, removePlayers, scanner)) {
                break;
            }
            System.out.println("\nStage " + currStage++ + " complete.");
            quest.removeFirst();
        }

        rewardPlayers(eligiblePlayers);

        quest.clear();
    }

    public void rewardPlayers(ArrayList<Player> winners) {
        for (Player p : players) {
            if (winners.contains(p)){
                System.out.println("Player " + p.getId() + " wins " + ((QuestCard) currCard).shields + " shields!");
                winners.get(winners.indexOf(p)).shields += ((QuestCard) currCard).shields;
                System.out.println("Player " + p.getId() + " shields: " + p.shields + "\n");
            }
        }
    }

    public void setupAttack(Player attacker, ArrayList<Card> stage, Scanner scanner) {
        int attackVal = 0;
        ArrayList<String> cards = new ArrayList<>();
        String playerInput;

        System.out.println("Set up your Attack.\n");

        while(true) {
            System.out.println("Attack So Far:\nValue: " + attackVal + ", Cards: " + attack.get(attacker.getId()));
            System.out.println("Current Hand: " + attacker.printableHand());

            if (attacker.hand.isEmpty()) {
                System.out.println("Ran out of cards.");
                break;
            }

            System.out.println("\nPick a card from 1-" + (attacker.getHandSize()) + ". Type <quit> to confirm attack.");

            playerInput = scanner.nextLine().replaceAll("\\s", "");

            if (playerInput.equalsIgnoreCase("quit")) {
                break;
            }

            if (!checkValid(playerInput, attacker)) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            int choice = Integer.parseInt(playerInput)-1;

            if (attacker.hand.get(choice).getName().equalsIgnoreCase("Foe")) {
                System.out.println("Cannot add a foe to your attack.");
                continue;
            }

            if (cards.contains(attacker.hand.get(choice).getName())){
                System.out.println("Cannot repeat cards in your attack.");
                continue;
            }

            attackVal += ((AdventureCard) attacker.hand.get(choice)).value;
            cards.add(attacker.hand.get(choice).getName());
            attack.get(attacker.getId()).add(attacker.hand.get(choice));
            adventureDiscard.add(attacker.hand.remove(choice));
        }

        System.out.println("Attack set.\n");
    }

    public boolean playStage(ArrayList<Card> stage, ArrayList<Player> eligiblePlayers, ArrayList<Player> removePlayers, Scanner scanner) {
        String playerInput = "";

        removePlayers.add(currSponsor);
        populateEligiblePlayers(stage, removePlayers, eligiblePlayers);

        if (eligiblePlayers.isEmpty()) {
            System.out.println("There are no eligible players.");
            return false;
        }

        System.out.println("Eligible Players:");

        for (Player p : eligiblePlayers) {
            System.out.println("Player " + p.getId());
        }

        for (Player p : eligiblePlayers) {
            printPlayer(p);
            System.out.println("Would you like to tackle this stage? (Y/N)");

            while (true) {
                playerInput = scanner.nextLine().replaceAll("\\s", "");

                if (playerInput.equalsIgnoreCase("yes") || playerInput.equalsIgnoreCase("y")) {
                    System.out.println("Drawing for stage.");
                    sortHand(p.getId()-1);
                    p.draw++;
                    drawAdventure(p.getId()-1, 1);
                    trim(p, scanner);
                    setupAttack(p, stage, scanner);
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

        resolveAttack(removePlayers, stage);

        for (Player p : removePlayers) {
            eligiblePlayers.remove(p);
        }

        return true;
    }

    public void resolveAttack(ArrayList<Player> removePlayers, ArrayList<Card> stage) {
        int stageVal = 0;
        for (Card c : stage) {
            stageVal += ((AdventureCard) c).value;
        }

        for (int id : attack.keySet()) {
            if (!removePlayers.contains(players.get(id-1))) {
                int attackVal = 0;
                for (Card c : attack.get(id)) {
                    attackVal += ((AdventureCard) c).value;
                }

                if (stageVal > attackVal) {
                    System.out.println("Player " + id + " failed the attack!");
                    removePlayers.add(players.get(id-1));
                } else {
                    System.out.println("Player " + id + " succeeded the attack!");
                }
            }
            attack.get(id).clear();
        }
        System.out.println("\n");
    }

    public void populateEligiblePlayers(ArrayList<Card> stage, ArrayList<Player> removePlayers, ArrayList<Player> playerPool) {

//        for (Player p : playerPool) {
//            if (!isEligibleForStage(stage, p)) {
//                removePlayers.add(p);
//            }
//        }

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

    public void trim(Player player, Scanner playerInput) {

        if (player.getHandSize() <= 12) {
            return;
        }

        printPlayer(player);
        System.out.println("You have " + player.getHandSize() + " cards and must trim " + ((player.getHandSize() < 12) ? "0" : (player.getHandSize() - 12)) + " cards.\n");

        String input;

        for (int i = player.getHandSize(); i > 12; i--) {
            System.out.println("Pick a card to trim by entering a number between 1-" + player.getHandSize() + ": ");
            input = playerInput.nextLine().replaceAll("\\s+","");
            if (checkValid(input, player)) {
                int discNum = Integer.parseInt(input) - 1;
                System.out.println("\nYou chose " + (discNum + 1) + ". " + player.hand.get(discNum) + "\n");
                adventureDiscard.add(player.hand.remove(discNum));
                System.out.println("Current hand: " + player.printableHand());
            } else {
                i++;
            }
        }

        System.out.println("Hand after completed trimming: " + player.printableHand() + "\n");
    }

    public void setupQuest(Player player, Scanner scanner) {
        String input = "";

        printPlayer(player);
        for (int j = 0; j < ((QuestCard) currCard).stages; j++) {
            ArrayList<Card> stage = new ArrayList<>();
            boolean foeAdded = false;

            System.out.println("\nBuilding Stage " + (j+1) + "...");

            while (!player.hand.isEmpty() && !input.equalsIgnoreCase("quit")) {
                System.out.println("Pick a card from 1-" + player.getHandSize() + ". Type <quit> to confirm stage.");
                System.out.println("Hand: " + player.printableHand());

                input = scanner.nextLine().replaceAll("\\s", "");

                while (!input.equalsIgnoreCase("quit") && !checkValid(input, player)) {
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
                System.out.println(" Value: " + value + ", Cards: " + stage);
            }

            input = "";

            printQuest();

            if (player.hand.isEmpty()) {
                System.out.println("Player ran out of cards.");
                break;
            }

            System.out.println("\nFinished Stage " + (j+1) + ". " + (((QuestCard) currCard).stages - j - 1) + " stage(s) left.\n\n");
        }

        System.out.println((quest.size() == ((QuestCard) currCard).stages) ? "Valid Quest.\n" : "Invalid Quest.\n");

        printQuest();

    }

    private void printQuest() {
        System.out.println("\nQuest:");
        int stageNum = 1;
        for (ArrayList<Card> i : quest) {
            int value = 0;
            for (Card c : i) {
                value += ((AdventureCard) c).value;
            }
            System.out.println("Stage #" + stageNum + " Value: " + value + ", Cards: " + i);
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

    public void addCard(String cardName, int value, int num, Player player) {
        int notFound = num;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < this.adventureDeck.size(); j++) {
                if (cardName.equalsIgnoreCase("Foe") && this.adventureDeck.get(j).getName().equalsIgnoreCase("Foe")) {
                    if (value == ((AdventureCard) this.adventureDeck.get(j)).value) {
                        player.hand.add(this.adventureDeck.remove(j));
                        notFound--;
                        break;
                    }
                } else if (this.adventureDeck.get(j).getName().equalsIgnoreCase(cardName)){
                    player.hand.add(this.adventureDeck.remove(j));
                    notFound--;
                    break;
                }
            }
        }

        if (notFound == 0) {
            return;
        }

        for (int i = 0; i < notFound; i++) {
            for (int j = 0; j < this.adventureDiscard.size(); j++) {
                if (cardName.equalsIgnoreCase("Foe") && this.adventureDiscard.get(j).getName().equalsIgnoreCase("Foe")) {
                    if (value == ((AdventureCard) this.adventureDiscard.get(j)).value) {
                        player.hand.add(this.adventureDiscard.get(j));
                        break;
                    }
                } else if (this.adventureDiscard.get(j).getName().equalsIgnoreCase(cardName)){
                    player.hand.add(this.adventureDiscard.get(j));
                    break;
                }
            }
        }
    }

    private void printPlayer(Player thisPlayer) {
        System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
        System.out.println("CURRENT PLAYER\n" + thisPlayer + "\n");
    }

    private boolean checkValid(String input, Player player) {
        System.out.println("input = " + input);
        try {
            if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > player.getHandSize()) {
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

    public void drawAdventure(int index, int num) {
        if (players.get(index).draw <= 0) {
            return;
        }
        for (int j = 0; j < num; j++) {
            if (getAdventureDiscardSize() == 0) {
                refillDeck(adventureDeck, adventureDiscard);
            }
            players.get(index).hand.add(adventureDeck.removeLast());
            players.get(index).draw--;
        }

    }

    public void sortHand(int index) {
        ArrayList<Card> temp = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();


        int min = 100;
        int indexMin = -1;
        boolean foes = true;

        names.add("Dagger");
        names.add("Sword");
        names.add("Horse");
        names.add("Battle-Axe");
        names.add("Lance");
        names.add("Excalibur");

        while(players.get(index).getHandSize() > 0) {
            while (foes) {
                for (int i = 0; i < players.get(index).getHandSize(); i++) {
                    if (((AdventureCard) players.get(index).hand.get(i)).value < min && players.get(index).hand.get(i).getName().equalsIgnoreCase("foe")) {
                        indexMin = i;
                        min = ((AdventureCard) players.get(index).hand.get(i)).value;
                    }
                }

                if (indexMin >= 0) {
                    temp.add(players.get(index).hand.remove(indexMin));
                }
                min = 100;
                foes = false;
                for (Card c : players.get(index).hand) {
                    if (c.getName().equalsIgnoreCase("foe")) {
                        foes = true;
                    }
                }
            }

            for (String name : names) {
                for (int j = 0; j < players.get(index).getHandSize(); j++) {
                    if (players.get(index).hand.get(j).getName().equalsIgnoreCase(name.replaceAll("\\s", ""))) {
                        temp.add(players.get(index).hand.remove(j));
                        j--;
                    }
                }
            }

        }

        for (Card c : temp) {
            players.get(index).hand.add(c);
        }
    }

    private void refillDeck(ArrayList<Card> deck, ArrayList<Card> discard) {
        Random rand = new Random();

        int totalCards = discard.size();

        for (int i = 0; i < totalCards; i++) {
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
        Game game = new Game(2);
        game.initialize();
        game.initializeHands();

        System.out.println(game.adventureDeck);
        System.out.println(game.eventDeck);

        game.play();
    }
}

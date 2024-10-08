import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int playerNum;
    public Player currPlayer;
    public Card currCard;

    private ArrayList<AdventureCard> adventureDeck = new ArrayList<>();
    public ArrayList<Card> eventDeck = new ArrayList<>();
    private ArrayList<AdventureCard> adventureDiscard = new ArrayList<>();
    public ArrayList<Card> eventDiscard = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Player> winners = new ArrayList<>();

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
            draw(players.get(i), 12);
        }
    }

    public void playTurn(Player currentPlayer) {

        Random rand = new Random();
        Scanner playerInput = new Scanner(System.in);
        int nextEvent = rand.nextInt(eventDeck.size());

        currCard = eventDeck.get(nextEvent);
        eventDiscard.add(eventDeck.remove(nextEvent));
        currPlayer = currentPlayer;

        System.out.println("|\n|\n|\n|\n|\n|\n|\n|\n|\n|\n<------------------------------------------------------------->\n");
        System.out.println("CURRENT PLAYER\n" + currPlayer + "\n");
        System.out.println("Card Drawn: " + currCard);

        System.out.println("Press <Return> to end turn.");
        playerInput.nextLine();

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
                draw(currPlayer, 2);
                break;
            case "Prosperity":
                for (int i = 0; i < players.size(); i++) {
                    draw(players.get(i), 2);
                }
                break;
        }
    }

    private void draw(Player player, int num) {
        Random rand = new Random();
        for (int j = 0; j < num; j++) {
            int nextCard = rand.nextInt(getAdventureDeckSize());
            player.hand.add(adventureDeck.remove(nextCard));
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

        game.playTurn(game.players.getFirst());
    }
}

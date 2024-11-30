package com.backend;

import com.game.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class GameController {
    private int playerNum;
    private int round;
    public int trimmer;
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

    public GameController() {
        playerNum = 4;
        round = 0;

        initializeDecks();
        initializeHands();
    }

    @GetMapping("/start")
    public String start() {
        currPlayer = players.get(round % playerNum);
        round++;
        drawEvent();

        return "CURRENT PLAYER\n\n" + currPlayer + "\n\nPlayer " + currPlayer.getId() + " has drawn a" + ((currCard instanceof QuestCard) ? " Quest: " : "n Event: ") + currCard + ".";
    }

    @GetMapping("/resolveEvent")
    public String resolveEvent() {
        String result = "";
        switch (currCard.getName()) {
            case "Plague":
                currPlayer.shields = (currPlayer.shields > 2) ? currPlayer.shields - 2 : 0;
                result = "Player " + currPlayer.getId() + " lost 2 shields.";
                break;
            case "Queen's Favor":
                currPlayer.draw += 2;
                drawAdventure(currPlayer.getId()-1, 2);
                result = "Player " + currPlayer.getId() + " has drawn 2 cards" + ((currPlayer.getHandSize() > 12) ? " and needs to trim." : " and does not need to trim.");
                trimmer = (currPlayer.getHandSize() > 12)? currPlayer.getId()-1 : -1;
                break;
            case "Prosperity":
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).draw += 2;
                    drawAdventure(players.get(i).getId()-1, 2);

                    if (trimmer == -1 && players.get(i).getHandSize() > 12) {
                        trimmer = i;
                    }
                }
                result = "All players have drawn 2" + ((trimmer > -1) ? (" and some need to trim.\nPlayer " + (trimmer+1) + " will begin trimming...") : " and no trimming is needed.");
                break;
        }

        return result;
    }

    @GetMapping("/resolveQuest")
    public String resolveQuest() {
        String result = "";
        ArrayList<Player> eligibleSponsors = checkSponsorEligibility();

        if (eligibleSponsors.isEmpty()) {
            result = "\nNo one is eligible to sponsor the quest.\n";
        } else {
            result += eligibleSponsors.get(0).toString() + "\n\nWould you like to sponser the Quest: " + currCard + "?";
        }
        return result;
    }

    @GetMapping("resolveSponsorChoice")
    public void resolveSponsorChoice() {

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

    @GetMapping("/fetchTrimmer")
    public Player getTrimmer() {
        return players.get(trimmer);
    }

    @PutMapping("/trim")
    public String trim(@RequestBody String input) {
        String result;
        String[] inpArr = input.replaceAll("\\s","").split(",");
        int[] intInp = new int[inpArr.length];

        for (int i = 0; i < inpArr.length; i++) {
            intInp[i] = Integer.parseInt(inpArr[i]) - 1;
        }

        Arrays.sort(intInp);

        for (int i = intInp.length - 1; i >= 0; i--) {
            adventureDiscard.add(players.get(trimmer).hand.remove(intInp[i]));
        }

        result = "Player " + (trimmer+1) + " finished trimming.";

        System.out.println(players.get(trimmer) + "\n Finished trimming.");

        int count = 0;
        while(true) {
            if (trimmer != playerNum-1) {
                trimmer++;
            } else {
                trimmer = 0;
            }

            if (players.get(trimmer).getHandSize() > 12) {
                result += "\nPlayer " + (trimmer+1) + " needs to trim next.";
                break;
            } else if (count >= 4) {
                result += "\nAll players have completed necessary trimming.";
                trimmer = -1;
                break;
            }

            count++;
        }

        return result;
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

    public void drawEvent() {
        if (eventDeck.isEmpty()) {
            refillDeck(eventDeck, eventDiscard);
        }

        currCard = eventDeck.getLast();
        eventDiscard.add(eventDeck.removeLast());
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

    private void refillDeck(ArrayList<Card> deck, ArrayList<Card> discard) {
        Random rand = new Random();

        int totalCards = discard.size();

        for (int i = 0; i < totalCards; i++) {
            int nextCard = rand.nextInt(discard.size());
            deck.add(discard.remove(nextCard));
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
}

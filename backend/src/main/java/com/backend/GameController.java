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
    public Integer currAttacker = -1;
    public Player currPlayer;
    public Card currCard;
    public Player currSponsor;

    public ArrayList<Card> adventureDeck = new ArrayList<>();
    public ArrayList<Card> eventDeck = new ArrayList<>();
    public ArrayList<Card> adventureDiscard = new ArrayList<>();
    public ArrayList<Card> eventDiscard = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Player> winners = new ArrayList<>();
    public ArrayList<Integer> attackers = new ArrayList<>();
    public ArrayList<ArrayList<Card>> quest = new ArrayList<>();
    public HashMap<Integer, ArrayList<Card>> attack = new HashMap<>();
    public ArrayList<Player> eligibleSponsors = new ArrayList<>();

    public GameController() {
        playerNum = 4;
        round = 0;

        initializeDecks();
        initializeHands();
    }

    @PutMapping("/rigA1Scenario")
    public String rigA1Scenario() {

        for (int i = 0; i < 12; i++) {
            adventureDeck.add(players.get(0).hand.removeFirst());
            adventureDeck.add(players.get(1).hand.removeFirst());
            adventureDeck.add(players.get(2).hand.removeFirst());
            adventureDeck.add(players.get(3).hand.removeFirst());
        }

        addCard("Foe", 5, 2, players.getFirst());
        addCard("Foe", 15, 2, players.getFirst());
        addCard("Dagger", 5, 1, players.getFirst());
        addCard("Sword", 10, 2, players.getFirst());
        addCard("Horse", 10, 2, players.getFirst());
        addCard("Battle-Axe", 15, 2, players.getFirst());
        addCard("Lance", 20, 1, players.getFirst());

        addCard("Foe", 5, 2, players.get(1));
        addCard("Foe", 15, 2, players.get(1));
        addCard("Foe", 40, 1, players.get(1));
        addCard("Dagger", 5, 1, players.get(1));
        addCard("Sword", 10, 1, players.get(1));
        addCard("Horse", 10, 2, players.get(1));
        addCard("Battle-Axe", 15, 2, players.get(1));
        addCard("Excalibur", 30, 1, players.get(1));

        addCard("Foe", 5, 3, players.get(2));
        addCard("Foe", 15, 1, players.get(2));
        addCard("Dagger", 5, 1, players.get(2));
        addCard("Sword", 10, 3, players.get(2));
        addCard("Horse", 10, 2, players.get(2));
        addCard("Battle-Axe", 15, 1, players.get(2));
        addCard("Lance", 20, 1, players.get(2));

        addCard("Foe", 5, 1, players.getLast());
        addCard("Foe", 15, 2, players.getLast());
        addCard("Foe", 40, 1, players.getLast());
        addCard("Dagger", 5, 2, players.getLast());
        addCard("Sword", 10, 1, players.getLast());
        addCard("Horse", 10, 2, players.getLast());
        addCard("Battle-Axe", 15, 1, players.getLast());
        addCard("Lance", 20, 1, players.getLast());
        addCard("Excalibur", 30, 1, players.getLast());

        eventDeck.removeLast();
        eventDeck.addLast(new QuestCard("Quest",4, 4));

        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();


        adventureDeck.addLast(new AdventureCard("Lance", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Lance", 20));
        adventureDeck.addLast(new AdventureCard("Lance", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 30));

        return printAfterRigging(11, 2);
    }

    @PutMapping("/rigA3Scenario1")
    public String rigA3Scenario1() {

        for (int i = 0; i < 12; i++) {
            adventureDeck.add(players.get(0).hand.removeFirst());
            adventureDeck.add(players.get(1).hand.removeFirst());
            adventureDeck.add(players.get(2).hand.removeFirst());
            adventureDeck.add(players.get(3).hand.removeFirst());
        }

        addCard("Foe", 5, 2, players.getFirst());
        addCard("Foe", 10, 2, players.getFirst());
        addCard("Foe", 15, 2, players.getFirst());
        addCard("Dagger", 5, 1, players.getFirst());
        addCard("Horse", 10, 2, players.getFirst());
        addCard("Battle-Axe", 15, 2, players.getFirst());
        addCard("Lance", 20, 1, players.getFirst());

        addCard("Foe", 40, 1, players.get(1));
        addCard("Foe", 50, 1, players.get(1));
        addCard("Sword", 10, 3, players.get(1));
        addCard("Horse", 10, 2, players.get(1));
        addCard("Battle-Axe", 15, 2, players.get(1));
        addCard("Lance", 20, 2, players.get(1));
        addCard("Excalibur", 30, 1, players.get(1));

        addCard("Foe", 5, 4, players.get(2));
        addCard("Dagger", 5, 3, players.get(2));
        addCard("Horse", 10, 5, players.get(2));

        addCard("Foe", 50, 1, players.getLast());
        addCard("Foe", 70, 1, players.getLast());
        addCard("Sword", 10, 3, players.getLast());
        addCard("Horse", 10, 2, players.getLast());
        addCard("Battle-Axe", 15, 2, players.getLast());
        addCard("Lance", 20, 2, players.getLast());
        addCard("Excalibur", 30, 1, players.getLast());

        eventDeck.removeLast();
        eventDeck.removeLast();
        eventDeck.addLast(new QuestCard("Quest",3, 3));
        eventDeck.addLast(new QuestCard("Quest",4, 4));

        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();


        adventureDeck.addLast(new AdventureCard("Lance", 20));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 40));
        adventureDeck.addLast(new AdventureCard("Foe", 5));

        return printAfterRigging(35, 3);
    }

    @PutMapping("/rigA3Scenario2")
    public String rigA3Scenario2() {

        for (int i = 0; i < 12; i++) {
            adventureDeck.add(players.get(0).hand.removeFirst());
            adventureDeck.add(players.get(1).hand.removeFirst());
            adventureDeck.add(players.get(2).hand.removeFirst());
            adventureDeck.add(players.get(3).hand.removeFirst());
        }

        addCard("Foe", 5, 2, players.getFirst());
        addCard("Foe", 10, 2, players.getFirst());
        addCard("Foe", 15, 2, players.getFirst());
        addCard("Foe", 20, 2, players.getFirst());
        addCard("Dagger", 5, 4, players.getFirst());

        addCard("Foe", 25, 1, players.get(1));
        addCard("Foe", 30, 1, players.get(1));
        addCard("Sword", 10, 3, players.get(1));
        addCard("Horse", 10, 2, players.get(1));
        addCard("Battle-Axe", 15, 2, players.get(1));
        addCard("Lance", 20, 2, players.get(1));
        addCard("Excalibur", 30, 1, players.get(1));

        addCard("Foe", 25, 1, players.get(2));
        addCard("Foe", 30, 1, players.get(2));
        addCard("Sword", 10, 3, players.get(2));
        addCard("Horse", 10, 2, players.get(2));
        addCard("Battle-Axe", 15, 2, players.get(2));
        addCard("Lance", 20, 2, players.get(2));
        addCard("Excalibur", 30, 1, players.get(2));

        addCard("Foe", 25, 1, players.getLast());
        addCard("Foe", 30, 1, players.getLast());
        addCard("Foe", 70, 1, players.getLast());
        addCard("Sword", 10, 3, players.getLast());
        addCard("Horse", 10, 2, players.getLast());
        addCard("Battle-Axe", 15, 2, players.getLast());
        addCard("Lance", 20, 2, players.getLast());

        eventDeck.removeLast();
        eventDeck.removeLast();
        eventDeck.removeLast();
        eventDeck.removeLast();
        eventDeck.removeLast();
        eventDeck.addLast(new QuestCard("Quest",3, 3));
        eventDeck.addLast(new EventCard("Queen's Favor", "The player who draws this card immediately draws 2 adventure cards."));
        eventDeck.addLast(new EventCard("Prosperity", "All players immediately draw 2 adventure cards."));
        eventDeck.addLast(new EventCard("Plague", "The player who draws this card immediately loses 2 shields."));
        eventDeck.addLast(new QuestCard("Quest",4, 4));

        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();

        adventureDeck.addLast(new AdventureCard("Foe", 35));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 50));
        adventureDeck.addLast(new AdventureCard("Foe", 40));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 50));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 30));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 40));
        adventureDeck.addLast(new AdventureCard("Battle-Axe", 15));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 25));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 20));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 5));

        return printAfterRigging(46, 6);
    }

    @PutMapping("/rigA3Scenario3")
    public String rigA3Scenario3() {

        for (int i = 0; i < 12; i++) {
            adventureDeck.add(players.get(0).hand.removeFirst());
            adventureDeck.add(players.get(1).hand.removeFirst());
            adventureDeck.add(players.get(2).hand.removeFirst());
            adventureDeck.add(players.get(3).hand.removeFirst());
        }

        addCard("Foe", 50, 1, players.getFirst());
        addCard("Foe", 70, 1, players.getFirst());
        addCard("Dagger", 5, 2, players.getFirst());
        addCard("Sword", 10, 2, players.getFirst());
        addCard("Horse", 10, 2, players.getFirst());
        addCard("Battle-Axe", 15, 2, players.getFirst());
        addCard("Lance", 20, 2, players.getFirst());

        addCard("Foe", 5, 2, players.get(1));
        addCard("Foe", 10, 1, players.get(1));
        addCard("Foe", 15, 2, players.get(1));
        addCard("Foe", 20, 2, players.get(1));
        addCard("Foe", 25, 1, players.get(1));
        addCard("Foe", 30, 2, players.get(1));
        addCard("Foe", 40, 1, players.get(1));
        addCard("Excalibur", 30, 1, players.get(1));

        addCard("Foe", 5, 2, players.get(2));
        addCard("Foe", 10, 1, players.get(2));
        addCard("Foe", 15, 2, players.get(2));
        addCard("Foe", 20, 2, players.get(2));
        addCard("Foe", 25, 2, players.get(2));
        addCard("Foe", 30, 1, players.get(2));
        addCard("Foe", 40, 1, players.get(2));
        addCard("Lance", 20, 1, players.get(2));

        addCard("Foe", 5, 2, players.getLast());
        addCard("Foe", 10, 1, players.getLast());
        addCard("Foe", 15, 2, players.getLast());
        addCard("Foe", 20, 2, players.getLast());
        addCard("Foe", 25, 2, players.getLast());
        addCard("Foe", 30, 1, players.getLast());
        addCard("Foe", 50, 1, players.getLast());
        addCard("Excalibur", 30, 1, players.getLast());

        eventDeck.removeLast();
        eventDeck.addLast(new QuestCard("Quest",2, 2));

        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();
        adventureDeck.removeLast();


        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Sword", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Horse", 10));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Dagger", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 5));
        adventureDeck.addLast(new AdventureCard("Foe", 10));
        adventureDeck.addLast(new AdventureCard("Foe", 15));
        adventureDeck.addLast(new AdventureCard("Foe", 5));

        return printAfterRigging(17, 2);
    }

    private String printAfterRigging(int adv, int evnt) {
        String result = "";

        result += "RIGGED CARDS\n" + players.get(0) + "\n\n" + players.get(1) + "\n\n" + players.get(2) + "\n\n" + players.get(3) + "\n\n";

        result += "Last " + (adv - 1) +" cards of adventure deck: ";
        for (int i = 1; i < adv; i ++) {
            result += i + ". " + adventureDeck.get(adventureDeck.size() - i) + " ";
        }

        result +="\n\nLast " + (evnt - 1) + " cards of event deck: ";
        for (int i = 1; i < evnt; i ++) {
            result += i + ". " + eventDeck.get(eventDeck.size() - i) + " ";
        }

        result += "\n\nPlayers' hands rigged for testing";

        return result;
    }

    @DeleteMapping("resetGame")
    public String resetGame() {
        players.clear();
        adventureDeck.clear();
        adventureDiscard.clear();
        eventDeck.clear();
        eventDiscard.clear();
        quest.clear();
        attack.clear();
        eligibleSponsors.clear();
        attackers.clear();
        winners.clear();

        round = 0;
        currAttacker = 0;
        currCard = null;
        currSponsor = null;

        initializeDecks();
        initializeHands();

        return "Game Reset.";
    }

    @GetMapping("/start")
    public String start() {
        String result = "";
        currSponsor = null;

        if (currPlayer != null) {
            if (checkWinner()) {
                result += "WINNER" + ((winners.size() > 1) ? "S\n\n" : "\n\n");
                for (Player winner : winners) {
                    result += "Player " + winner.getId() + " wins!\n";
                }

                result += "\nPLAYER HANDS\n\n";

                for (Player p : players) {
                    result += p + "\n\n";
                }
                return result;
            }
        }

        currPlayer = players.get(round % playerNum);
        round++;
        drawEvent();

        return "CURRENT PLAYER\n\n" + currPlayer + "\n\n\n\n\nPlayer " + currPlayer.getId() + " has drawn a" + ((currCard instanceof QuestCard) ? " Quest: " : "n Event: ") + currCard + ".";
    }

    @GetMapping("/resolveEvent")
    public String resolveEvent() {
        String result = "EVENT: " + currCard.getName() + "\n\n";
        switch (currCard.getName()) {
            case "Plague":
                result += "Player " + currPlayer.getId() + ((currPlayer.shields > 2) ? " will lose 2 shields.\n\n" : " has 0 shields and will lose 0 shields.\n\n");
                currPlayer.shields = (currPlayer.shields > 2) ? currPlayer.shields - 2 : 0;
                result += currPlayer;
                break;
            case "Queen's Favor":
                currPlayer.draw += 2;
                result += drawAdventure(currPlayer.getId()-1, 2);
                sortHand(currPlayer.getId()-1);
                result += "\n\nPlayer " + currPlayer.getId() + " has drawn 2 cards" + ((currPlayer.getHandSize() > 12) ? " and needs to trim." : " and does not need to trim.");
                trimmer = (currPlayer.getHandSize() > 12)? currPlayer.getId()-1 : -1;
                break;
            case "Prosperity":
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).draw += 2;
                    result += drawAdventure(i, 2);
                    sortHand(i);

                    if (trimmer == -1 && players.get(i).getHandSize() > 12) {
                        trimmer = i;
                    }
                }
                result += "\n\nAll players have drawn 2" + ((trimmer > -1) ? (" and some need to trim.\n\nPlayer " + (trimmer+1) + " will begin trimming...") : " and no trimming is needed.");
                break;
        }

        return result;
    }

    @GetMapping("/resolveQuest")
    public String resolveQuest() {
        String result = "QUEST: " + currCard;
        eligibleSponsors = checkSponsorEligibility();

        if (eligibleSponsors.isEmpty()) {
            result += "\n\nNo one is eligible to sponsor the quest.\n";
        } else {
            result += "\n\nThe quest can be sponsored.\n";
        }
        return result;
    }

    @GetMapping("/askToSponsor")
    public String askToSponsor() {
        String result = "QUEST: " + currCard;

        if (eligibleSponsors.isEmpty()) {
            result = "\nNo one is eligible to sponsor the quest.\n";
        } else {
            result += "\n\n" + eligibleSponsors.get(0).toString() + "\n\nWould you like to sponser the quest?";
        }
        return result;
    }

    @PutMapping("/resolveSponsorChoice")
    public String resolveSponsorChoice(@RequestBody String input) {
        String result = "";
        String inp = input.replaceAll("\\s+","").toLowerCase();

        if (inp.equals("y") || inp.equals("yes")) {
            result += "Prepare to setup your stages...";
            currSponsor = eligibleSponsors.get(0);
            currSponsor.draw += ((QuestCard) currCard).stages;
            eligibleSponsors.clear();
        } else if (inp.equals("n") || inp.equals("no")) {
            result += "Okay.";
            eligibleSponsors.remove(0);
        } else {
            result += "Invalid Input. Try Again.\n";
        }

        return result;
    }

    @GetMapping("/initiateStageBuilding")
    public String initiateStageBuilding() {
        String result = currSponsor.toString() + "\n\nQUEST: " + currCard + "\tNo. of Stages built: " + quest.size() + "/" + ((QuestCard) currCard).stages + "\n";

        String questSoFar = "";
        int count = 1, score = 0;
        for (ArrayList<Card> stage : quest) {
            questSoFar += "Stage " + count + ": ";
            for (int i = 0; i < stage.size(); i++) {
                score += ((AdventureCard) stage.get(i)).value;
                questSoFar += (i+1) + ". " + ((AdventureCard) stage.get(i)) + " ";
            }
            questSoFar += "\n\tValue = " + score + "\n";
            score = 0;
            count++;
        }

        if (quest.size() == ((QuestCard) currCard).stages) {
            questSoFar += "\nQuest building complete.";
            for (Player p : players) {
                if (p.getId() != currSponsor.getId()) {
                    attackers.add(p.getId() - 1);
                }
            }
        } else {
            questSoFar += "\nChoose cards for Stage " + count + " below (must contain at least 1 foe and exceed the total value of the previous stage).";
        }

        return result + questSoFar;
    }

    @PutMapping("/buildStage")
    public String buildStage(@RequestBody String input) {
        boolean foeAdded = false;
        int prevScore = 0, currScore = 0;
        String result;
        String[] inpArr = input.replaceAll("\\s","").split(",");
        int[] intInp = new int[inpArr.length];
        ArrayList<Card> stage = new ArrayList<>();

        for (int i = 0; i < inpArr.length; i++) {
            intInp[i] = Integer.parseInt(inpArr[i]) - 1;
        }

        Arrays.sort(intInp);

        if (!quest.isEmpty()) {
            for (Card c : quest.get(quest.size() - 1)) {
                prevScore += ((AdventureCard) c).value;
            }
        }

        for (int i = intInp.length - 1; i >= 0; i--) {
            foeAdded = currSponsor.hand.get(intInp[i]).getName().equalsIgnoreCase("Foe") || foeAdded;
            currScore += ((AdventureCard) currSponsor.hand.get(intInp[i])).value;
            stage.addFirst(currSponsor.hand.get(intInp[i]));
            adventureDiscard.addLast(currSponsor.hand.remove(intInp[i]));
        }

        if (!foeAdded || currScore < prevScore) {
            result = "Invalid Stage. Try Again.";

            for (int i = 0; i < stage.size(); i++) {
                currSponsor.hand.add(stage.get(i));
                adventureDiscard.removeLast();
            }
            sortHand(currSponsor.getId()-1);
        } else {
            result = "Stage built successfully.\n\nCards: ";
            int stageVal = 0;
            for (int i = 0; i < stage.size(); i++) {
                result += (i+1) + ". " + stage.get(i) + " ";
                stageVal += ((AdventureCard) stage.get(i)).value;
            }
            result += "\n\tValue: " + stageVal;
            quest.add(stage);
            currSponsor.draw += intInp.length;
        }

        return result;
    }

    @GetMapping("/askAttacker")
    public String askAttacker() {
        String result = "QUEST: " + currCard + "\t\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";

        if (currAttacker == -1) {
            currAttacker++;
        }


        while (true) {
            if (attackers.isEmpty()) {
                result += "No attackers left.";
                break;
            }

            if (attackers.contains(currAttacker)) {
                result += players.get(currAttacker) + "\n\nWould you like to attack this stage?";
                break;
            } else {
                currAttacker++;
                if (currAttacker >= players.size()) {
                    result += "All players have responded.";
                    currAttacker = -1;
                    break;
                }
            }

        }

        return result;
    }

    @PutMapping("/resolveAttackerResponse")
    public String resolveAttackerResponse(@RequestBody String input) {
        String result = "";
        String inp = input.replaceAll("\\s+","").toLowerCase();

        if (inp.equals("y") || inp.equals("yes")) {
            result += "Drawing...\n";
            players.get(currAttacker).draw += 1;
            result += drawAdventure(currAttacker, 1);
            sortHand(currAttacker);
            if (players.get(currAttacker).getHandSize() > 12) {
                trimmer = currAttacker;
            }
        } else if (inp.equals("n") || inp.equals("no")) {
            result += "Okay.";
            attackers.remove(currAttacker);
            currAttacker++;
        } else {
            result += "Invalid Input. Try Again.\n";
        }

        return result;
    }

    @GetMapping("/askForAttackChoices")
    public String askForAttackChoices() {
        String result = "QUEST: " + currCard + "\t\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
        result += "CURRENT PLAYER\n\n" + players.get(currAttacker) + "\n\nChoose the cards for your attack below (e.g. 1,2,3).";

        return result;
    }

    @PutMapping("/buildAttack")
    public String buildAttack(@RequestBody String input) {
        ArrayList<String> cardsAdded = new ArrayList<>();
        String result = "";
        input = input.replaceAll("\\s","");
        String[] inpArr = input.split(",");
        boolean valid = true;
        int[] intInp = new int[inpArr.length];
        ArrayList<Card> stage = new ArrayList<>();
        int inpArrLength;

        if (input.isEmpty()) {
            inpArrLength = 0;
        } else {
            for (int i = 0; i < inpArr.length; i++) {
                intInp[i] = Integer.parseInt(inpArr[i]) - 1;
            }

            Arrays.sort(intInp);
            inpArrLength = inpArr.length;
        }


        for (int i = inpArrLength - 1; i >= 0; i--) {
            valid = !(players.get(currAttacker).hand.get(intInp[i]).getName().equalsIgnoreCase("Foe") || cardsAdded.contains(players.get(currAttacker).hand.get(intInp[i]).getName())) && valid;

            cardsAdded.add(players.get(currAttacker).hand.get(intInp[i]).getName());
            attack.get(currAttacker+1).addFirst(players.get(currAttacker).hand.get(intInp[i]));
            adventureDiscard.addLast(players.get(currAttacker).hand.remove(intInp[i]));
        }


        if (!valid) {
            for (int i = 0; i < attack.get(currAttacker+1).size(); i++) {
                players.get(currAttacker).hand.add(attack.get(currAttacker+1).remove(i));
                adventureDiscard.removeLast();
                sortHand(currAttacker);
            }
            result += "Invalid Attack. Try Again.";
        } else {
            result += "QUEST: " + currCard + "\t\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
            result += "CURRENT PLAYER\n\n" + players.get(currAttacker) + "\n\nYOUR ATTACK: ";
            int attackVal = 0;
            for (int i = 0; i < attack.get(currAttacker+1).size(); i++) {
                attackVal += ((AdventureCard) attack.get(currAttacker+1).get(i)).value;
                result += (i+1) + ". " + attack.get(currAttacker+1).get(i) + " ";
            }
            result += "\n\tVALUE: " + attackVal;
            currAttacker++;
        }

        return  result;
    }

    @PutMapping("/resolveAttacks")
    public String resolveAttacks() {
        String result = "QUEST: " + currCard + "\t\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
        int questValue = 0;

        for (Card c : quest.get(0)) {
            questValue += ((AdventureCard) c).value;
        }

        for (int i = 0; i < attackers.size(); i++) {
            int playerValue = 0;
            for (Card c : attack.get(attackers.get(i) + 1)) {
                playerValue += ((AdventureCard) c).value;
            }

            if (playerValue >= questValue) {
                result += "\n\nPlayer " + (attackers.get(i) +1) + " succeeds their attack.\nShields: " + players.get(attackers.get(i)).shields + "\tHand Size: " + players.get(attackers.get(i)).getHandSize() + "\nHand: " + players.get(attackers.get(i)).printableHand();
                attack.get(attackers.get(i) + 1).clear();
            } else {
                result += "\n\nPlayer " + (attackers.get(i) + 1) + " fails their attack.\nShields: " + players.get(attackers.get(i)).shields + "\tHand Size: " + players.get(attackers.get(i)).getHandSize() + "\nHand: " + players.get(attackers.get(i)).printableHand();
                attack.get(attackers.get(i) + 1).clear();
                attackers.remove(i);
                i--;
            }
        }

        result += "\n\nStage " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + " attacks complete.\n\n";

        quest.remove(0);

        if (quest.isEmpty()) {
            result += "\n\nQUEST COMPLETE!!";
        }

        return result;
    }

    @GetMapping("/questReward")
    public String questReward() {
        String result = "QUEST: " + currCard + "\n\nRewarding quest winners...\n\n";

        if (attackers.isEmpty()) {
            result += "There are no winners...";
        }

        for (Integer p : attackers) {
            result += "Player " + (p + 1) + " completed the quest and is rewarded " + ((QuestCard) currCard).shields + " shields\n\n";
            players.get(p).shields += ((QuestCard) currCard).shields;
            result += players.get(p) + "\n\n";
        }

        attackers.clear();

        return result;
    }

    @GetMapping("/sponsorDraw")
    public String sponsorDraw() {
        String result = "SPONSOR\n\n" + currSponsor + "\n\nDrawing " + currSponsor.draw + " cards...";
        result += drawAdventure(currSponsor.getId() - 1, currSponsor.draw);
        sortHand(currSponsor.getId() - 1);

        if (currSponsor.getHandSize() > 12) {
            trimmer = currSponsor.getId() - 1;
        }

        return result;
    }

    @GetMapping("/fetchTrimmer")
    public String getTrimmer() {
        String result = ((currCard instanceof EventCard) ? "EVENT: " + currCard.getName() : "QUEST: " + currCard) + "\n\n";
        if (trimmer > -1) {
            result += players.get(trimmer) + "\n\nTrim " + (players.get(trimmer).getHandSize() - 12) + " card(s), type your choices for trimming below.";
        } else {
            result += "No trimming necessary.";
        }

        return result;
    }

    @PutMapping("/trim")
    public String trim(@RequestBody String input) {
        String result = ((currCard instanceof EventCard) ? "EVENT: " + currCard.getName() : "QUEST: " + currCard) + "\n\n";
        String[] inpArr = input.replaceAll("\\s","").split(",");
        int[] intInp = new int[inpArr.length];

        if (trimmer > -1) {
            for (int i = 0; i < inpArr.length; i++) {
                intInp[i] = Integer.parseInt(inpArr[i]) - 1;
            }

            Arrays.sort(intInp);

            for (int i = intInp.length - 1; i >= 0; i--) {
                adventureDiscard.add(players.get(trimmer).hand.remove(intInp[i]));
            }

            result +=  players.get(trimmer) + "\n\n";

            result += "Player " + (trimmer+1) + " finished trimming.";

            if (currSponsor != null && currSponsor.getId() == (trimmer + 1)) {
                result += "\n\nSponsor drawing and trimming complete.";
                trimmer = -1;
                return result;
            }
        }

        int count = 0;
        while(true) {
            if (trimmer != playerNum-1) {
                trimmer++;
            } else {
                trimmer = 0;
            }

            if (players.get(trimmer).getHandSize() > 12) {
                result += "\n\nPlayer " + (trimmer+1) + " needs to trim next.";
                break;
            } else if (count >= 4) {
                result += "\n\nAll players have completed necessary trimming.";
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

    private boolean checkWinner() {
        for (Player player : players) {
            if (player.shields >= 7) {
                winners.add(player);
            }
        }
        return !winners.isEmpty();
    }

    public ArrayList<Player> checkSponsorEligibility() {
        ArrayList<Player> eligibleSponsors = new ArrayList<>();
        QuestCard quest = (QuestCard) currCard;
        int currIndex = currPlayer.getId()-1;

        for (int i = currIndex; i < players.size(); i++) {
            int count = 0;
            for (Card j : players.get(i).hand) {
                if (j.getName().equals("Foe")) {
                    count++;
                }
            }
            if (count >= quest.stages) {
                eligibleSponsors.add(players.get(i));
            }
        }

        for (int i = 0; i < currIndex; i++) {
            int count = 0;
            for (Card j : players.get(i).hand) {
                if (j.getName().equals("Foe")) {
                    count++;
                }
            }
            if (count >= quest.stages) {
                eligibleSponsors.add(players.get(i));
            }
        }
//        for (Player player : players) {
//            int count = 0;
//            for (Card j : player.hand) {
//                if (j.getName().equals("Foe")) {
//                    count++;
//                }
//            }
//            if (count >= quest.stages) {
//                eligibleSponsors.add(player);
//            }
//        }

        return eligibleSponsors;
    }

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
        eventDiscard.add(eventDeck.remove(eventDeck.size()-1));
    }

    public String drawAdventure(int index, int num) {
        String result = "";
        if (players.get(index).draw <= 0) {
            return result;
        }
        for (int j = 0; j < num; j++) {
            if (getAdventureDeckSize() == 0) {
                refillDeck(adventureDeck, adventureDiscard);
            }
            result += "\nPlayer " + players.get(index).getId() + " draws a " + adventureDeck.get(adventureDeck.size() - 1);
            players.get(index).hand.add(adventureDeck.remove(adventureDeck.size()-1));
            players.get(index).draw--;
        }
        return result;
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

    private void addCard(String cardName, int value, int num, Player player) {
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
}

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
                result += "WINNER" + ((winners.size() > 1) ? "S\n" : "\n");
                for (Player winner : winners) {
                    result += winner;
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
                currPlayer.shields = (currPlayer.shields > 2) ? currPlayer.shields - 2 : 0;
                result += "Player " + currPlayer.getId() + " lost 2 shields.";
                break;
            case "Queen's Favor":
                currPlayer.draw += 2;
                drawAdventure(currPlayer.getId()-1, 2);
                sortHand(currPlayer.getId()-1);
                result += "Player " + currPlayer.getId() + " has drawn 2 cards" + ((currPlayer.getHandSize() > 12) ? " and needs to trim." : " and does not need to trim.");
                trimmer = (currPlayer.getHandSize() > 12)? currPlayer.getId()-1 : -1;
                break;
            case "Prosperity":
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).draw += 2;
                    drawAdventure(i, 2);
                    sortHand(i);

                    if (trimmer == -1 && players.get(i).getHandSize() > 12) {
                        trimmer = i;
                    }
                }
                result += "All players have drawn 2" + ((trimmer > -1) ? (" and some need to trim.\nPlayer " + (trimmer+1) + " will begin trimming...") : " and no trimming is needed.");
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
                questSoFar += (i+1) + ". " + ((AdventureCard) stage.get(i)).toString();
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
            result = "Stage built successfully.\nCards: ";
            for (int i = 0; i < stage.size(); i++) {
                result += (i+1) + ". " + stage.get(i).toString() + " ";
            }
            quest.add(stage);
            currSponsor.draw += intInp.length;
        }

        return result;
    }

    @GetMapping("/askAttacker")
    public String askAttacker() {
        String result = "QUEST: " + currCard + "\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";

        if (currAttacker == -1) {
            currAttacker++;
        }


        while (true) {
            if (attackers.isEmpty()) {
                result += "No attackers left.";
                break;
            }

            if (!(attackers.contains(currAttacker))) {
                System.out.println("currAtt = " + currAttacker + " bool = " + (attackers.contains(players.get(currAttacker))) + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                currAttacker++;
                continue;
            }

            if (currAttacker < players.size()) {
                result += players.get(currAttacker) + "Would you like to attack this stage?";
                break;
            } else {
                result += "All players have responded.";
                currAttacker = -1;
                break;
            }

        }

        return result;
    }

    @PutMapping("/resolveAttackerResponse")
    public String resolveAttackerResponse(@RequestBody String input) {
        String result = "";
        String inp = input.replaceAll("\\s+","").toLowerCase();

        if (inp.equals("y") || inp.equals("yes")) {
            result += "Drawing...";
            players.get(currAttacker).draw += 1;
            drawAdventure(currAttacker, 1);
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
        String result = "QUEST: " + currCard + "\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
        result += "CURRENT PLAYER\n\n" + players.get(currAttacker) + "\n\nChoose the cards for your attack below (e.g. 1,2,3).";

        return result;
    }

    @PutMapping("/buildAttack")
    public String buildAttack(@RequestBody String input) {
        ArrayList<String> cardsAdded = new ArrayList<>();
        String result = "";
        String[] inpArr = input.replaceAll("\\s","").split(",");
        boolean valid = true;
        int[] intInp = new int[inpArr.length];
        ArrayList<Card> stage = new ArrayList<>();

        for (int i = 0; i < inpArr.length; i++) {
            intInp[i] = Integer.parseInt(inpArr[i]) - 1;
        }

        Arrays.sort(intInp);

        for (int i = intInp.length - 1; i >= 0; i--) {
            valid = !(players.get(currAttacker).hand.get(intInp[i]).getName().equalsIgnoreCase("Foe") || cardsAdded.contains(players.get(currAttacker).hand.get(intInp[i]).getName())) && valid;
            attack.get(currAttacker+1).addFirst(players.get(currAttacker).hand.get(intInp[i]));
            adventureDiscard.addLast(players.get(currAttacker).hand.remove(intInp[i]));
            cardsAdded.add(players.get(currAttacker).hand.get(i).getName());
        }

        if (!valid) {
            for (int i = 0; i < attack.get(currAttacker+1).size(); i++) {
                players.get(currAttacker).hand.add(attack.get(currAttacker+1).remove(i));
                adventureDiscard.removeLast();
                sortHand(currAttacker);
            }
            result += "Invalid Attack. Try Again.";
        } else {
            result += "QUEST: " + currCard + "\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
            result += "CURRENT PLAYER\n\n" + players.get(currAttacker) + "\n\nYOUR ATTACK: ";
            for (int i = 0; i < attack.get(currAttacker+1).size(); i++) {
                result += (i+1) + ". " + attack.get(currAttacker+1).get(i) + " ";
            }
            currAttacker++;
        }

        return  result;
    }

    @PutMapping("/resolveAttacks")
    public String resolveAttacks() {
        String result = "QUEST: " + currCard + "\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\n";
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
                result += "Player " + (attackers.get(i) +1) + " succeeds their attack.\n";
                attack.get(attackers.get(i) + 1).clear();
            } else {
                result += "Player " + (attackers.get(i) + 1) + " fails their attack.\n";
                attack.get(attackers.get(i) + 1).clear();
                attackers.remove(i);
                i--;
            }
        }

        result += "Stage " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + " attacks complete.\n\n";

        quest.remove(0);

        if (quest.isEmpty()) {
            result += "QUEST COMPLETE!!";
        }

        return result;
    }

    @GetMapping("/questReward")
    public String questReward() {
        String result = "QUEST: " + currCard + "\tCURRENT STAGE: " + ((((QuestCard)currCard).stages - quest.size()) + 1) + "/" + ((QuestCard)currCard).stages + "\n\nRewarding quest winners...\n";

        if (attackers.isEmpty()) {
            result += "There are no winners...";
        }

        for (Integer p : attackers) {
            result += "Player " + (p + 1) + " completed the quest and is rewarded " + ((QuestCard) currCard).shields + " shields\n";
            players.get(p).shields += ((QuestCard) currCard).shields;
        }

        attackers.clear();

        return result;
    }

    @GetMapping("/sponsorDraw")
    public String sponsorDraw() {
        String result = "SPONSOR\n\n" + currSponsor + "\n\nDrawing " + currSponsor.draw + " cards...";
        drawAdventure(currSponsor.getId() - 1, currSponsor.draw);
        sortHand(currSponsor.getId() - 1);

        if (currSponsor.getHandSize() > 12) {
            trimmer = currSponsor.getId() - 1;
        }

        return result;
    }

    @GetMapping("/fetchTrimmer")
    public Player getTrimmer() {
        return players.get(trimmer);
    }

    @PutMapping("/trim")
    public String trim(@RequestBody String input) {
        String result = ((currCard instanceof EventCard) ? "EVENT: " + currCard.getName() : "QUEST: " + currCard) + "\n\n";
        String[] inpArr = input.replaceAll("\\s","").split(",");
        int[] intInp = new int[inpArr.length];

        for (int i = 0; i < inpArr.length; i++) {
            intInp[i] = Integer.parseInt(inpArr[i]) - 1;
        }

        Arrays.sort(intInp);

        for (int i = intInp.length - 1; i >= 0; i--) {
            adventureDiscard.add(players.get(trimmer).hand.remove(intInp[i]));
        }

        result += "Player " + (trimmer+1) + " finished trimming.";

        if (currSponsor != null && currSponsor.getId() == (trimmer + 1)) {
            result += "\nSponsor drawing and trimming complete.";
            return result;
        }

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

    public void drawAdventure(int index, int num) {
        if (players.get(index).draw <= 0) {
            return;
        }
        for (int j = 0; j < num; j++) {
            if (getAdventureDeckSize() == 0) {
                refillDeck(adventureDeck, adventureDiscard);
            }
            players.get(index).hand.add(adventureDeck.remove(adventureDeck.size()-1));
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

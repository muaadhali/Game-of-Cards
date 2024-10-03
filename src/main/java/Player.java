import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand = new ArrayList<>();
    private int id;
    public int shields;

    public Player(int id) {
        this.id = id;
    }

    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        String cards = "";
        for (int i = 0; i < getHandSize(); i++) {
            cards += (i+1) + ". " + hand.get(i) + " ";
        }
        return "Player: " + id + "\nHand: " + cards;
    }
}

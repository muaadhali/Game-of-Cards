import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand = new ArrayList<>();
    private int id;

    public Player(int id) {
        this.id = id;
    }

    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        return "Player: " + id + ", Hand: " + hand;
    }
}

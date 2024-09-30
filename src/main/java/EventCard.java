public class EventCard extends Card {
    public String name;
    public String effect;

    public EventCard(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String toString() {
        return "|Name: " + name + ", Effect: " + effect + "|";
    }
}


public class AdventureCard {
    public String name;
    public int value;

    public AdventureCard(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return "|Name: " + name + ", Value: " + value + "|";
    }
}

public class AdventureCard extends Card{
    public String name;
    public int value;

    public AdventureCard(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public String toString() {
        return "|Name: " + name + ", Value: " + value + "|";
    }
}

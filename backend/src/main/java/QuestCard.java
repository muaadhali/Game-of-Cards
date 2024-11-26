public class QuestCard extends Card {
    public String name;
    public int stages;
    public int shields;

    public QuestCard(String name, int stages, int shields) {
        this.name = name;
        this.stages = stages;
        this.shields = shields;
    }

    @Override
    public String getName() {
        return name;
    }

    public String toString() {
        return "|Stages: " + stages + ", Shields: " + shields + "|";
    }
}

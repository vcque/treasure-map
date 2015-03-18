package vcque.fizzbuzz.treasuremap.model;

public class Treasure extends Field {
    private int nbOfTreasures;

    public Treasure(int nbOfTreasures) {
        this.nbOfTreasures = nbOfTreasures;
    }

    @Override
    public boolean hasTreasure() {
        return nbOfTreasures > 0;
    }

    @Override
    public int collectTreasure() {
        nbOfTreasures--;
        return 1;
    }
}

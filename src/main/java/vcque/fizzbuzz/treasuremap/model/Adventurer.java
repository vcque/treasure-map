package vcque.fizzbuzz.treasuremap.model;

/**
 * An adventurer scavenging the map.
 * 
 * @author vcque
 *
 */
public class Adventurer {
    /** The way it is facing. */
    private Orientation orientation;
    /** Its position on the map. */
    private Position position;
    /** The money it has. */
    private int treasure = 0;
    /** The actions it intends to take. */
    private Instruction instruction = new Instruction();
    /** The adventurer name. */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getTreasure() {
        return treasure;
    }

    public void addTreasure(int treasure) {
        this.treasure += treasure;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
    
    @Override
    public String toString() {
        return String.format("%s : %s %s : %d$", name, position, orientation, treasure);
    }
}

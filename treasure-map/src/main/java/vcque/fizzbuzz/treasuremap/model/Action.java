package vcque.fizzbuzz.treasuremap.model;

import static java.lang.String.format;

/**
 * Represents an adventurer action.
 * @author vcque
 *
 */
public enum Action {
    
    /** turn left. */
    LEFT("G"),
    /** Turn right. */
    RIGHT("D"),
    /** Move to a given direction. */
    FORWARD("A"),
    /** Do nothing. */
    IDLE("S");

    private String label;
    
    private Action(String label) {
        this.label = label;
    }
    
    /**
     * Get the resulting position of an action according to the current position and orientation.
     * @param position The position before action.
     * @param orientation The orientation before action.
     * @return The position after action.
     */
    public Position positionOn(Position position, Orientation orientation) {
        if (this == FORWARD) return position.add(orientation.direction());
        else return position;
    }

    /**
     * Get the resulting orientation after an action according to the current orientation.
     * @param orientation The orientation before action.
     * @return The orientation after action.
     */
    public Orientation orientationOn(Orientation orientation) {
        if (this == LEFT) return orientation.left();
        if (this == RIGHT) return orientation.right();
        else return orientation;
    }

    /**
     * Parse a String into a label.
     * @param label
     * @return
     */
    public static Action fromString(String label) {
        if (label == null || label.isEmpty()) return null;
        
        for (Action action : values()) {
            if (label.equals(action.label)) return action;
        }
        throw new IllegalArgumentException(format("%s did not match any known action.", label));
    }
}

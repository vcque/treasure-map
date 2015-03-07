package vcque.fizzbuzz.treasuremap.model;

import static java.lang.String.format;

/**
 * Represents a cardinal orientation.
 * @author vcque
 *
 */
public enum Orientation {

    S("S", Position.Y),
    N("N", Position.Y.not()),
    E("E", Position.X),
    W("O", Position.X.not());
    
    static {
        order(N, E, S, W);
    }
    
    private final String label;
    private final Position direction;
    private Orientation left;
    private Orientation right;
    
    private Orientation(String label, Position direction) {
        this.label = label;
        this.direction = direction;
    }

    private static void order(Orientation... orients) {
        final int length = orients.length;
        for (int i = 1; i < length; i++) {
            link(orients[i - 1], orients[i]);
        }
        link(orients[length - 1], orients[0]);
    }

    private static void link(Orientation left, Orientation right) {
        left.right = right;
        right.left = left;
    }
    
    /**
     * 
     * @return The unit direction vector that points to this orientation.
     */
    public Position direction() {
        return direction;
    }
    
    /**
     * 
     * @return the orientation at the left.
     */
    public Orientation left() {
        return left;
    }

    
    /**
     * 
     * @return the orientation at the right.
     */
    public Orientation right() {
        return right;
    }
    
    @Override
    public String toString() {
        return label;
    }
    
    /**
     * Parse a String to its corresponding {@link Orientation}
     * @param label The String to parse
     * @return <code>null</code> if label is null or empty. <br> The corresponding {@link Orientation} otherwise.
     * @throws IllegalArgumentException when the provided string does not correspond to anything.
     */
    public static Orientation fromString(String label) {
        if (label == null || label.isEmpty()) return null;
        
        for (Orientation o : values()) {
            if (label.equals(o.label)) return o;
        }
        throw new IllegalArgumentException(format("%s did not match any known orientation.", label));
    } 
}

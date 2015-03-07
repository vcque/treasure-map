package vcque.fizzbuzz.treasuremap.model;

import java.util.Objects;

/**
 * Immutable value object representing a 2D position/vector on a discrete grid (with integers).
 * 
 * @author vcque
 *
 */
public final class Position {

    /** The zero vector (0, 0). */
    public static final Position ZERO = new Position(0, 0);
    /** The x vector (1, 0). */
    public static final Position X = new Position(1, 0);
    /** The y vector (0, 1). */
    public static final Position Y = new Position(0, 1);

    public final int x;
    public final int y;

    /**
     * Construct a {@link Position} with given coordinates.
     * @param x horizontal position on the grid.
     * @param y vertical position on the grid.
     */
    public static Position of(int x, int y) {
        // might implement a cache for often used objects.
        return new Position(x, y);
    }

    /**
     * Do prefer using {@link #of(int, int)} for instantiating {@link Position}.
     * @param x horizontal position on the grid.
     * @param y vertical position on the grid.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position other) {
        return of(x + other.x, y + other.y);
    }

    public Position not() {
        return of(-x, -y);
    }

    public Position sub(Position other) {
        return of(x - other.x, y - other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Position other = (Position) obj;
        // Can do this because of primitives
        return x == other.x && y == other.y;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

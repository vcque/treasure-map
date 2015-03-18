package vcque.fizzbuzz.treasuremap.model;

/**
 * The treasure field that adventurers scavenge for loots and profit.
 * 
 * @author vcque
 *
 */
public class TreasureMap {
    
    /** The horizontal size. */
    private final int width;
    /** The vertical size. */
    private final int height;
    
    private final Field[][] backend;
    
    /**
     * Default constructor.
     * @param x the horizontal size.
     * @param y the vertical size.
     */
    public TreasureMap(int x, int y, Field[][] map) {
        this.width = x;
        this.height = y;
        backend = map;
        check();
    }

    /** Check that internal data is valid. */
    private void check() {
        if (backend.length != width) {
            throw new IllegalArgumentException("The map has not the right x size.");
        }
        for (Field[] line : backend) {
            if (line.length != height) {
                throw new IllegalArgumentException("The map has not the right y size.");
            }
        }
    }

    /**
     * Collect treasure on a given tile.
     * @param pos position
     * @return The number of treasure collected.
     * @throws IllegalArgumentException if there's no treasure to collect.
     */
    public int collect(Position pos) {
        if (hasTreasure(pos)) {
            return get(pos).collectTreasure();
        }
        throw new IllegalArgumentException(String.format("Nothing to collect on : %s ", pos));
    }

    public boolean isValid(Position pos) {
        return get(pos).isValid();
    }

    public boolean hasTreasure(Position pos) {
        return get(pos).hasTreasure();
    }

    /**
     * Get a tile state with bound check.
     */
    private Field get(Position pos) {
        if (pos.x < 1) return new Mountain();
        if (pos.y < 1) return new Mountain();
        if (pos.x > width) return new Mountain();
        if (pos.y > height) return new Mountain();
        return backend[pos.x - 1][pos.y - 1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

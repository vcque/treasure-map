package vcque.fizzbuzz.treasuremap.model;

/**
 * The treasure field that adventurers scavenge for loots and profit.
 * 
 * @author vcque
 *
 */
public class TreasureMap {

    public static byte MOUNTAIN = -1;
    
    /** The horizontal size. */
    private final int width;
    /** The vertical size. */
    private final int height;
    
    private final byte[][] backend;
    
    /**
     * Default constructor.
     * @param x the horizontal size.
     * @param y the vertical size.
     */
    public TreasureMap(int x, int y, byte[][] map) {
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
        for (byte[] line : backend) {
            if (line.length != height) {
                throw new IllegalArgumentException("The map has not the right y size.");
            }
        }
    }

    /**
     * 
     * @param pos
     * @return <code>true</code> if there's treasure on the given tile.
     */
    public boolean hasTreasure(Position pos) {
        return get(pos) > 0;
    }
    
    /**
     * Collect treasure on a given tile.
     * @param pos
     * @return The number of treasure collected.
     * @throws IllegalArgumentException if there's no treasure to collect.
     */
    public int collect(Position pos) {
        if (hasTreasure(pos)) {
            backend[pos.x - 1][pos.y - 1]--;
            return 1;
        }
        throw new IllegalArgumentException(String.format("Nothing to collect on : %s ", pos));
    }
    
    public boolean isValid(Position pos) {
        return get(pos) != MOUNTAIN;
    }
    
    /**
     * Get a tile state with bound check.
     * @param pos
     * @return
     */
    private byte get(Position pos) {
        if (pos.x < 1) return MOUNTAIN;
        if (pos.y < 1) return MOUNTAIN;
        if (pos.x > width) return MOUNTAIN;
        if (pos.y > height) return MOUNTAIN;
        return backend[pos.x - 1][pos.y - 1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

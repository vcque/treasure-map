package vcque.fizzbuzz.treasuremap.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static vcque.fizzbuzz.treasuremap.parser.Utils.toStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import vcque.fizzbuzz.treasuremap.model.Position;
import vcque.fizzbuzz.treasuremap.model.TreasureMap;
import vcque.fizzbuzz.treasuremap.parser.exception.ParseException;

/**
 * Treasure parser unit tests.
 * @author vcque
 *
 */
public class TreasureMapParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private final TreasureMapParser parser = new TreasureMapParser();

    /** Simple test of an empty map. */
    @Test
    public void simple() throws ParseException {
        final TreasureMap map = parser.parse(toStream("C 4 5"));
        assertNotNull(map);
        assertEquals(map.getWidth(), 4);
        assertEquals(map.getHeight(), 5);
    }

    /** Simple test with mountains. */
    @Test
    public void mountain() throws ParseException {
        String input = "";
        input += "C 4 5\n";
        input += "M 2-2\n";
        input += "M 3-3\n";
        final TreasureMap map = parser.parse(toStream(input));
        
        assertNotNull(map);
        assertTrue(map.isValid(Position.of(1, 1)));
        assertFalse(map.isValid(Position.of(2, 2)));
        assertFalse(map.isValid(Position.of(3, 3)));
    }

    /** Simple test with treasure. */
    @Test
    public void treasure() throws ParseException {
        String input = "";
        input += "C 4 5\n";
        input += "T 2-2 1\n";
        input += "T 3-3 4\n";
        final TreasureMap map = parser.parse(toStream(input));
        
        assertNotNull(map);
        assertFalse(map.hasTreasure(Position.of(1, 1)));
        assertTrue(map.hasTreasure(Position.of(2, 2)));
        assertTrue(map.hasTreasure(Position.of(3, 3)));
        
        assertEquals(1, map.collect(Position.of(2, 2)));
        assertFalse(map.hasTreasure(Position.of(2, 2)));
        
        assertEquals(1, map.collect(Position.of(3, 3)));
        assertEquals(1, map.collect(Position.of(3, 3)));
        assertEquals(1, map.collect(Position.of(3, 3)));
        assertEquals(1, map.collect(Position.of(3, 3)));
        assertFalse(map.hasTreasure(Position.of(3, 3)));
    }
    
}

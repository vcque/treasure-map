package vcque.fizzbuzz.treasuremap.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static vcque.fizzbuzz.treasuremap.parser.Utils.toStream;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import vcque.fizzbuzz.treasuremap.model.Action;
import vcque.fizzbuzz.treasuremap.model.Adventurer;
import vcque.fizzbuzz.treasuremap.model.Orientation;
import vcque.fizzbuzz.treasuremap.model.Position;
import vcque.fizzbuzz.treasuremap.parser.exception.ParseException;

/**
 * Adventurer parser unit tests.
 * @author vcque
 *
 */
public class AdventurerParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private final AdventurerParser parser = new AdventurerParser();

    /** Simple test of a unique adventurer. */
    @Test
    public void simple() throws ParseException {
        final List<Adventurer> adventurers = parser.parse(toStream("Louis 1-1 E AGDAGD Linon"));
        assertNotNull(adventurers);
        assertEquals(1, adventurers.size());
        
        final Adventurer adv = adventurers.get(0);
        assertEquals(adv.getName(), "Louis");
        assertEquals(adv.getPosition(), Position.of(1, 1));
        assertEquals(adv.getOrientation(), Orientation.E);
        assertEquals(adv.getInstruction().next(), Action.FORWARD);
        assertEquals(adv.getInstruction().next(), Action.LEFT);
        assertEquals(adv.getInstruction().next(), Action.RIGHT);
        assertEquals(adv.getInstruction().next(), Action.FORWARD);
        assertEquals(adv.getInstruction().next(), Action.LEFT);
        assertEquals(adv.getInstruction().next(), Action.RIGHT);
        assertTrue(adv.getInstruction().done());
        assertEquals(adv.getInstruction().next(), Action.IDLE);
    }

    /** Simple test with many adventurer. */
    @Test
    public void multiple() throws ParseException {
        final InputStream in = getClass().getClassLoader().getResourceAsStream("adventurer/multiple");
        final List<Adventurer> adventurers = parser.parse(in);
        assertNotNull(adventurers);
        assertEquals(3, adventurers.size());
        assertEquals(adventurers.get(2).getName(), "Jacques");
    }

    
    /** Empty file test. */
    @Test
    public void empty() throws ParseException {
        final List<Adventurer> adventurers = parser.parse(toStream(""));
        assertNotNull(adventurers);
        assertEquals(0, adventurers.size());
    }

    /** Garbage data test. */
    @Test
    public void garbage() throws ParseException {
        exception.expect(ParseException.class);
        parser.parse(toStream("qs6f57sd6g4gd"));
    }
    
    /** Non-existing file test. */
    @Test
    public void noFile() throws ParseException {
        exception.expect(ParseException.class);
        parser.parse(new File("sdqfsgb"));
    }
}

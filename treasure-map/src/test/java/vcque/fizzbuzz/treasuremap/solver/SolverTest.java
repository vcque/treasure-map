package vcque.fizzbuzz.treasuremap.solver;

import static org.junit.Assert.assertEquals;
import static vcque.fizzbuzz.treasuremap.parser.Utils.toStream;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import vcque.fizzbuzz.treasuremap.model.Adventurer;
import vcque.fizzbuzz.treasuremap.model.Orientation;
import vcque.fizzbuzz.treasuremap.model.Position;
import vcque.fizzbuzz.treasuremap.model.Scene;
import vcque.fizzbuzz.treasuremap.model.TreasureMap;
import vcque.fizzbuzz.treasuremap.parser.AdventurerParser;
import vcque.fizzbuzz.treasuremap.parser.TreasureMapParser;
import vcque.fizzbuzz.treasuremap.parser.exception.ParseException;

/**
 * Test the solver.
 * @author vcque
 *
 */
public class SolverTest {

    private final Solver solver = new Solver();
    private final AdventurerParser advParser = new AdventurerParser();
    private final TreasureMapParser mapParser = new TreasureMapParser();

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    /** Simple solve. */
    @Test
    public void simple() throws ParseException {
        final Scene scene = setUp("C 4 5", "Louis 1-1 E AADAAGGA Linon");
        solver.solve(scene);
        
        final Adventurer adv = scene.getAdventurers().get(0);
        assertEquals(adv.getPosition(), Position.of(3, 2));
        assertEquals(adv.getOrientation(), Orientation.N);
    }

    @Test
    public void oobAdventurer() throws ParseException {
        exception.expect(IllegalStateException.class);
        final Scene scene = setUp("C 4 5", "Louis 10-10 E AA Linon");
        solver.solve(scene);
    }

    /** Map with an adventurer on a mountain. */
    @Test
    public void mountainAdventurer() throws ParseException {
        exception.expect(IllegalStateException.class);
        
        String map = "";
        map += "C 4 5\n";
        map += "M 3-2\n";
        final Scene scene = setUp(map, "Louis 3-2 E AA Linon");
        solver.solve(scene);
    }

    /** Map where the adventurer literally sits on a pile of gold. */
    @Test
    public void easyTreasure() throws ParseException {
        String map = "";
        map += "C 4 5\n";
        map += "T 3-2 10\n";
        final Scene scene = setUp(map, "Louis 3-2 E AA Linon");
        solver.solve(scene);
        assertEquals(scene.getAdventurers().get(0).getTreasure(), 10);
    }
    
    /** Map where adventurers are blocked. */
    @Test
    public void blocking() throws ParseException {
        String map = "";
        map += "C 8 5\n";
        map += "M 4-4\n";
        
        String adv = "";
        adv += "A 3-2 E AAAAAAAAA A\n"; // Blocked by another adventurer
        adv += "B 8-5 S AAAAAAAAA B\n"; // Blocked by map edge
        adv += "C 3-4 E AAAAAAAAA C\n"; // Blocked by mountain
        adv += "Blocker 4-2 E G Baddy"; // Blocker
        final Scene scene = setUp(map, adv);
        solver.solve(scene);
        assertEquals(scene.getAdventurers().get(0).getPosition(), Position.of(3, 2));
        assertEquals(scene.getAdventurers().get(1).getPosition(), Position.of(8, 5));
        assertEquals(scene.getAdventurers().get(2).getPosition(), Position.of(3, 4));
    }
    
    /**
     * Build a scene from two Strings to parse.
     * @param mapStr The map string, parsed with {@link TreasureMapParser}.
     * @param advStr The adventurers string, parsed with {@link AdventurerParser}.
     * @return A built scene.
     */
    private Scene setUp(String mapStr, String advStr) throws ParseException {
        final TreasureMap map = mapParser.parse(toStream(mapStr));
        final List<Adventurer> advs = advParser.parse(toStream(advStr));
        return Scene.of(map, advs);
    }
}

package vcque.fizzbuzz.treasuremap.solver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import vcque.fizzbuzz.treasuremap.model.Action;
import vcque.fizzbuzz.treasuremap.model.Adventurer;
import vcque.fizzbuzz.treasuremap.model.Orientation;
import vcque.fizzbuzz.treasuremap.model.Position;
import vcque.fizzbuzz.treasuremap.model.Scene;
import vcque.fizzbuzz.treasuremap.model.TreasureMap;

/**
 * Thread-safe solver of treasure map problems. <br>
 * Do mind that the given solution is dependent on the order of adventurer in the queue.
 * 
 * @author vcque
 *
 */
public class Solver {

    /**
     * Solve a scene, mutating it to a state where adventurers have no instruction left.
     * 
     * @param scene
     */
    public void solve(Scene scene) {
        checkScene(scene);
        final List<Adventurer> adventurers = scene.getAdventurers();

        final Set<Position> occuped = adventurers.stream().map(adv -> adv.getPosition())
                .collect(Collectors.toCollection(() -> new HashSet<Position>(adventurers.size())));

        while (anyoneMoves(adventurers)) {
            adventurers.parallelStream().forEach(adv -> act(adv, occuped, scene.getMap()));
        }
    }

    /**
     * Validates data before solving the scene.
     * @param scene The scene to validate.
     */
    private void checkScene(Scene scene) {
        if (scene == null) throw new NullPointerException("The scene can't be null.");
        if (scene.getMap() == null) throw new NullPointerException("The scene can't have a null map.");
        if (scene.getAdventurers() == null) throw new NullPointerException("The scene can't have a null adventurer list.");
        if (scene.getAdventurers().contains(null)) throw new NullPointerException("No null adventurer allowed.");
        
        final Set<Position> occuped = new HashSet<>(scene.getAdventurers().size());
        for (Adventurer adv : scene.getAdventurers()) {
            final Position pos = adv.getPosition();
            if (pos == null) throw new NullPointerException("No null position allowed for an adventurer.");
            if (occuped.contains(pos)) throw new IllegalStateException("At least 2 adventurers have the same starting position.");
            if (!scene.getMap().isValid(pos)) throw new IllegalStateException("An adventurer is not on a valid position (OOB or mountain)");
            occuped.add(pos);
        }
    }

    /**
     * 
     * @param adventurers
     *            The moving adventurers.
     * @return <code>true</code> if anyone has still actions to do.
     */
    private boolean anyoneMoves(final List<Adventurer> adventurers) {
        return adventurers.stream().anyMatch(adv -> !adv.getInstruction().done());
    }

    /**
     * The action of the adventurer.
     * 
     * @param adventurer
     *            The adventurer we are computing
     * @param occuped
     *            The occupied tiles. It is used as parameter to removes the need of state in the solver and make it
     *            thread-safe.
     * @param map
     *            The treasure map.
     */
    private void act(Adventurer adventurer, Set<Position> occuped, TreasureMap map) {
        final Position currentPosition = adventurer.getPosition();
        if (map.hasTreasure(currentPosition)) {
            adventurer.addTreasure(map.collect(currentPosition));
            return;
        }

        final Action action = adventurer.getInstruction().next();
        final Position nextPosition = action.positionOn(adventurer.getPosition(), adventurer.getOrientation());
        final Orientation nextOrientation = action.orientationOn(adventurer.getOrientation());
        adventurer.setOrientation(nextOrientation);

        if (map.isValid(nextPosition) && !occuped.contains(nextPosition)) {
            occuped.remove(currentPosition);
            occuped.add(nextPosition);
            adventurer.setPosition(nextPosition);
        }
    }

}

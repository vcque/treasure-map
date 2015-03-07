package vcque.fizzbuzz.treasuremap.model;

import java.util.LinkedList;

/**
 * An {@link Adventurer} intended actions.
 * 
 * @author vcque
 *
 */
public class Instruction {

    private LinkedList<Action> instruction = new LinkedList<>();

    /** Add an action to the adventurer's intents. */
    public void append(Action action) {
        if (action == null) throw new NullPointerException("No null allowed.");
        instruction.add(action);
    }

    /** consume the next adventurer's action. */
    public Action next() {
        if (instruction.isEmpty())
            return Action.IDLE;
        else
            return instruction.removeFirst();
    }

    /**
     * 
     * @return <code>true</code> if the adventurer has no action left.
     */
    public boolean done() {
        return instruction.isEmpty();
    }
}

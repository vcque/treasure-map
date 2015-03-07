package vcque.fizzbuzz.treasuremap.parser.exception;

import java.io.Serializable;

/**
 * Exception while parsing files for treasure-map.
 * @author vcque
 *
 */
public class ParseException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor with message.
     * @param msg Message to show.
     */
    public ParseException(String msg) {
        super(msg);
    }
    
    public ParseException(String msg, Throwable t) {
        super(msg, t);
    }
}

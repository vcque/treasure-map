package vcque.fizzbuzz.treasuremap.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vcque.fizzbuzz.treasuremap.model.Action;
import vcque.fizzbuzz.treasuremap.model.Adventurer;
import vcque.fizzbuzz.treasuremap.model.Instruction;
import vcque.fizzbuzz.treasuremap.model.Orientation;
import vcque.fizzbuzz.treasuremap.model.Position;
import vcque.fizzbuzz.treasuremap.parser.exception.ParseException;

/**
 * Parse a
 * @author vcque
 *
 */
public class AdventurerParser {

    private final static Pattern ADVENTURER_PATTERN = Pattern.compile("^(\\w+) (\\d+)-(\\d+) ([NOES]) ([ADG]+) (\\w+)$");

    /**
     * Parse a stream to a list of adventurer.
     * @param in The stream to parse.
     * @return The list of adventurers described in the stream.
     * @throws ParseException When an error occurs.
     */
    public List<Adventurer> parse(InputStream in) throws ParseException {
        final List<Adventurer> result = new ArrayList<>();
        final Scanner scanner = new Scanner(new BufferedInputStream(in));

        try {
            while (scanner.hasNextLine()) {
                result.add(parseAdventurer(scanner.nextLine()));
            }
            scanner.close();
        } catch (Throwable t) {
            throw new ParseException("Error while parsing.", t);
        } finally {
            scanner.close();
        }
        return result;
    }

    /**
     * Parse a stream to a list of adventurer.
     * @param in The stream to parse.
     * @return The list of adventurers described in the stream.
     * @throws ParseException When an error occurs.
     */
    public List<Adventurer> parse(File file) throws ParseException {
        try {
            return parse(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ParseException("Could not find file.", e);
        }
    }
    
    private Adventurer parseAdventurer(String line) {
        final Matcher matcher = ADVENTURER_PATTERN.matcher(line);
        matcher.find();
        final Adventurer adv = new Adventurer();
        adv.setName(matcher.group(1));
        final int x = Integer.parseInt(matcher.group(2));
        final int y = Integer.parseInt(matcher.group(3));
        adv.setPosition(Position.of(x, y));
        adv.setOrientation(Orientation.fromString(matcher.group(4)));

        final Instruction instruction = new Instruction();
        for (Character c : matcher.group(5).toCharArray()) {
            instruction.append(Action.fromString(c.toString()));
        }
        adv.setInstruction(instruction);
        return adv;
    }
}

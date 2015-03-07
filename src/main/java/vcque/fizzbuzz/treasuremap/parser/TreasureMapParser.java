package vcque.fizzbuzz.treasuremap.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vcque.fizzbuzz.treasuremap.model.TreasureMap;
import vcque.fizzbuzz.treasuremap.parser.exception.ParseException;

/**
 * Parses a file into an {@link TreasureMap}.
 * 
 * @author vcque
 *
 */
public class TreasureMapParser {

    private final static Pattern MAP_PATTERN = Pattern.compile("^C (\\d+) (\\d+)$");
    private final static Pattern TREASURE_PATTERN = Pattern.compile("^T (\\d+)-(\\d+) (\\d+)$");
    private final static Pattern MOUNTAIN_PATTERN = Pattern.compile("^M (\\d+)-(\\d+)$");

    public TreasureMap parse(InputStream in) throws ParseException {
        final Scanner scanner = new Scanner(new BufferedInputStream(in));
        byte [][] data = null;
        try {
            data = parseMap(scanner.nextLine());
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.startsWith("T")) {
                    parseTreasure(data, line);
                } else if (line.startsWith("M")) {
                    parseMountain(data, line);
                } else {
                    scanner.close();
                    throw new ParseException("Could not recognize line type.");
                }
            }
            scanner.close();
        } catch (ParseException e) {
            throw e;
        } catch (Throwable t) {
            throw new ParseException("Error while parsing.", t);
        } finally {
            scanner.close();
        }

        return new TreasureMap(data.length, data[0].length, data);
    }

    private void parseMountain(byte[][] data, String line) {
        final Matcher matcher = MOUNTAIN_PATTERN.matcher(line);
        matcher.find();
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        data[x - 1][y - 1] = TreasureMap.MOUNTAIN;
    }

    private void parseTreasure(byte[][] data, String line) {
        final Matcher matcher = TREASURE_PATTERN.matcher(line);
        matcher.find();
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        final byte value = Byte.parseByte(matcher.group(3));
        data[x - 1][y - 1] = value;
    }

    private byte[][] parseMap(String line) {
        final Matcher matcher = MAP_PATTERN.matcher(line);
        matcher.find();
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        return new byte[x][y];
    }

}

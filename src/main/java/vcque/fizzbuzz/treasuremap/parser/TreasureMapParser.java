package vcque.fizzbuzz.treasuremap.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vcque.fizzbuzz.treasuremap.model.Field;
import vcque.fizzbuzz.treasuremap.model.Mountain;
import vcque.fizzbuzz.treasuremap.model.Treasure;
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
        Field [][] data;
        try (Scanner scanner = new Scanner(new BufferedInputStream(in))) {
            data = parseMap(scanner.nextLine());
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.startsWith("T")) {
                    parseTreasure(data, line);
                } else if (line.startsWith("M")) {
                    parseMountain(data, line);
                } else {
                    throw new ParseException("Could not recognize line type.");
                }
            }
        } catch (Throwable t) {
            throw new ParseException("Error while parsing.", t);
        }

        return new TreasureMap(data.length, data[0].length, data);
    }

    private void parseMountain(Field[][] data, String line) {
        final Matcher matcher = MOUNTAIN_PATTERN.matcher(line);
        if (!matcher.find()) return;
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        data[x - 1][y - 1] = new Mountain();
    }

    private void parseTreasure(Field[][] data, String line) {
        final Matcher matcher = TREASURE_PATTERN.matcher(line);
        if (!matcher.find()) return;
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        final int value = Integer.parseInt(matcher.group(3));
        data[x - 1][y - 1] = new Treasure(value);
    }

    private Field[][] parseMap(String line) {
        final Matcher matcher = MAP_PATTERN.matcher(line);
        if (!matcher.find()) return new Field[0][0];
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        Field[][] map = new Field[x][y];
        for (int i = 0; i<x; i++) {
            for (int j = 0; j<y; j++) {
                map[i][j] = new Field();
            }
        }
        return map;
    }

}

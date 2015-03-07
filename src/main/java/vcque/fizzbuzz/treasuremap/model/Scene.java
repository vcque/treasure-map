package vcque.fizzbuzz.treasuremap.model;

import java.util.List;

/**
 * A Treasure map scene that represents a complete problem.
 * 
 * @author vcque
 *
 */
public class Scene {

    public static Scene of(TreasureMap map, List<Adventurer> advs) {
        final Scene result = new Scene();
        result.setMap(map);
        result.setAdventurers(advs);
        return result;
    }
    
    private TreasureMap map;
    private List<Adventurer> adventurers;

    public TreasureMap getMap() {
        return map;
    }

    public void setMap(TreasureMap map) {
        this.map = map;
    }

    public List<Adventurer> getAdventurers() {
        return adventurers;
    }

    public void setAdventurers(List<Adventurer> adventurers) {
        this.adventurers = adventurers;
    }
}

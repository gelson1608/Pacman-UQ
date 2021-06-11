package pacman.hunter;

import pacman.util.Direction;
import pacman.util.Position;

//A Speedy hunter that has a special ability that allows the hunter to travel
// twice as fast
public class Speedy extends Hunter {

    /**
     * Creates a Speedy Hunter with its special ability.
     */

    public Speedy() {
        super();
        //special ability
    }

    /**
     * Creates a Speedy Hunter by copying the internal state of another hunter.
     * @param original hunter to copy from.
     */

    public Speedy(Hunter original) {
        super(original);
    }
}
